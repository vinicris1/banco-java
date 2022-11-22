import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;

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
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				frame.dispose();
				UserPanel userPanel = new UserPanel();
				userPanel.frame.setVisible(true);
			}
		});
		
		JLabel lblNewLabel_0 = new JLabel("Moeda:");
		lblNewLabel_0.setBounds(19, 33, 77, 25);
		frame.getContentPane().add(lblNewLabel_0);
		
		transferValue = new JTextField();
		transferValue.setToolTipText("Email");
		transferValue.setBounds(66, 69, 75, 25);
		frame.getContentPane().add(transferValue);
		transferValue.setColumns(10);
		
		JComboBox transferCurrency = new JComboBox();
		transferCurrency.setModel(new DefaultComboBoxModel(new String[] {"BRL", "USD", "EUR"}));
		transferCurrency.setBounds(106, 34, 70, 25);
		frame.getContentPane().add(transferCurrency);
		
		JLabel lblNewLabel_1 = new JLabel("Valor:");
		lblNewLabel_1.setBounds(23, 72, 60, 19);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Transferir para:");
		lblNewLabel_2.setBounds(33, 136, 90, 25);
		frame.getContentPane().add(lblNewLabel_2);
		
		transferReceiver = new JTextField();
		transferReceiver.setBounds(126, 136, 150, 25);
		frame.getContentPane().add(transferReceiver);
		transferReceiver.setColumns(10);
		
		JButton btnNewButton = new JButton("Transferir");
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

				double[] balance = InfoFuncs.GetBalance();

				if (value.length() < 1 || Double.parseDouble(value) < 1) {
					JOptionPane.showMessageDialog(null, "Valor inválido.");
				} else if (receiverEmail.length() < 1 || receiverEmail.equalsIgnoreCase(Login.userEmail)) {
					JOptionPane.showMessageDialog(null, "Email inválido.");
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
		btnNewButton.setBounds(158, 199, 90, 25);
		frame.getContentPane().add(btnNewButton);
	}
}
