/**
 * Created by Trevor on 3/14/2017.
 */

import javax.swing.*;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import com.alee.laf.WebLookAndFeel;
import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.KeyEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;


public class GUI extends JPanel implements ActionListener {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    protected JButton b1,b2, b3;
    // Selection using dropdown for store
    // Division for stock greater than 1?
    // Employee update products/transactions delete
    // Executives can aggregate/ nested aggregate
	
	private final String userName = "cs304proj";
    private final String password = "password";
	private final String serverName = "josephso.me";
	private final int portNumber = 3306;
	private final String dbName = "304test";
	private Statement stmt;
	private Connection conn;
	private ResultSet rs;

    public GUI() {
        //Construct Components
        b1 = new JButton("CUSTOMER");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING);
        conn = null;
        Properties connectionProps = new Properties();
        connectionProps.put("user", this.userName);
        connectionProps.put("password", this.password);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"
                            + this.serverName + ":" + this.portNumber + "/" + this.dbName,
                    connectionProps);
        } catch (Exception e) {
            System.out.print("error on connection");
        }

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("Customer Panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                
                frame.getContentPane().add (new CustPanel(conn));
                frame.addComponentListener(new ComponentAdapter() {
                	public void componentResized(ComponentEvent e) {
                		CustPanel temp = (CustPanel) frame.getContentPane().getComponent(0);
                		temp.width = temp.getWidth();
                		temp.height = temp.getHeight();
                		temp.setPositions();
                	}
                });
                frame.pack();
                frame.setVisible(true);
            }
        });
        b2 = new JButton("EMPLOYEE");
        b2.setVerticalTextPosition(AbstractButton.CENTER);
        b2.setHorizontalTextPosition(AbstractButton.LEADING);
        b3 = new JButton("EXECUTIVE");
        b3.setBounds(20,30,40,200);
        //b3.setHorizontalTextPosition(AbstractButton.RIGHT);


        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("employee panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                frame.getContentPane().add (new EmpPanel(conn));
                frame.pack();
                frame.setVisible(true);
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("exec panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                frame.getContentPane().add (new ExecPanel(conn));
                frame.pack();
                frame.setVisible(true);
            }
        });
        setPreferredSize(new Dimension(315,355));
        //setLayout(null);
        add(b1);
        add(b2);
        add(b3);
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("304Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().add(new GUI());

        GUI newContentPane = new GUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //frame.getContentPane().add();
        //Display the window.
        frame.pack();
        frame.setVisible(true);



    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WebLookAndFeel.install();
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
class TablePanel extends JPanel {
	JTable t3;
    DefaultTableModel dtm1;
    JScrollPane sp1;

    public TablePanel(String[][] data, String[] columns) {
    	setPreferredSize(new Dimension(500, 500));
        dtm1 = new DefaultTableModel(data, columns);
        t3 = new JTable(dtm1);
        sp1 = new JScrollPane(t3);
        sp1.setBounds(110, 95, 100, 100);
        add(sp1);
    }
}
class CustPanel extends JPanel {
    JLabel l1, l2;
    JButton b1;
    JTextField t1, t2;
    JCheckBox cb1, cb2;
    JRadioButton rb1, rb2;
    ButtonGroup bg1;
    public int width;
    public int height;
    boolean valid = true;
    private Statement stmt;
    private Connection conn;
    private ResultSet rs;

    public void updateResults(Connection c) {
    	String columns = "*";
    	String where = "";
        String[][] data = new String[20][3];

    	if (valid) {
    		if (cb1.isSelected()) {

    			columns = "PRODPRICE";
    		}
    		if (cb2.isSelected()) {
    			if (columns.equals("*")) {
    				columns = "PRODNAME";
    			} else {
    				columns += ", PRODNAME";
    			}
    		}
            if (!t1.getText().trim().equals("")) {
        		if (rb1.isSelected()) {
        			where = "WHERE PRODPRICE <= " + t1.getText().trim();
        		} else if (rb2.isSelected()) {
        			where = "WHERE PRODPRICE > " + t1.getText().trim();
        		}
            }
    		if (!t2.getText().trim().equals("")) {
    			if (where.equals("")) {
    				where = "WHERE PRODNAME LIKE '%" + t2.getText().trim() + "%'";
    			} else {
    				where += " AND PRODNAME LIKE '%" + t2.getText().trim() + "%'";
    			}
    		}

            try {
                stmt = c.createStatement();

                rs = stmt.executeQuery("SELECT " + columns + " FROM PRODUCTS " + where);
                // stmt.execute(statement) for non queries
                int i = 0;
                while (rs.next()) {

                	if (columns.equals("*")) {
                		data[i][0] = rs.getString("UPCcode");
                    	data[i][1] = rs.getString("PRODNAME");
                    	data[i][2] = rs.getString("PRODPRICE");
                	} else if ((columns.contains("PRODNAME"))&&(columns.contains("PRODPRICE"))) {
                    	data[i][0] = rs.getString("PRODNAME");
                    	data[i][1] = rs.getString("PRODPRICE");
                	} else if (columns.contains("PRODNAME")) {
                		data[i][0] = rs.getString("PRODNAME");
                	} else {
                		data[i][0] = rs.getString("PRODPRICE");
                	}
                    i++;
                }
            } catch (Exception d) {

            }
    		JFrame tableFrame = new JFrame("Results");
            String[] cols = {};
            String[][] cols2 = {{"UPCcode","PRODNAME","PRODPRICE"},
                                {"PRODNAME","PRODPRICE"},
                                {"PRODNAME"},
                                {"PRODPRICE"}};
            if (columns.equals("*")) {
    		    cols = cols2[0];
            } else if (columns.contains(",")) {
                cols = cols2[1];
            } else if (columns.contains("PRODNAME")) {
                cols = cols2[2];
            } else {
                cols = cols2[3];
            }
    		tableFrame.getContentPane().add(new TablePanel(data, cols));
    		tableFrame.pack();
    		tableFrame.setVisible(true);
    	} else {
    		JOptionPane.showMessageDialog(null,
        				"Please fix errors first",
        				"Error",
        				JOptionPane.ERROR_MESSAGE);
    	}
    }

    public void setPositions() {
    	l1.setBounds(width/2-100,  5,         100, 25);
        l2.setBounds(width/2,      5,         100, 25);
        t1.setBounds(width/2-100,  35,        100, 25);
        t2.setBounds(width/2,      35,        100, 25);
        rb1.setBounds(width/2-100, 65,        50,  25);
        rb2.setBounds(width/2-50,  65,        100, 25);
        cb1.setBounds(5,           height-60, 100,  25);
        cb2.setBounds(5,           height-30, 100, 25);
        b1.setBounds(width-100,    height-30, 100, 25);
    }

    public CustPanel (Connection c) {

        l1 = new JLabel("Price");
        l2 = new JLabel("Name");
        b1 = new JButton("Go");
        t1 = new JTextField(10);
        t2 = new JTextField(10);
        cb1= new JCheckBox("Price");
        cb2= new JCheckBox("Name");
        rb1 = new JRadioButton("<=");
        rb2 = new JRadioButton(">");
        bg1 = new ButtonGroup();
        bg1.add(rb1);
        bg1.add(rb2);
        width = 400;
        height = 400;
        t1.getDocument().addDocumentListener(new DocumentListener() {
        	public void changedUpdate(DocumentEvent e) {
        		valid = isValid();
        	}
        	public void removeUpdate(DocumentEvent e) {
        		valid = isValid();
        	}
        	public void insertUpdate(DocumentEvent e) {
        		valid = isValid();
        	}
        	public boolean isValid() {
        		if (t1.getText().equals("")) {
        			return true;
        		}
        		int value;
        		try {
        			value = Integer.parseInt(t1.getText()); 
        		} catch (NumberFormatException e) {
        			JOptionPane.showMessageDialog(null,
        				"Please enter a number",
        				"Error",
        				JOptionPane.ERROR_MESSAGE);
        			return false;
        		}
        		if (value < 0) {
        			JOptionPane.showMessageDialog(null,
        				"Please enter a number greater than 0",
        				"Error",
        				JOptionPane.ERROR_MESSAGE);
        			return false;
        		} else {
        			return true;
        		}
        	}
        });
        setPreferredSize (new Dimension (width, height));
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateResults(c);
            }
        });
        
        setPositions();
        setLayout(null);
        add(l1);
        add(l2);
        add(t1);
        add(t2);
        add(rb1);
        add(rb2);
        add(cb1);
        add(cb2);
        add(b1);
    }

}
class EmpPanel extends JPanel {

