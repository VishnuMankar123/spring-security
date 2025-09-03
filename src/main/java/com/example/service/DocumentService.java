package com.example.service;

import com.example.entity.User;
import com.example.repo.UserRepository;
import com.itextpdf.html2pdf.HtmlConverter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.ArrayList;
import java.util.List;


@Service
public class DocumentService {


    @Autowired
    private SpringTemplateEngine templateEngine;

    // PDF Generation
    public void generatePdf(HttpServletResponse response) throws Exception {
        // Create dummy users
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "Vishnu Mankar", "vishnu@example.com"));
        users.add(new User(2L, "Anita Sharma", "anita@example.com"));
        users.add(new User(3L, "Rohan Singh", "rohan@example.com"));


        Context context = new Context();
        context.setVariable("users", users);
        String html = templateEngine.process("users_template", context);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=users.pdf");

        HtmlConverter.convertToPdf(html, response.getOutputStream());
    }
}