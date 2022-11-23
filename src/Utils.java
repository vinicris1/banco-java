import java.sql.*;

public class Utils {
	public static double[] GetBalance() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			conn = DBConnect.StartConnection();
			stmt = conn.createStatement();

			String qry = String.format("SELECT balance_brl, balance_usd, balance_eur FROM usuarios WHERE email='%s'", Login.userEmail);
			rs = stmt.executeQuery(qry);

			if (rs.next()) {
				double[] arr = {0, 0, 0};

				arr[0] = rs.getDouble(1);
				arr[1] = rs.getDouble(2);
				arr[2] = rs.getDouble(3);

				return arr;
			}
		} catch (SQLException err) {
			err.printStackTrace();
		} finally {
			DBConnect.EndConnection(conn);

			try {
				stmt.close();
				rs.close();
			} catch (SQLException err) {
				err.printStackTrace();
			}
		}

		return null;
	}
}
