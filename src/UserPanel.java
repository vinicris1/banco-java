import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.text.DecimalFormat;

public class UserPanel {

	JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserPanel window = new UserPanel();
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
	public UserPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(18, 18, 20));
		frame.setResizable(false);
		frame.setBounds(100, 100, 550, 351);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		DecimalFormat df = new DecimalFormat("0.00");

		double[] balance = Utils.GetBalance();
		
		JLabel lblNewLabel = new JLabel(String.format("Bem vindo, %s.", Login.name));
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 11, 480, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(20, 20, 33));
		panel.setBounds(75, 62, 142, 177);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblBrl = new JLabel("BRL: " + df.format(balance[0]));
		lblBrl.setForeground(new Color(255, 255, 255));
		lblBrl.setBounds(10, 52, 122, 30);
		panel.add(lblBrl);
		lblBrl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBrl.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblUsd = new JLabel("USD: " + df.format(balance[1]));
		lblUsd.setForeground(new Color(255, 255, 255));
		lblUsd.setBounds(10, 93, 122, 30);
		panel.add(lblUsd);
		lblUsd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsd.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblEur = new JLabel("EUR: " + df.format(balance[2]));
		lblEur.setForeground(new Color(255, 255, 255));
		lblEur.setBounds(10, 136, 122, 30);
		panel.add(lblEur);
		lblEur.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEur.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1_1 = new JLabel("Saldo");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1_1.setBounds(42, 11, 60, 30);
		panel.add(lblNewLabel_1_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(20, 20, 30));
		panel_1.setBorder(null);
		panel_1.setBounds(301, 62, 135, 177);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnNewButton = new JButton("Transferência   >");
		btnNewButton.setBackground(new Color(20, 20, 30));
		btnNewButton.setForeground(new Color(43, 132, 116));
		btnNewButton.setBounds(0, 67, 120, 25);
		panel_1.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("Opções");
		lblNewLabel_1.setBounds(35, 11, 60, 30);
		panel_1.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(255, 255, 255));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JButton btnNewButton_1 = new JButton("Converter  >");
		btnNewButton_1.setBackground(new Color(20, 20, 30));
		btnNewButton_1.setForeground(new Color(43, 132, 116));
		btnNewButton_1.setBounds(0, 103, 100, 25);
		panel_1.add(btnNewButton_1);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Transfer trf = new Transfer();
				trf.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
	}
}
