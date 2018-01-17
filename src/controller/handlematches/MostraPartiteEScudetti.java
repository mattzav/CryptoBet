package controller.handlematches;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
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
		HttpSession session = req.getSession();
		session.removeAttribute("squadreVisibili");
		session.removeAttribute("paginaCorrente");
		session.removeAttribute("statoPrevious");
		session.removeAttribute("statoNext");

		session.setAttribute("squadreVisibili", squadre.subList(0, Math.min(25, squadre.size())));
		session.setAttribute("paginaCorrente", 1);
		session.setAttribute("statoPrevious", "disabled");
		session.setAttribute("statoNext", "");

		RequestDispatcher dispatcher = req.getRequestDispatcher("SquadreCampionati.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		RequestDispatcher dispatcher = req.getRequestDispatcher("SquadreCampionati.jsp");
		
		Enumeration<String> parameter = req.getParameterNames();
		String method = parameter.nextElement();

		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getSquadraDAO();
		ArrayList<Squadra> squadre = (ArrayList<Squadra>) squadraDao.findAllWithTitle();
		HttpSession session = req.getSession();

		if (method.equals("next")) {
			if (session.getAttribute("statoNext").equals("disabled")) {
				System.out.println("esco");
				dispatcher.forward(req, resp);
				return;
			}
			session.setAttribute("paginaCorrente", (Integer) session.getAttribute("paginaCorrente") + 1);
		} else if (method.equals("previous")) {
			if (session.getAttribute("statoPrevious").equals("disabled")) {
				dispatcher.forward(req, resp);
				return;
			}
			session.setAttribute("paginaCorrente", (Integer) session.getAttribute("paginaCorrente") - 1);
		}
		List<Squadra> subList = new ArrayList<>();
		session.setAttribute("squadreVisibili", subList);

		for (int i = 25 * ((Integer) session.getAttribute("paginaCorrente") - 1); i < Math.min(squadre.size(),
				25 * ((Integer) session.getAttribute("paginaCorrente"))); i++) {
			subList.add(squadre.get(i));
		}

		if (subList.size() < 25)
			session.setAttribute("statoNext", "disabled");
		else
			session.setAttribute("statoNext", "");

		if ((Integer) session.getAttribute("paginaCorrente") == 1)
			session.setAttribute("statoPrevious", "disabled");
		else
			session.setAttribute("statoPrevious", "");

		dispatcher.forward(req, resp);

	}

}
