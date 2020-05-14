import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class sendData {
	 Connection connection = null;
	 	static String databaseName = "crypto";
	    static String username = "worker";
	    static String password = "";
	    static String ip="";
	    static String port=":"+3306+"/";
	    static ComboPooledDataSource dataSource=null;
	    
	public  void setDataSource() throws PropertyVetoException
	
	    {
	        dataSource = new ComboPooledDataSource();
	        dataSource.setJdbcUrl("jdbc:mysql://"+ ip + port + databaseName);
	        dataSource.setUser(username);
	        dataSource.setPassword(password);
	        // Optional Settings
	        dataSource.setInitialPoolSize(0);
	        dataSource.setMinPoolSize(0);
	        dataSource.setAcquireIncrement(5);
	        dataSource.setMaxPoolSize(1);
	        dataSource.setMaxIdleTime(600);
	        dataSource.setMaxStatements(100);
	       
	
	  }

	
	void updateTable(String tableName,String time, String bid, String ask, String lastTrade) throws InstantiationException, IllegalAccessException, ClassNotFoundException, PropertyVetoException {
		
	    try {
	    	 connection = dataSource.getConnection();
	        try (Statement statement = connection.createStatement()) {
	        	
	  	      statement.execute("INSERT INTO "+tableName+" (bid,ask,lastTrade,time) VALUES ('"+
	        bid+"','"+
	        ask+"','"+
	        lastTrade+"','"+
	        time+
	        "');");
	  	    connection.close();
	  	    }
	       
	        catch(Exception e) {
	        	System.out.println("statement not executed");
	        	connection.rollback();
	        	e.printStackTrace();
	        }
	        
	    } catch (SQLException e) {
	    	
	        throw new IllegalStateException("Cannot connect the database!", e);
	        
	    }
	    
	}
	}


