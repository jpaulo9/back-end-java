package com.rest.api.services;


import com.rest.api.controller.PersonController;
import com.rest.api.data.vo.v1.PersonVO;
import com.rest.api.exception.RequireIsNullException;
import com.rest.api.exception.ResourceNotFoundException;
import com.rest.api.mapper.DozerMapper;
import com.rest.api.model.Person;
import com.rest.api.respository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;


import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class PersonService {


    private Logger  logger = Logger.getLogger(PersonService.class.getName());

    @Autowired
    PersonRepository repository;
    @Autowired
    PagedResourcesAssembler assembler;

    public PersonVO findById(Long id){

    	
        logger.info("Finding one Id person!");

        var entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }

    public PagedModel<EntityModel<PersonVO>> listPerson (Pageable pageable){
        logger.info("List person");

        var personPage = repository.findAll(pageable);

        var personPageVO =
        personPage.map(p -> DozerMapper.parseObject(p, PersonVO.class));

        personPageVO.map(
                p -> p.add(linkTo(methodOn(PersonController.class)
                        .findById(p.getKey())).withSelfRel()));

        Link link = linkTo(methodOn(PersonController.class)
                .getPersons(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc")).withSelfRel();

        return assembler.toModel(personPageVO, link);
    }


    public PersonVO newPerson(PersonVO person) {

    	if(person == null) throw new RequireIsNullException();
    	logger.info("Creating one person!");

        var entity = DozerMapper.parseObject(person, Person.class);

        var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;

    }


    public PersonVO editPerson(PersonVO person) {
    	
    	if(person == null) throw new RequireIsNullException();

    	logger.info("Updating one person!");

        var nEdt = repository.findById(person.getKey())
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        nEdt.setFirstName(person.getFirstName());
        nEdt.setLastName(person.getLastName());
        nEdt.setAddress(person.getAddress());
        nEdt.setGender(person.getGender());


        var vo=  DozerMapper.parseObject(repository.save(nEdt),PersonVO.class);
        vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    @Transactional
    public PersonVO disabledPerson(Long id){


        logger.info("Disabling one Id person!");

        repository.disablePerson(id);
        var entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);

        vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return vo;
    }
    public void deletePerson(Long id) {
        logger.info("Deliting one person!");

        var nEdt = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(nEdt);
    }


}
