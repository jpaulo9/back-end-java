package com.rest.api.controller;


import com.rest.api.MD_Type;
import com.rest.api.data.vo.v1.PersonVO;
import com.rest.api.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/person/v1")
@Tag(name = "People", description = "Endpoints for Manaing People")
public class PersonController {


	@Autowired
	private PersonService service ;

	@GetMapping(value = "/list", produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
	@Operation( summary = "Get all person",
			description = "Find all person",
			tags = {"People"},
			responses = {
					@ApiResponse( description = "Get persons Sucess", responseCode = "200",
							content = @Content(schema = @Schema(implementation = PersonVO.class))

					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public List<PersonVO> getPersons()
			throws Exception{
		return service.listPerson();

	}
	@GetMapping(value = "/{id}",
			produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
	@Operation( summary = "Get person",
			description = "Find person",
			tags = {"People"},
			responses = {
					@ApiResponse( description = "Get person Sucess", responseCode = "200",
							content = @Content(schema = @Schema(implementation = PersonVO.class))

					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public PersonVO findById(@PathVariable(value="id") Long id)
			{
		return service.findById(id);
		
	}
	@PostMapping(value = "/add", consumes = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML},
			produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
	@Operation( summary = "Add person",
	description = "Add persons by passing JSON, XML and YML",
	tags = {"People"},
	responses = {
			@ApiResponse( description = "Sucess", responseCode = "200",
					content = @Content(schema = @Schema(implementation = PersonVO.class))

			),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
			@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
	})
	public PersonVO newPerson(@RequestBody PersonVO person) {

		return service.newPerson(person);

	}
	@PostMapping(value = "/v2", consumes = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML},
			produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})

//	public PersonVO2 newPerson(@RequestBody PersonVO2 person) {
//
//		return service.newPerson(person);
//
//	}


	@PutMapping(value = "/att", consumes = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML},
			produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
	@Operation( summary = "Edt person",
			description = "Update data person",
			tags = {"People"},
			responses = {
					@ApiResponse( description = "Editing Sucess", responseCode = "200",
							content = @Content(schema = @Schema(implementation = PersonVO.class))

					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public PersonVO Up(@RequestBody PersonVO person)
			{
		return service.editPerson(person);

	}

	@PatchMapping(value = "/{id}",
			produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
	@Operation( summary = "Disable a specific person vy your ID",
			description = "Disable a specific person vy your ID",
			tags = {"People"},
			responses = {
					@ApiResponse( description = "Disable person Sucess", responseCode = "200",
							content = @Content(schema = @Schema(implementation = PersonVO.class))

					),
					@ApiResponse(description = "No content", responseCode = "204", content = @Content),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public PersonVO disabledPerson(@PathVariable(value="id") Long id)
	{
		return service.disabledPerson(id);

	}
	@DeleteMapping(value = "/{id}")
	@Operation( summary = "Delete person",
			description = "Exclude person",
			tags = {"People"},
			responses = {
					@ApiResponse( description = "No Content", responseCode = "204",content = @Content
					),
					@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
			})
	public ResponseEntity<?> deletePerson(@PathVariable(value="id") Long id)
			{
				service.deletePerson(id);

				return ResponseEntity.noContent().build();
	}


}
