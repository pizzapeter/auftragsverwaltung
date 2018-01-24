	package at.Data;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;

public class Database {
	Connection con = null;
	public Database() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		}
		catch(ClassNotFoundException ex) {
			System.out.println("Error: unable to load driver class!");
		}
		
		try {
			 Driver myDriver = new oracle.jdbc.driver.OracleDriver();
			 DriverManager.registerDriver( myDriver );
			 //con = DriverManager.getConnection("jdbc:oracle:thin:@212.152.179.117:1521:ora11g","d5a13","d5a");
			 con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.128.152:1521:ora11g","d5a13","d5a");
		}
		catch(Exception ex) {
			System.out.println("Error: unable to load driver class!");   
		}
	}
	public Connection getCon() {
		return con;
	}
}
