package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import persistence.PostgresDAOFactory;
import persistence.dao.ScommessaDao;

public class VerificaScommessa extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ScommessaDao scommessa = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getScommessaDao();
		
		// prendo dalla richiesta il codice della scommessa e la verifico prendendomi l'esito
		String esito = scommessa.verificaScommessa(Long.valueOf(req.getParameter("codiceScommessa")));
		System.out.println(esito);
		
		// restituisco l'esito al client
		resp.getWriter().write(esito);
	}
}
