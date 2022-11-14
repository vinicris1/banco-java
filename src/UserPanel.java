import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.awt.EventQueue;

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
		
		JLabel lblNewLabel = new JLabel(String.format("Bem vindo, %s!", Login.name));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(10, 11, 120, 40);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblBrl = new JLabel("BRL: " + Login.balanceBrl);
		lblBrl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBrl.setHorizontalAlignment(SwingConstants.CENTER);
		lblBrl.setBounds(320, 57, 60, 30);
		frame.getContentPane().add(lblBrl);
		
		JLabel lblUsd = new JLabel("USD: " + Login.balanceUsd);
		lblUsd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUsd.setHorizontalAlignment(SwingConstants.CENTER);
		lblUsd.setBounds(320, 98, 60, 30);
		frame.getContentPane().add(lblUsd);
		
		JLabel lblEur = new JLabel("EUR: " + Login.balanceEur);
		lblEur.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEur.setHorizontalAlignment(SwingConstants.CENTER);
		lblEur.setBounds(320, 139, 60, 30);
		frame.getContentPane().add(lblEur);
		
		JLabel lblNewLabel_1 = new JLabel("Saldo");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(320, 16, 60, 30);
		frame.getContentPane().add(lblNewLabel_1);
	}
}
