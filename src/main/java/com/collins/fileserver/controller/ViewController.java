package com.collins.fileserver.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.collins.fileserver.domain.File;
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
	public String getAllPages(Model model) {
		model.addAttribute("pages", pageService.getAllPages());
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

		if (bindingResult.hasErrors()) {
			return "newPage";
		}
		pageService.savePage(page);
		return "redirect:/files/"+page.getDirectory();
	}

	@GetMapping("/pages/new")
	public String getNewPage(Model model) {
		model.addAttribute("page", new Page());
		return "newPage";
	}

	@PostMapping("/files/{pageName}/new")
	public String postFileUpload(@PathVariable String pageName, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) {
			
		Page page = pageService.getPage(pageName);
		if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/files/" + pageName + "/new";
        }

		File newFile = pageService.saveFile(file, page);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + newFile.getName() + "!");
		return "redirect:/files/" + pageName;

	}

	@GetMapping("/files/{pageName}/new")
	public String getFileUpload(@PathVariable String pageName, Model model) {
		Page page = pageService.getPage(pageName);
		model.addAttribute("page", page);
		model.addAttribute("file", new File());
		return "newFile";

	}
	
	@GetMapping("/files/download/{pageName}/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String pageName, @PathVariable String fileName) {
		// Page page = pageRepository.findByDirectory(pageName);
		System.out.println("Request to get file. Page: " + pageName + " File: " + fileName);

		Resource file = pageService.getFile(pageName, fileName);

		if (file == null)
			throw new ResourceNotFoundException("File " + fileName + " of page " + pageName + " could not be found.");

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

}
