package persistence;

import persistence.dao.AmministratoreDao;
import persistence.dao.CartaDiCreditoDao;
import persistence.dao.ContoDao;
import persistence.dao.CredenzialiDao;
import persistence.dao.GiocatoreDao;
import persistence.dao.MovimentoCartaDao;

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
		return new AmministratoreDaoJDBC(dataSource);
	}


	@Override
	public GiocatoreDao getGiocatoreDAO() {
		// TODO Auto-generated method stub
		return new GiocatoreDaoJDBC(dataSource);
	}


	@Override
	public CredenzialiDao getCredenzialiDAO() {
		// TODO Auto-generated method stub
		return new CredenzialiDaoJDBC(dataSource);
	}


	@Override
	public ContoDao getContoDAO() {
		// TODO Auto-generated method stub
		return new ContoDaoJDBC(dataSource);
	}


	@Override
	public CartaDiCreditoDao getCartaDiCreditoDAO() {
		// TODO Auto-generated method stub
		return new CartaDiCreditoDaoJDBC(dataSource);
	}


	@Override
	public MovimentoCartaDao getMovimentoCartaDAO() {
		// TODO Auto-generated method stub
		return new MovimentoCartaDaoJDBC(dataSource);
	}


	@Override
	public UtilDao getUtilDAO() {
		return new UtilDao(dataSource);
	}
	
	// --------------------------------------------
}