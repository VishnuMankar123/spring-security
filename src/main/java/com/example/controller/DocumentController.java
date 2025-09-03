package com.example.controller;

import com.example.service.DocumentService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/export")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @GetMapping("/pdf")
    public void exportPdf(HttpServletResponse response) throws Exception {
        documentService.generatePdf(response);
    }

    @GetMapping("/docx")
    public void exportDocx(HttpServletResponse response) throws Exception {

    }
}

