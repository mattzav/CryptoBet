package controller.handlematches;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AggiornaDati extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().removeAttribute("loadingSquadre");
		req.getSession().removeAttribute("loadingPartite");
		RequestDispatcher disp = req.getRequestDispatcher("gestisciPartite.jsp");
		disp.forward(req, resp);
	}

	
}