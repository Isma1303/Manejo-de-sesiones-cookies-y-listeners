package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Requisito (POST): validar y crear un nuevo producto en PostgreSQL y redirigir
 * a /home.
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final ProductRepository repo = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String accion = req.getParameter("accion");
        String productIdStr = req.getParameter("id");

        try {
            if ("eliminar".equals(accion) && productIdStr != null) {
                long productId = Long.parseLong(productIdStr);
                repo.deleteById(productId);
                resp.sendRedirect(req.getContextPath() + "/admin");
                return;
            } else if ("editar".equals(accion) && productIdStr != null) {
                long productId = Long.parseLong(productIdStr);
                repo.findById(productId).ifPresent(product -> req.setAttribute("productToEdit", product));
            }

            req.setAttribute("products", repo.findAll());
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");

        if (action == null) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        switch (action) {
            case "addProduct":
                addProduct(req, resp);
                break;
            case "updateProduct":
                updateProduct(req, resp);
                break;
            case "deleteProduct":
                deleteProduct(req, resp);
                break;
            default:
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                break;
        }
    }

    private void addProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String stockStr = req.getParameter("stock");
        String imageUrl = req.getParameter("imageUrl");

        if (name == null || name.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price <= 0) {
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        int stock;
        try {
            stock = Integer.parseInt(stockStr);
            if (stock < 0) {
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        if (imageUrl == null || imageUrl.trim().isEmpty()) {
            imageUrl = "/imagenes/default.png";
        }

        Product newProduct = new Product(
            name.trim(),
            price,
            imageUrl.trim(),
            stock
        );

        boolean saved = repo.save(newProduct);

        if (saved) {
            resp.sendRedirect(req.getContextPath() + "/admin");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("id");
        String name = req.getParameter("name");
        String priceStr = req.getParameter("price");
        String stockStr = req.getParameter("stock");
        String imageUrl = req.getParameter("imageUrl");

        if (productIdStr == null || productIdStr.trim().isEmpty() || name == null || name.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        long productId;
        double price;
        int stock;

        try {
            productId = Long.parseLong(productIdStr);
            price = Double.parseDouble(priceStr);
            stock = Integer.parseInt(stockStr);
            if (price <= 0 || stock < 0) {
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                return;
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        Product product = repo.findById(productId).orElse(null);
        if (product == null) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        product.setName(name.trim());
        product.setPrice(price);
        product.setStock(stock);
        product.setImageUrl(imageUrl != null && !imageUrl.trim().isEmpty() ? imageUrl.trim() : "/imagenes/default.png");

        boolean updated = repo.update(product);

        if (updated) {
            resp.sendRedirect(req.getContextPath() + "/admin");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
        }
    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String productIdStr = req.getParameter("productId");

        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        long productId;
        try {
            productId = Long.parseLong(productIdStr);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
            return;
        }

        boolean deleted = repo.deleteById(productId);

        if (deleted) {
            resp.sendRedirect(req.getContextPath() + "/admin");
        } else {
            resp.sendRedirect(req.getContextPath() + "/admin?err=1");
        }
    }
}