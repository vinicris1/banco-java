import java.sql.*;

public class DBConnect {
	private static Connection conn = null;

	public static Connection StartConnection() {
		try {
			if (conn == null) {
				String url = "jdbc:mysql://localhost/banco";
				conn = DriverManager.getConnection(url, "root", "");
			}
		} catch (SQLException err) {
			err.printStackTrace();
		}

		return conn;
	}

	public static void EndConnection(Connection c) {
		try {
			if (c != null) {
				c.close();
				conn = null;
			}
		} catch (SQLException err) {
			err.printStackTrace();
		}
	}
}
