package com.collins.fileserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collins.fileserver.domain.File;
import com.collins.fileserver.domain.Page;
import com.collins.fileserver.repository.FileRepository;
import com.collins.fileserver.repository.PageRepository;

@RestController
@RequestMapping(value = "/api")
public class FileController {
	
	private final PageRepository pageRepository;
	private final FileRepository fileRepository;
	
	@Autowired
	public FileController(PageRepository pageRepository, FileRepository fileRepository) {
		super();
		this.pageRepository = pageRepository;
		this.fileRepository = fileRepository;
	}
	
	@GetMapping("/pages")
	public List<Page> getAllPages() {
		return pageRepository.findAll();
	}
	
	@GetMapping("/files")
	public List<File> getAllFiles() {
		return fileRepository.findAll();
	}
	
	@GetMapping("/pages/{pageId}")
	public List<File> getFilesInPage(@PathVariable Long pageId) {
		return fileRepository.findByPageId(pageId);
	}
	
	

}
