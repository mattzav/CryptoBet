package persistence;

import java.util.List;

import model.Campionato;
import persistence.dao.CampionatoDao;

public class CampionatoDaoJDBC implements CampionatoDao {

	private DataSource dataSource;

	public CampionatoDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	
	@Override
	public void save(Campionato campionato) {

	}

	@Override
	public Campionato findByPrimaryKey(Long codice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Campionato> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Campionato campionato) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Campionato campionato) {
		// TODO Auto-generated method stub

	}

}
