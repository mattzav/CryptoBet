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
				session.setAttribute("loggato", true);
				session.setAttribute("conto", g.getConto().getCodice());
			}
			RequestDispatcher disp;
			String page=(String) session.getAttribute("page");
			if(page!=null && page.equals("mioconto"))
				disp= req.getRequestDispatcher("MioConto.jsp");
			else disp= req.getRequestDispatcher("index.jsp");
			disp.forward(req, resp);
		}else {
			boolean result =PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getAmministratoreDAO().findByCredenziali(c);
			String messaggio="Benvenuto Amministratore "+username+"    ";
			session.setAttribute("username", username);
			session.setAttribute("mex", messaggio);
			session.setAttribute("loggato", true);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session=req.getSession();
		session.removeAttribute("username");
		session.setAttribute("loggato", false);
		RequestDispatcher disp;
		String page=(String) session.getAttribute("page");
		if(page!=null && page.equals("mioconto"))
			disp= req.getRequestDispatcher("MioConto.jsp");
		else disp= req.getRequestDispatcher("index.jsp");
		disp.forward(req, resp);
	}
}
