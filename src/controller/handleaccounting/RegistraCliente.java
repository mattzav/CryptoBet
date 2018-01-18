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
		// TODO Auto-generated method stub
		HttpSession session=req.getSession();
		rispristinaSessione(session,false);
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
			
			//creo il conto del nuovo giocatore con la carta di credito appena creata
			Conto conto=new Conto(5.0f,new java.util.Date(),carta);
			
			//creo il nuovo giocatore con nome cognome credenziali e conto 
			Giocatore nuovoGiocatore=new Giocatore(nome,cognome,nuovaCredenziale,conto);

			//prendo la connessione
			Connection connection=PostgresDAOFactory.dataSource.getConnection();
			
			try {
				//salvataggio
				cartaDao.save(carta,connection);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO().save(conto,connection);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCredenzialiDAO().save(nuovaCredenziale,connection);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getGiocatoreDAO().save(nuovoGiocatore,connection);
			}catch (SQLException e) {
				if(connection!=null) {
					try {
						connection.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					rispristinaSessione(session, false);
					return;
				}
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
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
		String page=(String) session.getAttribute("page");
		RequestDispatcher dispatcher=req.getRequestDispatcher(page);
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
