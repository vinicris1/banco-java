import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import java.sql.*;

public class Register {

	JFrame frame;

	private JTextField nameInput;
	private JTextField emailInput;
	private JPasswordField passwordInput;
	private JTextField cpfInput;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
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
	public Register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Login login = new Login();

		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 360);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("Nome completo:");
		lblNewLabel.setBounds(30, 20, 115, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail:");
		lblNewLabel_1.setBounds(30, 145, 50, 20);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Senha:");
		lblNewLabel_2.setBounds(30, 205, 50, 20);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblNewLabel_2);
		
		nameInput = new JTextField();
		nameInput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameInput.setBounds(30, 50, 365, 25);
		frame.getContentPane().add(nameInput);
		nameInput.setColumns(10);
		
		emailInput = new JTextField();
		emailInput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		emailInput.setBounds(30, 170, 365, 25);
		emailInput.setColumns(10);
		frame.getContentPane().add(emailInput);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				login.frame.setVisible(true);
				frame.dispose();
			}
		});
		
		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.setBounds(165, 275, 90, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;

				PreparedStatement command = null;
				PreparedStatement command2 = null;

				int id = 0;
				String name = nameInput.getText();
				String email = emailInput.getText();
				String password = passwordInput.getText();	
				String cpf = cpfInput.getText();

				if (name.length() < 1) {
					JOptionPane.showMessageDialog(btnNewButton, "Nome inválido.");
				} else if (cpf.length() < 11 || cpf.length() > 11) {
					JOptionPane.showMessageDialog(btnNewButton, "CPF Inválido.");
				} else if (!email.contains("@")) {
					JOptionPane.showMessageDialog(btnNewButton, "Email inválido.");
				} else if (password.length() < 1) {
					JOptionPane.showMessageDialog(btnNewButton, "Senha inválida.");
				} else {
					try {
						conn = DBConnect.StartConnection();

						String sql = "INSERT INTO usuarios(id, name, cpf, email, password, balance_brl, balance_usd, balance_eur) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";

						command = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
						command2 = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

						String selectEmail = String.format("SELECT * FROM usuarios WHERE email='%s'", email);
						String selectCpf = String.format("SELECT * FROM usuarios WHERE cpf='%s'", cpf);

						ResultSet rs = command.executeQuery(selectEmail);
						ResultSet rs2 = command2.executeQuery(selectCpf);

						if (rs.next()) {
							JOptionPane.showMessageDialog(btnNewButton, "Este email já foi registrado.");
						} else if (rs2.next()) {
							JOptionPane.showMessageDialog(btnNewButton, "Este CPF já foi registrado.");
						} else {
							command.setInt(1, id);
							command.setString(2, name);
							command.setString(3, cpf);
							command.setString(4, email);
							command.setString(5, password);
							command.setDouble(6, 50);
							command.setDouble(7, 0);
							command.setDouble(8, 0);

							if (command.executeUpdate() > 0) {
								JOptionPane.showMessageDialog(btnNewButton, "Conta criada com sucesso!");
								frame.setVisible(false);
								login.frame.setVisible(true);
							}
						}
					} catch (SQLException err) {
						err.printStackTrace();
					} finally {
						DBConnect.EndConnection(conn);
						try {
							command.close();
							command2.close();
						} catch (SQLException err) {
							err.printStackTrace();
						}
					}
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(btnNewButton);
		
		passwordInput = new JPasswordField();
		passwordInput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		passwordInput.setBounds(30, 230, 365, 25);
		frame.getContentPane().add(passwordInput);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(30, 80, 40, 20);
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblCpf);
		
		cpfInput = new JTextField();
		cpfInput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cpfInput.setBounds(30, 110, 365, 25);
		cpfInput.setColumns(10);
		frame.getContentPane().add(cpfInput);
	}
}
