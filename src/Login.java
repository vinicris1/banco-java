import java.awt.EventQueue;
import java.sql.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login {

	private JFrame frame;
	private JTextField user;
	private JPasswordField senha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 242, 323);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login Page");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(52, 23, 123, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail");
		lblNewLabel_1.setBounds(10, 48, 46, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Senha");
		lblNewLabel_2.setBounds(10, 99, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		user = new JTextField();
		user.setBounds(20, 68, 183, 20);
		frame.getContentPane().add(user);
		user.setColumns(10);
		
		senha = new JPasswordField();
		senha.setBounds(20, 116, 183, 20);
		frame.getContentPane().add(senha);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conexao = null;
				Statement comando = null;
				ResultSet resultado = null;
				
				try {
					conexao = Db_Connect.Conectar();
					comando = conexao.createStatement();
					String meu_sql = "Select * FROM usuarios WHERE email='"+user.getText()+"' and password='"+senha.getText().toString()+"'";
					resultado = comando.executeQuery(meu_sql);
					if(resultado.next()) {
						
						test t1 = new test();
						t1.frame.setVisible(true); //abrir janela 2
						
						frame.setVisible(false); //fechar janela de login caso de certo
					}else {
						JOptionPane.showMessageDialog(null, "Credenciais incorretas...");
				}
				} catch(SQLException err) {
					err.printStackTrace();
				}finally {
					Db_Connect.FecharConexao(conexao);
					try {
						comando.close();
						resultado.close();
					}catch(SQLException erro) {
						erro.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setBounds(114, 164, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		JButton Registrar = new JButton("Registrar");
		Registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				register reg = new register();
				reg.frame.setVisible(true);
			}
		});
		Registrar.setBounds(20, 164, 89, 23);
		frame.getContentPane().add(Registrar);
	}
}
