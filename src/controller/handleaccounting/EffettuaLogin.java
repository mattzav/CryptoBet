package controller.handleaccounting;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import model.users.Credenziali;
import model.users.Giocatore;
import model.users.TipoCredenziali;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.GiocatoreDao;

public class EffettuaLogin extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession sessione=req.getSession();
		
		String username=req.getParameter("user");
		String password=req.getParameter("pwd");
		
		Credenziali c=new Credenziali(username, password);
		
		String amministratore=req.getParameter("admin");
		
		if(amministratore == null) {
			
			//login come giocatore
			Giocatore g=PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getGiocatoreDAO().findByCredenziali(c);
			if(g!=null) {
				String messaggio="Benvenuto "+username+"  ";
				sessione.setAttribute("username", username);
				sessione.setAttribute("mex", messaggio);
				sessione.setAttribute("loggato", g);
				sessione.setAttribute("utente", TipoCredenziali.USER);
			}
		}else {
			
			//login come amministratore
			boolean risultato =PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getAmministratoreDAO().findByCredenziali(c);
			if(risultato) {
				String messaggio="Benvenuto Amministratore "+username+"    ";
				sessione.setAttribute("username", username);
				sessione.setAttribute("mex", messaggio);
				sessione.setAttribute("loggato", true);
				sessione.setAttribute("utente", TipoCredenziali.ADMIN);
			}
		}
		
		RequestDispatcher disp;
		String page=(String) sessione.getAttribute("page");
		disp= req.getRequestDispatcher(page);
		disp.forward(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		
		//log-out
		session.removeAttribute("username");
		session.removeAttribute("loggato");
		session.removeAttribute("utente");
		String page=(String) session.getAttribute("page");
		
		RequestDispatcher disp;
		disp= req.getRequestDispatcher(page);
		disp.forward(req, resp);
	}
}
