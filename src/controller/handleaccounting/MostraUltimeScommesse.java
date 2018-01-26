package controller.handleaccounting;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import model.betting.Scommessa;
import model.users.Credenziali;
import model.users.Giocatore;
import persistence.PostgresDAOFactory;
import persistence.dao.GiocatoreDao;
import persistence.dao.ScommessaDao;

public class MostraUltimeScommesse extends HttpServlet{
		
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// prendo il giocatore che è loggato
		Giocatore giocatore = (Giocatore) req.getSession().getAttribute("loggato");
		
		ScommessaDao scommessaDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getScommessaDao();
		
		// prendo dal db tutte le scommesse di quel giocatore
		ArrayList<Scommessa> scommesse = (ArrayList<Scommessa>) scommessaDao.findAll(giocatore);
		
		PrintWriter writer = resp.getWriter();
		
		JsonObject json = new JsonObject();
		
		
		
		// invio al client tutte le scommesse relative a quel cliente
		int i=0;
		for(Scommessa s: scommesse) {
			
			JsonObject jsonScommessa = new JsonObject();
			
			jsonScommessa.addProperty("codice", s.getCodice());

			jsonScommessa.addProperty("data", s.getData_emissione().toString());

			jsonScommessa.addProperty("importo", s.getSchema_scommessa().getImporto_giocato());

			jsonScommessa.addProperty("numeroEsiti", s.getSchema_scommessa().getNumero_esiti());

			jsonScommessa.addProperty("vincitaPotenziale", s.getSchema_scommessa().getVincita_potenziale());

			jsonScommessa.addProperty("stato", s.getStato());
			
			json.add(String.valueOf(i++), jsonScommessa);
		}
		writer.println(json.toString());
	
	}
}
