import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;

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
		value.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();

				if (Character.isDigit(c) || c == KeyEvent.VK_PERIOD || c == KeyEvent.VK_BACK_SPACE) {
					if (c == KeyEvent.VK_PERIOD) {
						String str = value.getText().toString();

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
		value.setBounds(20, 50, 100, 25);
		panel.add(value);
		value.setColumns(10);
		
		JComboBox fromConvert = new JComboBox();
		fromConvert.setBounds(150, 50, 55, 25);
		panel.add(fromConvert);
		fromConvert.setModel(new DefaultComboBoxModel(new String[] {"BRL", "USD", "EUR"}));
		
		JComboBox toConvert = new JComboBox();
		toConvert.setBounds(255, 50, 55, 25);
		panel.add(toConvert);
		toConvert.setModel(new DefaultComboBoxModel(new String[] {"BRL", "USD", "EUR"}));
		toConvert.setSelectedItem("");
		
		JLabel lblNewLabel_1 = new JLabel("Para");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(215, 55, 60, 15);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_3_1 = new JLabel("Moeda");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3_1.setBounds(150, 26, 60, 15);
		lblNewLabel_3_1.setForeground(new Color(255, 255, 255));
		panel.add(lblNewLabel_3_1);
		
		JLabel lblNewLabel = new JLabel("Conversor de Moeda");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 22, 202, 14);
		frame.getContentPane().add(lblNewLabel);

		JButton btnNewButton = new JButton("Converter");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(43, 132, 116));
		btnNewButton.setBounds(388, 50, 90, 25);
		btnNewButton.setFocusPainted(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				Statement stmt = null, stmt_2 = null;
				
				String valFrom = fromConvert.getSelectedItem().toString().toLowerCase();
				String valTo = toConvert.getSelectedItem().toString().toLowerCase();
				String getVal = value.getText().toString();

				int index = 0;
				if (valFrom.equals("brl")) {
					index = 0;
				} else if (valFrom.equals("usd")) {
					index = 1;
				} else if (valFrom.equals("eur")) {
					index = 2;
				}

				double[] balance = Utils.GetBalance();

				if (getVal.length() < 1 || Double.parseDouble(getVal) < 1) {
					JOptionPane.showMessageDialog(null, "Valor inv??lido.");
				} else if (valFrom.equals(valTo)) {
					JOptionPane.showMessageDialog(null, "Por favor, selecione duas moedas diferentes.");
				} else if (Double.parseDouble(getVal) > balance[index]) {
					JOptionPane.showMessageDialog(null, "Saldo insuficiente.");
				} else {
					double result = 0;
					if (valFrom.equals("brl") && valTo.equals("usd")) {
						result = (Double.parseDouble(getVal) / 5.36);
					} else if (valFrom.equals("brl") && valTo.equals("eur")) {
						result = (Double.parseDouble(getVal) / 5.52);
					} else if (valFrom.equals("usd") && valTo.equals("brl")) {
						result = (Double.parseDouble(getVal) * 5.36);
					} else if (valFrom.equals("usd") && valTo.equals("eur")) {
						result = (Double.parseDouble(getVal) * 0.97);
					} else if (valFrom.equals("eur") && valTo.equals("brl")) {
						result = (Double.parseDouble(getVal) * 5.52);
					} else if (valFrom.equals("eur") && valTo.equals("usd")) {
						result = (Double.parseDouble(getVal) * 1.03);
					}

					try {
						conn = DBConnect.StartConnection();
						
						stmt = conn.createStatement();
						String removeBalance = String.format("UPDATE usuarios SET balance_%s = (balance_%s - %s) WHERE email = '%s'", valFrom, valFrom, getVal, Login.userEmail);
						int exec = stmt.executeUpdate(removeBalance);
						
						stmt_2 = conn.createStatement();
						String addBalance = String.format("UPDATE usuarios SET balance_%s = (balance_%s + %s) WHERE email = '%s'", valTo, valTo, result, Login.userEmail);
						int exec_2 = stmt_2.executeUpdate(addBalance);
						
						if (exec > 0 && exec_2 > 0) {
							JOptionPane.showMessageDialog(null, "Transfer??cia realizada com sucesso.");
						}
					} catch (SQLException err) {
						err.printStackTrace();
					} finally {
						DBConnect.EndConnection(conn);

						try {
							if (conn != null) conn.close();
							if (stmt != null) stmt.close();
							if (stmt_2 != null) stmt_2.close();
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
		panel.add(btnNewButton);
	}
}
