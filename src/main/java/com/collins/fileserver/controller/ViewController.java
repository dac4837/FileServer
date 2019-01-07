package com.collins.fileserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.collins.fileserver.domain.Page;
import com.collins.fileserver.service.PageService;

@Controller
public class ViewController {

	private final PageService pageService;
	
	
	@Autowired
	public ViewController(PageService pageService) {
		super();
		this.pageService = pageService;
	}



	@GetMapping("/files")
    public String getAllPages( Model model) {
        model.addAttribute("pages", pageService.getAllPages() );
        return "pages";
    }
	
	
	@GetMapping("/files/{pageName}")
    public String getPage(@PathVariable String pageName, Model model) {
		Page page = pageService.getPage(pageName);
		model.addAttribute("page", page);
        model.addAttribute("files", pageService.getFilesByPageName(pageName));
        return "page";
    }
	
	@PostMapping("/pages/new")
    public String postNewPage(@ModelAttribute @Valid Page page, BindingResult bindingResult, Model model) {
		
		System.out.println(model);
		if(bindingResult.hasErrors()) {
			return "newPage";
		}
		pageService.savePage(page);
	    return "redirect:/files";
    }
	
	@GetMapping("/pages/new")
    public String getNewPage(Model model) {
		model.addAttribute("page", new Page());
        return "newPage";
    }
}
