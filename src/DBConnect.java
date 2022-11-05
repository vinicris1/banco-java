import java.sql.*;

public class DBConnect {
	private static Connection connect = null;

	public static Connection StartConnection() {
		try {
			if (connect == null) {
				String url = "jdbc:mysql://localhost/banco";
				connect = DriverManager.getConnection(url, "root", "");
			}
		} catch (SQLException err) {
			err.printStackTrace();
		}
		return connect;
	}

	public static void EndConnection(Connection c) {
		try {
			if (c != null) {
				c.close();
				connect = null;
			}
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}
}
