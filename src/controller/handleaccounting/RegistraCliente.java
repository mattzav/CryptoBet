package controller.handleaccounting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.users.CartaDiCredito;
import model.users.Conto;
import model.users.Credenziali;
import model.users.Giocatore;
import model.users.TipoCredenziali;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.CartaDiCreditoDao;

public class RegistraCliente extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sessione=req.getSession();
		
		rispristinaSessione(sessione,false);
		
		RequestDispatcher dispatcher=req.getRequestDispatcher("Registrati.html");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sessione=req.getSession();
		
		//parametri registrazione
		String nome = req.getParameter("name");
		String cognome = req.getParameter("surname");
		String codCarta =req.getParameter("creditCard");
		String password =req.getParameter("password");
		String username = req.getParameter("usr");
		
		if(req.getParameter("checkUsername")!=null) {
		
			//controllo username valido
			if(PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCredenzialiDAO().findByPrimaryKey(username)!=null) {
				resp.getWriter().print("errore");
				sessione.setAttribute("erroreUsername", true);
			}
			else {
				resp.getWriter().print("ok");
				sessione.setAttribute("erroreUsername", false);
			}
			return;
		}
		if(req.getParameter("checkCreditCard")!=null) {
			
			//controllo carta di credito
			if(PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO().findByPrimaryKey(codCarta)!=null) {
				sessione.setAttribute("erroreCreditCard", true);
				resp.getWriter().print("errore");
			}
			else {
				sessione.setAttribute("erroreCreditCard", false);
				resp.getWriter().print("ok");
			}
			return;
		}
		
		
		String errore = req.getParameterNames().nextElement();
		
		//notifica di un errore
		if(errore.contains("errore")) {
			boolean value=false;
			if(req.getParameter(errore).equals("true")) {
				value=true;
			}
			sessione.setAttribute(errore, value);
		}
		
		//controllo se ci sono errori
		boolean esisteErrore=false;
		Enumeration<String> erroriPresenti = sessione.getAttributeNames();
		while (erroriPresenti.hasMoreElements()) {
			
			String string = (String) erroriPresenti.nextElement();
			if(string.contains("errore")) {
				boolean value=(boolean) sessione.getAttribute(string);
				if(value) {
					esisteErrore=true;
				}
			}
		}
		
		if(!esisteErrore && req.getParameter("button")!=null) {
			
			//creo le credenziali del nuovo giocatore
			Credenziali nuovaCredenziale=new Credenziali(username,password);
			nuovaCredenziale.setTipo(TipoCredenziali.USER);

			//creo la carta di credito del nuovo giocatore
			CartaDiCredito carta=new CartaDiCredito(codCarta);
			CartaDiCreditoDao cartaDao=PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO();
			
			//creo il conto del nuovo giocatore con la carta di credito appena creata
			Conto conto=new Conto(5.0f,new java.util.Date(),carta);
			
			//creo il nuovo giocatore con nome cognome credenziali e conto 
			Giocatore nuovoGiocatore=new Giocatore(nome,cognome,nuovaCredenziale,conto);

			//prendo la connessione
			Connection connessione=PostgresDAOFactory.dataSource.getConnection();
			
			try {
			
				//salvataggio
				cartaDao.save(carta,connessione);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO().save(conto,connessione);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCredenzialiDAO().save(nuovaCredenziale,connessione);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getGiocatoreDAO().save(nuovoGiocatore,connessione);
			
			}catch (SQLException e) {
				if(connessione!=null) {
					try {
						connessione.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rispristinaSessione(sessione, false);
					return;
				}
			}finally {
				try {
					connessione.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String messaggio="Benvenuto "+username+"  ";
			sessione.setAttribute("username", username);
			sessione.setAttribute("mex", messaggio);
			sessione.setAttribute("loggato", nuovoGiocatore);
			sessione.setAttribute("utente", TipoCredenziali.USER);
			String page=(String) sessione.getAttribute("page");
			rispristinaSessione(sessione,true);

			RequestDispatcher dispatcher=req.getRequestDispatcher(page);
			dispatcher.forward(req, resp);
			return;
		}
		else if(esisteErrore && req.getParameter("button")!=null) {

			rispristinaSessione(sessione,false);
			RequestDispatcher dispatcher=req.getRequestDispatcher("Registrati.html");
			dispatcher.forward(req, resp);
		}
	}

	private void rispristinaSessione(HttpSession session,boolean checked) {
		
		if(!checked) {
			session.setAttribute("erroreName", true);
			session.setAttribute("erroreSurname", true);
			session.setAttribute("erroreCreditCard", true);
			session.setAttribute("erroreUsername", true);
			session.setAttribute("errorePassword", true);
			session.setAttribute("erroreCheckPassword", true);
			return;
		}
		session.removeAttribute("erroreName");
		session.removeAttribute("erroreSurname");
		session.removeAttribute("erroreCreditCard");
		session.removeAttribute("erroreUsername");
		session.removeAttribute("errorePassword");
		session.removeAttribute("erroreCheckPassword");

	}
}
