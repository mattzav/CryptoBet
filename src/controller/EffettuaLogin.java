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
import model.TipoCredenziali;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class EffettuaLogin extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session=req.getSession();
		String username=req.getParameter("user");
		String pwd=req.getParameter("pwd");
		Credenziali c=new Credenziali(username, pwd);
		String admin=req.getParameter("admin");
		if(admin == null) {
			Giocatore g=PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getGiocatoreDAO().findByCredenziali(c);
			if(g!=null) {
				String messaggio="Benvenuto "+username+"  ";
				session.setAttribute("username", username);
				session.setAttribute("mex", messaggio);
				session.setAttribute("loggato", TipoCredenziali.USER);
				session.setAttribute("conto", g.getConto().getCodice());
			}
		}else {
			boolean result =PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getAmministratoreDAO().findByCredenziali(c);
			System.out.println("pippo");
			if(result) {
				String messaggio="Benvenuto Amministratore "+username+"    ";
				session.setAttribute("username", username);
				session.setAttribute("mex", messaggio);
				session.setAttribute("loggato", TipoCredenziali.ADMIN);
			}
		}
		RequestDispatcher disp;
		String page=(String) session.getAttribute("page");
		disp= req.getRequestDispatcher(page);
		disp.forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		session.removeAttribute("username");
		session.removeAttribute("loggato");
		RequestDispatcher disp;
		String page=(String) session.getAttribute("page");
		disp= req.getRequestDispatcher(page);
		disp.forward(req, resp);
	}
}
