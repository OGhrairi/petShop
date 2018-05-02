package main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;


public class menu {
    private JTabbedPane tabs;
    private JPanel panel1;
    private JTextField singleAddGivenName;
    private JComboBox singleAddCommonName;
    private JTextField singleAddPrice;
    private JRadioButton singleAddMale;
    private JTextField singleAddColour;
    private JRadioButton singleAddFemale;
    private JTextField singleAddArr;
    private JTextField singleAddSell;
    private JButton singleAddSubmit;
    private JButton FileBrowse;
    private JButton submitButton;
    private JButton sellButton;
    private JList animalList;
    private JButton chooseFileDestinationButton;
    private JButton writeToFileButton;
    private JTextField fileNameEntry;
    private JTextField givenNameSearch;
    private JRadioButton searchMale;
    private JTextField legMin;
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
    private JButton refreshListButton;
    private JTextField saleDateEntry;
    private JTextField legMax;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
    Date date = new Date();
    FileFilter filter = new FileNameExtensionFilter(".txt files","txt");
    String[] parameters = new String[8];
    shop petShop = new shop();
    DefaultListModel <String> listModel;
    DefaultComboBoxModel <String> classModel = new DefaultComboBoxModel<>();
    DefaultComboBoxModel <String> orderModel = new DefaultComboBoxModel<>();
    DefaultComboBoxModel <String> familyModel = new DefaultComboBoxModel<>();
    DefaultComboBoxModel <String> genusModel = new DefaultComboBoxModel<>();
    DefaultComboBoxModel <String> speciesModel = new DefaultComboBoxModel<>();
    int IDCount = 1;
    public menu() {
        searchOrder.setModel(orderModel);
        searchClass.setModel(classModel);
        searchFamily.setModel(familyModel);
        searchGenus.setModel(genusModel);
        searchSpecies.setModel(speciesModel);
        listUpdater();//calls generation of the lists on load
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
                String TempPrice;
                try {
                    Integer.parseInt(singleAddPrice.getText());
                }catch (NumberFormatException e1){
                    popup("Animal price is invalid");
                    return;
                }
                TempPrice = singleAddPrice.getText();
                if(singleAddMale.isSelected()){
                    parameters[3] = "Male";
                }else parameters[3] = "Female";
                parameters[4] = singleAddColour.getText();
                if (dayFormatMatcher(singleAddArr.getText())){
                parameters[5] = singleAddArr.getText();
                }else{ if(singleAddArr.getText().isEmpty()){
                    parameters[5] = df.format(date);//sets the arrival date to today's date if no input detected
                }else {
                    popup("Arrival date is of incorrect format");
                    return;
                }}
                parameters[2] = petShop.discount(TempPrice,parameters[5]);
                if(singleAddSell.getText().isEmpty()) {
                    parameters[6] = null;
                }else if (dayFormatMatcher(singleAddSell.getText())){
                    parameters[6] = singleAddSell.getText();
                }else{
                    popup("Sale date is of incorrect format");
                    return;
                }
                parameters[7] = Integer.toString(IDCount);
                petShop.addAnimal(parameters);
                IDCount += 1;
                popup("Success");
            }
        });
        refreshListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listUpdater();//calls for refresh of the list
            }
        });
        sellButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selInd = animalList.getSelectedIndex();
                if (petShop.animalList.get(selInd).sold) {
                    popup("Selected animal is not for sale");
                } else if (dayFormatMatcher(saleDateEntry.getText())) {
                    petShop.animalList.get(selInd).sellingDate = saleDateEntry.getText();
                    petShop.animalList.get(selInd).sold = true;
                    popup("Selected animal marked as sold");
                    System.out.println(petShop.animalList.get(selInd).getSellingDate());
                    listUpdater();
                } else {
                    popup("Input date must be of YYYY-MM-DD format");
                }
            }
        });
        dailyRevSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dayFormatMatcher(dayRevEntry.getText())){
                Calendar inDate = petShop.strToDate(dayRevEntry.getText());
                int totalRev = 0;
                for(int i=0; i<petShop.animalList.size();i++){
                    if(petShop.animalList.get(i).sold &&
                            petShop.strToDate(petShop.animalList.get(i).sellingDate).equals(inDate)){
                        totalRev += petShop.animalList.get(i).getPriceNum();
                    }
                }dayRevResult.setText("£"+Integer.toString(totalRev));
                }
            }
        });
        monthRevSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(monthFormatMatcher(monthRevEntry.getText())){
                    String[] Mon = monthRevEntry.getText().split("-");
                    int totalRev=0;
                    String[] petMon;
                    for(int i=0;i<petShop.animalList.size();i++){
                        if(petShop.animalList.get(i).sold) {
                            petMon = petShop.animalList.get(i).sellingDate.split("-");
                            if (Mon[0].equals(petMon[0]) && Mon[1].equals(petMon[1])) {
                                totalRev += petShop.animalList.get(i).getPriceNum();
                            }
                        }
                    }monthRevResult.setText("£"+Integer.toString(totalRev));

                }
            }
        });
        tabs.addChangeListener(new ChangeListener() {//calls the data updater when changing tab
            @Override
            public void stateChanged(ChangeEvent e) {
                listUpdater();
            }
        });
        searchClass.addActionListener(new ActionListener() {//TODO: remove these box actions
            @Override
            public void actionPerformed(ActionEvent e) {
                orderModel.removeAllElements();
                String x = String.valueOf(searchClass.getSelectedItem());
                ArrayList<String> list = petShop.orderLister(x);
                for (int i=0; i< list.size();i++){
                    orderModel.addElement(list.get(i));
                }
                if(String.valueOf(searchClass.getSelectedItem()).equals("Reptilia")){
                    venomYes.setEnabled(true);
                    venomNo.setEnabled(true);
                }else{
                    venomYes.setEnabled(false);
                    venomNo.setEnabled(false);
                }
            }
        });
        searchOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                familyModel.removeAllElements();
                String x = String.valueOf(searchOrder.getSelectedItem());
                ArrayList<String> list = petShop.familyLister(x);
                for (int i=0; i< list.size();i++){
                    familyModel.addElement(list.get(i));
                }
            }
        });
        searchFamily.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                genusModel.removeAllElements();
                String x = String.valueOf(searchFamily.getSelectedItem());
                ArrayList<String> list = petShop.genusLister(x);
                for (int i=0; i< list.size();i++){
                    genusModel.addElement(list.get(i));
                }
            }
        });
        searchGenus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                speciesModel.removeAllElements();
                String x = String.valueOf(searchGenus.getSelectedItem());
                ArrayList<String> list = petShop.speciesLister(x);
                for (int i=0; i< list.size();i++){
                    speciesModel.addElement(list.get(i));
                }
            }
        });
    }
    //string/array inputs are of form [givenName, commonName, price, sex, colour, arrivalDate, sellingDate]
    public boolean dayFormatMatcher(String input){
        String in = input;
        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
        boolean result = datePattern.matcher(in).matches();
        return result;
    }
    public boolean monthFormatMatcher(String input){
        String in = input;
        Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}");
        boolean result = datePattern.matcher(in).matches();
        return result;
    }
    public void popup(String value){//creates popup dialog where the value arg is the message to be displayed
        JOptionPane.showMessageDialog(null, value);
    }

    public void listUpdater(){//Main function responsible for fetching updated animal list data
        listModel.clear();//clears any existing entries in the list for re-population
        System.out.println(petShop.animalCount);
        String[] lOut = new String[petShop.animalCount];//create an array where the length is equal to the
        //number of animals in the shop
        for(int i=0; i< petShop.animalList.size();i++) {
                lOut[i] = "Name: " + petShop.animalList.get(i).getGivenName() + " , Common Name: " +
                        petShop.animalList.get(i).getCommonName() + " , Sex: " + petShop.animalList.get(i).getSex() +
                        " , Colour: " + petShop.animalList.get(i).getColour() + " , Price: £" +
                        petShop.animalList.get(i).getPrice();
                listModel.addElement(lOut[i]);

            //for each animal in the array, output its information
        }
        //TODO handle displaying each part of the classifications for the combo box options in the search pane

    }

    private void createUIComponents() {
        listModel = new DefaultListModel<>();
        animalList = new JList(listModel);


    }
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setContentPane(new menu().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
