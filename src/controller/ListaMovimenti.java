package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Giocatore;
import model.MovimentoCarta;
import model.MovimentoScommessa;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;

public class ListaMovimenti extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session=req.getSession();
		Giocatore user=(Giocatore) session.getAttribute("loggato");
		ArrayList<MovimentoCarta> movimentiCarta=(ArrayList<MovimentoCarta>) PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getMovimentoCartaDAO().findAll(user.getConto());
		ArrayList<MovimentoScommessa> movimentiScommessa=(ArrayList<MovimentoScommessa>) PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getMovimentoScommessaDAO().findAll(user.getConto());
		PrintWriter pw=resp.getWriter();
		for(MovimentoCarta movimento:movimentiCarta) {
			pw.println(movimento.getCodice()+";"+movimento.getImporto()+";"+movimento.getTipo());
		}
		for(MovimentoScommessa movimento:movimentiScommessa) {
			pw.println(movimento.getCodice_transazione()+";"+movimento.getImporto()+";"+movimento.getTipo_transazione()+";"+movimento.getScommessa().getCodice());
		}
	}
}
