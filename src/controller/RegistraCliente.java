package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartaDiCredito;
import model.Conto;
import model.Credenziali;
import model.Giocatore;
import model.TipoCredenziali;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class RegistraCliente extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String nome = req.getParameter("name");
		String cognome = req.getParameter("surname");
		String codCarta =req.getParameter("creditCard");
		String password =req.getParameter("password");
		String username = req.getParameter("usr");
		System.out.println(req.getParameter("checkUsername")+" "+username);
		
		if(req.getParameter("checkUsername")!=null) {
			if(username.equals("") ||PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCredenzialiDAO().findByPrimaryKey(username)!=null) {
				resp.getWriter().print("errore");
			}
			else {
				resp.getWriter().print("ok");
				
			}
			return;
		}
		HttpSession session=req.getSession();
		String error = req.getParameter("errore");
		if(error!=null) {
			if(error.equals("true")) {
				System.out.println("c'è un errore");
				session.setAttribute("errore", true);
			}
			else session.removeAttribute("errore");
			return;
		}
		boolean existError=false;
		if (session.getAttribute("errore")!=null) {
			existError=(boolean) session.getAttribute("errore");
		}
		if(!existError) {
			//creo le credenziali del nuovo giocatore
			Credenziali nuovaCredenziale=new Credenziali(username,password);
			nuovaCredenziale.setTipo(TipoCredenziali.USER);
			//creo la carta di credito del nuovo giocatore
			CartaDiCredito carta=new CartaDiCredito(codCarta);
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO().save(carta);
			//creo il conto del nuovo giocatore con la carta di credito appena creata
			Conto conto=new Conto(5.0f,new java.util.Date(),carta);
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO().save(conto);
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCredenzialiDAO().save(nuovaCredenziale);
			//creo il nuovo giocatore con nome cognome credenziali e conto 
			Giocatore nuovoGiocatore=new Giocatore(nome,cognome,nuovaCredenziale,conto);
			
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getGiocatoreDAO().save(nuovoGiocatore);
			session.setAttribute("username", username);
			session.setAttribute("mex", "Bevenuto "+username);
			session.setAttribute("loggato", nuovoGiocatore);
		}
		RequestDispatcher dispatcher=req.getRequestDispatcher("Registrati.html");
		dispatcher.forward(req, resp);
	}
}
