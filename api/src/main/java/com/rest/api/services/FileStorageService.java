package com.rest.api.services;


import com.rest.api.config.FileStorageConfig;
import com.rest.api.exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {


    private final Path fileStorageLocal;

    @Autowired
    public FileStorageService (FileStorageConfig fileStorageConfig){

        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();

        this.fileStorageLocal = path;

        try{
            Files.createDirectories(this.fileStorageLocal);
        } catch (Exception e) {
            throw new FileStorageException(
                    "Could not create the directory where the upload files will be stored!",e);
        }

    }

    public String storeFile (MultipartFile file){

        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        try{
            if(filename.contains("..")){
                throw new FileStorageException(
                        "Sorry! Filename Contains invalid path sequence "+filename
                );
            }

            Path targetLocation = this.fileStorageLocal.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception e) {
            throw new FileStorageException("Could not store file "+filename+". Please try again!",e);
        }

    }
}
