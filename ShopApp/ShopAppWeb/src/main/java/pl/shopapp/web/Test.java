package pl.shopapp.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.ejb.SessionContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.shopapp.beans.SessionData;

/**
 * Servlet implementation class Test
 */

@WebServlet("/Test")
@DeclareRoles({"customer", "admin"})
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SessionData sd = (SessionData) request.getSession().getAttribute("SessionData");

		request.getRequestDispatcher("jsp/test.jsp").forward(request, response);

//		request.authenticate(response);
//		
////		request.login("helloUser", "helloUser");
//		
//		System.out.println("Auth type: "+request.getAuthType());
//		
//		System.out.println("Userrole: "+request.isUserInRole("helloUser"));
//		System.out.println("Remote user: "+request.getRemoteUser());
////		System.out.println("User principal: "+request.getUserPrincipal().getName());
//		if(request.isUserInRole("helloUser"))
//		response.getWriter().append("User role: ").append("helloUser");
//		else
//			response.getWriter().append("Role not allowed: ");
//		request.logout();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
		doGet(request, response);
	}

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   response.setContentType("text/html;charset=UTF-8");
   PrintWriter out = response.getWriter();
   try {
       String userName = request.getParameter("txtUserName");
       String password = request.getParameter("txtPassword");

       out.println("Before Login" + "<br><br>");
       out.println("Login: " + userName + "<br>");
       out.println("Login: " + password + "<br><br>");
       out.println("IsUserInRole?.."
                   + request.isUserInRole("customer")+"<br>");
       out.println("getRemoteUser?.." + request.getRemoteUser()+"<br>");
       out.println("getUserPrincipal?.."
                   + request.getUserPrincipal()+"<br>");
       out.println("getAuthType?.." + request.getAuthType()+"<br><br>");

       try {
           request.login(userName, password);
       } catch(ServletException ex) {
           out.println("Login Failed with a ServletException.."
               + ex.getMessage());
           return;
       }
       out.println("After Login..."+"<br><br>");
       out.println("IsUserInRole?.."
                   + request.isUserInRole("customer")+"<br>");
       out.println("getRemoteUser?.." + request.getRemoteUser()+"<br>");
       out.println("getUserPrincipal?.."
                   + request.getUserPrincipal()+"<br>");
       out.println("getAuthType?.." + request.getAuthType()+"<br><br>");

       request.logout();
       out.println("After Logout..."+"<br><br>");
       out.println("IsUserInRole?.."
                   + request.isUserInRole("customer")+"<br>");
       out.println("getRemoteUser?.." + request.getRemoteUser()+"<br>");
       out.println("getUserPrincipal?.."
                   + request.getUserPrincipal()+"<br>");
       out.println("getAuthType?.." + request.getAuthType()+"<br>");
   } finally {
       out.close();
   }
}
}
