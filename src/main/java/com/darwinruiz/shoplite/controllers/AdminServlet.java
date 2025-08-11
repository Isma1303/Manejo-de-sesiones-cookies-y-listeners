package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.Product;
import com.darwinruiz.shoplite.repositories.ProductRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Requisito (POST): validar y crear un nuevo producto en memoria y redirigir a
 * /home.
 */
@WebServlet("/admin")
public class AdminServlet extends HttpServlet {
    private final ProductRepository repo = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher("/admin.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Requisito:
        // - Leer name y price del formulario.
        // - Validar: nombre no vacío, precio > 0.
        // - Generar id con repo.nextId(), crear y guardar en repo.
        // - Redirigir a /home si es válido; si no, regresar a /admin?err=1.

        String accion = req.getParameter("accion");
            String name = req.getParameter("name");
            String priceParam = req.getParameter("price");

            System.out.println("Accion: " + accion);
            System.out.println("Name: " + name);
            System.out.println("PriceParam: " + priceParam);

            if ("guardar".equals(accion)) {

            if (name == null || name.trim().isEmpty() || priceParam == null || priceParam.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                return;
            }

            Integer price;
            try {
                price = Integer.parseInt(priceParam);
                if (price <= 0) {
                    resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                    return;
                }
            } catch (NumberFormatException e) {
                resp.sendRedirect(req.getContextPath() + "/admin?err=1");
                return;
            }

            long id = repo.nextId();
            Product product = new Product(id, name, price, null);
            repo.add(product);

            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/admin?err=1");
    }
}