package controller.handlematches;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.footballdata.Squadra;
import persistence.PostgresDAOFactory;
import persistence.dao.SquadraDao;

public class MostraPartiteEScudetti extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getSquadraDAO();
		ArrayList<Squadra> squadre = (ArrayList<Squadra>) squadraDao.findAll();
		req.getSession().setAttribute(
	}

}
