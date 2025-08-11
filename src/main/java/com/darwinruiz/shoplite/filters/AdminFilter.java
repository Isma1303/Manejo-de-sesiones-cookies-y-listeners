package com.darwinruiz.shoplite.filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Requisito: permitir /admin solo a usuarios con rol "ADMIN"; los demás ven 403.jsp.
 */

@WebFilter({"/admin/*"})
public class AdminFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest r = (HttpServletRequest) req;
        HttpServletResponse p = (HttpServletResponse) res;
        HttpSession sesion = r.getSession(false);

        // Requisito: validar sesión existente y atributo "role" con valor "ADMIN".
        // Si no cumple, hacer forward a /403.jsp. Si cumple, continuar.

        if(sesion !=null){
            Object obj = sesion.getAttribute("role");
            if(obj!=null && "ADMIN".equals(obj.toString())){
                chain.doFilter(req, res); // comportamiento temporal: compila y deja pasar
                return;
            }
        }
        r.getRequestDispatcher("/403.jsp").forward(r, p);

    }
}
