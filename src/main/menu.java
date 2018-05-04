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
    private JList searchList;
    private JTextField dayRevEntry;
    private JButton dailyRevSubmit;
    private JTextField monthRevEntry;
    private JButton monthRevSubmit;
    private JLabel dayRevResult;
    private JLabel monthRevResult;
    private JButton refreshListButton;
    private JTextField saleDateEntry;
    private JTextField legMax;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
    private Date date = new Date();
    private FileFilter filter = new FileNameExtensionFilter(".txt files","txt");
    String[] parameters = new String[8];
    private shop petShop = new shop();
    private DefaultListModel <String> listModel;
    private DefaultListModel <String> searchListModel;
    private DefaultComboBoxModel <String> classModel = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel <String> orderModel = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel <String> familyModel = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel <String> genusModel = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel <String> speciesModel = new DefaultComboBoxModel<>();
    private int IDCount = 1;
    menu() {
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
                }else if(singleAddFemale.isSelected()){
                    parameters[3] = "Female";
                }else {
                    popup("Select animal gender");
                    return;
                }
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
            public void actionPerformed(ActionEvent e) { //TODO decided whether this button can be removed
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

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchListModel.clear();
                ArrayList<animal> searchResults = new ArrayList<>(petShop.animalList);
                if(searchMale.isSelected() || searchFemale.isSelected()){//gender filter
                    for(int i=0; i<searchResults.size();i++){
                        if(searchMale.isSelected() && searchResults.get(i).sex != "Male"){
                            searchResults.remove(i);
                        }else if(searchFemale.isSelected() && searchResults.get(i).sex != "Female"){
                            searchResults.remove(i);
                        }
                    }
                }
                if(!givenNameSearch.equals("")){//TODO set up search results 
                    for (int i=0; i<searchResults.size();i++){
                        if(!searchResults.get(i).givenName.equals(givenNameSearch)){
                            searchResults.remove(i);
                        }
                    }
                }
                String[] y = new String[searchResults.size()];
                for(int i=0; i<y.length;i++){
                    y[i] = searchResults.get(i).commonName + " - " + searchResults.get(i).givenName;
                    searchListModel.addElement(y[i]);
                }System.out.println(petShop.animalList.size());
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
        String[] lOut = new String[petShop.animalCount];//create an array where the length is equal to the
        //number of animals in the shop
        for(int i=0; i< petShop.animalList.size();i++) {
                lOut[i] = "Name: " + petShop.animalList.get(i).getGivenName() + " , Common Name: " +
                        petShop.animalList.get(i).getCommonName() + " , Sex: " + petShop.animalList.get(i).getSex() +
                        " , Colour: " + petShop.animalList.get(i).getColour() + " , Price: £" +
                        petShop.animalList.get(i).getPrice();
                listModel.addElement(lOut[i]);

            //for each animal in the array, output its information
        }classListAssigner();

    }
    public void classListAssigner(){
        classModel.removeAllElements();
        classModel.addElement("None");
        orderModel.removeAllElements();
        orderModel.addElement("None");
        familyModel.removeAllElements();
        familyModel.addElement("None");
        genusModel.removeAllElements();
        genusModel.addElement("None");
        speciesModel.removeAllElements();
        speciesModel.addElement("None");
        ArrayList<ArrayList<String>> ls = new ArrayList<>();
        for(int i =0; i<=4; i++){
            ls.add(petShop.classLister(i));
        }
        for(int i=0; i<ls.get(0).size();i++){
            classModel.addElement(ls.get(0).get(i));
        }
        for(int i=0; i<ls.get(1).size();i++){
            orderModel.addElement(ls.get(1).get(i));
        }
        for(int i=0; i<ls.get(2).size();i++){
            familyModel.addElement(ls.get(2).get(i));
        }
        for(int i=0; i<ls.get(3).size();i++){
            genusModel.addElement(ls.get(3).get(i));
        }
        for(int i=0; i<ls.get(4).size();i++){
            speciesModel.addElement(ls.get(4).get(i));
        }

    }

    private void createUIComponents() {
        listModel = new DefaultListModel<>();
        animalList = new JList(listModel);
        searchListModel = new DefaultListModel<>();
        searchList = new JList(searchListModel);

    }
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setContentPane(new menu().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
