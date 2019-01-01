package com.collins.fileserver.storage;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.collins.fileserver.domain.Page;

@Service
public class FileSystemStorageService implements StorageService {

    private final String rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = properties.getLocation();
    }
    
    private Path getPath(Page page) {
    	if(page != null && page.getDirectory() != null && !page.getDirectory().isEmpty()) 
    		return Paths.get(rootLocation, page.getDirectory());
    	else return Paths.get(rootLocation);
    }

    @Override
    public void store(MultipartFile file, Page page) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
            	Files.createDirectories(getPath(page));
                Files.copy(inputStream, getPath(page).resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

    @Override
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

    @Override
    public Path load(String filename, Page page) {
        return getPath(page).resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename, Page page) {
        try {
            Path file = load(filename, page);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(this.rootLocation).toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(Paths.get(this.rootLocation));
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
