package pl.shopapp.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.SessionData;

/**
 * Servlet Filter implementation class FilterTransaction
 */
@WebFilter(urlPatterns={"/Transaction", "/jsp/transaction.jsp"})
public class FilterTransaction implements Filter {

    /**
     * Default constructor. 
     */
    public FilterTransaction() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		SessionData sd = (SessionData) req.getSession().getAttribute("SessionData");
		if(sd == null || sd.getIdRole() != 2) {
			req.getSession().invalidate();
			resp.sendRedirect("http://localhost:8080/ShopAppWeb/LoginServlet");
			return;
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
