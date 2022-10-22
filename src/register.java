import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class register {

	JFrame frame;
	private JTextField nome;
	private JTextField email;
	private JPasswordField senha;
	private JTextField cpf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					register window = new register();
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
	public register() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 308);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome completo:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(23, 22, 118, 14);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(23, 116, 85, 14);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Senha:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(23, 162, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		nome = new JTextField();
		nome.setBounds(33, 38, 365, 20);
		frame.getContentPane().add(nome);
		nome.setColumns(10);
		
		email = new JTextField();
		email.setColumns(10);
		email.setBounds(33, 131, 365, 20);
		frame.getContentPane().add(email);
		
		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conexao = null;
				
				PreparedStatement comando = null;
				PreparedStatement comando2 = null;
				
				String nomeR = nome.getText();
				String emailR = email.getText();
				String password = senha.getText();	
				String cpfR = cpf.getText();
				int id = 0;
				
				String msg = "" + nomeR;
				msg += " \n";
				
				if(emailR.contains("@") && cpfR.length() == 11) {	    
					try {
						conexao = Db_Connect.Conectar();
						
						String sql = "INSERT INTO usuarios(id,name,email,password,cpf) VALUES(?,?,?,?,?)";
						
						
						comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
						comando2 = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);						
						
						String select = "SELECT * FROM usuarios WHERE email='"+emailR+"'";
						String selectCpf = "SELECT * FROM usuarios WHERE cpf='"+cpfR+"'";
						
						
						ResultSet rs = comando.executeQuery(select);
						ResultSet rs2 = comando2.executeQuery(selectCpf);
						
						if(rs.next()) {
						    JOptionPane.showMessageDialog(btnNewButton, "Olá, "+ msg + "esse e-mail já foi registrado!");
						}else if(rs2.next()){
						    JOptionPane.showMessageDialog(btnNewButton, "Olá, "+ msg + "esse cpf já foi registrado!");
						}else {
						    comando.setInt(1, id);
	                        comando.setString(2, nomeR);
	                        comando.setString(3, emailR);
	                        comando.setString(4, password);
	                        comando.setString(5, cpfR);
	                        
	                        if(comando.executeUpdate()>0) {
	                            JOptionPane.showMessageDialog(btnNewButton, "Bem-vindo, "+ msg + "sua conta foi criada com sucesso!");
	                            frame.setVisible(false);
	                        }
						}
						}catch(SQLException er) {
							er.printStackTrace();
					}finally{
						Db_Connect.FecharConexao(conexao);
						try {
							comando.close();
							comando2.close();
						}catch(SQLException err){
							err.printStackTrace();
						}
					}
				}else if(cpfR.length() > 11 || cpfR.length() < 11){
				    JOptionPane.showMessageDialog(btnNewButton, "Olá, "+ msg + "por favor coloque um cpf válido!");
				}else {
				    JOptionPane.showMessageDialog(btnNewButton, "Olá, "+ msg + "por favor coloque um email válido!");
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(335, 210, 89, 23);
		frame.getContentPane().add(btnNewButton);
		
		senha = new JPasswordField();
		senha.setBounds(33, 179, 365, 20);
		frame.getContentPane().add(senha);
		
		JLabel lblCpf = new JLabel("Cpf:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpf.setBounds(23, 69, 118, 14);
		frame.getContentPane().add(lblCpf);
		
		cpf = new JTextField();
		cpf.setColumns(10);
		cpf.setBounds(33, 85, 365, 20);
		frame.getContentPane().add(cpf);
	}
}
