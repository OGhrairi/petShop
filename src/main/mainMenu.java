package main;

import javax.swing.*;

public class mainMenu {
    private JButton addMultAnimal;
    private JButton addSingAnimal;
    private JButton searchButton;
    private JButton computeRev;
    private JButton writeAnimals;
    private JPanel mainPanel;

    public mainMenu(){

    }
    public static void main(String[] args){
        JFrame frame = new JFrame("mainMenu");
        frame.setContentPane(new mainMenu().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
