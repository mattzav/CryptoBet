package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import model.Credenziali;
import model.Giocatore;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class EffettuaLogin extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session=req.getSession();
		String username=req.getParameter("user");
		String pwd=req.getParameter("pwd");
		Credenziali c=new Credenziali(username, pwd);
		Giocatore g=PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getGiocatoreDAO().findByCerdenziali(c);
		if(g!=null) {
			String messaggio="Benvenuto "+username+"  ";
			session.setAttribute("username", username);
			session.setAttribute("mex", messaggio);
			session.setAttribute("loggato", true);
			session.setAttribute("conto", g.getConto().getCodice());
		}
		RequestDispatcher disp= req.getRequestDispatcher("index.jsp");
		disp.forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		session.removeAttribute("username");
		session.setAttribute("loggato", false);
		RequestDispatcher disp= req.getRequestDispatcher("index.jsp");
		disp.forward(req, resp);
	}
}
