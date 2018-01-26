package controller.handlematches;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import persistence.dao.PartitaDao;

public class AggiornaPartite extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession sessione = req.getSession();

		// prendo un'unica connessione per l'intera sessione di aggiornamento
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			System.out.println("aggiornamento partite in corso");
			if(connection!=null)
				connection.setAutoCommit(false);
			sessione.setAttribute("loadingPartite", true);

			PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();

			String partite = req.getParameter("partite");

			for (String p : partite.split(";")) {

				String partita[] = p.split("@");

				// ignoro le partite cancellate o relative al campionato 466, ignorato per
				// scelta.
				if (partita[5].equals("CANCELED") || partita[0].equals("466"))
					continue;

				// prendo tutti i dati relativi alla partita
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

				// salvo la partita
				partitaDao.save(match, connection);
				if(connection!=null)
					connection.commit();

			}
			System.out.println("aggiornamento partite concluso");
			sessione.removeAttribute("loadingPartite");
		} catch (SQLException e1) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} finally {
			try {
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
