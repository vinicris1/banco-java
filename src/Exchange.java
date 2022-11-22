import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;

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
        frame.setBounds(100, 100, 403, 172);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JComboBox fromConvert = new JComboBox();
        fromConvert.setModel(new DefaultComboBoxModel(new String[] {"BRL", "USD", "EUR"}));
        fromConvert.setBounds(96, 52, 52, 22);
        frame.getContentPane().add(fromConvert);
        
        JComboBox toConvert = new JComboBox();
        toConvert.setModel(new DefaultComboBoxModel(new String[] {"BRL", "USD", "EUR"}));
        toConvert.setSelectedItem("");
        toConvert.setBounds(191, 52, 52, 22);
        frame.getContentPane().add(toConvert);
        
        
        JLabel lblNewLabel_1 = new JLabel("to");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(150, 56, 46, 14);
        frame.getContentPane().add(lblNewLabel_1);
        
        value = new JTextField();
        value.setBounds(34, 54, 52, 20);
        frame.getContentPane().add(value);
        value.setColumns(10);
        
        
        
        JButton btnNewButton = new JButton("Converter");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                double[] balance = InfoFuncs.GetBalance();
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
                
               if (teste.length() < 1 || getVal < 1) { //Arrumar para n pegar com o valor vazio
                    JOptionPane.showMessageDialog(null, "Você não pode converter um valor menor ou igual a 0.");
                }else if(valFrom.equals(valTo)) {
                    JOptionPane.showMessageDialog(null, "Não pode converter a mesma moeda");
                }else if(getVal > balance[index]) {
                    JOptionPane.showMessageDialog(null, "Você não tem saldo suficiente");
                }else {
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
                            
                        } catch (SQLException err) {
                            err.printStackTrace();
                        }
                    }
                }
                    
  
                
            }
        });
        btnNewButton.setBounds(280, 91, 89, 23);
        frame.getContentPane().add(btnNewButton);
        
        JLabel lblNewLabel_3 = new JLabel("Valor para transferir");
        lblNewLabel_3.setBounds(24, 33, 114, 14);
        frame.getContentPane().add(lblNewLabel_3);
        
        JLabel lblNewLabel_3_1 = new JLabel("Valor para receber");
        lblNewLabel_3_1.setBounds(191, 31, 114, 14);
        frame.getContentPane().add(lblNewLabel_3_1);
        
        
        
        
    }
}
