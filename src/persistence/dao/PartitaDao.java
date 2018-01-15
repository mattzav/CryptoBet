package persistence.dao;

import java.util.List;

import model.Campionato;
import model.Partita;

public interface PartitaDao {

	public void save(Partita partita);  // Create
	public Long findExistingMatch(Partita p);     // Retrieve
	public List<Partita> findAll(String nome_campionato);   
	public void update(Partita partita);
	public float[] getPuntiSquadre(long codice);
	public float[] getMediaPartiteASegno(long codicePartita);
	public float[] getMediaGoal(long codicePartita);
	public float getQuota(long codice,String esito);
}
