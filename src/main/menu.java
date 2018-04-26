package main;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class menu {String[] commonNameList = {"Dog", "Cat", "Rabbit", "Golden Hamster",
        "Roborobski Hamster", "Guinea Pig", "Edward's Fig parrot",
        "Norwegian Blue", "Hyacinth Macaw", "Yellow Canary", "Goldfish",
        "Koi", "Common Barbel", "Boa Constrictor", "Corn Snake","Black-necked Spitting Cobra"};
    private JTabbedPane tabs;
    private JPanel panel1;
    private JTextField singleAddGivenName;
    private JComboBox singleAddCommonName = new JComboBox(commonNameList);
    private JTextField singleAddPrice;
    private JRadioButton singleAddMale;
    private JTextField singleAddColour;
    private JRadioButton singleAddFemale;
    private JTextField singleAddArrDay;
    private JTextField singleAddArrMonth;
    private JTextField singleAddArrYear;
    private JTextField singleAddSellDay;
    private JTextField singleAddSellMonth;
    private JTextField singleAddSellYear;
    private JButton singleAddSubmit;
    private JButton FileBrowse;
    private JButton submitButton;
    private JTextField animalSellList;
    private JButton sellButton;
    private JList animalList;
    private JButton chooseFileDestinationButton;
    private JButton writeToFileButton;
    private JTextField fileNameEntry;
    private JTextField givenNameSearch;
    private JRadioButton searchMale;
    private JTextField searchResults;
    private JComboBox searchClass;
    private JComboBox searchOrder;
    private JComboBox searchGenus;
    private JComboBox searchSpecies;
    private JComboBox searchFamily;
    private JButton searchButton;
    private JTextField commonNameSearch;
    private JRadioButton searchFemale;
    private JRadioButton venomYes;
    private JRadioButton venomNo;
    private JRadioButton talkYes;
    private JRadioButton talkNo;
    private JTextField searchColour;
    private JList list1;
    private JTextField dayRevEntry;
    private JButton dailyRevSubmit;
    private JTextField monthRevEntry;
    private JButton monthRevSubmit;
    private JLabel dayRevResult;
    private JLabel monthRevResult;

    FileFilter filter = new FileNameExtensionFilter(".txt files","txt");
    public menu() {

        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        FileBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int rVal = fc.showOpenDialog(panel1);
                if(rVal == JFileChooser.APPROVE_OPTION) {
                    File animalFile = fc.getSelectedFile();
                }
            }
        });
        singleAddSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              //  String[] parameters = new String[];
                //parameters[0] = singleAddGivenName.getText();
               // parameters[1] = singleAddCommonName.getSelectedItem();
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setContentPane(new menu().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        shop petShop = new shop();
    }
}