    private Statement stmt;

    private ResultSet rs;
    public EmpPanel (Connection c) {
            JButton update, delete, inventory, bestCust;
        update = new JButton("Update");
        delete = new JButton("Delete");
        inventory = new JButton("Customer Transactions");
        bestCust = new JButton("Best Customer");
        setPreferredSize (new Dimension (395, 156));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("Delete Panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                frame.getContentPane().add (new DeletePanel(c));
                frame.pack();
                frame.setVisible(true);
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("Update Panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                frame.getContentPane().add (new UpdatePanel(c));
                frame.pack();
                frame.setVisible(true);
            }
        });
        inventory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("Inventory Panel");
                frame.getContentPane().add(new InventoryPanel(c));
                frame.pack();
                frame.setVisible(true);
            }
        });
        bestCust.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                stmt = c.createStatement();
                rs = stmt.executeQuery("SELECT CUSTNO FROM TRANSACTIONS t WHERE UPCCODE IN (SELECT UPCCODE FROM PRODUCTS) GROUP BY CUSTNO HAVING COUNT(*) = (SELECT COUNT(*) FROM PRODUCTS)");
                String[][] data = new String[1][1];
                int i = 0;
                while(rs.next()) {
                    data[i][0] = rs.getString("CUSTNO");
                    i++;
                }
                JFrame tableFrame = new JFrame("Results");
                String[] cols = {"CUSTNO"};
                tableFrame.getContentPane().add(new TablePanel(data, cols));
                tableFrame.pack();
                tableFrame.setVisible(true);

            } catch (Exception d) {

            }
            }
        });
        add(delete);
        add(update);
        add(inventory);
        add(bestCust);

    }

}
class ExecPanel extends JPanel {
    private Statement stmt;
    private Connection conn;
    private ResultSet rs;

