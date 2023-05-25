package com.rest.api.controller;


import com.rest.api.MD_Type;
import com.rest.api.data.vo.v1.BookVO;
import com.rest.api.data.vo.v1.PersonVO;
import com.rest.api.services.BookService;
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
@RequestMapping("/api/book/v1")
@Tag(name = "Book", description = "Endpoints for Manaing Book")
public class BookController {


    @Autowired
    private BookService service ;

    @GetMapping(value = "/list", produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
    @Operation( summary = "Get all book",
            description = "Find all book",
            tags = {"Book"},
            responses = {
                    @ApiResponse( description = "Get books Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookVO.class))

                    ),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public List<BookVO> getBooks()
            throws Exception{
        return service.listBooks();

    }
    @GetMapping(value = "/{id}",
            produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
    @Operation( summary = "Get book",
            description = "Find book",
            tags = {"Book"},
            responses = {
                    @ApiResponse( description = "Get book Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookVO.class))

                    ),
                    @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public BookVO findById(@PathVariable(value="id") Long id)
    {
        return service.findById(id);

    }

    // -------------------

    @PostMapping(value = "/add", consumes = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML},
            produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
    @Operation( summary = "Add book",
            description = "Add books by passing JSON, XML and YML",
            tags = {"Book"},
            responses = {
                    @ApiResponse( description = "Sucess", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = BookVO.class))

                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public BookVO newBook(@RequestBody BookVO book) {

        return service.newBook(book);

    }
//    @PostMapping(value = "/v2", consumes = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML},
//            produces = {MD_Type.APPLICATION_JSON, MD_Type.APPLICATION_XML, MD_Type.APPLICATION_YML})
//
//    public PersonVO2 newPerson(@RequestBody PersonVO2 person) {
//
//        return service.newPerson(person);
//
//    }


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
    public BookVO Up(@RequestBody BookVO book)
    {
        return service.editBook(book);

    }


    // ------------------------------------

    @DeleteMapping(value = "/{id}")
    @Operation( summary = "Delete book",
            description = "Exclude book",
            tags = {"Book"},
            responses = {
                    @ApiResponse( description = "No Content", responseCode = "204",content = @Content
                    ),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<?> deleteBook(@PathVariable(value="id") Long id)
    {
        service.deleteBook(id);

        return ResponseEntity.noContent().build();
    }


}
