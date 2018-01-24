package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class IdBroker {

	// Standard SQL (queste stringhe andrebbero scritte in un file di configurazione
	// private static final String query = "SELECT NEXT VALUE FOR SEQ_ID AS id";
	// postgresql

	private static String tableName;
	private static IdBroker istance;
	
	private IdBroker(String classIdGenerator) {
		this.tableName = classIdGenerator;
	}
	
	public static IdBroker getIstance(String _tableName) {
		if(istance == null) {
			istance=new IdBroker(_tableName);
		}
		tableName=_tableName;
		return istance;
	}

	public Long getId() {
		Long id = null;
		String query = "SELECT max(T.codice) from "+tableName+" as T";
		Connection connection=PostgresDAOFactory.dataSource.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			if(result==null) {
				id=new Long(0);
			}else {
				result.next();
				id = result.getLong(1);
			}
		} catch (SQLException e) {
			throw new PersistenceException(e.getMessage());
		}finally {
			try {
				if(connection!=null)
					connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return id+1;
	}
}