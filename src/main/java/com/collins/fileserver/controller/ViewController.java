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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.collins.fileserver.domain.File;
import com.collins.fileserver.domain.Page;
import com.collins.fileserver.service.PageService;
import com.collins.fileserver.service.StorageService;

@Controller
public class ViewController {

	private final PageService pageService;
	private final StorageService storageService;
	
	
	@Autowired
	public ViewController(PageService pageService, StorageService storageService) {
		super();
		this.pageService = pageService;
		this.storageService = storageService;
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
	
	@PostMapping("/files/{pageName}/new")
    public String postFileUpload(@PathVariable String pageName, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
		Page page = pageService.getPage(pageName);
		File newFile = storageService.store(file, page);
		pageService.saveFile(newFile);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
		//pageService.savePage(page);
	    return "redirect:/files/" + pageName;
    }
	
	@GetMapping("/files/{pageName}/new")
    public String getFileUpload(@PathVariable String pageName, Model model) {
		Page page = pageService.getPage(pageName);
		model.addAttribute("page", page);
		model.addAttribute("file", new File());
        return "newFile";
    }
	
	
}
