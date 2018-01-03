package model;

import java.util.Date;

import persistence.IdBroker;

public class Partita {

	private Long codice;
	private Campionato campionato;
	private Squadra squadra_casa;
	private Squadra squadra_ospite;
	private Date data_ora;
	private boolean finita;
	private int goal_casa;
	private int goal_ospite;

	public Partita(Squadra squadra_casa, Squadra squadra_ospite, int goal_casa, int goal_ospite, Campionato campionato,
			Date data_ora, boolean finita) {
		this.codice = IdBroker.getIstance("partita").getId();
		this.finita = finita;
		this.campionato = campionato;
		this.squadra_casa = squadra_casa;
		this.data_ora = data_ora;
		this.squadra_ospite = squadra_ospite;
		this.goal_casa = goal_casa;
		this.goal_ospite = goal_ospite;
	}

	public Partita(Long codice, Squadra squadra_casa, Squadra squadra_ospite, int goal_casa, int goal_ospite,
			Campionato campionato, long data_ora, boolean finita) {
		this.codice = codice;
		this.finita = finita;
		this.campionato = campionato;
		this.squadra_casa = squadra_casa;
		this.data_ora = new Date(data_ora);
		this.squadra_ospite = squadra_ospite;
		this.goal_casa = goal_casa;
		this.goal_ospite = goal_ospite;
	}

	public int getGoal_casa() {
		return goal_casa;
	}

	public int getGoal_ospite() {
		return goal_ospite;
	}

	public boolean isFinita() {
		return finita;
	}

	public void setFinita(boolean finita) {
		this.finita = finita;
	}

	public void setGoal_casa(int goal_casa) {
		this.goal_casa = goal_casa;
	}

	public void setGoal_ospite(int goal_ospite) {
		this.goal_ospite = goal_ospite;
	}

	public Date getData_ora() {
		return data_ora;
	}

	public void setData_ora(Date data_ora) {
		this.data_ora = data_ora;
	}

	public Long getCodice() {
		return codice;
	}

	public void setCodice(Long codice) {
		this.codice = codice;
	}

	public Campionato getCampionato() {
		return campionato;
	}

	public void setCampionato(Campionato campionato) {
		this.campionato = campionato;
	}

	public Squadra getSquadra_casa() {
		return squadra_casa;
	}

	public void setSquadra_casa(Squadra squadra_casa) {
		this.squadra_casa = squadra_casa;
	}

	public Squadra getSquadra_ospite() {
		return squadra_ospite;
	}

	public void setSquadra_ospite(Squadra squadra_ospite) {
		this.squadra_ospite = squadra_ospite;
	}

	@Override
	public String toString() {
		return "Partita tra " + squadra_casa.getNome() + " vs " + squadra_ospite.getNome() + " prevista alle "
				+ data_ora + " risultato" + goal_casa + " - " + goal_ospite + " finita: " + finita;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Partita partita=(Partita)(arg0);
		return partita.codice == codice;
	}
}
