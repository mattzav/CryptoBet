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

public class VersamentoSuConto extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		String importoStr=req.getParameter("importo");
		Giocatore user=(Giocatore) session.getAttribute("loggato");
		Conto contoUtente=user.getConto();
		CartaDiCredito carta=contoUtente.getCarta();
		Float importo=Float.valueOf(importoStr);
		if(carta.preleva(importo)) {
			contoUtente.versa(importo);

			//prendo una connessione unica
			Connection connection=PostgresDAOFactory.dataSource.getConnection();
			try {
				
				//corpo transazione 
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO().update(carta,connection);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO().update(contoUtente,connection);
				MovimentoCarta movimento=new MovimentoCarta(new java.util.Date(), TipoMovimento.VERSAMENTO, Float.valueOf(importo), contoUtente);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getMovimentoCartaDAO().save(movimento,connection);
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				if(connection!=null) {
					try {
						connection.rollback();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					resp.getWriter().print("Errore: Crollo della rete");
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
			//non ci sono stati errori
			resp.getWriter().print("Congratulazioni: versamento effettuato con successo;"+contoUtente.getSaldo());
		}
		else {
			resp.getWriter().print("Errore: importo non disponibile");
		}
	}
}
