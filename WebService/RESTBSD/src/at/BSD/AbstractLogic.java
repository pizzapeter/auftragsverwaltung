package at.BSD;

import java.sql.Connection;
import java.sql.SQLException;

import at.Data.Database;

public class AbstractLogic {
	private Database db = new Database();

	public Database getDb() {
		return db;
	}

	protected Connection getConn() {
		return db.getCon();
	}

	protected void commit() throws SQLException {
		db.getCon().commit();
	}

	protected void close() throws SQLException {
		db.getCon().close();
	}

	protected void rollback() throws SQLException {
		if (db != null && db.getCon() != null && !db.getCon().isClosed()) {
			db.getCon().rollback();
		}
	}
}
