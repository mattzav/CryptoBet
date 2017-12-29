package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UtilDao {

	
private DataSource dataSource;

public UtilDao(DataSource dataSource) {
		this.dataSource=dataSource;
}

public void dropDatabase(){
	
	Connection connection = dataSource.getConnection();
	try {
		String delete = "drop SEQUENCE if EXISTS sequenza_id;"
				+ "drop table if exists iscritto;"
				+ "drop table if exists afferisce;"							
				+ "drop table if exists studente;"
				+ "drop table if exists corso;"
				+ "drop table if exists corsodilaurea;"
				+ "drop table if exists dipartimento;"
				+ "drop table if exists indirizzo;"
				+ "drop table if exists gruppo;";
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
	DataSource d=PostgresDAOFactory.dataSource;
	UtilDao u=new UtilDao(d);
	u.createDatabase();
}

public void createDatabase(){
	
	Connection connection = dataSource.getConnection();
	try {
		
		String delete = 
				"create table cartaDiCredito (codice varchar(255) primary key,data_scadenza date, saldo float);"
				+"create table conto (codice bigint primary key, saldo float,data_apertura date, codice_carta varchar(255) REFERENCES cartaDiCredito(\"codice\"));"
				+ "create table movimentoCarta (codice bigint primary key, tipo varchar(255),importo float);"
				+ "create table credenziali(username varchar(255) primary key,password varchar(255),tipo varchar(255));"				
				+ "create table giocatore (codice bigint primary key,nome varchar(255),cognome varchar(255),data_nascita date,username varchar(255) REFERENCES credenziali(\"username\"),conto bigint REFERENCES conto(\"codice\"));"
				+ "create table amministatore(codice bigint primary key,username varchar(255) REFERENCES credenziali(\"username\"));"
				+ "create table squadra (nome varchar(255) primary key);"
				+ "create table campionato (codice bigint primary key, nome varchar(255));"
				+ "create table partita (codice bigint primary key,squadraCasa varchar(255) REFERENCES squadra(\"nome\"),squadraOspite varchar(255) REFERENCES squadra(\"nome\"),campionato bigint REFERENCES campionato(\"codice\"));";
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


public  void resetDatabase() {
		
		Connection connection = dataSource.getConnection();
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
