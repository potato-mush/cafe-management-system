import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Signup extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField tfUsername;
    private JTextField tfName;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPW;
    private JButton createBtn, backBtn;

    public Signup() {

        setLayout(new BorderLayout());
        JLabel background = new JLabel(new ImageIcon(new ImageIcon("images/background.jpg").getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH)));
        add(background);

        setSize(600, 406);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Signup Form");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel l = new JLabel("Create Account");
        l.setForeground(Color.black);
        l.setFont(new Font("Georgia", Font.PLAIN, 30));
        l.setBounds(185, 25, 330, 56);
        contentPane.add(l);

        JLabel lblUsername = new JLabel("Username :");
        lblUsername.setForeground(Color.DARK_GRAY);
        lblUsername.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblUsername.setBounds(99, 99, 92, 26);
        contentPane.add(lblUsername);

        JLabel lblName = new JLabel("Name :");
        lblName.setForeground(Color.DARK_GRAY);
        lblName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblName.setBounds(99, 138, 92, 26);
        contentPane.add(lblName);

        JLabel lblPassword = new JLabel("Password :");
        lblPassword.setForeground(Color.DARK_GRAY);
        lblPassword.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblPassword.setBounds(99, 173, 92, 26);
        contentPane.add(lblPassword);

        JLabel lblConfirmPW = new JLabel("Confirm Password :");
        lblConfirmPW.setForeground(Color.DARK_GRAY);
        lblConfirmPW.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblConfirmPW.setBounds(99, 218, 192, 26);
        contentPane.add(lblConfirmPW);

        tfUsername = new JTextField();
        tfUsername.setBounds(265, 102, 148, 20);
        contentPane.add(tfUsername);
        tfUsername.setColumns(10);

        tfName = new JTextField();
        tfName.setColumns(10);
        tfName.setBounds(265, 139, 148, 20);
        contentPane.add(tfName);

        pfPassword = new JPasswordField();
        pfPassword.setColumns(10);
        pfPassword.setBounds(265, 175, 148, 20);
        contentPane.add(pfPassword);

        pfConfirmPW = new JPasswordField();
        pfConfirmPW.setColumns(10);
        pfConfirmPW.setBounds(265, 217, 148, 20);
        contentPane.add(pfConfirmPW);

        createBtn = new JButton("Create");
        createBtn.addActionListener(this);
        createBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        createBtn.setBounds(140, 305, 100, 30);
        createBtn.setBackground(Color.DARK_GRAY);
        createBtn.setForeground(Color.WHITE);
        contentPane.add(createBtn);

        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        backBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        backBtn.setBounds(300, 305, 100, 30);
        backBtn.setBackground(Color.DARK_GRAY);
        backBtn.setForeground(Color.WHITE);
        contentPane.add(backBtn);

        background.add(contentPane);
        background.add(l);
        background.add(createBtn);
        background.add(backBtn);
        background.add(lblUsername);
        background.add(tfUsername);
        background.add(lblName);
        background.add(tfName);
        background.add(lblPassword);
        background.add(pfPassword);
        background.add(lblConfirmPW);
        background.add(pfConfirmPW);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == createBtn) {
            registerUser();
        }
        if (ae.getSource() == backBtn) {
            this.setVisible(false);
            new Login().setVisible(true);
        }
    }

    private void registerUser() {
        String name = tfName.getText();
        String email = tfUsername.getText();
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPW.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this,
                    "Confirm Password does not match",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        user = addUserToDatabase(name, email, password);
        if (user != null) {
            JOptionPane.showMessageDialog(this, "Account Successfully Created.", "Notice", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            Login lg = new Login();
            lg.show();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
        public User user;

        private User addUserToDatabase (String name, String username, String password){
            User user = null;
            final String DB_URL = "jdbc:mysql://localhost/cafe_db?serverTimezone=UTC";
            final String USERNAME = "root";
            final String PASSWORD = "";

            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO users (name, username, password) " +
                        "VALUES (?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, username);
                preparedStatement.setString(3, password);

                //Insert row into the table
                int addedRows = preparedStatement.executeUpdate();
                if (addedRows > 0) {
                    user = new User();
                    user.name = name;
                    user.username = username;
                    user.password = password;
                }

                stmt.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return user;
        }

        public static void main (String[]args){
            new Signup();
        }
    }




