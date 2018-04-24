package test;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class test1 {//generated from form creation
    private JButton testButton1;
    private JPanel testPanel1;

    public test1() {//generated from action listener
        testButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Alert");
            }
        });
    }
    public static void main(String[] args){//manually created, generates the actual instance
        //of the form class defined above
        JFrame frame = new JFrame("test1");
        frame.setContentPane(new test1().testPanel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
