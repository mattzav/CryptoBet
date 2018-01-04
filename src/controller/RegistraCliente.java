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
		HttpSession session=req.getSession();
		session.setAttribute("username", username);
		session.setAttribute("mex", "Bevenuto "+username);
		session.setAttribute("loggato", true);
		RequestDispatcher disp=req.getRequestDispatcher("index.jsp");
		disp.forward(req, resp);
	}
}
