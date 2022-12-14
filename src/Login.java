import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;

import java.sql.*;

public class Login {

	JFrame frame;

	private JTextField email;
	private JPasswordField password;
	
	public static String userName;
	public static String userEmail;
	public static String userCpf;

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
		frame.setBounds(100, 100, 352, 314);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		
		ImageIcon logo = new ImageIcon(getClass().getResource("/icon.png"));
		frame.setIconImage(logo.getImage());
		
		JLabel lblNewLabel = new JLabel("BANCO JAVA");
		lblNewLabel.setBackground(new Color(43, 132, 116));
		lblNewLabel.setForeground(new Color(43, 132, 116));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewLabel.setBounds(110, 21, 116, 14);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Email:");
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
				Statement stmt = null;
				ResultSet rs = null;

				String emailStr = email.getText().toString();
				String passwordStr = password.getText().toString();

				if (emailStr.length() < 1) {
					JOptionPane.showMessageDialog(null, "Por favor, insira um email.");
				} else if (passwordStr.length() < 1) {
					JOptionPane.showMessageDialog(null, "Por favor, insira uma senha.");
				} else {
					try {
						conn = DBConnect.StartConnection();
						
						stmt = conn.createStatement();
						String qry = String.format("SELECT * FROM usuarios WHERE email='%s' AND password = '%s'", emailStr, passwordStr);
						rs = stmt.executeQuery(qry);
						
						if (rs.next()) {
							userName = rs.getString(2);
							userEmail = rs.getString(3);
							userCpf = rs.getString(5);

							frame.dispose();
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
							if (conn != null) conn.close();
							if (stmt != null) stmt.close();
							if (rs != null) rs.close();
						} catch (SQLException err) {
							err.printStackTrace();
						}
					}
				}
			}
		});
		btnNewButton.setFocusPainted(false);
		
		JButton Registrar = new JButton("Registrar");
		Registrar.setBackground(new Color(18, 18, 20));
		Registrar.setForeground(new Color(43, 132, 116));
		Registrar.setFont(new Font("Tahoma", Font.BOLD, 11));
		Registrar.setBounds(50, 221, 240, 25);
		Registrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Register register = new Register();
				register.frame.setVisible(true);
			}
		});
		Registrar.setFocusPainted(false);

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
