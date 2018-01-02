package persistence;

import persistence.dao.AmministratoreDao;
import persistence.dao.CampionatoDao;
import persistence.dao.CartaDiCreditoDao;
import persistence.dao.ContoDao;
import persistence.dao.CredenzialiDao;
import persistence.dao.GiocatoreDao;
import persistence.dao.MovimentoCartaDao;
import persistence.dao.PartitaDao;
import persistence.dao.SquadraDao;

public class PostgresDAOFactory extends DAOFactory {

	
	
	public static  DataSource dataSource;
	

	// --------------------------------------------

	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			dataSource=new DataSource("jdbc:postgresql://localhost:5432/CryptoBet","postgres","postgres");
		} 
		catch (Exception e) {
			System.err.println("PostgresDAOFactory.class: failed to load MySQL JDBC driver\n"+e);
			e.printStackTrace();
		}
	}


	@Override
	public AmministratoreDao getAmministratoreDAO() {
		// TODO Auto-generated method stub
		return new AmministratoreDaoJDBC();
	}


	@Override
	public GiocatoreDao getGiocatoreDAO() {
		// TODO Auto-generated method stub
		return new GiocatoreDaoJDBC();
	}


	@Override
	public CredenzialiDao getCredenzialiDAO() {
		// TODO Auto-generated method stub
		return new CredenzialiDaoJDBC();
	}


	@Override
	public ContoDao getContoDAO() {
		// TODO Auto-generated method stub
		return new ContoDaoJDBC();
	}


	@Override
	public CartaDiCreditoDao getCartaDiCreditoDAO() {
		// TODO Auto-generated method stub
		return new CartaDiCreditoDaoJDBC();
	}


	@Override
	public MovimentoCartaDao getMovimentoCartaDAO() {
		// TODO Auto-generated method stub
		return new MovimentoCartaDaoJDBC();
	}


	@Override
	public UtilDao getUtilDAO() {
		return new UtilDao();
	}


	@Override
	public SquadraDao getSquadraDAO() {
		// TODO Auto-generated method stub
		return new SquadraDaoJDBC();
	}


	@Override
	public CampionatoDao getCampionatoDao() {
		return new CampionatoDaoJDBC();
	}


	@Override
	public PartitaDao getPartitaDao() {
		return new PartitaDaoJDBC();
	}
	
	// --------------------------------------------
}