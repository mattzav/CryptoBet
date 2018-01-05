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

import model.Esito;
import model.EsitoPartita;
import model.Partita;
import persistence.PostgresDAOFactory;
import persistence.dao.EsitoPartitaDao;
import persistence.dao.PartitaDao;

public class ModificaQuota extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		// premo sull'icona di modifica

		if (req.getParameterNames().nextElement().contains(";")) {
			String partitaSelezionata = req.getParameterNames().nextElement();
			session.setAttribute("quotaSelezionata",
					Long.valueOf(partitaSelezionata.substring(0, partitaSelezionata.length() - 1)));
			session.setAttribute("modificaQuota", true);
		} else {
			// controllo se ha premuto su "suggerimento quota"
			boolean suggerimento = false;
			Enumeration<String> attributi = req.getParameterNames();
			while (attributi.hasMoreElements()) {
				if (attributi.nextElement().equals("suggerimento")) {
					suggerimento = true;
					break;
				}
			}

			// se non ha premuto su suggerimento quota allora significa che ha
			if (!suggerimento) {
				boolean annulla = false;
				attributi = req.getParameterNames();
				while (attributi.hasMoreElements()) {
					if (attributi.nextElement().equals("annulla")) {
						annulla = true;
						break;
					}
				}
				if (!annulla) {
					String esito = req.getParameter("esitoScelto");
					float nuovaQuota = Float.valueOf((String) (req.getParameter("nuovaQuota")));
					EsitoPartitaDao esitoPartitaDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL)
							.getEsitoPartitaDao();
					EsitoPartita esitoPartita = esitoPartitaDao.findByPrimaryKey(new Esito(esito), new Partita(
							(long) (session.getAttribute("quotaSelezionata")), null, null, 0, 0, null, 0, false));
					esitoPartita.setQuota(nuovaQuota);
					esitoPartitaDao.update(esitoPartita);
					ArrayList<EsitoPartita> esitiAttivi = (ArrayList<EsitoPartita>) session.getAttribute("esitiAttivi");
					for (EsitoPartita e : esitiAttivi)
						if (e.getPartita().getCodice().equals(esitoPartita.getPartita().getCodice())
								&& e.getEsito().getDescrizione().equals(esitoPartita.getEsito().getDescrizione()))
							e.setQuota(esitoPartita.getQuota());
				} else {
					session.removeAttribute("quotaSelezionata");
					session.removeAttribute("modificaQuota");
				}
			}
			else {
				String esito = req.getParameter("esitoScelto");
				long codicePartita = (long) session.getAttribute("quotaSelezionata");
				boolean esitospeciale=true;
				if(esito.contains("1") || esito.contains("2") || esito.contains("X"))
					esitospeciale=false;
				PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getPartitaDao();
				int punti[]=partitaDao.getPuntiSquadre(codicePartita);
			}
		}

		RequestDispatcher dispatcher = req.getRequestDispatcher("gestisciEsiti.jsp");
		dispatcher.forward(req, resp);
	}
}
