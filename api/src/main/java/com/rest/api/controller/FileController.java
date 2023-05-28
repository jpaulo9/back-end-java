package com.rest.api.controller;

import com.rest.api.data.vo.v1.UploadFileResponseVO;
import com.rest.api.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.http.HttpRequest;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "File Endpoint")
@RestController
@RequestMapping("/api/file/v1")
public class FileController {

    private Logger logger = Logger.getLogger(FileController.class.getName());


    @Autowired
    private FileStorageService service;

    @PostMapping("/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Storing file to disk");

        var filename = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/file/v1/downloadFile/")
                .path(filename)
                .toUriString();

        return
                new UploadFileResponseVO(
                        filename, fileDownloadUri, file.getContentType(), file.getSize());
    }
    @PostMapping("/uploadMultFiles")
    public List<UploadFileResponseVO> uploadMultFiles(
            @RequestParam("files") MultipartFile [] files) {
        logger.info("Storing file to disk");

      return Arrays.asList(files)
              .stream()
              .map(file -> uploadFile(file))
              .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        logger.info("Reading file to disk");

        Resource resource = service.loadFileResource(fileName);

        String contentype="";

        try {

            contentype = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.info("Could not determine file type!");
        }

        if(contentype.isBlank()) contentype = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentype))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""+resource.getFilename()+"\""
                ).body(resource);
    }

}
