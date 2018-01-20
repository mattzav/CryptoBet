package controller.handlebetting;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.betting.MovimentoScommessa;
import model.betting.SchemaDiScommessa;
import model.betting.Scommessa;
import model.footballdata.Esito;
import model.footballdata.EsitoPartita;
import model.footballdata.Partita;
import model.users.Conto;
import model.users.Giocatore;
import model.users.TipoMovimento;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.CampionatoDao;
import persistence.dao.ContoDao;
import persistence.dao.EsitoPartitaDao;
import persistence.dao.PartitaDao;

public class Scommetti extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();
		
		req.getSession().setAttribute("campionati", campionatoDao.findAll());
		
		ArrayList<Esito> esiti = (ArrayList<Esito>) PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getEsitoDao().findAll();
		
		req.getSession().setAttribute("esiti", esiti);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("Scommetti.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		EsitoPartitaDao esitoPartitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getEsitoPartitaDao();
		PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
		HttpSession sessione = req.getSession();

		ArrayList<Partita> partiteAttive = (ArrayList<Partita>) sessione.getAttribute("partiteAttive");
		ArrayList<String> campionatiAttivi = (ArrayList<String>) sessione.getAttribute("campionatiAttivi");
		ArrayList<EsitoPartita> esitiAttivi = (ArrayList<EsitoPartita>) sessione.getAttribute("esitiAttivi");

		if (partiteAttive == null) {
			partiteAttive = new ArrayList<>();
			sessione.setAttribute("partiteAttive", partiteAttive);
		}

		if (esitiAttivi == null) {
			esitiAttivi = new ArrayList<>();
			sessione.setAttribute("esitiAttivi", esitiAttivi);
		}

		if (campionatiAttivi == null) {
			campionatiAttivi = new ArrayList<>();
			sessione.setAttribute("campionatiAttivi", campionatiAttivi);
		}

		String campionatoCorrente = req.getParameter("campionato");
		SchemaDiScommessa schemaDiScommessa = (SchemaDiScommessa) sessione.getAttribute("schema");

		if (!(campionatoCorrente == null || campionatoCorrente.equals(""))) {

			// click su un campionato
			if (!campionatiAttivi.contains(campionatoCorrente)) {

				// aggiungi campionato
				campionatiAttivi.add(campionatoCorrente);
				
				for (Partita partita : partitaDao.findAll(campionatoCorrente)) {
					ArrayList<EsitoPartita> listaEsiti = (ArrayList<EsitoPartita>) esitoPartitaDao.findByPartita(partita);
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
			} else {
				
				//campionato gia selezionato
				campionatiAttivi.remove(campionatoCorrente);
				ArrayList<EsitoPartita> esitiDaEliminare = new ArrayList<>();

				for (EsitoPartita e : esitiAttivi)
					if (e.getPartita().getCampionato().getNome().equals(campionatoCorrente)) {
						esitiDaEliminare.add(e);
						partiteAttive.remove(e.getPartita());
					}

				esitiAttivi.removeAll(esitiDaEliminare);
			}
		} else {
			
			if (schemaDiScommessa == null) {
				schemaDiScommessa = new SchemaDiScommessa(1.0f, 1.0f, 0.0f, 0, 1.0f, new ArrayList<EsitoPartita>());
				sessione.setAttribute("schema", schemaDiScommessa);
			}

			String importo = req.getParameter("importo");
			
			if (importo != null) {
				
				//inserimento importo
				sessione.setAttribute("importo", Float.valueOf(importo));
				schemaDiScommessa.setImporto_giocato(Float.valueOf(importo));
				PrintWriter pw = resp.getWriter();
				pw.print(schemaDiScommessa.getVincita_potenziale() + ";" + schemaDiScommessa.getBonus());
				return;
				
			} else {
				
				//click su un esito
				String bottone = req.getParameterNames().nextElement();
				if (bottone.contains(";")) {
					
					//bottone esito partita
					String[] datiEsitoSelezionato = bottone.split(";");
					Long codicePartita = Long.valueOf(datiEsitoSelezionato[0]);
					String esitoSelezionato = datiEsitoSelezionato[1];
					for (EsitoPartita esito : esitiAttivi) {
						String descricione = esito.getEsito().getDescrizione() + " ";
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
				} else if (bottone.equals("giocaScommessa")) {

					if (sessione.getAttribute("utente") == null) {
						resp.getWriter().print("Errore : Utente non loggato");
						return;
					} else if (schemaDiScommessa.getNumero_esiti() == 0) {
						resp.getWriter().print("Errore : Non e' possibile giocare una scommessa vuota");
						return;
					} else if (sessione.getAttribute("utente").equals("USER")) {
						
						Giocatore utente = (Giocatore) sessione.getAttribute("loggato");
						Conto contoUtente = utente.getConto();

						if (contoUtente.preleva((Float) sessione.getAttribute("importo"))) {
						
							//prendo la connection per assicurare l'atomicità
							Connection connessione=PostgresDAOFactory.dataSource.getConnection();
							try {
								connessione.setAutoCommit(false);
							} catch (SQLException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							try {
							
								//transazione e memorizzazione scommessa
								PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getScommessaDao()
										.save(new Scommessa(new Date(), contoUtente, schemaDiScommessa, "non verificata"),connessione);
								ContoDao contoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO();
								contoDao.update(contoUtente,connessione);
								connessione.commit();
							
							} catch (SQLException e1) {
								if(connessione!=null) {
									try {
										connessione.rollback();
									} catch (SQLException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
									contoUtente.versa(Float.valueOf((Float) sessione.getAttribute("importo")));
									resp.getWriter().println("Errore: non è stato possibile memorizzare l'operazione");
									return;
								}
							}finally {
								try {
									connessione.close();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							//ripristino della session
							sessione.removeAttribute("schema");
							for (EsitoPartita e : schemaDiScommessa.getEsiti_giocati()) {
								e.setGiocato(false);
							}
							resp.getWriter().println("ok");
							resp.getWriter().println(contoUtente.getSaldo());
							
						} else {
							resp.getWriter().println("Errore : credito non sufficente");
						}
						return;
						
					} else {
						resp.getWriter().println("Errore : utente loggato come admin");
						return;
					}
				} else if (bottone.equals("svuota")) {
					sessione.removeAttribute("schema");
					for (EsitoPartita e : schemaDiScommessa.getEsiti_giocati()) {
						e.setGiocato(false);
					}
				}
			}
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("Scommetti.jsp");
		dispatcher.forward(req, resp);

	}

}
