package model;

public class Partita {

	private Long id;
	private Campionato campionato;
	private Squadra squadra_casa;
	private Squadra squadra_ospite;
	
	public Partita(Campionato campionato, Squadra squadra_casa, Squadra squadra_ospite) {
		
		this.campionato = campionato;
		this.squadra_casa = squadra_casa;
		this.squadra_ospite = squadra_ospite;
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
	
	
}
