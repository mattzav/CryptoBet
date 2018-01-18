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
		
		Enumeration<String> parametri = req.getParameterNames();
		
		// prendo il parametro per vedere che metodo è stato invocato
		String metodo = parametri.nextElement();

		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getSquadraDAO();
		ArrayList<Squadra> squadre = (ArrayList<Squadra>) squadraDao.findAllWithTitle();
		HttpSession sessione = req.getSession();

		// se metodo è uguale a "next" significa che devo spostare la pagina avanti
		if (metodo.equals("next")) {
			// se ho premuto su next e il tasto era disabilitato non eseguo alcuna operazione
			if (sessione.getAttribute("statoNext").equals("disabled")) {
				dispatcher.forward(req, resp);
				return;
			}
			// altrimenti aumento la pagina corrente di 1
			sessione.setAttribute("paginaCorrente", (Integer) sessione.getAttribute("paginaCorrente") + 1);
		} 
		// se metodo è uguale a "previous" significa che devo spostare la pagina indietro
		else if (metodo.equals("previous")) {
			// se il tasto premuto era disabilitato, non eseguo alcuna operazione
			if (sessione.getAttribute("statoPrevious").equals("disabled")) {
				dispatcher.forward(req, resp);
				return;
			}
			// altrimenti diminuisco la pagina corrente di 1
			sessione.setAttribute("paginaCorrente", (Integer) sessione.getAttribute("paginaCorrente") - 1);
		}
		
		List<Squadra> subList = new ArrayList<>();
		sessione.setAttribute("squadreVisibili", subList);

		// carico la nuova lista delle squadre di cui il client vedrà gli scudetti
		for (int i = 25 * ((Integer) sessione.getAttribute("paginaCorrente") - 1); i < Math.min(squadre.size(),
				25 * ((Integer) sessione.getAttribute("paginaCorrente"))); i++) {
			subList.add(squadre.get(i));
		}

		// se la nuova lista ha meno di 25 elementi devo disabilitare il tasto "next"
		if (subList.size() < 25)
			sessione.setAttribute("statoNext", "disabled");
		else
			sessione.setAttribute("statoNext", "");

		// se sono arrivato alla prima pagina devo disattivare il tasto "previous"
		if ((Integer) sessione.getAttribute("paginaCorrente") == 1)
			sessione.setAttribute("statoPrevious", "disabled");
		else
			sessione.setAttribute("statoPrevious", "");

		dispatcher.forward(req, resp);

	}

}
