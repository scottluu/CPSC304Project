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
class CustPanel extends JPanel {
    JLabel l1, l2;
    JButton b1;
    JTextField t1, t2;
    JCheckBox cb1;


    public CustPanel () {
        l1 = new JLabel("Price");
        l2 = new JLabel("Name");
        b1 = new JButton("Go");
        t1 = new JTextField(10);
        t2 = new JTextField(10);
        cb1= new JCheckBox("Price");



        setPreferredSize (new Dimension (395, 156));
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text1 = t1.getText();
                String text2 = t2.getText();
                System.out.println(text1 + text2);
                //jcomp4.selectAll();
            }
        });
        t1.setBounds (205, 115, 100, 25);
        t2.setBounds (205, 115, 100, 25);
        //b1.setVerticalTextPosition(AbstractButton.BOTTOM);
        //b1.setHorizontalTextPosition(AbstractButton.RIGHT);
        //b1.setBounds(300,120,10,10);
        add(l1);
        add(l2);
        add(b1);
        add(t1);
        add(t2);
        add(cb1);
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


    public DeletePanel () {

        setPreferredSize (new Dimension (395, 156));


    }

}
class UpdatePanel extends JPanel {


    public UpdatePanel () {

        setPreferredSize (new Dimension (395, 156));


    }

}
