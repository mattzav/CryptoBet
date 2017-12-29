package controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.HttpJspPage;

import model.Squadra;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.SquadraDao;



public class AggiornaDati extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
<<<<<<< HEAD
		System.out.println(req.getSession().getAttribute("squadre"));
=======
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String squadre=req.getParameter("squadre");
		SquadraDao squadraDao=PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getSquadraDAO();
		for(String s:squadre.split(";")) {
			squadraDao.save(new Squadra(s));
		}
		RequestDispatcher disp=req.getRequestDispatcher("gestisciPartite.jsp");
		disp.forward(req, resp);
>>>>>>> 3fbb51d173024f397909ac8497bc42a661499997
	}
}