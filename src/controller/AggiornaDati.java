package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import model.Campionato;
import model.Partita;
import model.Squadra;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.CampionatoDao;
import persistence.dao.PartitaDao;
import persistence.dao.SquadraDao;

public class AggiornaDati extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getSession().getAttribute("squadre"));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getParameter("aggiorna").equals("Aggiorna")) {
			String squadre = req.getParameter("squadre");
			System.out.println(squadre);
			String campionati = req.getParameter("campionati");
			System.out.println(campionati + "ciao");
			SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getSquadraDAO();
			CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();

			for (String s : squadre.split(";")) {
				squadraDao.save(new Squadra(s));
			}

			for (String s : campionati.split(";")) {
				int index = s.indexOf(":");

				String id = s.substring(0, index);
				String caption = s.substring(index + 1, s.length());

				campionatoDao.save(new Campionato(Long.valueOf(id), caption));
			}
		}
		else if (req.getParameter("aggiorna").equals("Aggiorna Partite")) {
			PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
			String partite = req.getParameter("partite");
			for(String p: partite.split(";")){
				String partita[] = p.split("£");
				boolean finish = partita[5] == "FINISHED";	
				java.util.Date date = new java.util.Date(Integer.valueOf(partita[6].substring(0, 4))-1900, Integer.valueOf(partita[6].substring(5, 7)), Integer.valueOf(partita[6].substring(8, 10)), Integer.valueOf(partita[6].substring(11, 13)), Integer.valueOf(partita[6].substring(14, 16)), Integer.valueOf(partita[6].substring(17, 19)));
				java.sql.Date date2 = new Date(date.getTime());
				System.out.println(date);
				System.out.println(date2);
			}
		}
		RequestDispatcher disp = req.getRequestDispatcher("gestisciPartite.jsp");
		disp.forward(req, resp);
	}
}