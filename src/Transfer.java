import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

import java.sql.*;

public class Transfer {

	JFrame frame;
	
	private JTextField transferValue;
	private JTextField transferReceiver;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Transfer window = new Transfer();
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
	public Transfer() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(18, 18, 20));
		frame.setResizable(false);
		frame.setBounds(100, 100, 566, 357);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		ImageIcon logo = new ImageIcon(getClass().getResource("/icon.png"));
		frame.setIconImage(logo.getImage());

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
				UserPanel userPanel = new UserPanel();
				userPanel.frame.setVisible(true);
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(20, 20, 30));
		panel.setBounds(149, 29, 231, 278);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Transferir para:");
		lblNewLabel_2.setBounds(10, 11, 90, 25);
		panel.add(lblNewLabel_2);
		lblNewLabel_2.setForeground(new Color(255, 255, 255));
		
		transferReceiver = new JTextField();
		transferReceiver.setBounds(10, 34, 140, 25);
		panel.add(transferReceiver);
		transferReceiver.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Valor:");
		lblNewLabel_1.setBounds(10, 144, 60, 19);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		
		transferValue = new JTextField();
		transferValue.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (Character.isDigit(c) || c == KeyEvent.VK_PERIOD || c == KeyEvent.VK_BACK_SPACE) {
					if (c == KeyEvent.VK_PERIOD) {
						String str = transferValue.getText().toString();

						int dot = str.indexOf('.');

						if (dot != -1) {
							e.consume();
						}
					}
				} else {
					e.consume();
				}
			}
		});
		transferValue.setBounds(10, 164, 80, 25);
		panel.add(transferValue);
		transferValue.setToolTipText("Email");
		transferValue.setColumns(10);
		
		JLabel lblNewLabel_0 = new JLabel("Moeda:");
		lblNewLabel_0.setBounds(10, 84, 77, 25);
		panel.add(lblNewLabel_0);
		lblNewLabel_0.setForeground(new Color(255, 255, 255));
		
		JComboBox transferCurrency = new JComboBox();
		transferCurrency.setBounds(10, 108, 50, 25);
		panel.add(transferCurrency);
		transferCurrency.setModel(new DefaultComboBoxModel(new String[] {"BRL", "USD", "EUR"}));
		
		JButton btnNewButton = new JButton("Transferir");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(43, 132, 116));
		btnNewButton.setBounds(55, 223, 122, 25);
		btnNewButton.setFocusPainted(false);
		panel.add(btnNewButton);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;

				String currency = transferCurrency.getSelectedItem().toString().toLowerCase();
				String receiverEmail = transferReceiver.getText();
				String value = transferValue.getText();

				int index = 0;
				if (currency.equals("brl")) {
					index = 0;
				} else if (currency.equals("usd")) {
					index = 1;
				} else if (currency.equals("eur")) {
					index = 2;
				}

				double[] balance = Utils.GetBalance();

				if (receiverEmail.length() < 1 || receiverEmail.equalsIgnoreCase(Login.userEmail)) {
					JOptionPane.showMessageDialog(null, "Email inv??lido.");
				} else if (value.length() < 1 || Double.parseDouble(value) < 1) {
					JOptionPane.showMessageDialog(null, "Valor inv??lido.");
				} else if (Double.parseDouble(value) > balance[index]) {
					JOptionPane.showMessageDialog(null, "Saldo insuficiente.");
				} else {
					try {
						conn = DBConnect.StartConnection();
						stmt = conn.createStatement();

						String findUserQry = String.format("SELECT * FROM usuarios WHERE email='%s'", receiverEmail);
						rs = stmt.executeQuery(findUserQry);

						if (rs.next()) {
							Statement stmt_2 = null;
							Statement stmt_3 = null;

							try {
								stmt_2 = conn.createStatement();
								String setReceiverBalance = String.format("UPDATE usuarios SET balance_%s = (balance_%s + %s) WHERE email = '%s'", currency, currency, value, transferReceiver.getText());
								int exec = stmt_2.executeUpdate(setReceiverBalance);

								stmt_3 = conn.createStatement();
								String setSenderBalance = String.format("UPDATE usuarios SET balance_%s = (balance_%s - %s) WHERE email = '%s'", currency, currency, value, Login.userEmail);
								int exec_2 = stmt_3.executeUpdate(setSenderBalance);
						
								if (exec > 0 && exec_2 > 0) {
									JOptionPane.showMessageDialog(null, "Transfer??ncia realizada com sucesso.");
								} else {
									JOptionPane.showMessageDialog(null, "Erro ao realizar transfer??ncia.");
								}
							} catch (SQLException err) {
								err.printStackTrace();
							} finally {
								DBConnect.EndConnection(conn);

								try {
									if (stmt_2 != null) stmt_2.close();
									if (stmt_3 != null) stmt_3.close();
								} catch (SQLException err) {
									err.printStackTrace();
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "Email n??o encontrado.");
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

						frame.dispose();
						UserPanel userPanel = new UserPanel();
						userPanel.frame.setVisible(true);
					}
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("Tranfer??ncia");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 123, 14);
		frame.getContentPane().add(lblNewLabel);
	}
}
