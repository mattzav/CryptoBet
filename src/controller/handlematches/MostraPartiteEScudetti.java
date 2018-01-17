package controller.handlematches;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.footballdata.Squadra;
import persistence.PostgresDAOFactory;
import persistence.dao.SquadraDao;

public class MostraPartiteEScudetti extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getSquadraDAO();
		ArrayList<Squadra> squadre = (ArrayList<Squadra>) squadraDao.findAllWithTitle();
		req.getSession().setAttribute("squadreVisibili", squadre.subList(0, Math.min(25, squadre.size())));
		req.getSession().setAttribute("paginaCorrente", 1);

		RequestDispatcher dispatcher = req.getRequestDispatcher("SquadreCampionati.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String next_page = req.getParameter("method");
		String returned_value = "successo";
		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getSquadraDAO();
		ArrayList<Squadra> squadre = (ArrayList<Squadra>) squadraDao.findAllWithTitle();
		HttpSession session = req.getSession();
		session.removeAttribute("squadreVisibili");
		
		if (next_page.equals("true")) {
			session.setAttribute("paginaCorrente", (Integer) session.getAttribute("paginaCorrente") + 1);
		} else if (next_page.equals("false"))
			session.setAttribute("paginaCorrente", (Integer) session.getAttribute("paginaCorrente") - 1);
		
		System.out.println(session.getAttribute("paginaCorrente"));
		ArrayList<Squadra> subList = new ArrayList<>();
		
		for(int i = 25*((Integer)session.getAttribute("paginaCorrente")-1); i< Math.min(squadre.size(), 25*((Integer)session.getAttribute("paginaCorrente")));i++) {
			System.out.println(squadre.get(i).getNome());
			subList.add(squadre.get(i));
		}
		session.setAttribute("squadreVisibili",
				subList);
		resp.getWriter().write(returned_value);
		
		RequestDispatcher dispatcher = req.getRequestDispatcher("SquadreCampionati.jsp");
		dispatcher.forward(req, resp);
	}

}
