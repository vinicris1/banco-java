import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;

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
        
        JLabel lblNewLabel = new JLabel(Login.userName);
        lblNewLabel.setBounds(49, 43, 87, 39);
        frame.getContentPane().add(lblNewLabel);
        
        JLabel lblCpf = new JLabel(Login.cpf);
        lblCpf.setBounds(49, 93, 87, 39);
        frame.getContentPane().add(lblCpf);
        
        JLabel lblBrl = new JLabel(Double.toString(Login.balanceBrl));
        lblBrl.setBounds(49, 143, 87, 39);
        frame.getContentPane().add(lblBrl);
        
        JLabel lblUsd = new JLabel("usd");
        lblUsd.setBounds(178, 165, 87, 39);
        frame.getContentPane().add(lblUsd);
        
        JLabel lblEur = new JLabel("eur");
        lblEur.setBounds(297, 177, 87, 39);
        frame.getContentPane().add(lblEur);





      
    }
}
