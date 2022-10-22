import java.sql.*;

public class select {
    Connection conexao = null;
		Statement comando = null;
		ResultSet resultado = null;
		
		try {
			conexao = Db_Connect.Conectar();
			comando = conexao.createStatement();
			String meu_sql = "SELECT * FROM contatos";
			resultado = comando.executeQuery(meu_sql);
			while(resultado.next()) {
				System.out.println(resultado.getString("cpf") + " " + resultado.getString("nome")+ " " + resultado.getString("idade"));
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
