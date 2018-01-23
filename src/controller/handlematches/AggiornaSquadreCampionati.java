package controller.handlematches;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.footballdata.Campionato;
import model.footballdata.Squadra;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.CampionatoDao;
import persistence.dao.SquadraDao;

public class AggiornaSquadreCampionati extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession sessione=req.getSession();
		sessione.setAttribute("loadingSquadre", true);
		
		// prendo le squadre che il client ha passato come parametro
		String squadre = req.getParameter("squadre");
		

		// prendo i campionati che il client ha passato come parametro
		String campionati = req.getParameter("campionati");
		

		// prendo gli scudetti che il client ha passato come parametro
		String scudetti = req.getParameter("scudetti");
		
		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getSquadraDAO();
		CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();

		// prendo un'unica connessione per l'intera sessione di aggiornamento per evitare di effettuare troppe connessioni al db
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			
			// salvo tutte le squadre
			for (String s : squadre.split(";")) {
				squadraDao.save(new Squadra(s),connection);
			}
			System.out.println("ho finito le squadre");
			
			// salvo tutti gli scudetti relative alle squadre
			for(String s:scudetti.split("endSquadra")) {
				String squadra_scudetto[] = s.split("endScudetto");
				Squadra squadra_con_scudetto = new Squadra(squadra_scudetto[0]);
				squadra_con_scudetto.setScudetto(squadra_scudetto[1]);
				squadraDao.update(squadra_con_scudetto, connection);
			}
			


			// salvo tutti i campionati
			for (String s : campionati.split(";")) {
				int index = s.indexOf(":");
				String id = s.substring(0, index);

				// ignoriamo per scelta il campionato con codice 466
				if (id.equals("466"))
					continue;
				
				String nome_campionato = s.substring(index + 1, s.length());
				campionatoDao.save(new Campionato(Long.valueOf(id), nome_campionato),connection);
			}
		}catch (SQLException e) {
			if(connection!=null) {
				try {
					// se qualcosa genera eccezione eseguo il rollback
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			throw new RuntimeException("Errore di connessione");
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		sessione.removeAttribute("loadingSquadre");
	}
}
