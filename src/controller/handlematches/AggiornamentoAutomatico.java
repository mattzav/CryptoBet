package controller.handlematches;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import model.footballdata.Campionato;
import model.footballdata.Partita;
import persistence.DAOFactory;
import persistence.PostgresDAOFactory;
import persistence.dao.PartitaDao;


public class AggiornamentoAutomatico extends HttpServlet {

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		new Thread() {
			
			@Override
			public void run() {
				
				while(true) {
					Date data=new Date();
					Connection connessione=PostgresDAOFactory.dataSource.getConnection();
					try {
						connessione.setAutoCommit(false);
						ArrayList<Campionato> campionati=(ArrayList<Campionato>) PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getCampionatoDao().findAll();
						for(Campionato c : campionati) {
							PartitaDao partitaDao=PostgresDAOFactory.getDAOFactory(DAOFactory.POSTGRESQL).getPartitaDao();
							ArrayList<Partita> partite = (ArrayList<Partita>) partitaDao.findAll(c.getNome());
							for(Partita p: partite) {
								if(p.getData_ora().getTime()<data.getTime()) {
									p.setFinita(true);
									partitaDao.save(p, connessione);
								}
							}
						}
						connessione.commit();
					} catch (SQLException e1) {
						System.out.println("Errore di persistenza");
						try {
							connessione.rollback();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}finally {
						try {
							connessione.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					try {
						sleep(1000*60*60);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		}.start();
		
	}
}
