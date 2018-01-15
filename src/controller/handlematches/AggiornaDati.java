package controller.handlematches;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import model.footballdata.Campionato;
import model.footballdata.Esito;
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

		// se il parametro aggiorna ha valore Aggiorna significa che l'amministratore ha
		// richiesto un aggiornamento delle squadre e dei campionati
		if (req.getParameter("aggiorna").equals("Aggiorna")) {
			String squadre = req.getParameter("squadre");
			String campionati = req.getParameter("campionati");
			SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getSquadraDAO();
			CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();

			for (String s : squadre.split(";")) {
				squadraDao.save(new Squadra(s));
			}

			for (String s : campionati.split(";")) {
				int index = s.indexOf(":");
				String id = s.substring(0, index);

				// ignoriamo per scelta il campionato con codice 466
				if (id.equals("466"))
					continue;
				String caption = s.substring(index + 1, s.length());
				campionatoDao.save(new Campionato(Long.valueOf(id), caption));
			}
		}
		// se invece il parametro aggiorna ha valore AggiornaPartite significa che
		// l'amministratore ha richiesto un aggiornamento delle partite
		else if (req.getParameter("aggiorna").equals("Aggiorna Partite")) {
			PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
			String partite = req.getParameter("partite");
			for (String p : partite.split(";")) {
				String partita[] = p.split("@");
				if (partita[5].equals("CANCELED") || partita[0].equals("466"))
					continue;
				boolean finish = partita[5].equals("FINISHED");
				String data = partita[6].substring(0, 10) + " " + partita[6].substring(11, 19);
				java.sql.Date d = null;
				SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
				try {
					d = new java.sql.Date(localDateFormat.parse(data).getTime());

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				Partita a = null;
				System.out.println(partita[0]);
				Partita match = new Partita(new Squadra(partita[1]), new Squadra(partita[2]), -1, -1,
						new Campionato(Long.valueOf(partita[0]), null), new java.util.Date(d.getTime()), finish);
				if (!partita[3].equals("null") && !partita[4].equals("null")) {
					match.setGoal_casa(Integer.valueOf(partita[3]));
					match.setGoal_ospite(Integer.valueOf(partita[4]));
				}
				partitaDao.save(match);
			}
		}
		RequestDispatcher disp = req.getRequestDispatcher("gestisciPartite.jsp");
		disp.forward(req, resp);
	}
}