package com.collins.fileserver.service;

import java.sql.SQLException;
import java.util.List;

import org.h2.jdbc.JdbcSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collins.fileserver.controller.ClientWebException;
import com.collins.fileserver.controller.ResourceNotFoundException;
import com.collins.fileserver.domain.File;
import com.collins.fileserver.domain.Page;
import com.collins.fileserver.repository.FileRepository;
import com.collins.fileserver.repository.PageRepository;
import com.collins.fileserver.storage.StorageException;

@Service
public class PageService {

	private final PageRepository pageRepository;
	private final FileRepository fileRepository;
	
	@Autowired
	public PageService(PageRepository pageRepository, FileRepository fileRepository) {
		super();
		this.pageRepository = pageRepository;
		this.fileRepository = fileRepository;
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
		return pageRepository.save(page);	
	}
	
	public File saveFile(File file) {
			return fileRepository.save(file);
		
	}
	
	public File getFile(String directory, String fileName) {
		Page page = this.getPage(directory);
		File file = fileRepository.findByPageIdAndName(page.getId(), fileName);
		if (file == null) throw new ResourceNotFoundException("file "+ directory + "/" + fileName + " was not found");
		return file;
		
	}
	
	
}
