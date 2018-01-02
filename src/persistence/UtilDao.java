package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.geometry.Pos;
import model.Campionato;
import model.Partita;
import model.Squadra;
import persistence.dao.CampionatoDao;
import persistence.dao.PartitaDao;
import persistence.dao.SquadraDao;
import sun.java2d.pipe.SpanShapeRenderer.Simple;

public class UtilDao {

	public UtilDao() {
	}

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

	public static void main(String[] args) {
		UtilDao u = new UtilDao();
		u.createDatabase();

//		CampionatoDao campionatoDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao();
//		PartitaDao partitaDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
//		SquadraDao squadraDao = PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getSquadraDAO();
//
//		Squadra squadra = new Squadra("milan");
//		Squadra squadra2 = new Squadra("juventus");
//		Squadra squadra3 = new Squadra("roma");
//		Squadra squadra4 = new Squadra("napoli");
//		squadraDao.save(squadra);
//		squadraDao.save(squadra2);
//		squadraDao.save(squadra3);
//		squadraDao.save(squadra4);
//
//		Campionato campionato = new Campionato("serie A");
//		campionatoDao.save(campionato);
//		Campionato campionato2 = new Campionato("serie B");
//		campionatoDao.save(campionato2);
//
//		
//		String data = "2018-01-02 22:59:20";
//		java.sql.Date d = null;
//		SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
//		try {
//			d = new java.sql.Date(localDateFormat.parse(data).getTime());
//	
//			
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}		
//		
//
//		Partita partita = new Partita(squadra, squadra2, 2, 1, campionato,new java.util.Date(d.getTime()),
//				false);
//		partitaDao.save(partita);
//		Partita partita1 = new Partita(squadra3, squadra4, 2, 2, campionato, new java.util.Date(d.getTime()),
//				true);
//		partitaDao.save(partita1);
//
//		System.out.println(partita.getCodice() + " " + partita1.getCodice());
//
//		for (Partita p : partitaDao.findAll())
//			System.out.println(p);
//
//		for (Campionato c : campionatoDao.findAll())
//			System.out.println(c);
//
//		for (Squadra s : squadraDao.findAll())
//			System.out.println(s);
	}

	public void createDatabase() {

		Connection connection = PostgresDAOFactory.dataSource.getConnection();
		try {

			String delete = "create table cartaDiCredito (codice varchar(255) primary key,data_scadenza date, saldo float);"
					+ "create table conto (codice bigint primary key, saldo float,data_apertura date, codice_carta varchar(255) REFERENCES cartaDiCredito(\"codice\"));"
					+ "create table movimentoCarta (codice bigint primary key, tipo varchar(255),importo float);"
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
