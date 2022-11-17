import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.Font;

import java.sql.*;
import java.awt.Color;

public class Login {

	JFrame frame;

	private JTextField email;
	private JPasswordField password;
	
	public static String name;
	public static String userEmail;
	public static String cpf;
	public static double balanceBrl;
	public static double balanceUsd;
	public static double balanceEur;

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
		frame.getContentPane().setBackground(new Color(18, 18, 20));
		frame.setResizable(false);
		frame.setBounds(100, 100, 350, 312);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("BANCO JAVA");
		lblNewLabel.setBackground(new Color(43, 132, 116));
		lblNewLabel.setForeground(new Color(43, 132, 116));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(110, 21, 116, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail:");
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(50, 51, 46, 14);
		
		JLabel lblNewLabel_2 = new JLabel("Senha:");
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(50, 112, 46, 14);
		
		email = new JTextField();
		email.setBackground(new Color(255, 255, 255));
		email.setFont(new Font("Tahoma", Font.PLAIN, 12));
		email.setBounds(50, 76, 240, 25);
		email.setColumns(10);
		
		password = new JPasswordField();
		password.setBackground(new Color(255, 255, 255));
		password.setFont(new Font("Tahoma", Font.PLAIN, 12));
		password.setBounds(50, 137, 240, 25);
		
		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.setBackground(new Color(43, 132, 116));
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNewButton.setBounds(50, 185, 240, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				Statement command = null;
				ResultSet result = null;
				
				try {
					conn = DBConnect.StartConnection();
					command = conn.createStatement();

					String loginSql = String.format("SELECT * FROM usuarios WHERE email='%s' AND password = '%s'", email.getText(), password.getText().toString());

					result = command.executeQuery(loginSql);
					
					if (result.next()) {
						String selectEmail = String.format("SELECT * FROM usuarios WHERE email='%s'", email.getText());

						ResultSet rs = command.executeQuery(selectEmail);
						
						if (rs.next()) {
							name = rs.getString(2);
							userEmail = rs.getString(3);
							cpf = rs.getString(5);
							balanceBrl = rs.getDouble(6);
							balanceUsd = rs.getDouble(7);
							balanceEur = rs.getDouble(8);
						}

						frame.setVisible(false);

						UserPanel userPanel = new UserPanel();
						userPanel.frame.setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Credenciais incorretas.");
					}
				} catch (SQLException err) {
					err.printStackTrace();
				} finally {
					DBConnect.EndConnection(conn);

					try {
						command.close();
						result.close();
					} catch (SQLException err) {
						err.printStackTrace();
					}
				}
			}
		});
		
		JButton Registrar = new JButton("Registrar");
		Registrar.setBackground(new Color(18, 18, 20));
		Registrar.setForeground(new Color(43, 132, 116));
		Registrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		Registrar.setBounds(50, 221, 240, 25);
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
		frame.getContentPane().add(email);
		frame.getContentPane().add(password);
		frame.getContentPane().add(btnNewButton);
		frame.getContentPane().add(Registrar);
	}
}
