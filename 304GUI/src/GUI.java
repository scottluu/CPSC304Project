/**
 * Created by Trevor on 3/14/2017.
 */

import javax.swing.*;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
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
import java.util.ArrayList;


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

    public GUI() {
        //Construct Components
        b1 = new JButton("CUSTOMER");
        b1.setVerticalTextPosition(AbstractButton.CENTER);
        b1.setHorizontalTextPosition(AbstractButton.LEADING);

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("Customer Panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                
                frame.getContentPane().add (new CustPanel());
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
                frame.getContentPane().add (new EmpPanel());
                frame.pack();
                frame.setVisible(true);
            }
        });
        b3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("exec panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                frame.getContentPane().add (new ExecPanel());
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

        //Add the ubiquitous "Hello World" label.

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
                createAndShowGUI();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
class MyPanel2 extends JPanel {
    private JButton jcomp1;
    private JTextField jcomp4;

    public MyPanel2() {
        //construct components
        jcomp1 = new JButton ("test1");
        jcomp4 = new JTextField (5);

        jcomp1.setVerticalTextPosition(AbstractButton.CENTER);
        jcomp1.setHorizontalTextPosition(AbstractButton.LEADING);
        //adjust size and set layout
        setPreferredSize (new Dimension (395, 156));
        setLayout (null);
        jcomp4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = jcomp4.getText();
                System.out.print(text);
                //jcomp4.selectAll();
            }
        });
        //set component bounds (only needed by Absolute Positioning)
        jcomp1.setBounds (20, 45, 100, 25);

        jcomp4.setBounds (205, 115, 100, 25);

        //add components
        add (jcomp1);

        add (jcomp4);
    }
}
class MyPanel3 extends JPanel {
    JList list;

    public MyPanel3 (String[] b) {
        list = new JList(b);
         //data has type Object[]
        //list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        list.setVisibleRowCount(-1);
        setPreferredSize (new Dimension (395, 156));
        setLayout (null);
        JScrollPane listScroller = new JScrollPane(list,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        listScroller.setViewportView(list);
        //listScroller.getViewport().setPreferredSize();
        //listScroller.setPreferredSize(new Dimension(20, 80));
        //listScroller.setAlignmentX(LEFT_ALIGNMENT);
        list.setBounds (0, 0, 395, 156);

        add(list);

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

    public void updateResults() {
    	if (valid) {
    		if (cb1.isSelected()) {

    		}
    		if (cb2.isSelected()) {
    			
    		}
    		JFrame tableFrame = new JFrame("Results");
    		String[][] data = {{"1", "2"}};
    		String[] cols = {"1","2"};
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

    public CustPanel () {

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
                updateResults();
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


    public EmpPanel () {
            JButton update, delete;
        update = new JButton("Update");
        delete = new JButton("Delete");
        setPreferredSize (new Dimension (395, 156));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("Delete Panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                frame.getContentPane().add (new DeletePanel());
                frame.pack();
                frame.setVisible(true);
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame ("Update Panel");
                //frame.setDefaultCloseOperation((JFrame.EXIT_ON_CLOSE));
                frame.getContentPane().add (new UpdatePanel());
                frame.pack();
                frame.setVisible(true);
            }
        });
        add(delete);
        add(update);

    }

}
class ExecPanel extends JPanel {


    public ExecPanel () {

        setPreferredSize (new Dimension (395, 156));


    }

}
class DeletePanel extends JPanel {


    JLabel l1, l2;
    JTextField t1;
    JButton b1;

    public DeletePanel () {
        l1 = new JLabel("Fire Employee");
        l2 = new JLabel("Employee Number");
        t1 = new JTextField("test");
        b1 = new JButton("Fire!");
        setLayout(null);
        setPreferredSize (new Dimension (395, 156));
        b1.setBounds(310,120,70,30);
        l1.setBounds(0,0,120,30);
        l2.setBounds(0,20,120,30);
        t1.setBounds(130 ,20,70,30);
        add(l1);
        add(l2);
        add(t1);
        add(b1);


    }

}
class UpdatePanel extends JPanel {


    JLabel l1,l2,l3,l4;
    JTextField t1,t2,t3,t4;
    public UpdatePanel () {
        l1 = new JLabel("Price");
        l2 = new JLabel("Name");
        l3 = new JLabel("UPCcode");
        l4 = new JLabel("Stock");
        t1 = new JTextField(10);
        t2 = new JTextField(10);
        t3 = new JTextField(10);
        t4 = new JTextField(10);
        setPreferredSize (new Dimension (395, 156));
        add(l1);
        add(l2);
        add(l3);
        add(l4);
        add(t1);
        add(t2);
        add(t3);
        add(t4);
    }

}
