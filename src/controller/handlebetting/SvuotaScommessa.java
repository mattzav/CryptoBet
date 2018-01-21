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

public class SvuotaScommessa extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sessione = req.getSession();
		String bottone= req.getParameter("bottone");

		SchemaDiScommessa schemaDiScommessa = (SchemaDiScommessa) sessione.getAttribute("schema");
		
		if (schemaDiScommessa == null) {
			schemaDiScommessa = new SchemaDiScommessa(1.0f, 1.0f, 0.0f, 0, 1.0f, new ArrayList<EsitoPartita>());
			sessione.setAttribute("schema", schemaDiScommessa);
		}

		
		if(bottone.equals("svuota")) {
			sessione.removeAttribute("schema");
			for (EsitoPartita e : schemaDiScommessa.getEsiti_giocati()) {
				e.setGiocato(false);
			}
			sessione.removeAttribute("importo");
		}
	}
}
