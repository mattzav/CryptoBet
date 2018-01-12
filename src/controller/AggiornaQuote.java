package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.jmx.snmp.Enumerated;

import model.Campionato;
import model.Esito;
import model.EsitoPartita;
import model.Partita;
import model.Squadra;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.CampionatoDao;
import persistence.dao.EsitoDao;
import persistence.dao.EsitoPartitaDao;
import persistence.dao.PartitaDao;

public class AggiornaQuote extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();
		req.getSession().setAttribute("campionati", campionatoDao.findAll());
		req.getSession().removeAttribute("esitiAttivi");
		req.getSession().removeAttribute("partiteAttive");
		req.getSession().removeAttribute("campionatiAttivi");
		req.getSession().removeAttribute("esiti");
		req.getSession().setAttribute("esiti", new String[]{"1","X","2","1X","12","X2","U","O","GG","NG"});
		req.getSession().removeAttribute("modificaQuota");
		RequestDispatcher dispatcher = req.getRequestDispatcher("gestisciEsiti.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		EsitoPartitaDao esitoPartitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getEsitoPartitaDao();
		PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
		EsitoDao esitoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getEsitoDao();
		
		String richiesta = req.getParameterNames().nextElement();
		HttpSession session = req.getSession();
		ArrayList<EsitoPartita> esitiAttivi = (ArrayList<EsitoPartita>) session.getAttribute("esitiAttivi");
		
		// se la richiesta non contiene il ; significa che ho selezionato un campionato
		if (!richiesta.contains(";")) {
			ArrayList<Partita> partiteAttive = (ArrayList<Partita>) session.getAttribute("partiteAttive");
			ArrayList<String> campionatiAttivi = (ArrayList<String>) session.getAttribute("campionatiAttivi");

			if (partiteAttive == null) {
				partiteAttive = new ArrayList<>();
				session.setAttribute("partiteAttive", partiteAttive);
			}

			if (esitiAttivi == null) {
				esitiAttivi = new ArrayList<>();
				session.setAttribute("esitiAttivi", esitiAttivi);
			}

			if (campionatiAttivi == null) {
				campionatiAttivi = new ArrayList<>();
				session.setAttribute("campionatiAttivi", campionatiAttivi);
			}

			String current = req.getParameter("campionato");
			
			// se il campionato passato nella richiesta non è attivo lo attivo e attivo tutte gli esiti relativi alle partite di quel campionato
			if (!campionatiAttivi.contains(current)) {

				campionatiAttivi.add(current);
				for (Partita partita : partitaDao.findAll(current)) {
					esitiAttivi.addAll(esitoPartitaDao.findByPartita(partita));
					partiteAttive.add(partita);
				}
			} 
			//se invece è attivo lo rimuovo e rimuovo tutti gli esiti relativi alle partite di quel campionato
			else {
				campionatiAttivi.remove(current);
				ArrayList<EsitoPartita> esitiDaEliminare = new ArrayList<>();

				for (EsitoPartita e : esitiAttivi)
					if (e.getPartita().getCampionato().getNome().equals(current)) {
						esitiDaEliminare.add(e);
						partiteAttive.remove(e.getPartita());
					}

				esitiAttivi.removeAll(esitiDaEliminare);
			}
		}
		// se contiene invece il ; significa che ho selezionato un esito e quindi devo abilitarlo se disabilitato o viceversa
		else {

			String valori[] = richiesta.split(";");
			String partita = valori[0];
			String esito = valori[1];

			EsitoPartita esitoPartita = esitoPartitaDao.findByPrimaryKey(new Esito(esito),
					new Partita(Long.valueOf(partita), null, null, 0, 0, null, 0, false));

			for (EsitoPartita e : esitiAttivi)
				if (e.getPartita().getCodice().equals(esitoPartita.getPartita().getCodice())
						&& e.getEsito().getDescrizione().equals(esitoPartita.getEsito().getDescrizione()))
					e.setDisponibile(!e.isDisponibile());

			esitoPartita.setDisponibile(!esitoPartita.isDisponibile());
			esitoPartitaDao.update(esitoPartita);

		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("gestisciEsiti.jsp");
		dispatcher.forward(req, resp);

	}
}
