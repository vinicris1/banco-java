import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Exchange {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;

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
        frame.setBounds(100, 100, 450, 158);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        JComboBox comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"BRL", "U$D", "EUR"}));
        comboBox.setBounds(82, 53, 52, 22);
        frame.getContentPane().add(comboBox);
        
        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setBounds(342, 57, 46, 14);
        frame.getContentPane().add(lblNewLabel);
        
        JComboBox comboBox_2 = new JComboBox();
        comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"U$D", "EUR", "BRL"}));
        comboBox_2.setBounds(239, 53, 52, 22);
        frame.getContentPane().add(comboBox_2);
        
        JLabel lblNewLabel_1 = new JLabel("to");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(136, 57, 46, 14);
        frame.getContentPane().add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("=");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(301, 57, 46, 14);
        frame.getContentPane().add(lblNewLabel_2);
        
        textField = new JTextField();
        textField.setBounds(20, 55, 52, 20);
        frame.getContentPane().add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(182, 54, 52, 20);
        frame.getContentPane().add(textField_1);
        
        JButton btnNewButton = new JButton("Converter");
        btnNewButton.setBounds(335, 85, 89, 23);
        frame.getContentPane().add(btnNewButton);
        
        JLabel lblNewLabel_3 = new JLabel("Valor para transferir");
        lblNewLabel_3.setBounds(10, 34, 114, 14);
        frame.getContentPane().add(lblNewLabel_3);
        
        JLabel lblNewLabel_3_1 = new JLabel("Valor para receber");
        lblNewLabel_3_1.setBounds(177, 32, 114, 14);
        frame.getContentPane().add(lblNewLabel_3_1);
    }
}
