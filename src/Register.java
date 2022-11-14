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
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 360);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
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
				Login L = new Login();
				L.frame.setVisible(true);
				frame.dispose();
			}
		});
		
		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.setBounds(165, 275, 90, 25);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conexao = null;
				
				PreparedStatement comando = null;
				PreparedStatement comando2 = null;
				
				String nomeR = nameInput.getText();
				String emailR = emailInput.getText();
				String password = passwordInput.getText();	
				String cpfR = cpfInput.getText();
				int id = 0;
				
				String msg = "" + nomeR;
				msg += " \n";
				
				if (emailR.contains("@") && cpfR.length() == 11) {
					try {
						conexao = DBConnect.StartConnection();

						String sql = "INSERT INTO usuarios(id,name,emailInput,password,cpfInput,balance_brl,balance_usd,balance_eur) VALUES(?,?,?,?,?,?,?,?)";

						comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
						comando2 = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

						String select = "SELECT * FROM usuarios WHERE emailInput='" + emailR + "'";
						String selectCpf = "SELECT * FROM usuarios WHERE cpfInput='" + cpfR + "'";

						ResultSet rs = comando.executeQuery(select);
						ResultSet rs2 = comando2.executeQuery(selectCpf);

						if (rs.next()) {
							JOptionPane.showMessageDialog(btnNewButton, "OlÃ¡, " + msg + "esse e-mail jÃ¡ foi registrado!");
						} else if (rs2.next()) {
							JOptionPane.showMessageDialog(btnNewButton, "OlÃ¡, " + msg + "este CPF jÃ¡ foi registrado!");
						} else {
							comando.setInt(1, id);
							comando.setString(2, nomeR);
							comando.setString(3, emailR);
							comando.setString(4, password);
							comando.setString(5, cpfR);
							comando.setDouble(6, 50);
							comando.setDouble(7, 0);
							comando.setDouble(8, 0);

							if (comando.executeUpdate() > 0) {
								JOptionPane.showMessageDialog(btnNewButton, "Bem-vindo, " + msg + "sua conta foi criada com sucesso!");
								frame.setVisible(false);
							}
						}
						} catch (SQLException err) {
							err.printStackTrace();
						} finally {
							DBConnect.EndConnection(conexao);
							try {
								comando.close();
								comando2.close();
							} catch (SQLException err) {
								err.printStackTrace();
							}
						}
				} else if (cpfR.length() > 11 || cpfR.length() < 11) {
					JOptionPane.showMessageDialog(btnNewButton, "OlÃ¡, " + msg + "por favor coloque um CPF vÃ¡lido!");
				} else {
					JOptionPane.showMessageDialog(btnNewButton, "OlÃ¡, " + msg + "por favor coloque um e-mail vÃ¡lido!");
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
