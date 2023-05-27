package com.rest.api.services;


import com.rest.api.controller.BookController;
import com.rest.api.data.vo.v1.BookVO;
import com.rest.api.exception.RequireIsNullException;
import com.rest.api.exception.ResourceNotFoundException;
import com.rest.api.mapper.DozerMapper;
import com.rest.api.model.Book;
import com.rest.api.respository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    BookRepository repository;

    @Autowired
    PagedResourcesAssembler assembler;

    public BookVO findById(Long id){


        logger.info("Finding one Id Book!");

        var entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        BookVO vo = DozerMapper.parseObject(entity, BookVO.class);

        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());

        return vo;
    }

    public PagedModel<EntityModel<BookVO>> listBooks (Pageable pageable){
        logger.info("List Books");

        var bookPage = repository.findAll(pageable);

        var booksVO =
        bookPage.map(b->DozerMapper.parseObject(b, BookVO.class));

        booksVO.map(
                b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
        Link link = linkTo(methodOn(BookController.class)
                .getBooks(pageable.getPageNumber(),
                        pageable.getPageSize(),
                        "asc")).withSelfRel();


        return assembler.toModel(booksVO, link);
    }


    public BookVO newBook(BookVO book) {

        if(book == null) throw new RequireIsNullException();
        logger.info("Creating one book!");

        var entity = DozerMapper.parseObject(book, Book.class);

        var vo = DozerMapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;

    }

    public BookVO editBook(BookVO book) {

        if(book == null) throw new RequireIsNullException();

        logger.info("Updating one book!");

        var nEdt = repository.findById(book.getKey())
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        nEdt.setAuthor(book.getAuthor());
        nEdt.setLaunchDate(book.getLaunchDate());
        nEdt.setPrice(book.getPrice());
        nEdt.setTitle(book.getTitle());


        var vo=  DozerMapper.parseObject(repository.save(nEdt),BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());

        return vo;
    }

    public void deleteBook(Long id) {
        logger.info("Deliting one book!");

        var nEdt = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(nEdt);
    }


}