    public ExecPanel (Connection c) {
        JButton bestMonth;
        JButton noStores;
        JButton worstMonth, avgSales, storeSales, highestPrice, lowestPrice;
        JButton nestedAgg;
        //SELECT AVG(PRODPRICE), STORENUM FROM PRODUCTS p, TRANSACTIONS t WHERE p.UPCCODE = t.UPCCODE GROUP BY STORENUM
        String[] aggstring = {"MAX", "MIN", "SUM", "AVG", "COUNT"};
        String[] aggstring1 = {"noStores", "avgSales", "storeSales", "highestPrice","lowestPrice"};
        JComboBox agglist1 = new JComboBox(aggstring);
        JComboBox agglist2 = new JComboBox(aggstring);
        noStores = new JButton("Total Sales");
        avgSales = new JButton("Average Sales");
        storeSales = new JButton("Sales per Store");
        highestPrice = new JButton("Highest Price");
        lowestPrice = new JButton("Lowest Price");
        nestedAgg = new JButton("Nested Aggregation");



        setPreferredSize (new Dimension (395, 156));
        nestedAgg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String agg1 = (String)agglist1.getSelectedItem();
                String agg2 = (String)agglist2.getSelectedItem();

                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery("SELECT "+agg2+"(x.PRODPRICE) as PRICE FROM (SELECT "+agg1+"(PRODPRICE) as PRODPRICE, STORENUM FROM PRODUCTS p, TRANSACTIONS t WHERE p.UPCCODE = t.UPCCODE GROUP BY STORENUM) x");
                    String[][] data = new String[1][1];
                    int i = 0;
                    while(rs.next()) {
                        data[i][0] = rs.getString("PRICE");
                        i++;
                    }
                    JFrame tableFrame = new JFrame("Results");
                    String[] cols = {"$"};
                    tableFrame.getContentPane().add(new TablePanel(data, cols));
                    tableFrame.pack();
                    tableFrame.setVisible(true);
                } catch (Exception d) {

                }
            }
        });
        noStores.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery("SELECT SUM(PRODPRICE), STORENUM FROM PRODUCTS p, TRANSACTIONS t WHERE p.UPCCODE = t.UPCCODE GROUP BY STORENUM");
                    String[][] data = new String[20][2];
                    int i = 0;
                    while(rs.next()) {
                        data[i][0] = rs.getString("SUM(PRODPRICE)");
                        data[i][1] = rs.getString("STORENUM");

                        i++;
                    }
                    JFrame tableFrame = new JFrame("Results");
                    String[] cols = {"SUM(PRODPRICE)", "STORENUM"};
                    tableFrame.getContentPane().add(new TablePanel(data, cols));
                    tableFrame.pack();
                    tableFrame.setVisible(true);
                } catch (Exception d) {

                }
            }
        });
        avgSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery("SELECT AVG(PRODPRICE), STORENUM FROM PRODUCTS p, TRANSACTIONS t WHERE p.UPCCODE = t.UPCCODE GROUP BY STORENUM");
                    String[][] data = new String[20][2];
                    int i = 0;
                    while(rs.next()) {
                        data[i][0] = rs.getString("AVG(PRODPRICE)");
                        data[i][1] = rs.getString("STORENUM");

                        i++;
                    }
                    JFrame tableFrame = new JFrame("Results");
                    String[] cols = {"AVG(PRODPRICE)", "STORENUM"};
                    tableFrame.getContentPane().add(new TablePanel(data, cols));
                    tableFrame.pack();
                    tableFrame.setVisible(true);
                } catch (Exception d) {

                }
            }
        });
        highestPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery("SELECT MAX(PRODPRICE), STORENUM FROM PRODUCTS p, TRANSACTIONS t WHERE p.UPCCODE = t.UPCCODE GROUP BY STORENUM");
                    String[][] data = new String[20][2];
                    int i = 0;
                    while(rs.next()) {
                        data[i][0] = rs.getString("MAX(PRODPRICE)");
                        data[i][1] = rs.getString("STORENUM");

                        i++;
                    }
                    JFrame tableFrame = new JFrame("Results");
                    String[] cols = {"MAX(PRODPRICE)", "STORENUM"};
                    tableFrame.getContentPane().add(new TablePanel(data, cols));
                    tableFrame.pack();
                    tableFrame.setVisible(true);
                } catch (Exception d) {

                }
            }
        });
        lowestPrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery("SELECT MIN(PRODPRICE), STORENUM FROM PRODUCTS p, TRANSACTIONS t WHERE p.UPCCODE = t.UPCCODE GROUP BY STORENUM");
                    String[][] data = new String[20][2];
                    int i = 0;
                    while(rs.next()) {
                        data[i][0] = rs.getString("MIN(PRODPRICE)");
                        data[i][1] = rs.getString("STORENUM");

                        i++;
                    }
                    JFrame tableFrame = new JFrame("Results");
                    String[] cols = {"MIN(PRODPRICE)", "STORENUM"};
                    tableFrame.getContentPane().add(new TablePanel(data, cols));
                    tableFrame.pack();
                    tableFrame.setVisible(true);
                } catch (Exception d) {

                }
            }
        });
        storeSales.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery("SELECT COUNT(PRODPRICE), STORENUM FROM PRODUCTS p, TRANSACTIONS t WHERE p.UPCCODE = t.UPCCODE GROUP BY STORENUM");
                    String[][] data = new String[20][2];
                    int i = 0;
                    while(rs.next()) {
                        data[i][0] = rs.getString("COUNT(PRODPRICE)");
                        data[i][1] = rs.getString("STORENUM");

                        i++;
                    }
                    JFrame tableFrame = new JFrame("Results");
                    String[] cols = {"COUNT(PRODPRICE)", "STORENUM"};
                    tableFrame.getContentPane().add(new TablePanel(data, cols));
                    tableFrame.pack();
                    tableFrame.setVisible(true);
                } catch (Exception d) {

                }
            }
        });

        //add(bestMonth);
        add(noStores);
        //add(worstMonth);
        add(avgSales);
        add(storeSales);
        add(highestPrice);
        add(lowestPrice);
        add(agglist1);
        add(agglist2);
        add(nestedAgg);

    }

}
class DeletePanel extends JPanel {


