package com.collins.fileserver.service;

import java.util.List;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.collins.fileserver.controller.ClientWebException;
import com.collins.fileserver.controller.ResourceNotFoundException;
import com.collins.fileserver.domain.File;
import com.collins.fileserver.domain.Page;
import com.collins.fileserver.repository.FileRepository;
import com.collins.fileserver.repository.PageRepository;

@Service
public class PageService {

	private final PageRepository pageRepository;
	private final FileRepository fileRepository;
	private final StorageService storageService;
	
	@Autowired
	public PageService(PageRepository pageRepository, FileRepository fileRepository,
			StorageService storageService) {
		super();
		this.pageRepository = pageRepository;
		this.fileRepository = fileRepository;
		this.storageService = storageService;
	}
	
	public List<Page> getAllPages() {
		return pageRepository.findAll();
	}
	
	public Page getPage(String directory) {
		Page page = pageRepository.findByDirectory(directory);
		if (page == null) throw new ResourceNotFoundException(directory + " was not found");
		
		return page;
	}
	
	public List<File> getFilesByPageName(String directory) {
		Page page = this.getPage(directory);
		return fileRepository.findByPageId(page.getId());
	}
	
	public Page savePage(Page page) {
		try {
		return pageRepository.save(page);	
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(e.getCause());
			if(e instanceof JdbcSQLException)	throw new ClientWebException ("Error saving. Already exists"); 
			else throw new ClientWebException (e.getMessage());
		}
	}
	
	public File saveFile( MultipartFile file, Page page) {
			File storedFile = storageService.store(file, page);
			return fileRepository.save(storedFile);
		
	}
	
	public Resource getFile(String directory, String fileName) {
		Page page = this.getPage(directory);
		File file = fileRepository.findByPageIdAndName(page.getId(), fileName);
		if (file == null) throw new ResourceNotFoundException("file "+ directory + "/" + fileName + " was not found");
		return storageService.loadAsResource(file);
		
	}
	

	
	
}
