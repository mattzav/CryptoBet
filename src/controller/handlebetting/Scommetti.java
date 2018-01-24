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

		SchemaDiScommessa schemaDiScommessa = (SchemaDiScommessa) sessione.getAttribute("schema");
			
		if (schemaDiScommessa == null) {
			schemaDiScommessa = new SchemaDiScommessa(1.0f, 1.0f, 0.0f, 0, 1.0f, new ArrayList<EsitoPartita>());
			sessione.setAttribute("schema", schemaDiScommessa);
		}
		
		String bottone = req.getParameter("bottone");
		//click su un esito
		if(bottone.equals("giocaScommessa")) {

			if (sessione.getAttribute("utente") == null) {
				resp.getWriter().print("Errore : Utente non loggato");
				return;
			} else if (schemaDiScommessa.getNumero_esiti() == 0) {
				resp.getWriter().print("Errore : Non e' possibile giocare una scommessa vuota");
				return;
			} else if (sessione.getAttribute("utente").equals("USER")) {
				
				Giocatore utente = (Giocatore) sessione.getAttribute("loggato");
				Conto contoUtente = utente.getConto();
				Connection connessione=PostgresDAOFactory.dataSource.getConnection();
				Date data =new Date();
				for(EsitoPartita esito:schemaDiScommessa.getEsiti_giocati()) {
					try {
						Partita partitaGiocata=partitaDao.findByPrimaryKey(esito.getPartita().getCodice(), connessione);
						if(partitaGiocata.getData_ora().getTime()<data.getTime()) {
							resp.getWriter().print("Errore : La partita "+partitaGiocata.getSquadra_casa().getNome()+" vs "+partitaGiocata.getSquadra_ospite().getNome()+" e' gia' iniziata, rimuovi l'esito selezionato");
							return;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (contoUtente.preleva((Float) sessione.getAttribute("importo"))) {
				
					//prendo la connection per assicurare l'atomicità
					try {
						if(connessione!=null)
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
						if(connessione!=null)
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
							if(connessione!=null)
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
		} 
	}

}
