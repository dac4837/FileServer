package com.collins.fileserver.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.collins.fileserver.domain.Page;

public interface StorageService {

    void init();

    void store(MultipartFile file, Page page);

    Stream<Path> loadAll(Page page);

    Path load(String filename, Page page);

    Resource loadAsResource(String filename, Page page);

    void deleteAll();

}