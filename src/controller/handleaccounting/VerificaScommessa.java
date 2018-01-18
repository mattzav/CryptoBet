package controller.handleaccounting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.betting.Scommessa;
import model.users.Giocatore;
import persistence.PostgresDAOFactory;
import persistence.dao.ScommessaDao;

public class VerificaScommessa extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		ScommessaDao scommessa = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getScommessaDao();
		HttpSession session=req.getSession();
		
		// prendo dalla richiesta il codice della scommessa e la verifico prendendomi l'esito
		String esito = scommessa.verificaScommessa(Long.valueOf(req.getParameter("codiceScommessa")));
		String response=esito;
		if(esito.equals("vinta")) {
			Giocatore user=(Giocatore) session.getAttribute("loggato");
			Scommessa scommessaVincente=scommessa.findByPrimaryKey(Long.valueOf(req.getParameter("codiceScommessa")));
			user.getConto().setSaldo(user.getConto().getSaldo()+scommessaVincente.getSchema_scommessa().getVincita_potenziale());
			response+=";"+user.getConto().getSaldo();
		}
		// restituisco l'esito al client
		resp.getWriter().write(response);
	}
}
