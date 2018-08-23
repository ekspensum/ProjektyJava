package controller;

import java.io.IOException;

import javax.servlet.ServletConfig;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.DaneTransakcji;
import model.dao.Operacja;
import model.dao.OperacjaRachCHF;
import model.dao.OperacjaRachEUR;
import model.dao.OperacjaRachUSD;

@WebServlet("/panelKlientaFirmowego")
public class PanelKlientaFirmowego extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.getRequestDispatcher("jsp/panelKlientaFirmowego.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("doPostPKF");
		
		DaneTransakcji dt = (DaneTransakcji) request.getServletContext().getAttribute("transakcja");
		if (dt != null) {
			Operacja[] op = new Operacja[3];
			op[0] = new OperacjaRachUSD();
			op[1] = new OperacjaRachEUR();
			op[2] = new OperacjaRachCHF();

			if ("Sprzedaj".equals(dt.getRodzaj()))
				op[dt.getIndex()].sprzedaj(dt);
			else if ("Kup".equals(dt.getRodzaj()))
				op[dt.getIndex()].kup(dt);
			System.out.println("transakcja na PKF doPost");
		}

		doGet(request, response);
	}

}
