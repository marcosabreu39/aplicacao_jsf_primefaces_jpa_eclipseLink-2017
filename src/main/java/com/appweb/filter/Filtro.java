package com.appweb.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



@WebFilter(urlPatterns = { "/view/private/cadastrar-cliente.xhtml/*", "/view/private/consultar-cliente.xhtml/*", 
		"/view/private/detalhes-cliente.xhtml/*"})
public class Filtro implements javax.servlet.Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {

			String login = "";

			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			HttpSession session = req.getSession(false);
			
			session = session == null ? req.getSession(true) : session;

			session.setMaxInactiveInterval(60);

			login = (String) session.getAttribute("login");

			if (login == null || login.equals("")) {

				res.sendRedirect(req.getContextPath() + "/view/index.xhtml");

			} else {
				chain.doFilter(request, response);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}