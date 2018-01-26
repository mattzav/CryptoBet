package controller.handlebetting;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.betting.SchemaDiScommessa;
import model.footballdata.EsitoPartita;

public class InserisciImporto extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession sessione = req.getSession();

		SchemaDiScommessa schemaDiScommessa = (SchemaDiScommessa) sessione.getAttribute("schema");
			
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
			DecimalFormat formato=new DecimalFormat("#.##");
			pw.print(formato.format(schemaDiScommessa.getVincita_potenziale()) + ";" + formato.format(schemaDiScommessa.getBonus()));
			return;
			
		}
	}
}
