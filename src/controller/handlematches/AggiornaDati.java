package controller.handlematches;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.footballdata.Campionato;
import model.footballdata.Partita;
import model.footballdata.Squadra;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.CampionatoDao;
import persistence.dao.PartitaDao;
import persistence.dao.SquadraDao;

public class AggiornaDati extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		RequestDispatcher disp = req.getRequestDispatcher("gestisciPartite.jsp");
		disp.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session =req.getSession();
		
		// se il parametro aggiorna ha valore Aggiorna significa che l'amministratore ha
		// richiesto un aggiornamento delle squadre e dei campionati
		if (req.getParameter("aggiorna").equals("Aggiorna")) {
			System.out.println("inizio aggiornamento");
			
			// aggiungo il loader nella session 
			session.setAttribute("loadingSquadre", true);
			
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
			session.removeAttribute("loadingSquadre");
			System.out.println("fine aggiornamento");

		}
		// se invece il parametro aggiorna ha valore AggiornaPartite significa che
		// l'amministratore ha richiesto un aggiornamento delle partite
		else if (req.getParameter("aggiorna").equals("Aggiorna Partite")) {
			
			// prendo un'unica connessione per l'intera sessione di aggiornamento
			Connection connection = PostgresDAOFactory.dataSource.getConnection();
			try {
				
				session.setAttribute("loadingPartite", true);
				
				PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
				
				String partite = req.getParameter("partite");
				
				for (String p : partite.split(";")) {
					
					String partita[] = p.split("@");
					
					// ignoro le partite cancellate o relative al campionato 466, ignorato per scelta.
					if (partita[5].equals("CANCELED") || partita[0].equals("466"))
						continue;
					
					//prendo tutti i dati relativi alla partita
					boolean finish = partita[5].equals("FINISHED");
					String data = partita[6].substring(0, 10) + " " + partita[6].substring(11, 19);
					java.sql.Date d = null;
					SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
					try {
						d = new java.sql.Date(localDateFormat.parse(data).getTime());
						
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
					// creo la partita
					Partita match = new Partita(new Squadra(partita[1]), new Squadra(partita[2]), -1, -1,
							new Campionato(Long.valueOf(partita[0]), null), new java.util.Date(d.getTime()), finish);
					
					if (!partita[3].equals("null") && !partita[4].equals("null")) {
						match.setGoal_casa(Integer.valueOf(partita[3]));
						match.setGoal_ospite(Integer.valueOf(partita[4]));
					}
					try {
						//salvo la partita
						partitaDao.save(match,connection);
					} catch (SQLException e) {
						if(connection!=null) {
							try {
								// eseguo il rollaback in caso d'eccezione
								connection.rollback();
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
				session.removeAttribute("loadingPartite");
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		RequestDispatcher disp = req.getRequestDispatcher("gestisciPartite.jsp");
		disp.forward(req, resp);
	}
}