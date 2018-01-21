package controller.handlebetting;

import java.io.IOException;
import java.util.ArrayList;

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

public class SelezionaEsito extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession sessione=req.getSession();
		
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

		SchemaDiScommessa schemaDiScommessa = (SchemaDiScommessa) sessione.getAttribute("schema");
		if (schemaDiScommessa == null) {
			schemaDiScommessa = new SchemaDiScommessa(1.0f, 1.0f, 0.0f, 0, 1.0f, new ArrayList<EsitoPartita>());
			sessione.setAttribute("schema", schemaDiScommessa);
		}

		String bottone = req.getParameter("bottone");
		if (bottone.contains(";")) {
				
				//bottone esito partita
				String[] datiEsitoSelezionato = bottone.split(";");
				Long codicePartita = Long.valueOf(datiEsitoSelezionato[0]);
				String esitoSelezionato = datiEsitoSelezionato[1];
				for (EsitoPartita esito : esitiAttivi) {
					String descricione = esito.getEsito().getDescrizione();
					if (esito.getPartita().getCodice().equals(codicePartita) && descricione.equals(esitoSelezionato)) {
						if (schemaDiScommessa.canAdd(esito)) {
							if (!esito.isGiocato()) {
								if (schemaDiScommessa.getNumero_esiti() >= 20) {
									resp.getWriter().print("Errore: Hai raggiunto il limite massimo");
									return;
								}
								//esito aggiunto
								schemaDiScommessa.addEsito(esito);
								esito.setGiocato(true);

							} else {
								
								//esito rimosso
								schemaDiScommessa.removeEsito(esito);
								esito.setGiocato(false);
							}
						} 
						
						//dati scommessa aggiornati
						resp.getWriter().print(esito.getPartita().getSquadra_casa().getNome() + ";"
								+ esito.getPartita().getSquadra_ospite().getNome() + ";" + esito.getQuota() + ";"
								+ schemaDiScommessa.getQuota_totale() + ";" + +schemaDiScommessa.getBonus() + ";"
								+ schemaDiScommessa.getVincita_potenziale());
						return;
					}
				}
			}
	}
}
