package controller.handlematches;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import model.footballdata.Squadra;
import persistence.PostgresDAOFactory;
import persistence.dao.SquadraDao;

public class MostraPartiteEScudetti extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getSquadraDAO();
		ArrayList<Squadra> squadre = (ArrayList<Squadra>) squadraDao.findAllWithTitle();

		JsonObject jsonRisultato = new JsonObject();

		for (int i = 0; i < Math.min(25, squadre.size()); i++) {
			JsonObject squadra = new JsonObject();
			squadra.addProperty("nome", squadre.get(i).getNome());
			squadra.addProperty("scudetto", squadre.get(i).getScudetto());

			jsonRisultato.add("squadra" + String.valueOf(i), squadra);
		}

		JsonObject ultimaPagina = new JsonObject();
		
		if(squadre.size() < 25)
			ultimaPagina.addProperty("ultima", true);
		else
			ultimaPagina.addProperty("ultima", false);
		
		jsonRisultato.add("ultimaPagina", ultimaPagina);

		resp.getWriter().println(jsonRisultato.toString());

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// prendo il parametro per vedere il numero della pagina
		int numeroPagina = Integer.valueOf(((String) req.getParameter("numeroPagina")));
		
		
		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getSquadraDAO();
		ArrayList<Squadra> squadre = (ArrayList<Squadra>) squadraDao.findAllWithTitle();

		// creo il JSON da restituire
		JsonObject jsonRisultato = new JsonObject();

		int dimensione = 0;
		// carico la nuova lista delle squadre di cui il client vedrà gli scudetti
		for (int i = 25 * (numeroPagina - 1); i < Math.min(squadre.size(),
				25 * numeroPagina); i++) {
			
			//creo un oggetto json per ogni squadra
			JsonObject jsonSquadra = new JsonObject();
			jsonSquadra.addProperty("nome", squadre.get(i).getNome());
			jsonSquadra.addProperty("scudetto", squadre.get(i).getScudetto());
			
			//aggiungo al risultato finale, il json con la squadra appena creato
			jsonRisultato.add("squadra"+i, jsonSquadra);
			dimensione++;
		}

		JsonObject ultimaPagina = new JsonObject();
		
		// se la nuova lista ha meno di 25 elementi devo disabilitare il tasto premuto
		if (dimensione < 25)
			ultimaPagina.addProperty("ultima", true);
		else
			ultimaPagina.addProperty("ultima", false);

		jsonRisultato.add("ultimaPagina", ultimaPagina);

		// restituisco il risultato sotto forma di json al client
		resp.getWriter().println(jsonRisultato.toString());

	}

}
