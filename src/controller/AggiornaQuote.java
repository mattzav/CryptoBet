package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Campionato;
import model.EsitoPartita;
import model.Partita;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.CampionatoDao;
import persistence.dao.EsitoPartitaDao;
import persistence.dao.PartitaDao;

public class AggiornaQuote extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();
		req.getSession().setAttribute("campionati", campionatoDao.findAll());
		RequestDispatcher dispatcher = req.getRequestDispatcher("gestisciEsiti.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		EsitoPartitaDao esitoPartitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getEsitoPartitaDao();
		PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
		
		HttpSession session = req.getSession();
		
		ArrayList<Partita> partiteAttive = (ArrayList<Partita>) session.getAttribute("partiteAttive");
		ArrayList<String> campionatiAttivi = (ArrayList<String>) session.getAttribute("campionatiAttivi");
		ArrayList<EsitoPartita> esitiAttivi = (ArrayList<EsitoPartita>) session.getAttribute("esitiAttivi");

		if(partiteAttive == null) {
			partiteAttive = new ArrayList<>();
			session.setAttribute("partiteAttive", partiteAttive);
		}

		if(esitiAttivi == null) {
			esitiAttivi = new ArrayList<>();
			session.setAttribute("esitiAttivi", esitiAttivi);
		}
		
		if(campionatiAttivi == null) {
			campionatiAttivi = new ArrayList<>();
			session.setAttribute("campionatiAttivi", campionatiAttivi);
		}
		
		String current = req.getParameter("campionato");
		System.out.println(current);
		if(!campionatiAttivi.contains(current)) {
			
			campionatiAttivi.add(current);
			for(Partita partita:partitaDao.findAll(current)) {
				esitiAttivi.addAll(esitoPartitaDao.findByPartita(partita));
				partiteAttive.add(partita);
			}
		}
		else {
			campionatiAttivi.remove(current);
			ArrayList<EsitoPartita> esitiDaEliminare = new ArrayList<>();
			
			
			for(EsitoPartita e:esitiAttivi)
				if(e.getPartita().getCampionato().getNome().equals(current)) {
					esitiDaEliminare.add(e);
					partiteAttive.remove(e.getPartita());
				}
			
			esitiAttivi.removeAll(esitiDaEliminare);
		}
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("gestisciEsiti.jsp");
		dispatcher.forward(req, resp);
		
	}
}
