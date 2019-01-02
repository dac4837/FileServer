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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.collins.fileserver.domain.Page;
import com.collins.fileserver.storage.StorageException;
import com.collins.fileserver.storage.StorageService;

@Controller
//@RequestMapping("/pages")
@RequestMapping(value = "/storage")
public class FileUploadController {

    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    //@GetMapping("/{page}/")
    ///*@PathVariable Page page,*/
    @GetMapping("/")
    public String listUploadedFiles(/*@PathVariable Page page,*/ Model model) throws IOException {
    	System.out.println("Getting");

        model.addAttribute("files", storageService.loadAll(null).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }
    /*
    @GetMapping("/")
    public String listPages( Model model) throws IOException {
    	System.out.println("Getting");
//TODO implement
        model.addAttribute("files", storageService.loadAll(null).map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }
    */

    @GetMapping("/files/{filename:.+}")
    //@GetMapping("/{page}/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(/*@PathVariable Page page,*/ @PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename, new Page());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    //@PostMapping("/{page}/")
    @PostMapping("/")
    public String handleFileUpload(/*@PathVariable Page page,*/ @RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        storageService.store(file, new Page());
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageException exc) {
        return ResponseEntity.notFound().build();
    }

}