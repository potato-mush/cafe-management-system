import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class adminDashboard extends JFrame {
    private JPanel mainPanel;
    private JButton addEmployeeBtn;
    private JButton removeEmployeeBtn;
    private JButton updateEmployeeBtn;
    private JButton viewUserBtn;
    private JButton viewEmployeeBtn;
    private JButton logOutBtn;
    private JPanel btnPanel;
    private JPanel displayPanel;
    private JTable employeeTbl;
    private JTable userTbl;
    private JScrollPane employeePane;
    private JScrollPane usersPane;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton registerButton;
    private JButton cancelButton;
    private JTextField tfLname;
    private JTextField tfFname;
    private JTextField tfMname;
    private JTextField tfcontact;
    private JTextField tfAddress;
    private JComboBox cbPosition;
    private JLabel lblEmployee_ID;
    private JPanel btnGroupPanel;
    private JPanel regForm;
    private JPanel bdCalendar;
    private JLabel lblZero;
    private JScrollPane salesPane;
    private JTable tblSales;
    private JButton viewLogsButton;
    JDateChooser bdayCal = new JDateChooser();
    Random rand = new Random();
    int random_ID = rand.nextInt(9999-100) + 100;;

    public adminDashboard() {

        add(mainPanel);

        //button layout
        employeePane.setVisible(false);
        usersPane.setVisible(false);
        salesPane.setVisible(false);
        regForm.setVisible(false);
        btnGroupPanel.setVisible(false);
        deleteButton.setVisible(false);
        updateButton.setVisible(false);
        usersPane.setBounds(0, 0, 0, 0);
        salesPane.setBounds(0, 0, 0, 0);
        regForm.setBounds(0, 0, 0, 0);
        displayPanel.setBounds(0, 0, 700, 550);

        //button icons
        addEmployeeBtn.setIcon(new ImageIcon(new ImageIcon("images/add.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        removeEmployeeBtn.setIcon(new ImageIcon(new ImageIcon("images/remove.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        updateEmployeeBtn.setIcon(new ImageIcon(new ImageIcon("images/update.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        viewEmployeeBtn.setIcon(new ImageIcon(new ImageIcon("images/view-employee.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        viewUserBtn.setIcon(new ImageIcon(new ImageIcon("images/view-users.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        viewLogsButton.setIcon(new ImageIcon(new ImageIcon("images/sales.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
        logOutBtn.setIcon(new ImageIcon(new ImageIcon("images/logout.png").getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH)));

        //set min and max Date
        Calendar minDate = Calendar.getInstance();
        minDate.set(1998, 11, 31);
        Calendar maxDate = Calendar.getInstance();
        maxDate.set(2006, 11, 31);

        //set Default date
        Calendar defaultDate = Calendar.getInstance();
        defaultDate.set(1998, 12, 1);

        bdayCal.setMinSelectableDate(minDate.getTime());
        bdayCal.setMaxSelectableDate(maxDate.getTime());
        bdayCal.setDate(defaultDate.getTime());

        bdCalendar.add(bdayCal);

        //table model
        employeeTbl.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "ID", "Employee ID", "Last Name", "First Name", "Middle Name", "Birth Date", "Address", "Contact", "Position"
                }
        ));

        tblSales.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "User's Money", "Subtotal", "VAT/Tax", "Total", "User's Change", "Date/Time"
                }
        ));

        userTbl.setModel(new DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Name", "Username", "Password"
                }
        ));

        addEmployeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPane.setVisible(false);
                employeePane.setVisible(false);
                salesPane.setVisible(false);
                updateButton.setVisible(false);
                deleteButton.setVisible(false);
                regForm.setVisible(true);
                btnGroupPanel.setVisible(true);

                lblEmployee_ID.setText("SCP" + String.format("%06d", random_ID));
                tfcontact.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        int length = String.valueOf(tfcontact.getText()).length();
                        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9') {
                            tfcontact.setEditable(true);
                            if (length == 0) {
                                tfcontact.setEditable(true);
                            } else if (length == 10){
                                tfcontact.setEditable(false);
                            } else if (length < 9) {
                            }
                        } else if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                            tfcontact.setEditable(true);
                        } else {
                            tfcontact.setEditable(false);
                        }
                    }
                });

                registerButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setAddEmployeeBtn();
                        dispose();
                        adminDashboard adminDB = new adminDashboard();
                        adminDB.show();
                    }
                });
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        regForm.setVisible(false);
                        btnGroupPanel.setVisible(false);
                    }
                });
            }
        });

        removeEmployeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPane.setVisible(false);
                regForm.setVisible(false);
                salesPane.setVisible(false);
                btnGroupPanel.setVisible(false);
                employeePane.setVisible(true);
                employeeTbl.setEnabled(true);
                employeeTbl.setFocusable(true);
                DefaultTableModel employeeTblModel = (DefaultTableModel) employeeTbl.getModel();
                employeeTblModel.setRowCount(0);
                updateButton.setVisible(false);
                deleteButton.setVisible(true);
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        setRemoveBtn();
                    }
                });

                setViewEmployeeBtn();
            }
        });

        updateEmployeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPane.setVisible(false);
                regForm.setVisible(false);
                salesPane.setVisible(false);
                btnGroupPanel.setVisible(false);
                employeePane.setVisible(true);
                employeeTbl.setEnabled(true);
                employeeTbl.setFocusable(true);
                DefaultTableModel employeeTblModel = (DefaultTableModel) employeeTbl.getModel();
                employeeTblModel.setRowCount(0);
                deleteButton.setVisible(false);
                updateButton.setVisible(true);
                updateButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int choice = JOptionPane.showConfirmDialog(null, "Do you want to save changes??", "Update", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.YES_NO_OPTION) {
                            setUpdateBtn();
                        } else if (choice == JOptionPane.NO_OPTION) {

                        }
                    }
                });

                setViewEmployeeBtn();
            }
        });

        viewEmployeeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usersPane.setVisible(false);
                salesPane.setVisible(false);
                regForm.setVisible(false);
                btnGroupPanel.setVisible(false);
                updateButton.setVisible(false);
                deleteButton.setVisible(false);
                employeePane.setSize(715, 526);
                employeePane.setVisible(true);
                employeeTbl.setEnabled(false);
                employeeTbl.setFocusable(false);
                employeeTbl.setRowSelectionAllowed(false);
                DefaultTableModel employeeTblModel = (DefaultTableModel) employeeTbl.getModel();
                employeeTblModel.setRowCount(0);

                setViewEmployeeBtn();
            }
        });

        viewUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePane.setVisible(false);
                salesPane.setVisible(false);
                regForm.setVisible(false);
                btnGroupPanel.setVisible(false);
                updateButton.setVisible(false);
                deleteButton.setVisible(false);
                usersPane.setSize(715, 526);
                usersPane.setVisible(true);
                DefaultTableModel userTblModel = (DefaultTableModel) userTbl.getModel();
                userTblModel.setRowCount(0);

                setViewUserBtn();
            }
        });

        setTitle("Dashboard");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        logOutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login lg = new Login();
                lg.show();
            }
        });
        viewLogsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePane.setVisible(false);
                usersPane.setVisible(false);
                regForm.setVisible(false);
                btnGroupPanel.setVisible(false);
                updateButton.setVisible(false);
                deleteButton.setVisible(false);
                salesPane.setSize(715, 526);
                salesPane.setVisible(true);
                DefaultTableModel salesModel = (DefaultTableModel) tblSales.getModel();
                salesModel.setRowCount(0);

                setViewLogsButton();
            }
        });
    }
    public void setAddEmployeeBtn() {
        String employee_ID = lblEmployee_ID.getText();
        String lastname = tfLname.getText();
        String firstname = tfFname.getText();
        String middlename = tfMname.getText();
        String contact = lblZero.getText() + tfcontact.getText();
        String address = tfAddress.getText();
        String position = String.valueOf(cbPosition.getSelectedItem());
        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = bdayCal.getDate();
        String birthDate = date_format.format(date);

        if (lastname.isEmpty() || firstname.isEmpty() || contact.isEmpty() || address.isEmpty() || position.equals("<none>")) {
            JOptionPane.showMessageDialog(this,
                    "Please fill all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        employee = addEmployeeToDB(employee_ID, lastname, firstname, middlename, birthDate, address, contact, position);
        if (employee != null) {
            JOptionPane.showMessageDialog(this, "Employee Successfully Added.", "Notice", JOptionPane.INFORMATION_MESSAGE);
            lblEmployee_ID.setText("SCP" + String.format("%06d", random_ID));
            tfLname.setText("");
            tfFname.setText("");
            tfMname.setText("");
            Calendar defaultDate = Calendar.getInstance();
            defaultDate.set(1998, 12, 1);
            bdayCal.setDate(defaultDate.getTime());
            tfAddress.setText("");
            tfcontact.setText("");
            cbPosition.setSelectedIndex(1);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new employee",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public Employee employee;

    private Employee addEmployeeToDB (String employee_ID, String lastname, String firstname, String middlename, String birthDate, String address, String contact, String position){
        Employee employee = null;
        final String DB_URL = "jdbc:mysql://localhost/cafe_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO employee (employee_ID, lastname, firstname, middlename, birthDate, address, contact, position) VALUES (?, ?, ?, ? ,? ,? ,? ,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, employee_ID);
            preparedStatement.setString(2, lastname);
            preparedStatement.setString(3, firstname);
            preparedStatement.setString(4, middlename);
            preparedStatement.setString(5, birthDate);
            preparedStatement.setString(6, address);
            preparedStatement.setString(7, contact);
            preparedStatement.setString(8, position);

            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0) {
                employee = new Employee();
                employee.employee_ID = employee_ID;
                employee.firstname = firstname;
                employee.lastname = lastname;
                employee.middlename = middlename;
                employee.birthDate = birthDate;
                employee.address = address;
                employee.contact = contact;
                employee.position = position;
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return employee;
    }

    public void setRemoveBtn() {
        DefaultTableModel model = (DefaultTableModel) employeeTbl.getModel();
        int row = employeeTbl.getSelectedRow();
        String eve = model.getValueAt(row, 1).toString();
        String delRow = "DELETE FROM employee WHERE employee_ID = '" + eve + "' ";

        try {
            final String DB_URL = "jdbc:mysql://localhost/cafe_db?serverTimezone=UTC";
            final String USERNAME = "root";
            final String PASSWORD = "";

            Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            PreparedStatement pst;
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this row?", "Remove", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_NO_OPTION) {
                pst = con.prepareStatement(delRow);
                pst.executeUpdate();
            } else if (choice == JOptionPane.NO_OPTION) {

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
    }

    public void setUpdateBtn() {
        final String DB_URL = "jdbc:mysql://localhost/cafe_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        DefaultTableModel model = (DefaultTableModel) employeeTbl.getModel();
        try {
            Connection con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement st = con.createStatement();
            for(int i = 0; i < model.getRowCount(); i++){

                int id = Integer.valueOf(model.getValueAt(i, 0).toString());
                String lastname = model.getValueAt(i,2).toString();
                String firstname = model.getValueAt(i,3).toString();
                String middlename = model.getValueAt(i,4).toString();
                String birthDate = model.getValueAt(i,5).toString();
                String address = model.getValueAt(i,6).toString();
                String contact = model.getValueAt(i,7).toString();
                String position = model.getValueAt(i,8).toString();

                String updateQuery = "UPDATE employee SET `lastname`='"+lastname+"',`firstname`='"+firstname+"',`middlename`='"+middlename+"',`birthDate`='"+birthDate+"',`address`='"+address+"',`contact`='"+contact+"',`position`='"+position+"' WHERE `id` = " +id;

                st.addBatch(updateQuery);
            }
            int[] updatedRow = st.executeBatch();
            System.out.println(updatedRow.length);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void setViewUserBtn(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cafe_db?serverTimezone=UTC","root","");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM users");

            while(rs.next()) {
                String name = rs.getString("name");
                String username = rs.getString("username");
                String password = rs.getString("password");

                String tbData [] = {name, username, password};
                DefaultTableModel tblModel = (DefaultTableModel)userTbl.getModel();

                tblModel.addRow(tbData);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setViewLogsButton(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cafe_db?serverTimezone=UTC","root","");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM tbl_sales");

            while(rs.next()) {
                String userMoney = rs.getString("userMoney");
                String subTotal = rs.getString("subTotal");
                String tax = rs.getString("tax");
                String total = rs.getString("total");
                String userChange = rs.getString("userChange");
                String time = rs.getString("time");

                String tbData [] = {userMoney, subTotal, tax,total ,userChange ,time};
                DefaultTableModel tblModel = (DefaultTableModel)tblSales.getModel();

                tblModel.addRow(tbData);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void setViewEmployeeBtn(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost/cafe_db?serverTimezone=UTC","root","");
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employee");

            while(rs.next()) {
                String id = rs.getString("id");
                String employee_ID = rs.getString("employee_ID");
                String lastname = rs.getString("lastname");
                String firstname = rs.getString("firstname");
                String middlename = rs.getString("middlename");
                String birthDate = rs.getString("birthDate");
                String address = rs.getString("address");
                String contact = rs.getString("contact");
                String position = rs.getString("position");

                String tbData [] = {id, employee_ID, lastname, firstname, middlename, birthDate,address, contact, position};
                DefaultTableModel tblModel = (DefaultTableModel)employeeTbl.getModel();

                tblModel.addRow(tbData);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new adminDashboard();
    }
}
