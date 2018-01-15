package controller.handlematches;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.footballdata.Esito;
import model.footballdata.EsitoPartita;
import model.footballdata.Partita;
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
		// se non ha premuto su suggerimento quota allora significa che l'amministratore ha deciso di modificarne una
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
			String tipo_esito ="";
		
			// controllo riguardo che tipo di esito il client ha chiesto il suggerimento
			if (esito.contains("1") || esito.contains("2") || esito.contains("X"))
				tipo_esito += "classico";
			else if (esito.contains("G"))
				tipo_esito += "goal";
			else
				tipo_esito += "numerogoal";

			PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getPartitaDao();

			
			if (tipo_esito.equals("classico")) {
				
				//se ha chiesto un suggerimento riguardo gli esiti classici, chiedo al db la media punti delle due squadre
				float punti[] = partitaDao.getPuntiSquadre(codicePartita);

				float probabilita_eventi[] = new float[3];
				
				// se le due squadre hanno media punti pari a 0, inizializzo le probabilita
				if(punti[0] == 0 && punti[1] == 0) {
					probabilita_eventi[0]=0.5f;
					probabilita_eventi[2]=0.5f;
					probabilita_eventi[1]=1/2.5f;
				}
				// altrimenti le calcolo come proporzioni delle due medie punti
				else {
					probabilita_eventi[0] =(float)( punti[0] / (punti[0] + punti[1]));
					probabilita_eventi[1] =(float)( 1/(3.0f + (Math.abs(punti[0] - punti[1]))));
					probabilita_eventi[2] =(float)( punti[1] / (punti[0] + punti[1]));
				}
				
				float probabilita = 0;

				
				// calcolo la probabilità dell'esito come somma delle probabilità degli esiti giocati
				if (esito.contains("1"))
					probabilita += probabilita_eventi[0];
				if (esito.contains("X"))
					probabilita += probabilita_eventi[1];
				if (esito.contains("2"))
					probabilita += probabilita_eventi[2];

				//evito di avere una probabilita pari a 0 che originirebbe quota infinita o una probabilita pari a 1 che originirebbe una quota pari a 1
				if(probabilita == 0)
					probabilita=0.1f;
				else if (probabilita >= 1)
					probabilita=0.9f;
				
				//calcolo il valore atteso (quota finale) 
				float valore_atteso = (1/probabilita);
				
				//la restituisco al client
				resp.getWriter().write(String.valueOf(valore_atteso).substring(0,Math.min(4, String.valueOf(valore_atteso).length())));
			}else if (tipo_esito.equals("goal")) {
				
				//se ha chiesto un suggerimento riguardo gli esiti (GG o NG), chiedo al db la media delle partite in cui le due squadre hanno segnato almeno un goal
				float media_partite_a_segno[] = partitaDao.getMediaPartiteASegno(codicePartita);
				
				float probabilita = 0;	
				
				// calcolo la probabilita del GG come il prodotto delle medie delle partite in cui entrambi hanno segnato
				if(esito.equals("GG"))
					probabilita=media_partite_a_segno[0]*media_partite_a_segno[1];
				// calcolo la probabilita del NG come (1-P(GG))
				else if(esito.equals("NG"))
					probabilita=(1-(media_partite_a_segno[0]*media_partite_a_segno[1]));
				
				//evito probabilita pari a 0 o maggiori di 1
				if(probabilita == 0)
					probabilita= 0.1f;
				else if(probabilita >= 1)
					probabilita = 0.9f;
				
				float valore_atteso = (1/probabilita);
				resp.getWriter().write(String.valueOf(valore_atteso).substring(0,Math.min(4, String.valueOf(valore_atteso).length())));

			}
			else if(tipo_esito.equals("numerogoal")) {
				
				//se ha chiesto un suggerimento riguardo gli esiti (U o O), chiedo al db la media goal delle due squadre
				float media_goal_a_partita[] = partitaDao.getMediaGoal(codicePartita);
			
				float probabilita = 0;
				
				//calcolo la probabilita dell'under
				if(esito.equals("U")) {
					probabilita = 0.5f+((3-(media_goal_a_partita[0]+media_goal_a_partita[1]))/10);
				}
				//altrimenti quella dell'over
				else {
					probabilita = 0.5f+(((media_goal_a_partita[0]+media_goal_a_partita[1])-3)/10);
				}
				
				//evito probabilita pari a 0 o maggiori di 1
				if(probabilita == 0)
					probabilita= 0.1f;
				else if(probabilita >= 1)
					probabilita = 0.9f;
				
				//calcolo il valore atteso che sarebbe la quota finale
				float valore_atteso = (1/probabilita);
				
				//restituisco la quota al client
				resp.getWriter().write(String.valueOf(valore_atteso).substring(0,Math.min(4, String.valueOf(valore_atteso).length())));

			}

		}
	}

}
