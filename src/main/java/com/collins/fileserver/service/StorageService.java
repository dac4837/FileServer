package com.collins.fileserver.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.collins.fileserver.controller.ClientWebException;
import com.collins.fileserver.domain.File;
import com.collins.fileserver.domain.Page;

@Service
public class StorageService {

    private final String rootLocation;

    @Autowired
    public StorageService(StorageProperties properties) {
        this.rootLocation = properties.getLocation();
    }
    
    private Path getPath(Page page) {
    	if(page != null && page.getDirectory() != null && !page.getDirectory().isEmpty()) 
    		return Paths.get(rootLocation, page.getDirectory());
    	else return Paths.get(rootLocation);
    }

  
    public File store(MultipartFile file, Page page) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + fileName);
            }
            if (fileName.contains("..")) {
                // This is a security check
                throw new ClientWebException(
                        "Cannot store file with relative path outside current directory "
                                + fileName);
            }
           InputStream inputStream = file.getInputStream();
            	Files.createDirectories(getPath(page));
                Files.copy(inputStream, getPath(page).resolve(fileName));
                
                File newFile = new File();
                newFile.setName(fileName);
                newFile.setPage(page);
                return newFile;
            
        }
        catch(FileAlreadyExistsException e) {
        	throw new FileExistsException("File " + file.getOriginalFilename() + " already exists");
        }
        catch (IOException e) {
        	System.out.println("FileSystemStorageService.store io Ex");
        	System.out.println(e);
            throw new StorageException("Failed to upload file " + file.getOriginalFilename(), e);
        }
    }

    public Stream<Path> loadAll(Page page) {
        try {
            return Files.walk(getPath(page), 1)
                .filter(path -> !path.equals(getPath(page)))
                .map(getPath(page)::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public Path load(File file) {
        return getPath(file.getPage()).resolve(file.getName());
    }

    public Resource loadAsResource(File file) {
        try {
            Path filePath = load(file);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException(
                        "Could not read file: " + file);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + file, e);
        }
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(this.rootLocation).toFile());
    }

    
    public void init() {
        try {
            Files.createDirectories(Paths.get(this.rootLocation));
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
