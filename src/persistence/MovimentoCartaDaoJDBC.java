package persistence;

import java.util.List;

import model.MovimentoCarta;
import persistence.dao.MovimentoCartaDao;

public class MovimentoCartaDaoJDBC implements MovimentoCartaDao{
	private DataSource dataSource;

	public MovimentoCartaDaoJDBC(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(MovimentoCarta movimento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MovimentoCarta findByPrimaryKey(Long codice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MovimentoCarta> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(MovimentoCarta movimento) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(MovimentoCarta movimento) {
		// TODO Auto-generated method stub
		
	}
	
}
