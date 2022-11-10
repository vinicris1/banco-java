import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Login {

	JFrame frame;
	private JTextField user;
	private JPasswordField senha;

	// Launch the application
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

	// Create the application
	public Login() {
		initialize();
	}

	//Initialize the contents of the frame
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 350, 270);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Banco Java");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(110, 20, 100, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(50, 50, 46, 14);
		
		JLabel lblNewLabel_2 = new JLabel("Senha:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(50, 108, 46, 14);
		
		user = new JTextField();
		user.setFont(new Font("Tahoma", Font.PLAIN, 12));
		user.setBounds(50, 75, 240, 25);
		user.setColumns(10);
		
		senha = new JPasswordField();
		senha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		senha.setBounds(50, 130, 240, 25);
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(185, 175, 90, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conexao = null;
				Statement comando = null;
				ResultSet resultado = null;
				
				try {
					conexao = DBConnect.StartConnection();
					comando = conexao.createStatement();
					String meu_sql = "SELECT * FROM usuarios WHERE email ='" + user.getText() + "' AND password = '" + senha.getText().toString() + "'";
					resultado = comando.executeQuery(meu_sql);
					
					if (resultado.next()) {
						test t1 = new test();
						t1.frame.setVisible(true); //abrir janela 2
						frame.setVisible(false); // fechar janela de login caso de certo
					} else {
						JOptionPane.showMessageDialog(null, "Credenciais incorretas...");
					}
				} catch(SQLException err) {
					err.printStackTrace();
				} finally {
					DBConnect.EndConnection(conexao);

					try {
						comando.close();
						resultado.close();
					} catch(SQLException err) {
						err.printStackTrace();
					}
				}
			}
		});
		
		JButton Registrar = new JButton("Registrar");
		Registrar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		Registrar.setBounds(50, 175, 90, 25);
		Registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Register reg = new Register();
				reg.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		frame.getContentPane().setLayout(null);
		frame.getContentPane().add(lblNewLabel);
		frame.getContentPane().add(lblNewLabel_1);
		frame.getContentPane().add(lblNewLabel_2);
		frame.getContentPane().add(user);
		frame.getContentPane().add(senha);
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(Registrar);
	}
}
