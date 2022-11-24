import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Font;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.sql.*;

public class Exchange {

	JFrame frame;
	private JTextField value;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Exchange window = new Exchange();
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
	public Exchange() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setEnabled(false);
		frame.getContentPane().setBackground(new Color(18, 18, 20));
		frame.setBounds(100, 100, 550, 342);
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
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(28, 28, 33));
		panel.setBounds(5, 70, 525, 110);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Valor");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(20, 26, 60, 15);
		panel.add(lblNewLabel_3);
		lblNewLabel_3.setForeground(new Color(255, 255, 255));
		
		value = new JTextField();
		value.setBounds(20, 50, 100, 25);
		panel.add(value);
		value.setColumns(10);
		
		JComboBox fromConvert = new JComboBox();
		fromConvert.setBounds(255, 50, 55, 25);
		panel.add(fromConvert);
		fromConvert.setModel(new DefaultComboBoxModel(new String[] {"BRL", "USD", "EUR"}));
		
		JComboBox toConvert = new JComboBox();
		toConvert.setBounds(150, 50, 55, 25);
		panel.add(toConvert);
		toConvert.setModel(new DefaultComboBoxModel(new String[] {"BRL", "USD", "EUR"}));
		toConvert.setSelectedItem("");
		
		JLabel lblNewLabel_1 = new JLabel("Para");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(215, 55, 60, 15);
		panel.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		
		JButton btnNewButton = new JButton("Converter");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(43, 132, 116));
		btnNewButton.setBounds(388, 50, 90, 25);
		panel.add(btnNewButton);
		
		JLabel lblNewLabel_3_1 = new JLabel("Moeda");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3_1.setBounds(150, 26, 60, 15);
		panel.add(lblNewLabel_3_1);
		lblNewLabel_3_1.setForeground(new Color(255, 255, 255));
		
		JLabel lblNewLabel = new JLabel("Conversor de Moeda");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 22, 202, 14);
		frame.getContentPane().add(lblNewLabel);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double[] balance = Utils.GetBalance();
				double getVal = Double.parseDouble(value.getText());
				Connection conn = null;
				Statement stmt = null;
				ResultSet rs = null;
				
				String valFrom = fromConvert.getSelectedItem().toString().toLowerCase();
				String valTo = toConvert.getSelectedItem().toString().toLowerCase();
				
				double result = 0;
				
				if (valFrom.equals("brl") && valTo.equals("usd")) {
					result = (getVal / 5.36);
				} else if (valFrom.equals("brl") && valTo.equals("eur")) {
					result = (getVal / 5.52);
				} else if (valFrom.equals("usd") && valTo.equals("brl")) {
					result = (getVal * 5.36);
				} else if (valFrom.equals("usd") && valTo.equals("eur")) {
					result = (getVal * 0.97);
				} else if (valFrom.equals("eur") && valTo.equals("brl")) {
					result = (getVal * 5.52);
				} else if (valFrom.equals("eur") && valTo.equals("usd")) {
					result = (getVal * 1.03);
				}
				
				int index = 0;

				if (valFrom.equals("brl")) {
					index = 0;
				} else if (valFrom.equals("usd")) {
					index = 1;
				} else if (valFrom.equals("eur")) {
					index = 2;
				}
				
				String teste = value.getText();
				
				// Arrumar para n pegar com o valor vazio
				if (teste.length() < 1 || getVal < 1) {
					JOptionPane.showMessageDialog(null, "Valor inválido.");
				} else if (valFrom.equals(valTo)) {
					JOptionPane.showMessageDialog(null, "Por favor, selecione duas moedas diferentes.");
				} else if (getVal > balance[index]) {
					JOptionPane.showMessageDialog(null, "Saldo insuficiente.");
				} else {
					try {
						conn = DBConnect.StartConnection();
						stmt = conn.createStatement();

						String removeBalance = String.format("UPDATE usuarios SET balance_%s = (balance_%s - %s) WHERE email = '%s'", valFrom, valFrom, getVal, Login.userEmail);
						int exec = stmt.executeUpdate(removeBalance);
						
						String addBalance = String.format("UPDATE usuarios SET balance_%s = (balance_%s + %s) WHERE email = '%s'", valTo, valTo, result, Login.userEmail);
						int exec2 = stmt.executeUpdate(addBalance);
						
						if(exec > 0 && exec2 > 0) {
							System.out.println("ok");
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
	}
}
