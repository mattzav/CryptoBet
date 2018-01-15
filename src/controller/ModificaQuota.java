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

		// controllo se ha premuto su "suggerimento quota"
		boolean suggerimento = false;
		Enumeration<String> attributi = req.getParameterNames();
		while (attributi.hasMoreElements()) {
			if (attributi.nextElement().equals("suggerimento")) {
				suggerimento = true;
				break;
			}
		}
		// se non ha premuto su suggerimento quota allora significa che l'amministratore
		// ha deciso di modificarne una
		if (!suggerimento) {
			String esito = req.getParameter("esitoScelto");
			String quota_ = req.getParameter("nuovaQuota");
			float nuovaQuota = Float.valueOf(quota_.substring(0, Math.min(4, quota_.length())));
			EsitoPartitaDao esitoPartitaDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL)
					.getEsitoPartitaDao();

			// prendo dal DB l'esito partita selezionato, cambio la quota e invoco l'update
			EsitoPartita esitoPartita = esitoPartitaDao.findByPrimaryKey(new Esito(esito),
					new Partita(Long.valueOf(req.getParameter("partita")), null, null, 0, 0, null, 0, false));
			esitoPartita.setQuota(nuovaQuota);
			System.out.println(nuovaQuota);
			esitoPartitaDao.update(esitoPartita);
		} else {
			String esito = req.getParameter("esitoScelto");
			long codicePartita = Long.valueOf(req.getParameter("suggerimento"));
			PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getPartitaDao();
			float quota = partitaDao.getQuota(codicePartita, esito);
			resp.getWriter().write(String.valueOf(quota));
		}
	}
}
