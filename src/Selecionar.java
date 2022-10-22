import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Selecionar {

	public static void main(String[] args) {
		Connection conexao = null;
		Statement comando = null;
		ResultSet resultado = null;
		
		try {
			conexao = Db_Connect.Conectar();
			comando = conexao.createStatement();
			String meu_sql = "SELECT * FROM users";
			resultado = comando.executeQuery(meu_sql);
			while(resultado.next()) {
				System.out.println(resultado.getString("id") + " " + resultado.getString("name")+ " " + resultado.getString("email"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}finally {
			Db_Connect.FecharConexao(conexao);
			try {
				comando.close();
				resultado.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
