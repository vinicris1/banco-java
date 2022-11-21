import java.sql.*;

public class InfoFuncs {
	public static double[] GetBalance() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		double[] arr = {0, 0, 0};

		try {
			conn = DBConnect.StartConnection();
			stmt = conn.createStatement();

			String qry = String.format("SELECT balance_brl, balance_usd, balance_eur FROM usuarios WHERE email='%s'", Login.userEmail);

			rs = stmt.executeQuery(qry);

			if (rs.next()) {
				double brl = rs.getDouble(1);
				double usd = rs.getDouble(2);
				double eur = rs.getDouble(3);

				arr[0] = brl;
				arr[1] = usd;
				arr[2] = eur;
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

		return arr;
	}
}
