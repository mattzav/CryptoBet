package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Conto;
import model.Esito;
import model.EsitoPartita;
import model.Giocatore;
import model.MovimentoScommessa;
import model.Partita;
import model.SchemaDiScommessa;
import model.Scommessa;
import model.TipoMovimento;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.CampionatoDao;
import persistence.dao.ContoDao;
import persistence.dao.EsitoPartitaDao;
import persistence.dao.PartitaDao;

public class Scommetti extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();
		req.getSession().setAttribute("campionati", campionatoDao.findAll());
		req.getSession().removeAttribute("schema");
		req.getSession().removeAttribute("partiteAttive");
		req.getSession().removeAttribute("campionatiAttivi");
		req.getSession().removeAttribute("esitiAttivi");
		req.getSession().removeAttribute("campionato");
		req.getSession().removeAttribute("importo");
		ArrayList<Esito> esiti=(ArrayList<Esito>) PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getEsitoDao().findAll();
		req.getSession().setAttribute("esiti", esiti);
		RequestDispatcher dispatcher = req.getRequestDispatcher("Scommetti.jsp");
		dispatcher.forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
		SchemaDiScommessa schemaDiScommessa=(SchemaDiScommessa) session.getAttribute("schema");
		
		if(!(current==null || current.equals(""))) {
			
			//click su un campionato
			if(!campionatiAttivi.contains(current)) {
				
				//aggiungi campionato
				campionatiAttivi.add(current);
				for(Partita partita:partitaDao.findAll(current)) {
					ArrayList<EsitoPartita> listaEsiti=(ArrayList<EsitoPartita>) esitoPartitaDao.findByPartita(partita);
					for(EsitoPartita esito : listaEsiti) {
						if(schemaDiScommessa!=null) {
							for(EsitoPartita esitoGiocato:schemaDiScommessa.getEsiti_giocati()) {
								if(esito.getPartita().getCodice().equals(esitoGiocato.getPartita().getCodice()) && esito.getEsito().getDescrizione().equals(esitoGiocato.getEsito().getDescrizione()) )
								{
									System.out.println("giocato");
									esito.setGiocato(true);
									break;
								}
							}
						}
					}
					esitiAttivi.addAll(listaEsiti);
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
		}
		else {
			if(schemaDiScommessa==null) {
				schemaDiScommessa=new SchemaDiScommessa(1.0f, 1.0f, 0.0f, 0, 1.0f, new ArrayList<EsitoPartita>());
				session.setAttribute("schema", schemaDiScommessa);
			}
			String importo=req.getParameter("importo");
			if(importo!=null) {
				session.setAttribute("importo", Float.valueOf(importo));
				schemaDiScommessa.setImporto_giocato(Float.valueOf(importo));
				PrintWriter pw =resp.getWriter();
				pw.print(schemaDiScommessa.getVincita_potenziale());
				return;
			}
			else {
				String btn=req.getParameterNames().nextElement();
				if(btn.contains(";")) {
					String[] datiEsitoSelezionato=btn.split(";");
					Long codicePartita=Long.valueOf(datiEsitoSelezionato[0]);
					String esitoSelezionato=datiEsitoSelezionato[1];
					for(EsitoPartita esito:esitiAttivi) {
						String desc=esito.getEsito().getDescrizione()+" ";
						if(esito.getPartita().getCodice().equals(codicePartita) && desc.equals(esitoSelezionato)) {
							if(schemaDiScommessa.canAdd(esito)) {
								if(!esito.isGiocato()) {
									if(schemaDiScommessa.getNumero_esiti()>=20) {
										resp.getWriter().print("Errore: Hai raggiunto il limite massimo");
										return;
									}
									System.out.println("aggiunto");
									schemaDiScommessa.addEsito(esito);
									esito.setGiocato(true);
								}
								else {
									System.out.println("rimosso");
									schemaDiScommessa.removeEsito(esito);
									esito.setGiocato(false);
								}
							}
							else {
								System.out.println("esito già presente per questa partita");
							}
							resp.getWriter().print(esito.getPartita().getSquadra_casa().getNome()+";"
													+esito.getPartita().getSquadra_ospite().getNome()+";"
													+ esito.getQuota()+";"+schemaDiScommessa.getQuota_totale()+";"+
													+schemaDiScommessa.getBonus()+";"+schemaDiScommessa.getVincita_potenziale());
							return;
						}
					}
				}
				else if(btn.equals("giocaScommessa")) {
					System.out.println("gioca scommessa");
					if(session.getAttribute("utente")==null) {
						System.out.println("nessun utente");
						resp.getWriter().print("Errore : Utente non loggato");
						return;
					}
					else if(schemaDiScommessa.getNumero_esiti()==0) {
						resp.getWriter().print("Errore : Non è possibile giocare una scommessa vuota");
						return;
					}
					else if(session.getAttribute("utente").equals("USER")) {
						Giocatore utente= (Giocatore) session.getAttribute("loggato");
						Conto contoUtente=utente.getConto();
						if(contoUtente.preleva((Float) session.getAttribute("importo"))){
							session.removeAttribute("schema");
							PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getScommessaDao().save(new Scommessa(new Date(), contoUtente, schemaDiScommessa,"non verificata"));
							ContoDao contoDao=PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO();
							contoDao.update(contoUtente);
							
							for(EsitoPartita e:schemaDiScommessa.getEsiti_giocati()) {
								e.setGiocato(false);
							}
							
							resp.getWriter().print("ok");
						}
						else {
							resp.getWriter().print("Errore : credito non sufficente");
						}
						return;
					}else{
						System.out.println("ADMIN");
						resp.getWriter().print("Errore : utente loggato come admin");
						return;
					}
				}
				else if(btn.equals("svuota")) {
					session.removeAttribute("schema");
					for(EsitoPartita e:schemaDiScommessa.getEsiti_giocati()) {
						e.setGiocato(false);
					}
				}
			}
		}
		RequestDispatcher dispatcher = req.getRequestDispatcher("Scommetti.jsp");
		dispatcher.forward(req, resp);
		
	}

	private Partita getPartitaSelezionata(ArrayList<Partita> partiteAttive, String substring) {
		// TODO Auto-generated method stub
		System.out.println(Long.valueOf(substring));
		for(Partita p:partiteAttive) {
			System.out.println(substring+" "+p.getCodice()+" "+p.getSquadra_casa().getNome()+" "+p.getSquadra_ospite().getNome());
			if(p.getCodice().equals(Long.valueOf(substring)))
				return p;
		}
		System.out.println("non trovato");
		return null;
	}
}
