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
                double getVal = 0;
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;
                
                try{
                    getVal = Double.parseDouble(value.getText());
                    
                }catch(NumberFormatException nfe){
                    System.out.println("ERRO");
                }
                String valFrom = fromConvert.getSelectedItem().toString().toLowerCase();
                String valTo = toConvert.getSelectedItem().toString().toLowerCase();
                
                String brl = "BRL";
                String usd = "USD";
                String eur = "EUR";
                
              /*  double currency = 0;
                if(valFrom.equals("brl")) {
                    double result = (50 * 5.37);
                }*/
                
                
               if (getVal <= 0) {
                    JOptionPane.showMessageDialog(null, "Você não pode converter um valor menor ou igual a 0.");
                }if(getVal > 0) {
                    if(valFrom.equals(valTo)) {
                        JOptionPane.showMessageDialog(null, "Você não pode converter da mesma moeda.");
                    }else if(valFrom.equals(brl.toLowerCase())) { //brl para dolar ou eur
                       // if(balance[0] >= getVal) { //verificação
                            if(valTo.equals(usd.toLowerCase())) {
                                    double result = (getVal / 5.37);
                                    JOptionPane.showMessageDialog(null, result);
                                    /*try {
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
                                    }*/
                                
                            }else if(valTo.equals(eur.toLowerCase())) {
                                double result = (getVal / 5.52);
                                JOptionPane.showMessageDialog(null, result);
                            }
                       // }else {
                       //     JOptionPane.showMessageDialog(null, "Você não tem saldo suficiente");
                        //}
                        
                    }else if(valFrom.equals(usd.toLowerCase())) { //dolar para brl ou eur
                        if(balance[1] >= getVal) { //verificação
                            if(valTo.equals(brl.toLowerCase())) {
                                double result = (getVal * 5.37);
                                JOptionPane.showMessageDialog(null, result);
                            }else if(valTo.equals(eur.toLowerCase())) {
                                double result = (getVal * 0.97);
                                JOptionPane.showMessageDialog(null, result);
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "Você não tem saldo suficiente");
                        }
                        
                    }else if(valFrom.equals(eur.toLowerCase())) { //eur para brl ou usd
                        if(balance[2] >= getVal) { //verificação
                            if(valTo.equals(brl.toLowerCase())) {
                                double result = (getVal * 5.52);
                                JOptionPane.showMessageDialog(null, result);
                            }else if(valTo.equals(usd.toLowerCase())) {
                                double result = (getVal * 1.03);
                                JOptionPane.showMessageDialog(null, result);
                            }
                        }else {
                            JOptionPane.showMessageDialog(null, "Você não tem saldo suficiente");
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
