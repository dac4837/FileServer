package com.collins.fileserver.service;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.collins.fileserver.domain.File;
import com.collins.fileserver.domain.Page;

public interface StorageService {

    void init();

    void store(MultipartFile file, Page page);

    Stream<Path> loadAll(Page page);

    Path load(File file);

    Resource loadAsResource(File file);

    void deleteAll();

}