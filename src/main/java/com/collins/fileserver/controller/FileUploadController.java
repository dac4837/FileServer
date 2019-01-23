package com.collins.fileserver.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.collins.fileserver.domain.File;
import com.collins.fileserver.domain.Page;
import com.collins.fileserver.service.PageService;
import com.collins.fileserver.service.StorageService;
import com.collins.fileserver.storage.StorageException;
/**
 * I don't think this is being used
 */

@Controller
//@RequestMapping("/pages")
//@RequestMapping(value = "/storage")
public class FileUploadController {

	private final StorageService storageService;
	private final PageService pageService;

	@Autowired
	public FileUploadController(StorageService storageService, PageService pageService) {
		this.storageService = storageService;
		this.pageService = pageService;
	}

	// @GetMapping("/{page}/")
	/// *@PathVariable Page page,*/
	@GetMapping("/storage")
	public String listUploadedFiles(/* @PathVariable Page page, */ Model model) throws IOException {
		System.out.println("Getting");

		model.addAttribute("files",
				storageService.loadAll(null)
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toString())
						.collect(Collectors.toList()));

		return "uploadForm";
	}
	/*
	 * @GetMapping("/") public String listPages( Model model) throws IOException {
	 * System.out.println("Getting"); //TODO implement model.addAttribute("files",
	 * storageService.loadAll(null).map( path ->
	 * MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
	 * "serveFile", path.getFileName().toString()).build().toString())
	 * .collect(Collectors.toList()));
	 * 
	 * return "uploadForm"; }
	 */

	// @GetMapping("/files/{filename:.+}")
	@GetMapping("/files/download/{pageName}/{fileName:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String pageName, @PathVariable String fileName) {
		// Page page = pageRepository.findByDirectory(pageName);
		System.out.println("Request to get file. Page: " + pageName + " File: " + fileName);

		File file = pageService.getFile(pageName, fileName);

		if (file == null)
			throw new ResourceNotFoundException("File " + fileName + " of page " + pageName + " could not be found.");

		Resource fileResource = storageService.loadAsResource(file);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
				.body(fileResource);
	}

	// @PostMapping("/{page}/")
	@PostMapping("/storage")
	public String handleFileUpload(/* @PathVariable Page page, */ @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes) {
		System.out.println("Trying to upload a file");
		
		if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }

		storageService.store(file, new Page());
		
		
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

	@ExceptionHandler(StorageException.class)
	public void handleStorageFileNotFound(StorageException exc) {
		throw new ResourceNotFoundException(exc.getMessage());
	}
	
	
	

}