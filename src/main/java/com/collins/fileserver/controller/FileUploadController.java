package com.collins.fileserver.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.collins.fileserver.service.StorageException;
import com.collins.fileserver.service.StorageService;
/**
 * I don't think this is being used
 */


//@RequestMapping("/pages")
//@RequestMapping(value = "/storage")
public class FileUploadController {

	private final StorageService storageService;

	@Autowired
	public FileUploadController(StorageService storageService) {
		this.storageService = storageService;
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


	@ExceptionHandler(StorageException.class)
	public void handleStorageFileNotFound(StorageException exc) {
		throw new ResourceNotFoundException(exc.getMessage());
	}
	
	
	

}