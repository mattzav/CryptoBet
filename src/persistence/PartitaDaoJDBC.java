package persistence;

import java.util.List;

import model.Partita;
import persistence.dao.PartitaDao;

public class PartitaDaoJDBC implements PartitaDao {

	private DataSource dataSource;

	public PartitaDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	@Override
	public void save(Partita partita) {
		// TODO Auto-generated method stub

	}

	@Override
	public Partita findByPrimaryKey(Long codice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Partita> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Partita partita) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Partita partita) {
		// TODO Auto-generated method stub

	}

}
