package controller.handlebetting;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.betting.SchemaDiScommessa;
import model.footballdata.EsitoPartita;
import model.footballdata.Partita;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.EsitoPartitaDao;
import persistence.dao.PartitaDao;

public class SelezionaCampionato extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession sessione = req.getSession();
		PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
		EsitoPartitaDao esitoPartitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getEsitoPartitaDao();

		ArrayList<String> campionatiAttivi = (ArrayList<String>) sessione.getAttribute("campionatiAttivi");
		if (campionatiAttivi == null) {
			campionatiAttivi = new ArrayList<>();
			sessione.setAttribute("campionatiAttivi", campionatiAttivi);
		}

		ArrayList<Partita> partiteAttive = (ArrayList<Partita>) sessione.getAttribute("partiteAttive");
		ArrayList<EsitoPartita> esitiAttivi = (ArrayList<EsitoPartita>) sessione.getAttribute("esitiAttivi");

		if (partiteAttive == null) {
			partiteAttive = new ArrayList<>();
			sessione.setAttribute("partiteAttive", partiteAttive);
		}

		if (esitiAttivi == null) {
			esitiAttivi = new ArrayList<>();
			sessione.setAttribute("esitiAttivi", esitiAttivi);
		}

		String campionatoCorrente = req.getParameter("campionato");
		SchemaDiScommessa schemaDiScommessa = (SchemaDiScommessa) sessione.getAttribute("schema");

		if (!(campionatoCorrente == null || campionatoCorrente.equals(""))) {

			// click su un campionato
			if (!campionatiAttivi.contains(campionatoCorrente)) {

				// aggiungi campionato
				campionatiAttivi.add(campionatoCorrente);
				Date data=new Date();
				Connection connessione = PostgresDAOFactory.dataSource.getConnection();
				for (Partita partita : partitaDao.findAll(campionatoCorrente, connessione)) {
					if(partita.getData_ora().getTime()<data.getTime())
						continue;
					ArrayList<EsitoPartita> listaEsiti = (ArrayList<EsitoPartita>) esitoPartitaDao
							.findByPartita(partita);
					
					for (EsitoPartita esito : listaEsiti) {
						if (schemaDiScommessa != null) {
							for (EsitoPartita esitoGiocato : schemaDiScommessa.getEsiti_giocati()) {
								if (esito.getPartita().getCodice().equals(esitoGiocato.getPartita().getCodice())
										&& esito.getEsito().getDescrizione()
												.equals(esitoGiocato.getEsito().getDescrizione())) {
									esito.setGiocato(true);
									break;
								}
							}
						}
					}
					esitiAttivi.addAll(listaEsiti);
					partiteAttive.add(partita);
				}
				try {
					if (connessione != null)
						connessione.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {

				// campionato gia selezionato
				campionatiAttivi.remove(campionatoCorrente);
				ArrayList<EsitoPartita> esitiDaEliminare = new ArrayList<>();

				for (EsitoPartita e : esitiAttivi)
					if (e.getPartita().getCampionato().getNome().equals(campionatoCorrente)) {
						esitiDaEliminare.add(e);
						partiteAttive.remove(e.getPartita());
					}

				esitiAttivi.removeAll(esitiDaEliminare);
			}
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("Scommetti.jsp");
		dispatcher.forward(req, resp);
	}
}
