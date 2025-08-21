package com.darwinruiz.shoplite.controllers;

import com.darwinruiz.shoplite.models.User;
import com.darwinruiz.shoplite.repositories.UserRepository;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
    private final UserRepository users = new UserRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Verificar las credenciales usando UserRepository
        Optional<User> userOpt = users.findByEmail(email);

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            // Si son inválidas, redirigir a login.jsp?err=1
            resp.sendRedirect(req.getContextPath() + "/login.jsp?err=1");
            return;
        }

        User user = userOpt.get();

        // Si son válidas:
        // Invalidar la sesión anterior
        HttpSession oldSession = req.getSession(false);
        if (oldSession != null) {
            oldSession.invalidate();
        }

        // Crear una nueva sesión y asignar atributos
        HttpSession session = req.getSession(true);
        session.setAttribute("auth", true);
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("role", user.getRole());

        // Configurar maxInactiveInterval a 30 minutos
        session.setMaxInactiveInterval(30 * 60); // 30 minutos en segundos

        // Redirigir a /home
        resp.sendRedirect(req.getContextPath() + "/home");
    }
}
