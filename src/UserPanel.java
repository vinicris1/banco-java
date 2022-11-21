import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.Font;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		
		double[] balance = InfoFuncs.GetBalance();
		
		JLabel lblNewLabel = new JLabel(String.format("Bem vindo, %s!", Login.name));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 120, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblBrl = new JLabel("BRL: " + balance[0]);
		lblBrl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBrl.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrl.setBounds(320, 57, 60, 30);
		frame.getContentPane().add(lblBrl);
		
		JLabel lblUsd = new JLabel("USD: " + balance[1]);
		lblUsd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsd.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsd.setBounds(320, 83, 60, 30);
		frame.getContentPane().add(lblUsd);
		
		JLabel lblEur = new JLabel("EUR: " + balance[2]);
		lblEur.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEur.setHorizontalAlignment(SwingConstants.CENTER);
		lblEur.setBounds(320, 111, 60, 30);
		frame.getContentPane().add(lblEur);
		
		JLabel lblNewLabel_1 = new JLabel("Saldo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(320, 16, 60, 30);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Transferência");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Transfer trf = new Transfer();
				trf.frame.setVisible(true);
				frame.setVisible(false);
			}
		});
		btnNewButton.setBounds(301, 152, 120, 25);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Converter");
		btnNewButton_1.setBounds(301, 204, 100, 25);
		frame.getContentPane().add(btnNewButton_1);
	}
}
