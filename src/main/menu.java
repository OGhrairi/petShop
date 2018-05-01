package main;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    private JButton refreshListButton;
    private JTextField saleDateEntry;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
    Date date = new Date();
    FileFilter filter = new FileNameExtensionFilter(".txt files","txt");
    String[] parameters = new String[8];
    shop petShop = new shop();
    DefaultListModel <String> listModel;
    int IDCount = 1;
    public menu() {
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
                try {
                    Integer.parseInt(singleAddPrice.getText());
                }catch (NumberFormatException e1){
                    popup("Animal price is invalid");
                    return;
                }
                parameters[2] = singleAddPrice.getText();
                if(singleAddMale.isSelected() == true){
                    parameters[3] = "Male";
                }else parameters[3] = "Female";
                parameters[4] = singleAddColour.getText();
                if (dayFormatMatcher(singleAddArr.getText()) == true){
                parameters[5] = singleAddArr.getText();
                }else{ if(singleAddArr.getText().isEmpty() == true){
                    parameters[5] = df.format(date);//sets the arrival date to today's date if no input detected
                }else {
                    popup("Arrival date is of incorrect format");
                    return;
                }}
                if (dayFormatMatcher(singleAddSell.getText()) == true){
                    parameters[6] = singleAddSell.getText();
                }else{ if(singleAddSell.getText().isEmpty() == true){
                    parameters[6] = null;
                }else {
                    popup("Sale date is of incorrect format");
                    return;
                }}
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
                if(petShop.animalList.get(selInd).sold == true){
                    popup("Selected animal is not for sale");
                }else{if(dayFormatMatcher(saleDateEntry.getText()) == true) {
                    petShop.animalList.get(selInd).sellingDate = saleDateEntry.getText();
                    popup("Selected animal marked as sold");
                    System.out.println(petShop.animalList.get(selInd).getSellingDate());
                    listUpdater();
                    }else{popup("Input date must be of YYYY-MM-DD format");}
                }

            }
        });
        dailyRevSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dayFormatMatcher(dayRevEntry.getText()) == true){
                Calendar inDate = petShop.strToDate(dayRevEntry.getText());
                int totalRev = 0;
                for(int i=0; i<petShop.animalList.size();i++){//TODO fix this fucking thing, the comparison doesn't work
                    if(petShop.strToDate(petShop.animalList.get(i).sellingDate).equals(inDate)){
                        totalRev += petShop.animalList.get(i).getPriceNum();
                    }
                }dayRevResult.setText("£"+Integer.toString(totalRev));
                }
            }
        });
        monthRevSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(monthFormatMatcher(monthRevEntry.getText()) == true){
                    String[] Mon = monthRevEntry.getText().split("-");
                    System.out.println(Mon[0]+"-"+Mon[1]);
                    int totalRev=0;
                    String[] petMon;
                    for(int i=0;i<petShop.animalList.size();i++){
                        petMon = petShop.animalList.get(i).sellingDate.split("-");
                        if(Mon[0] == petMon[1]){
                            System.out.println("success");
                            totalRev += petShop.animalList.get(i).getPriceNum();
                        }
                    }monthRevResult.setText("£"+Integer.toString(totalRev));

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

    public void listUpdater(){//generates a list of all animals currently in the shop
        listModel.clear();//clears any existing entries in the list for repopulation
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
