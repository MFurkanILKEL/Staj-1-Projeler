package DB_Connection;

import java.sql.*;
import DB_Connection.Database_Connection;

public class Database_Connection {

	public static Connection getConnection_Kaizen_Db() {
		Connection con = null;
		String url, kullan�c�Ad�, �ifre;
		url = "jdbc:oracle:thin:@localhost:1521:xe";
		kullan�c�Ad� = "";
		�ifre = "";
		try {

			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			con = DriverManager.getConnection(url, kullan�c�Ad�, �ifre);

			return con;
		} catch (SQLException ex) {

			return null;
		}
	}

}
