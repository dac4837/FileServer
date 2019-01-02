package com.collins.fileserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.collins.fileserver.domain.Page;
import com.collins.fileserver.repository.FileRepository;
import com.collins.fileserver.repository.PageRepository;

@Controller
public class ViewController {

	private final PageRepository pageRepository;
	private final FileRepository fileRepository;
	
	
	public ViewController(PageRepository pageRepository, FileRepository fileRepository) {
		super();
		this.pageRepository = pageRepository;
		this.fileRepository = fileRepository;
	}



	@GetMapping("/files")
    public String getAllPages( Model model) {
        model.addAttribute("pages", pageRepository.findAll());
        return "pages";
    }
	
	
	@GetMapping("/files/{pageName}")
    public String getPage(@PathVariable String pageName, Model model) {
		Page page = pageRepository.findByName(pageName);
		System.out.println(page);
		System.out.println(fileRepository.findByPageId(page.getId()));
		model.addAttribute("page", page);
        model.addAttribute("files", fileRepository.findByPageId(page.getId()));
        return "page";
    }
	
}
