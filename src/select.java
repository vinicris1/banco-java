import java.sql.*;

public class select {
	public static void main(String[] args) {
		Connection conexao = null;
		Statement comando = null;
		ResultSet resultado = null;

		try {
			conexao = DBConnect.StartConnection();
			comando = conexao.createStatement();
			String meu_sql = "SELECT * FROM contatos";
			resultado = comando.executeQuery(meu_sql);

			while (resultado.next()) {
				System.out.println(resultado.getString("cpf") + " " + resultado.getString("nome")+ " " + resultado.getString("idade"));
			}
		} catch (SQLException err) {
			err.printStackTrace();
		} finally {
			DBConnect.EndConnection(conexao);

			try {
				comando.close();
				resultado.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
