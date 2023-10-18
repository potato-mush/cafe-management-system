import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener, MouseListener {

    private JTextField tfEmail;
    private JPasswordField pfPass;
    private JLabel label2;
    private JLabel label;
    private JLabel shdw_label;
    private JLabel shdw_label2;
    private JButton loginBtn;
    private JLabel signupLabel;
    private JLabel signupBtn;
    private JLabel userIcon;
    private JLabel passIcon;
    private JLabel titleIcon;

    public Login() {

        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(new ImageIcon("images/background.jpg").getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH)));
        add(background);
        setSize(700, 500);

        label = new JLabel("Welcome");
        label.setBounds(50, 35, 300, 100);
        label.setFont(new Font("Verdana", Font.BOLD, 52));

        label2 = new JLabel("to Coffee Brew");
        label2.setBounds(65, 100, 350, 70);
        label2.setFont(new Font("Verdana", Font.BOLD, 36));

        shdw_label = new JLabel("Welcome");
        shdw_label.setBounds(53, 38, 300, 100);
        shdw_label.setFont(new Font("Verdana", Font.BOLD, 52));
        shdw_label.setForeground(Color.WHITE);

        shdw_label2 = new JLabel("to Coffee Brew");
        shdw_label2.setBounds(68, 103, 350, 70);
        shdw_label2.setFont(new Font("Verdana", Font.BOLD, 36));
        shdw_label2.setForeground(Color.WHITE);

        tfEmail = new JTextField();
        tfEmail.setBounds(150, 220, 200, 30);
        tfEmail.setFont(new Font("Verdana", Font.PLAIN, 16));

        pfPass = new JPasswordField();
        pfPass.setBounds(150, 280, 200, 30);
        pfPass.setFont(new Font("Verdana", Font.PLAIN, 16));

        loginBtn = new JButton("Login");
        loginBtn.setBounds(180, 350, 120, 35);
        loginBtn.setBackground(Color.DARK_GRAY);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.addActionListener(this);
        loginBtn.setFont(new Font("Verdana", Font.PLAIN, 18));

        signupLabel = new JLabel("Create an account?");
        signupLabel.setBounds(150, 400, 200, 70);
        signupLabel.setFont(new Font("Verdana", Font.PLAIN, 14));

        signupBtn = new JLabel("Click here");
        signupBtn.setBounds(300, 420, 120, 30);
        signupBtn.setFont(new Font("Verdana", Font.BOLD, 14));
        signupBtn.setForeground(Color.blue);
        signupBtn.addMouseListener(this);

        userIcon = new JLabel();
        userIcon.setIcon(new ImageIcon(new ImageIcon("images/user.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        userIcon.setBounds(85, 215, 40, 40);

        passIcon = new JLabel();
        passIcon.setIcon(new ImageIcon(new ImageIcon("images/padlock.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        passIcon.setBounds(85, 275, 40, 40);

        background.add(label);
        background.add(label2);
        background.add(shdw_label);
        background.add(shdw_label2);
        background.add(tfEmail);
        background.add(pfPass);
        background.add(loginBtn);
        background.add(signupLabel);
        background.add(signupBtn);
        background.add(userIcon);
        background.add(passIcon);

        setTitle("Login Form");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

    }

    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == signupBtn) {
                dispose();
                Signup su = new Signup();
                su.show();
        }
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginBtn) {
            String email = tfEmail.getText();
            String password = String.valueOf(pfPass.getPassword());

            user = getAuthenticatedUser(email, password);

            if (user != null) {

            } else if (email.equals("admin") && password.equals("admin")) {
                dispose();
                adminDashboard admin = new adminDashboard();
                admin.show();
            } else {
                JOptionPane.showMessageDialog(Login.this, "Email or Password Invalid!", "Try Again", JOptionPane.ERROR_MESSAGE);
            }

            tfEmail.setText("");
            pfPass.setText("");
        }
    }
    public User user;
    private User getAuthenticatedUser(String username, String password) {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/cafe_db";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                user = new User();
                user.username = resultSet.getString("username");
                user.password = resultSet.getString("password");
                stmt.close();
                conn.close();


                JOptionPane.showMessageDialog(this, "Successfully Login!", "Notice", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                CoffeeShop coffeeShop = new CoffeeShop();
                coffeeShop.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static void main(String[] args) {

        new Login().setVisible(true);
    }

}