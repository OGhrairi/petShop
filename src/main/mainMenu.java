package main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainMenu {
    private JButton addMultAnimal;
    private JButton addSingAnimal;
    private JButton searchButton;
    private JButton computeRev;
    private JButton writeAnimals;
    private JPanel mainPanel;

    public mainMenu(){

        addSingAnimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animalEntry dialog = new animalEntry();
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        computeRev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"test message");
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animalSearcher searcher = new animalSearcher();
                searcher.pack();
                searcher.setVisible(true);
            }
        });
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("mainMenu");
        frame.setContentPane(new mainMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