    JLabel l1, l2;
    JTextField t1;
    JButton b1;
    private Statement stmt;
    private Connection conn;
    private ResultSet rs;

    public DeletePanel (Connection c) {
        l1 = new JLabel("Fire Employee");
        l2 = new JLabel("Employee Number");
        t1 = new JTextField("");
        b1 = new JButton("Fire!");
        setLayout(null);
        setPreferredSize (new Dimension (395, 156));
        b1.setBounds(310,120,70,30);
        l1.setBounds(0,0,120,30);
        l2.setBounds(0,20,120,30);
        t1.setBounds(130 ,20,70,30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = t1.getText();
                if (text.equals("")){
                    JOptionPane.showMessageDialog(null,
                            "Please enter an Employee Number",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    stmt = c.createStatement();
                    stmt.execute("DELETE FROM EMPLOYEE WHERE EMPLOYEEID = " + text);
                    // stmt.execute(statement) for non queries
                    int i = 0;
                    rs = stmt.executeQuery("SELECT * FROM EMPLOYEE");
                    String[] cols = {"EMPLOYEEID", "EMPLOYEENAME", "EMPLOYEEPOSITION"};
                    String[][] table = new String[70][3];
                    while (rs.next()) {
                        //print each result

                        table[i][0] = rs.getString("EMPLOYEEID");
                        table[i][1] = rs.getString("EMPLOYEENAME");
                        table[i][2] = rs.getString("EMPLOYEEPOS");
                        i++;
                    }

                    JFrame tableFrame = new JFrame("Results");
                    tableFrame.getContentPane().add(new TablePanel(table, cols));
                    tableFrame.pack();
                    tableFrame.setVisible(true);
                } catch (Exception d) {
                System.out.print("not working");
                }
                //jcomp4.selectAll();
            }
        });
        add(l1);
        add(l2);
        add(t1);
        add(b1);
    }
}
class UpdatePanel extends JPanel {

