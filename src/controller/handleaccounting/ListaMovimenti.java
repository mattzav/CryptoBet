package controller.handleaccounting;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import jdk.nashorn.api.scripting.JSObject;
import model.betting.MovimentoScommessa;
import model.users.Giocatore;
import model.users.MovimentoCarta;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class ListaMovimenti extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sessione=req.getSession();
		
		Giocatore utente=(Giocatore) sessione.getAttribute("loggato");
		
		ArrayList<MovimentoCarta> movimentiCarta=(ArrayList<MovimentoCarta>) PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getMovimentoCartaDAO().findAll(utente.getConto());
		ArrayList<MovimentoScommessa> movimentiScommessa=(ArrayList<MovimentoScommessa>) PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getMovimentoScommessaDAO().findAll(utente.getConto());
		
		JsonObject risultato=new JsonObject();
		int i=0;
		if(movimentiCarta!=null)
			for(MovimentoCarta movimento:movimentiCarta) {
				int tipo=1;
				if(movimento.getTipo().equals("VERSAMENTO"))
					tipo=0;
				JsonObject movimentoCorrente=new JsonObject();
				movimentoCorrente.addProperty("codice", movimento.getCodice());
				movimentoCorrente.addProperty("importo", movimento.getImporto());
				movimentoCorrente.addProperty("tipo", tipo);
				risultato.add(String.valueOf(i), movimentoCorrente);
				i++;
			}
		
		if(movimentiScommessa!=null)
			for(MovimentoScommessa movimento:movimentiScommessa) {
				JsonObject movimentoCorrente=new JsonObject();
				movimentoCorrente.addProperty("codice", movimento.getCodice_transazione());
				movimentoCorrente.addProperty("importo", movimento.getImporto());
				movimentoCorrente.addProperty("tipo", movimento.getTipo_transazione());
				movimentoCorrente.addProperty("scommessa", movimento.getScommessa().getCodice());
				risultato.add(String.valueOf(i), movimentoCorrente);
				i++;
			}
		resp.getWriter().println(risultato.toString());
	}
}
