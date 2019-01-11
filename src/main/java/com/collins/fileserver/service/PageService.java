package com.collins.fileserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collins.fileserver.domain.File;
import com.collins.fileserver.domain.Page;
import com.collins.fileserver.repository.FileRepository;
import com.collins.fileserver.repository.PageRepository;

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
		return pageRepository.findByDirectory(directory);
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
		return fileRepository.findByPageIdAndName(page.getId(), fileName);
		
	}
	
	
}
