import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

import java.sql.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

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
					JOptionPane.showMessageDialog(null, "Email inválido.");
				} else if (value.length() < 1 || Double.parseDouble(value) < 1) {
					JOptionPane.showMessageDialog(null, "Valor inválido.");
				} else if (Double.parseDouble(value) > balance[index]) {
					JOptionPane.showMessageDialog(null, "Saldo insuficiente.");
				} else {
					try {
						conn = DBConnect.StartConnection();
						stmt = conn.createStatement();

						String findUserQry = String.format("SELECT * FROM usuarios WHERE email='%s'", receiverEmail);
						rs = stmt.executeQuery(findUserQry);

						if (rs.next()) {
							Statement stmt2 = null;
							Statement stmt3 = null;

							try {
								stmt2 = conn.createStatement();
								stmt3 = conn.createStatement();
								
								String setReceiverBalance = String.format("UPDATE usuarios SET balance_%s = (balance_%s + %s) WHERE email = '%s'", currency, currency, value, transferReceiver.getText());
								int count = stmt2.executeUpdate(setReceiverBalance);

								String setSenderBalance = String.format("UPDATE usuarios SET balance_%s = (balance_%s - %s) WHERE email = '%s'", currency, currency, value, Login.userEmail);
								int count2 = stmt3.executeUpdate(setSenderBalance);
						
								if (count > 0 && count2 > 0) {
									JOptionPane.showMessageDialog(null, "Transferência realizada com sucesso.");
								} else {
									JOptionPane.showMessageDialog(null, "Erro ao realizar transferência.");
								}
							} catch (SQLException err) {
								err.printStackTrace();
							} finally {
								DBConnect.EndConnection(conn);

								try {
									stmt2.close();
									stmt3.close();
								} catch (SQLException err) {
									err.printStackTrace();
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "Email não encontrado.");
						}
					} catch (SQLException err) {
						err.printStackTrace();
					} finally {
						DBConnect.EndConnection(conn);

						try {
							stmt.close();
							rs.close();
						} catch (SQLException err) {
							err.printStackTrace();
						}
					}
				}
			}
		});
		
		JLabel lblNewLabel = new JLabel("Tranferência");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(10, 11, 123, 14);
		frame.getContentPane().add(lblNewLabel);
	}
}
