import java.sql.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class Register {

	JFrame frame;
	private JTextField nome;
	private JTextField email;
	private JPasswordField senha;
	private JTextField cpf;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Register window = new Register();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Register() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 360);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nome completo:");
		lblNewLabel.setBounds(30, 20, 115, 20);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("E-mail:");
		lblNewLabel_1.setBounds(30, 145, 50, 20);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Senha:");
		lblNewLabel_2.setBounds(30, 205, 50, 20);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblNewLabel_2);
		
		nome = new JTextField();
		nome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nome.setBounds(30, 50, 365, 25);
		frame.getContentPane().add(nome);
		nome.setColumns(10);
		
		email = new JTextField();
		email.setFont(new Font("Tahoma", Font.PLAIN, 12));
		email.setBounds(30, 170, 365, 25);
		email.setColumns(10);
		frame.getContentPane().add(email);
		
		JButton btnNewButton = new JButton("Registrar");
		btnNewButton.setBounds(165, 275, 90, 25);
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
				
				if (emailR.contains("@") && cpfR.length() == 11) {	    
					try {
						conexao = DBConnect.StartConnection();
						
						String sql = "INSERT INTO usuarios(id,name,email,password,cpf) VALUES(?,?,?,?,?)";
						
						
						comando = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
						comando2 = conexao.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);						
						
						String select = "SELECT * FROM usuarios WHERE email='" + emailR + "'";
						String selectCpf = "SELECT * FROM usuarios WHERE cpf='" + cpfR + "'";
						
						ResultSet rs = comando.executeQuery(select);
						ResultSet rs2 = comando2.executeQuery(selectCpf);
						
						if(rs.next()) {
						    JOptionPane.showMessageDialog(btnNewButton, "Olá, "+ msg + "esse e-mail já foi registrado!");
						}else if(rs2.next()){
						    JOptionPane.showMessageDialog(btnNewButton, "Olá, "+ msg + "este CPF já foi registrado!");
						}else {
						    comando.setInt(1, id);
	                        comando.setString(2, nomeR);
	                        comando.setString(3, emailR);
	                        comando.setString(4, password);
	                        comando.setString(5, cpfR);
	                        
	                        if(comando.executeUpdate()>0) {
	                            JOptionPane.showMessageDialog(btnNewButton, "Bem-vindo, " + msg + "sua conta foi criada com sucesso!");
	                            frame.setVisible(false);
	                        }
						}
						}catch(SQLException er) {
							er.printStackTrace();
					}finally{
						DBConnect.EndConnection(conexao);
						try {
							comando.close();
							comando2.close();
						}catch(SQLException err){
							err.printStackTrace();
						}
					}
				}else if(cpfR.length() > 11 || cpfR.length() < 11){
				    JOptionPane.showMessageDialog(btnNewButton, "Olá, " + msg + "por favor coloque um CPF válido!");
				}else {
				    JOptionPane.showMessageDialog(btnNewButton, "Olá, " + msg + "por favor coloque um e-mail válido!");
				}
				
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(btnNewButton);
		
		senha = new JPasswordField();
		senha.setFont(new Font("Tahoma", Font.PLAIN, 12));
		senha.setBounds(30, 230, 365, 25);
		frame.getContentPane().add(senha);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setBounds(30, 80, 40, 20);
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		frame.getContentPane().add(lblCpf);
		
		cpf = new JTextField();
		cpf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cpf.setBounds(30, 110, 365, 25);
		cpf.setColumns(10);
		frame.getContentPane().add(cpf);
	}
}
