package main;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class menu {
    private JTabbedPane tabs;
    private JPanel panel1;
    private JTextField singleAddGivenName;
    private JComboBox singleAddCommonName;
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
    DateFormat df = new SimpleDateFormat("YYYY-MM-DD");
    Date date = new Date();
    FileFilter filter = new FileNameExtensionFilter(".txt files","txt");
    String[] parameters = new String[7];
    shop petShop = new shop();
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
                parameters[0] = singleAddGivenName.getText();
                parameters[1] = singleAddCommonName.getSelectedItem().toString();
                parameters[2] = singleAddPrice.getText();
                if(singleAddMale.isSelected() == true){
                    parameters[3] = "Male";
                }else parameters[3] = "Female";
                parameters[4] = singleAddColour.getText();
                if (singleAddArrDay.getText() != null){
                parameters[5] = singleAddArrYear.getText() + "-" + singleAddArrMonth.getText() + "-" +
                        singleAddArrDay.getText();
                }else{
                    parameters[5] = df.format(date);
                }
                if(singleAddSellDay.getText() != null) {
                    parameters[6] = singleAddSellYear.getText() + "-" + singleAddSellMonth.getText() + "-" +
                            singleAddSellDay.getText();
                }else{
                    parameters[6] = null;
                }
                petShop.addAnimal(parameters);
                popup("Success");
            }
        });
    }
    //string/array inputs are of form [givenName, commonName, price, sex, colour, arrivalDate, sellingDate]
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setContentPane(new menu().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    public void popup(String value){//creates popup dialog where the value arg is the message to be displayed
        JOptionPane.showMessageDialog(null, value);
    }

    public void listUpdater(){
        animalList.removeAll();
        String[] lOut = new String[(petShop.animalCount)];
        for(int i=0; i< petShop.animalList.size();i++){
            lOut[i] = petShop.animalList.get(i).getGivenName() + " - " + petShop.animalList.get(i).getCommonName();

        }animalList.setListData(lOut);
    }
}
