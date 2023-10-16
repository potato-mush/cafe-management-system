import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CoffeeShop extends JFrame {

    private JPanel mainPanel;
    private JRadioButton smallRadioButton;
    private JRadioButton mediumRadioButton;
    private JRadioButton largeRadioButton;
    private JSpinner a1spinner;
    private JTextField tfuserMoney;
    private JLabel taxOutput;
    private JLabel totalOutput;
    private JLabel changeOutput;
    private JButton orderAgainButton;
    private JButton placeOrderButton;
    private JSpinner a2spinner;
    private JSpinner a3spinner;
    private JSpinner a4spinner;
    private JSpinner b1spinner;
    private JSpinner b2spinner;
    private JSpinner b3spinner;
    private JSpinner b4spinner;
    private JPanel menuPanel;
    private JPanel leftPanel;
    private JCheckBox a1CheckBox;
    private JLabel subtotalOutput;
    private JCheckBox a2CheckBox;
    private JCheckBox a3CheckBox;
    private JCheckBox a4CheckBox;
    private JCheckBox b1CheckBox;
    private JCheckBox b2CheckBox;
    private JCheckBox b3CheckBox;
    private JCheckBox b4CheckBox;
    private JLabel image1;
    private JLabel leftIcon;
    private JLabel rightIcon;
    private JLabel latte;
    private JLabel mocha;
    private JLabel bcoffee;
    private JLabel cappuccino;
    private JLabel croissant;
    private JLabel doughNut;
    private JLabel pancake;
    private JLabel cake;
    private JButton EXITButton;
    private JPanel selectionPanel;
    private JPanel buttonPanel;
    private JPanel receiptPanel;
    private JLabel msgError;
    private JLabel iconLarge;
    private JLabel iconMedium;
    private JLabel iconSmall;
    private JLabel lblCoffees;
    private JLabel lblSnacks;

    //data type declaration

    double tax, change, subtotal, total;
    public CoffeeShop () {
        //form layout
        add(mainPanel);
        setVisible(true);
        setTitle("Coffee Shop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1030, 750);
        setResizable(false);
        setLocationRelativeTo(null);

        //small, medium, large radiobutton group
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeGroup.add(smallRadioButton);
        sizeGroup.add(mediumRadioButton);
        sizeGroup.add(largeRadioButton);

        smallRadioButton.setSelected(true);

        //inserting image
        //title layout
        leftIcon.setIcon(new ImageIcon(new ImageIcon("images/icon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
        rightIcon.setIcon(new ImageIcon(new ImageIcon("images/icon.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        //drinks size icon
        iconSmall.setIcon(new ImageIcon(new ImageIcon("images/coffee-cup.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        iconMedium.setIcon(new ImageIcon(new ImageIcon("images/coffee-cup.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH)));
        iconLarge.setIcon(new ImageIcon(new ImageIcon("images/coffee-cup.png").getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH)));

        //coffee menu
        lblCoffees.setIcon(new ImageIcon(new ImageIcon("images/bean.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        latte.setIcon(new ImageIcon(new ImageIcon("images/a1.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));
        mocha.setIcon(new ImageIcon(new ImageIcon("images/a2.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));
        bcoffee.setIcon(new ImageIcon(new ImageIcon("images/a3.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));
        cappuccino.setIcon(new ImageIcon(new ImageIcon("images/a4.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));

        //snack menu
        lblSnacks.setIcon(new ImageIcon(new ImageIcon("images/cakeIcon.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        croissant.setIcon(new ImageIcon(new ImageIcon("images/b1.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));
        doughNut.setIcon(new ImageIcon(new ImageIcon("images/b2.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));
        cake.setIcon(new ImageIcon(new ImageIcon("images/b3.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));
        pancake.setIcon(new ImageIcon(new ImageIcon("images/b4.png").getImage().getScaledInstance(120, 100, Image.SCALE_SMOOTH)));

        //check if user's input is a number
        tfuserMoney.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                    tfuserMoney.setEditable(true);
                } else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE || e.getKeyChar() == '.') {
                    tfuserMoney.setEditable(true);
                } else {
                    tfuserMoney.setEditable(false);
                }
            }
        });

        placeOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receipt();
                String userMoney = ("₱ " + tfuserMoney.getText());
                String subTotal = subtotalOutput.getText();
                String tax = taxOutput.getText();
                String total = totalOutput.getText();
                String userChange = changeOutput.getText();
                String time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());

                sales = insertToDB(userMoney, subTotal, tax, total, userChange, time);
                if (sales != null) {
                    System.out.println("Success");
                } else {
                    System.out.println("Failed");
                }
            }
        });

        orderAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //clear JLabel output texts
                totalOutput.setText("");
                changeOutput.setText("");
                taxOutput.setText("");
                subtotalOutput.setText("");
                tfuserMoney.setText("");
                msgError.setText("");

                //unselect the checkboxes
                a1CheckBox.setSelected(false);
                a2CheckBox.setSelected(false);
                a3CheckBox.setSelected(false);
                a4CheckBox.setSelected(false);
                b1CheckBox.setSelected(false);
                b2CheckBox.setSelected(false);
                b3CheckBox.setSelected(false);
                b4CheckBox.setSelected(false);

                //set spinner value to 0
                a1spinner.setValue(0);
                a2spinner.setValue(0);
                a3spinner.setValue(0);
                a4spinner.setValue(0);
                b1spinner.setValue(0);
                b2spinner.setValue(0);
                b3spinner.setValue(0);
                b4spinner.setValue(0);

                //unselect the radiobutton
                smallRadioButton.setSelected(true);

            }
        });

        //system exit
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int exit = JOptionPane.showConfirmDialog(null, "Would you like to exit?", "System Notice", JOptionPane.YES_NO_OPTION);

                if (exit == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "Thank you! Please come again!", "System Notice", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                } else {

                }
            }
        });
    }

    public Sales sales;
    private Sales insertToDB(String userMoney, String subTotal, String tax, String total, String userChange, String time){
        Sales sales = null;
        final String DB_URL = "jdbc:mysql://localhost/cafe_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO tbl_sales (userMoney, subTotal, tax, total, userChange, time) VALUES (?, ?, ?, ? ,? ,? )";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, userMoney);
            preparedStatement.setString(2, subTotal);
            preparedStatement.setString(3, tax);
            preparedStatement.setString(4, total);
            preparedStatement.setString(5, userChange);
            preparedStatement.setString(6, time);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                sales = new Sales();
                sales.userMoney = userMoney;
                sales.subTotal = subTotal;
                sales.tax = tax;
                sales.total = total;
                sales.userChange = userChange;
                sales.time = time;
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sales;
    }

    public void receipt() {

        //declare item values
        double a1 = 75.00, a2 = 50.00, a3 = 45.00, a4 = 25.00, b1 = 25.00, b2 = 50.00, b3 = 75.00, b4 = 100.00;
        double a1Total = 0, a2Total = 0, a3Total = 0, a4Total = 0, b1Total = 0, b2Total = 0, b3Total = 0, b4Total = 0;

        //calculates the total of the items
        if(a1CheckBox.isSelected()) {
            if (mediumRadioButton.isSelected()) {
                int value = (Integer) (a1spinner.getValue());
                a1Total = (a1 * value) + 25;
            } else if (largeRadioButton.isSelected()) {
                int value = (Integer) (a1spinner.getValue());
                a1Total = (a1 * value) + 50;
            } else {
                int value = (Integer) (a1spinner.getValue());
                a1Total = a1 * value;
            }
        }
        if(a2CheckBox.isSelected()) {
            if (mediumRadioButton.isSelected()) {
                int value = (Integer) (a2spinner.getValue());
                a2Total = (a2 * value) + 25;
            } else if (largeRadioButton.isSelected()) {
                int value = (Integer) (a2spinner.getValue());
                a2Total = (a2 * value) + 50;
            } else {
                int value = (Integer) (a2spinner.getValue());
                a2Total = a2 * value;
            }
        }
        if(a3CheckBox.isSelected()) {
            if (mediumRadioButton.isSelected()) {
                int value = (Integer) (a3spinner.getValue());
                a3Total = (a3 * value) + 25;
            } else if (largeRadioButton.isSelected()) {
                int value = (Integer) (a3spinner.getValue());
                a3Total = (a3 * value) + 50;
            } else {
                int value = (Integer) (a3spinner.getValue());
                a3Total = a3 * value;
            }
        }
        if(a4CheckBox.isSelected()) {
            if (mediumRadioButton.isSelected()) {
                int value = (Integer) (a4spinner.getValue());
                a4Total = (a4 * value) + 25;
            } else if (largeRadioButton.isSelected()) {
                int value = (Integer) (a4spinner.getValue());
                a4Total = (a4 * value) + 50;
            } else {
                int value = (Integer) (a4spinner.getValue());
                a4Total = a4 * value;
            }
        }
        if(b1CheckBox.isSelected()) {
            int value = (Integer) (b1spinner.getValue());
            b1Total = b1 * value;
        }
        if(b2CheckBox.isSelected()) {
            int value = (Integer) (b2spinner.getValue());
            b2Total = b2 * value;
        }
        if(b3CheckBox.isSelected()) {
            int value = (Integer) (b3spinner.getValue());
            b3Total = b3 * value;
        }
        if(b4CheckBox.isSelected()) {
            int value = (Integer) (b4spinner.getValue());
            b4Total = b4 * value;
        }

        //Print Receipt
        String money = tfuserMoney.getText();
        if (money.isEmpty()){
            msgError.setText("Enter an amount of money!");
        } else {
            msgError.setText("");
            double usermoney = Double.parseDouble(money);
            DecimalFormat df = new DecimalFormat("0.00");

            subtotal = a1Total + a2Total + a3Total + a4Total + b1Total + b2Total + b3Total + b4Total;
            tax = subtotal * 0.12;
            total = subtotal + tax;
            change = usermoney - total;

            subtotalOutput.setText("₱ " + df.format(subtotal));
            taxOutput.setText("₱ " + df.format(tax));
            totalOutput.setText("₱ " + df.format(total));
            changeOutput.setText("₱ " + df.format(change));

            if (change < 0) {
                msgError.setText("Insufficient Balance!");
            } else {
                msgError.setText("");
            }
        }
    }

    public static void main(String[] args) {
        new CoffeeShop();
    }
}
