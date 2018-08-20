package controller;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.dao.UserZalogowany;


@WebFilter(urlPatterns= {"/panelAdministratora", "/panelOperatora", "/panelKlientaFirmowego", "/panelKlientaPrywatnego"})
public class FiltrDostepu implements Filter {


    public FiltrDostepu() {
        // TODO Auto-generated constructor stub
    }


	public void destroy() {
		// TODO Auto-generated method stub
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession sesja = req.getSession();
		UserZalogowany uz = (UserZalogowany) sesja.getAttribute("userZalogowany");

		if(uz == null)
			request.getRequestDispatcher("/logowanie").forward(request, response);
		else if(uz.getIdRola() == 1)
			request.getRequestDispatcher("/panelAdministratora").forward(request, response);
		else if(uz.getIdRola() == 2)
			request.getRequestDispatcher("/panelOperatora").forward(request, response);
		else if(uz.getIdRola() == 3)
			request.getRequestDispatcher("/panelKlientaFirmowego").forward(request, response);
		else if(uz.getIdRola() == 4)
			request.getRequestDispatcher("/panelKlientaPrywatnego").forward(request, response);

		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
