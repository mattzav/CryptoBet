package persistence;

import java.util.List;

import model.Amministratore;
import persistence.dao.AmministratoreDao;

public class AmministratoreDaoJDBC implements AmministratoreDao {
	private DataSource dataSource;

	public AmministratoreDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(Amministratore admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Amministratore findByPrimaryKey(String codice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Amministratore> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Amministratore admin) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Amministratore admin) {
		// TODO Auto-generated method stub
		
	}

}