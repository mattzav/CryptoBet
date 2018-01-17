package controller.handleaccounting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.users.CartaDiCredito;
import model.users.Conto;
import model.users.Giocatore;
import model.users.MovimentoCarta;
import model.users.TipoMovimento;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class PrelievoSuConto extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		String importoStr=req.getParameter("importo");
		Giocatore user=(Giocatore) session.getAttribute("loggato");
		Conto contoUtente=user.getConto();
		CartaDiCredito carta=contoUtente.getCarta();
		Float importo=Float.valueOf(importoStr);
		if(contoUtente.preleva(importo)) {
			carta.versa(importo);
			resp.getWriter().print("Congratulazioni: prelievo effettuato con successo;"+contoUtente.getSaldo());
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO().update(carta);
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO().update(contoUtente);
			MovimentoCarta movimento=new MovimentoCarta(new java.util.Date(), TipoMovimento.PRELIEVO, Float.valueOf(importo), contoUtente);
			PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getMovimentoCartaDAO().save(movimento);
		}
		else {
			resp.getWriter().print("Errore: importo non disponibile");
		}
	}
}
