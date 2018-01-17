package persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import model.footballdata.Campionato;
import model.footballdata.Partita;

public interface PartitaDao {

	public void save(Partita partita,Connection connection);  // Create
	public Long findExistingMatch(Partita p, Connection connection);     // Retrieve
	public List<Partita> findAll(String nome_campionato);   
	public void update(Partita partita,Connection connection);
	public float[] getPuntiSquadre(long codice,Connection connection);
	public float[] getMediaPartiteASegno(long codicePartita,Connection connection);
	public float[] getMediaGoal(long codicePartita,Connection connection);
	public float getQuota(long codice,String esito,Connection connection);
}
