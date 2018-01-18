package persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.footballdata.Campionato;
import model.footballdata.Partita;

public interface PartitaDao {

	public void save(Partita partita,Connection connection)throws SQLException;  // Create
	public Long findExistingMatch(Partita p, Connection connection)throws SQLException;     // Retrieve
	public List<Partita> findAll(String nome_campionato);   
	public void update(Partita partita,Connection connection)throws SQLException;
	public float[] getPuntiSquadre(long codice,Connection connection)throws SQLException;
	public float[] getMediaPartiteASegno(long codicePartita,Connection connection)throws SQLException;
	public float[] getMediaGoal(long codicePartita,Connection connection)throws SQLException;
	public float getQuota(long codice,String esito,Connection connection)throws SQLException;
}
