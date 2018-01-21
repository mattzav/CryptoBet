package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.geometry.Pos;
import model.footballdata.Campionato;
import model.footballdata.Partita;
import model.footballdata.Squadra;
import model.users.Conto;
import model.users.MovimentoCarta;
import model.users.TipoMovimento;
import persistence.dao.CampionatoDao;
import persistence.dao.MovimentoCartaDao;
import persistence.dao.PartitaDao;
import persistence.dao.SquadraDao;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class UtilDao {


	public void dropDatabase() {

		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String delete = "drop SEQUENCE if EXISTS sequenza_id;" + "drop table if exists iscritto;"
					+ "drop table if exists afferisce;" + "drop table if exists studente;"
					+ "drop table if exists corso;" + "drop table if exists corsodilaurea;"
					+ "drop table if exists dipartimento;" + "drop table if exists indirizzo;"
					+ "drop table if exists partita;" + "drop table if exists squadra;"
					+ "drop table if exists campionato;";
			PreparedStatement statement = connection.prepareStatement(delete);

			statement.executeUpdate();
			System.out.println("Executed drop database");

		} catch (SQLException e) {

			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void addTable(String name,String[] attributes) {
		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {

			String delete = "create table "+name+" (";
			for(String s:attributes) {
				delete+=s;
			}
			delete+=");";
			System.out.println(delete);
			PreparedStatement statement = connection.prepareStatement(delete);
			statement.executeUpdate();
			System.out.println(delete);
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				throw new PersistenceException(e.getMessage());
			}
		}
	}
	public static void main(String[] args) {
		Connection connessione=PostgresDAOFactory.dataSource.getConnection();
		Partita p=new Partita(Long.valueOf(213),new Squadra("Atalanta BC"), new Squadra("SSC Napoli"), -1, -1,new Campionato(Long.valueOf(456)," "), new Date().getTime(), false);
		try {
			connessione.setAutoCommit(false);
			PostgresDAOFactory.getDAOFactory(PostgresDAOFactory.POSTGRESQL).getPartitaDao().update(p, connessione);
			connessione.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//u.createDatabase();
		//u.addTable("scommessa", new String[]{"codice bigint primary key, data_emissione date, conto_associato bigint references conto(\"codice\"), importo_giocato float, quota_totale float,bonus float,numero_esiti int,vincita_potenziale float"});
		//u.addTable("scommessa_esitopartita", new String[]{"scommessa bigint not null references scommessa(\"codice\"), esito varchar(255) not null, partita bigint not null, primary key(scommessa,esito,partita),FOREIGN KEY  (esito, partita) REFERENCES esitopartita (esito, partita)"});
	//	u.addTable("movimentoScommessa", new String[]{"codice bigint primary key, importo float, tipo varchar(255), scommessa bigint references scommessa(\"codice\")"});
		//u.addTable("esito", new String[] {"descrizione varchar(255) primary key"});
	//	u.addTable("movimentoCarta", new String[]{"codice bigint primary key,data date,ora time,tipo varchar(255),importo float,conto bigint REFERENCES conto(\"codice\")"});
		//u.addTable("esitopartita", new String[] {"esito varchar(255) not null REFERENCES esito(\"descrizione\"),partita bigint not null REFERENCES partita(\"codice\"), quota float,disponibile boolean, primary key(esito,partita)"});
//		
//		CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();
//		PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
//		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getSquadraDAO();
////
//		Squadra squadra = new Squadra("milan");
//		Squadra squadra2 = new Squadra("juventus");
////		Squadra squadra3 = new Squadra("roma");
////		Squadra squadra4 = new Squadra("napoli");
//		squadraDao.save(squadra);
//		squadraDao.save(squadra2);
////		squadraDao.save(squadra3);
////		squadraDao.save(squadra4);
////
//		Campionato campionato = new Campionato(Long.valueOf(465),"serie A");
//		campionatoDao.save(campionato);
////		Campionato campionato2 = new Campionato("serie B");
////		campionatoDao.save(campionato2);
////
//		java.sql.Date d=null;
//		String data = "2018-01-02 20:59:20";
//		SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//		try {
//			d = new java.sql.Date(localDateFormat.parse(data).getTime());
//	
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
////		
////
//		Partita partita=new Partita(squadra, squadra2, 2, 1, campionato, new java.util.Date(d.getTime()), true);
//		partitaDao.save(partita);
////		Partita partita1 = new Partita(squadra3, squadra4, 2, 2, campionato, new java.util.Date(d.getTime()),
////				true);
////		partitaDao.save(partita1);
////
////		System.out.println(partita.getCodice() + " " + partita1.getCodice());
////
////		for (Partita p : partitaDao.findAll())
////			System.out.println(p);
////
////		for (Campionato c : campionatoDao.findAll())
////			System.out.println(c);
////
////		for (Squadra s : squadraDao.findAll())
////			System.out.println(s);
	}

	public void createDatabase() {

		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {

			String delete = 
					"create table cartaDiCredito (codice varchar(255) primary key,data_scadenza date, saldo float);"
					+ "create table conto (codice bigint primary key, saldo float,data_apertura date, codice_carta varchar(255) REFERENCES cartaDiCredito(\"codice\"));"
					+ "create table movimentoCarta (codice bigint primary key,data date,ora time,tipo varchar(255),importo float,conto bigint REFERENCES conto(\"codice\"));"
					+ "create table credenziali(username varchar(255) primary key,password varchar(255),tipo varchar(255));"
					+ "create table giocatore (codice bigint primary key,nome varchar(255),cognome varchar(255),data_nascita date,username varchar(255) REFERENCES credenziali(\"username\"),conto bigint REFERENCES conto(\"codice\"));"
					+ "create table amministatore(codice bigint primary key,username varchar(255) REFERENCES credenziali(\"username\"));"
					+ "create table squadra (nome varchar(255) primary key);"
					+ "create table campionato (codice bigint primary key, nome varchar(255));"
					+ "create table partita (codice bigint primary key,squadraCasa varchar(255) REFERENCES squadra(\"nome\"),squadraOspite varchar(255) REFERENCES squadra(\"nome\"),campionato bigint REFERENCES campionato(\"codice\"),data date,ora time,finita boolean,goalCasa smallint, goalOspite smallint);";
			PreparedStatement statement = connection.prepareStatement(delete);

			statement.executeUpdate();
			System.out.println("Executed create database");

		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				throw new PersistenceException(e.getMessage());
			}
		}
	}

	public void resetDatabase() {

		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {
			String delete = "delete FROM studente";
			PreparedStatement statement = connection.prepareStatement(delete);

			statement.executeUpdate();

			delete = "delete FROM gruppo";
			statement = connection.prepareStatement(delete);

			delete = "delete FROM corso";
			statement = connection.prepareStatement(delete);

			statement.executeUpdate();
		} catch (SQLException e) {

			throw new PersistenceException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {

				throw new PersistenceException(e.getMessage());
			}
		}
	}
}
