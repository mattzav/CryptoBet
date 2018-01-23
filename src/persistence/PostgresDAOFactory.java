package persistence;

import persistence.dao.AmministratoreDao;
import persistence.dao.CampionatoDao;
import persistence.dao.CartaDiCreditoDao;
import persistence.dao.ContoDao;
import persistence.dao.CredenzialiDao;
import persistence.dao.EsitoDao;
import persistence.dao.EsitoPartitaDao;
import persistence.dao.GiocatoreDao;
import persistence.dao.MovimentoCartaDao;
import persistence.dao.MovimentoScommessaDao;
import persistence.dao.PartitaDao;
import persistence.dao.ScommessaDao;
import persistence.dao.SquadraDao;

public class PostgresDAOFactory extends DAOFactory {
	
	public static  DataSource dataSource;

	static {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			//locale
//			dataSource=new DataSource("jdbc:postgresql://localhost:5432/CryptoBet","postgres","postgres");
			//remoto
			dataSource=new DataSource("jdbc:postgresql://horton.elephantsql.com:5432/ncutkecf", "ncutkecf","r0DcxBUKPFHyZWjz2WW5qs-t-IhVdV79");
		} 
		catch (Exception e) {
			System.err.println("PostgresDAOFactory.class: failed to load MySQL JDBC driver\n"+e);
			e.printStackTrace();
		}
	}


	@Override
	public AmministratoreDao getAmministratoreDAO() {
		return new AmministratoreDaoJDBC();
	}


	@Override
	public GiocatoreDao getGiocatoreDAO() {
		return new GiocatoreDaoJDBC();
	}


	@Override
	public CredenzialiDao getCredenzialiDAO() {
		return new CredenzialiDaoJDBC();
	}


	@Override
	public ContoDao getContoDAO() {
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


	@Override
	public EsitoPartitaDao getEsitoPartitaDao() {
		return new EsitoPartitaDaoJDBC();
	}


	@Override
	public EsitoDao getEsitoDao() {
		return new EsitoDaoJDBC();
	}


	@Override
	public MovimentoScommessaDao getMovimentoScommessaDAO() {
		return new MovimentoScommessaDaoJDBC();
	}


	@Override
	public ScommessaDao getScommessaDao() {
		return new ScommessaDaoJDBC();
	}
	
}