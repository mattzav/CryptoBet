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

public abstract class DAOFactory {
	
	public static final int POSTGRESQL = 2;

	public static DAOFactory getDAOFactory(int whichFactory) {
		switch ( whichFactory ) {
		case POSTGRESQL:
			return new PostgresDAOFactory();
		default:
			return null;
		}
	}
	
	public abstract AmministratoreDao getAmministratoreDAO();
	
	public abstract GiocatoreDao getGiocatoreDAO();
	
	public abstract CredenzialiDao getCredenzialiDAO();
	
	public abstract ContoDao getContoDAO();
	
	public abstract CartaDiCreditoDao getCartaDiCreditoDAO();
	
	public abstract MovimentoCartaDao getMovimentoCartaDAO();

	public abstract SquadraDao getSquadraDAO();
	
	public abstract CampionatoDao getCampionatoDao();
	
	public abstract PartitaDao getPartitaDao();

	public abstract EsitoPartitaDao getEsitoPartitaDao() ;

	public abstract EsitoDao getEsitoDao() ;
	
	public abstract ScommessaDao getScommessaDao();

	public abstract MovimentoScommessaDao getMovimentoScommessaDAO();
}
