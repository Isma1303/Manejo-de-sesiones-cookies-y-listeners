package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private final ProductRepository repo = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        // Requisito:
        // - Implementar paginación por query (?page, ?size) con defaults razonables.
        // - Enviar a la JSP:
        // items -> sublista de productos
        // page, size, total -> para controles de navegación

        List<Product> all = repo.findAll();

        Integer page = req.getParameter("page") != null ? Integer.parseInt(req.getParameter("page")) : 1;
        Integer size = req.getParameter("size") != null ? Integer.parseInt(req.getParameter("size")) : 3;
        Integer total = all.size();
        List<Product> items = all.stream().skip((page - 1) * size).limit(size).collect(Collectors.toList());

        req.setAttribute("items", items);
        req.setAttribute("page", page);
        req.setAttribute("size", size);
        req.setAttribute("total", total);

        try {
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}
