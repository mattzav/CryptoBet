package controller.handleaccounting;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		
		// invio al client tutte le scommesse relative a quel cliente
		for(Scommessa s: scommesse) {
			writer.write(s.getCodice()+":"+s.getData_emissione()+":"+s.getSchema_scommessa().getImporto_giocato()+":"+s.getSchema_scommessa().getNumero_esiti()+":"+s.getSchema_scommessa().getVincita_potenziale()+":"+s.getStato()+";");
		}
	}
}
