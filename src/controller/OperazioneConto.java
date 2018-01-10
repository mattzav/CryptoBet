package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.CartaDiCredito;
import model.Conto;
import model.Giocatore;
import model.MovimentoCarta;
import model.TipoMovimento;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class OperazioneConto extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String operazione=req.getParameter("operazione");
		HttpSession session=req.getSession();
		if(operazione.equals("versamento")) {
			String importoStr=req.getParameter("importo");
			Giocatore user=(Giocatore) session.getAttribute("loggato");
			Conto contoUtente=user.getConto();
			CartaDiCredito carta=contoUtente.getCarta();
			Float importo=Float.valueOf(importoStr);
			if(carta.preleva(importo)) {
				contoUtente.versa(importo);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCartaDiCreditoDAO().update(carta);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getContoDAO().update(contoUtente);
				MovimentoCarta movimento=new MovimentoCarta(new java.util.Date(), TipoMovimento.PRELIEVO, Float.valueOf(importo), contoUtente);
				PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getMovimentoCartaDAO().save(movimento);
			}
		}
	}
}