    JLabel l1,l2,l3,l4;
    JTextField t1,t2,t3,t4;
    JButton b1;
    private Statement stmt;
    private Connection conn;
    private ResultSet rs;
    public UpdatePanel (Connection c) {
        l1 = new JLabel("UPCcode");
        l2 = new JLabel("Price");
        l3 = new JLabel("Price");
        l4 = new JLabel("Stock");
        t1 = new JTextField(10);
        t2 = new JTextField(10);
        t3 = new JTextField(10);
        t4 = new JTextField(10);
        b1 = new JButton("Go");
        l1.setBounds(0,0,120,40);
        l2.setBounds(0,60,120,40);
        l3.setBounds(120,60,120,40);
        l4.setBounds(240,60,120,40);
        t1.setBounds(0,40,120,20);
        t2.setBounds(0,100,120,20);
        t3.setBounds(120,100,120,20);
        t4.setBounds(240,100,120,20);
        b1.setBounds(360, 100,60,40);
        setPreferredSize (new Dimension (430, 156));
        setLayout(null);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String UPCcode = t1.getText();
                String price = t2.getText();
                // USING MYSQL WHERE CHECK STATEMENTS NOT SUPPORTED
                if (price.equals("")){
                    JOptionPane.showMessageDialog(null,
                            "Please enter a price",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (UPCcode.equals("")){
                    JOptionPane.showMessageDialog(null,
                            "Please enter a UPC code",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (Integer.parseInt(price) > 100) {
                    JOptionPane.showMessageDialog(null,
                            "Price must be less than 100",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        stmt = c.createStatement();
                        stmt.executeUpdate("UPDATE PRODUCTS SET PRODPRICE = " + price + " WHERE UPCCODE = " + UPCcode);
                        rs = stmt.executeQuery("SELECT * FROM PRODUCTS");
                        String[][] data = new String[70][3];
                        int i = 0;
                        while(rs.next()) {
                            data[i][0] = rs.getString("UPCcode");
                            data[i][2] = rs.getString("PRODNAME");
                            data[i][1] = rs.getString("PRODPRICE");
                            i++;
                        }
                        JFrame tableFrame = new JFrame("Results");
                        String[] cols = {"UPCcode","PRODPRICE","PRODNAME"};
                        tableFrame.getContentPane().add(new TablePanel(data, cols));
                        tableFrame.pack();
                        tableFrame.setVisible(true);

                    } catch (Exception d) {
                        System.out.print("Update error");
                    }
                }


            }
        });
        add(l1);
        add(l2);

        add(t1);
        add(t2);

        add(b1);
    }

}
class InventoryPanel extends JPanel {


    JLabel l1, l2;
    JTextField t1;
    JButton b1;
    private Statement stmt;
    private Connection conn;
    private ResultSet rs;

    public InventoryPanel (Connection c) {
        l1 = new JLabel("Customer Transactions");
        l2 = new JLabel("Customer Number");
        t1 = new JTextField();
        b1 = new JButton("Go");
        setLayout(null);
        setPreferredSize (new Dimension (395, 156));
        b1.setBounds(310,120,70,30);
        l1.setBounds(0,0,150,30);
        l2.setBounds(0,20,120,30);
        t1.setBounds(130 ,20,70,30);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = t1.getText();
                if (text.equals("")){
                    JOptionPane.showMessageDialog(null,
                            "Please enter a Customer Number",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    stmt = c.createStatement();
                    rs = stmt.executeQuery("SELECT c.CUSTNO, ITEMSBOUGHT, s.ADDRESS FROM TRANSACTIONS t, STORE s, CUSTOMER c WHERE t.CUSTNO = c.CUSTNO AND t.STORENUM = s.STORENUM AND c.CUSTNO = "+ text);
                    String[][] data = new String[40][3];
                    int i = 0;
                    while(rs.next()) {
                        data[i][0] = rs.getString("CUSTNO");
                        data[i][1] = rs.getString("ITEMSBOUGHT");
                        data[i][2] = rs.getString("ADDRESS");
                        i++;
                    }
                    JFrame tableFrame = new JFrame("Results");
                    String[] cols = {"CUSTNO","ITEMSBOUGHT","ADDRESS"};
                    tableFrame.getContentPane().add(new TablePanel(data, cols));
                    tableFrame.pack();
                    tableFrame.setVisible(true);
                } catch (Exception d) {

                }
                //jcomp4.selectAll();
            }
        });
        add(l1);
        add(l2);
        add(t1);
        add(b1);
    }
}