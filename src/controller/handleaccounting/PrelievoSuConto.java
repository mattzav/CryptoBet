package controller.handleaccounting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.users.CartaDiCredito;
import model.users.Conto;
import model.users.Giocatore;
import model.users.MovimentoCarta;
import model.users.TipoMovimento;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class PrelievoSuConto extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sessione=req.getSession();
		
		String stringaImporto=req.getParameter("importo");
		Giocatore utente=(Giocatore) sessione.getAttribute("loggato");
		Conto contoUtente=utente.getConto();
		CartaDiCredito carta=contoUtente.getCarta();
		Float importo=Float.valueOf(stringaImporto);
		if(contoUtente.preleva(importo)) {
			carta.versa(importo);
			
			//prendo una connessione unica
			Connection connessione=PostgresDAOFactory.dataSource.getConnection();
			try {
				connessione.setAutoCommit(false);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
			
				//corpo transazione
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO().update(carta,connessione);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO().update(contoUtente,connessione);
				MovimentoCarta movimento=new MovimentoCarta(new java.util.Date(), TipoMovimento.PRELIEVO, Float.valueOf(importo), contoUtente);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getMovimentoCartaDAO().save(movimento,connessione);
				connessione.commit();
			} catch (SQLException e) {
			
				if(connessione!=null) {
					try {
						connessione.rollback();
						carta.preleva(importo);
						contoUtente.versa(importo);
						resp.getWriter().print("Errore: non e' stato possibile memorizzare l'operazione");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				return;
			}finally {
				
				try {
					connessione.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			resp.getWriter().print("Congratulazioni: prelievo effettuato con successo;"+contoUtente.getSaldo());
		}
		else {
			resp.getWriter().print("Errore: importo non disponibile");
		}
	}
}
