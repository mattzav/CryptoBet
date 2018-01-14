package controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

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
import persistence.dao.CartaDiCreditoDao;

public class RegistraCliente extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session=req.getSession();
//		session.removeAttribute("erroreName");
//		session.removeAttribute("erroreSurname");
//		session.removeAttribute("erroreCreditCard");
//		session.removeAttribute("erroreUsername");
//		session.removeAttribute("errorePassword");
//		session.removeAttribute("erroreCheckPassword");
		session.setAttribute("erroreName", true);
		session.setAttribute("erroreSurname", true);
		session.setAttribute("erroreCreditCard", true);
		session.setAttribute("erroreUsername", true);
		session.setAttribute("errorePassword", true);
		session.setAttribute("erroreCheckPassword", true);
		System.out.println("inizio"+session.getAttribute("erroreName"));
		RequestDispatcher dispatcher=req.getRequestDispatcher("Registrati.html");
		dispatcher.forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		String nome = req.getParameter("name");
		String cognome = req.getParameter("surname");
		String codCarta =req.getParameter("creditCard");
		String password =req.getParameter("password");
		String username = req.getParameter("usr");
		
		if(req.getParameter("checkUsername")!=null) {
			if(PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCredenzialiDAO().findByPrimaryKey(username)!=null) {
				resp.getWriter().print("errore");
				session.setAttribute("erroreUsername", true);
			}
			else {
				resp.getWriter().print("ok");
				session.setAttribute("erroreUsername", false);
			}
			return;
		}
		if(req.getParameter("checkCreditCard")!=null) {
			if(PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO().findByPrimaryKey(codCarta)!=null) {
				session.setAttribute("erroreCreditCard", true);
				resp.getWriter().print("errore");
			}
			else {
				session.setAttribute("erroreCreditCard", false);
				resp.getWriter().print("ok");
			}
			return;
		}
		String error = req.getParameterNames().nextElement();
		if(error.contains("errore")) {
			boolean value=false;
			if(req.getParameter(error).equals("true")) {
				value=true;
			}
			session.setAttribute(error, value);
		}
			
		boolean existError=false;
		Enumeration<String> occursErrors = session.getAttributeNames();
		while (occursErrors.hasMoreElements()) {
			String string = (String) occursErrors.nextElement();
			if(string.contains("errore")) {
				boolean value=(boolean) session.getAttribute(string);
				if(value) {
					System.out.println(string);
					existError=true;
				}
			}
		}
		if(!existError && req.getParameter("button")!=null) {
			
			//creo le credenziali del nuovo giocatore
			Credenziali nuovaCredenziale=new Credenziali(username,password);
			nuovaCredenziale.setTipo(TipoCredenziali.USER);
			//creo la carta di credito del nuovo giocatore
			CartaDiCredito carta=new CartaDiCredito(codCarta);
			CartaDiCreditoDao cartaDao=PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO();
			cartaDao.save(carta);
			//creo il conto del nuovo giocatore con la carta di credito appena creata
			Conto conto=new Conto(5.0f,new java.util.Date(),carta);
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO().save(conto);
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCredenzialiDAO().save(nuovaCredenziale);
			//creo il nuovo giocatore con nome cognome credenziali e conto 
			Giocatore nuovoGiocatore=new Giocatore(nome,cognome,nuovaCredenziale,conto);
			
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getGiocatoreDAO().save(nuovoGiocatore);
			String messaggio="Benvenuto "+username+"  ";
			session.setAttribute("username", username);
			session.setAttribute("mex", messaggio);
			session.setAttribute("loggato", nuovoGiocatore);
			session.setAttribute("utente", TipoCredenziali.USER);
			String page=(String) session.getAttribute("page");
			RequestDispatcher dispatcher=req.getRequestDispatcher(page);
			dispatcher.forward(req, resp);
			rispristinaSessione(session,true);
		}
		else if(existError && req.getParameter("button")!=null) {
			System.out.println("rispistino con errori");
			rispristinaSessione(session,false);
		}
		RequestDispatcher dispatcher=req.getRequestDispatcher("Registrati.html");
		dispatcher.forward(req, resp);
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
