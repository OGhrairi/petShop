package main;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


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
    private JRadioButton searchFemale;
    private JRadioButton venomYes;
    private JRadioButton venomNo;
    private JRadioButton talkYes;
    private JRadioButton talkNo;
    private JComboBox searchColour;
    private JList searchList;
    private JTextField dayRevEntry;
    private JButton dailyRevSubmit;
    private JTextField monthRevEntry;
    private JButton monthRevSubmit;
    private JLabel dayRevResult;
    private JLabel monthRevResult;
    private JTextField saleDateEntry;
    private JTextField legMax;
    private JComboBox searchComName;
    private JButton resetButton;
    private ButtonGroup venomous;
    private ButtonGroup talking;
    private ButtonGroup sexSearch;
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
    private DefaultComboBoxModel <String> comNameMod = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel <String> colourModel = new DefaultComboBoxModel<>();
    private int IDCount;
    private File animalFile;
    private File writeDest;
    private String dest;
    ArrayList<animal> searchResults;
    menu() {
        IDCount = 1;
        searchOrder.setModel(orderModel);
        searchClass.setModel(classModel);
        searchFamily.setModel(familyModel);
        searchGenus.setModel(genusModel);
        searchSpecies.setModel(speciesModel);
        searchComName.setModel(comNameMod);
        searchColour.setModel(colourModel);
        listUpdater();//calls generation of the lists on load
        JFileChooser readChooser = new JFileChooser();
        JFileChooser writeChooser = new JFileChooser();
        FileBrowse.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readChooser.setFileFilter(filter);
                readChooser.setAcceptAllFileFilterUsed(false);
                int rVal = readChooser.showOpenDialog(panel1);
                if(rVal == JFileChooser.APPROVE_OPTION) {
                    animalFile = readChooser.getSelectedFile();
                }
            }
        });
        chooseFileDestinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                writeChooser.setAcceptAllFileFilterUsed(false);
                int rVal = writeChooser.showOpenDialog(panel1);
                if(rVal == JFileChooser.APPROVE_OPTION){
                    writeDest = writeChooser.getSelectedFile();
                    dest = writeDest.getPath();
                }
            }
        });
        writeToFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fName;
                BufferedWriter bw = null;
                File f1 = new File(dest);
                f1.mkdirs();
                if(fileNameEntry.getText().isEmpty()){
                    popup("File name is empty");
                    return;
                }else{
                    fName = fileNameEntry.getText()+".txt";
                }
                File f2 = new File(f1,fName);
                if(f2.exists()){
                    popup("File already exists");
                    return;
                }
                try {
                    bw = new BufferedWriter(new FileWriter(f2));
                    ArrayList<animal> lst =  new ArrayList<>(petShop.animalList);
                    String outStr;
                    for(int i=0; i<lst.size();i++){
                        if(lst.get(i).sold) {
                            outStr = lst.get(i).getGivenName() + ", " + lst.get(i).getCommonName() + ", " +
                                    lst.get(i).getPrice() + ", " + lst.get(i).getSex() + ", " + lst.get(i).getColour()
                                    + ", " + lst.get(i).getArrivalDate() + ", " + lst.get(i).getSellingDate();
                            bw.write(outStr);
                            bw.newLine();
                        }
                    }
                    for(int j=0; j<lst.size();j++) {
                        if (!lst.get(j).sold) {
                            outStr = lst.get(j).getGivenName() + ", " + lst.get(j).getCommonName() + ", " +
                                    lst.get(j).getPrice() + ", " + lst.get(j).getSex() + ", " + lst.get(j).getColour()
                                    + ", " + lst.get(j).getArrivalDate();
                            bw.write(outStr);
                            bw.newLine();
                        }

                    }popup("File Successfully Written");
                }catch(IOException a){
                    popup("Error");
                }finally{
                    try{
                        if(bw != null){
                            bw.close();
                        }
                    }catch (IOException ee){
                        popup("Error");
                    }
                }
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BufferedReader br = null;
                try{
                    br = new BufferedReader(new FileReader(animalFile));
                    String line = null;
                    while((line = br.readLine()) != null){
                        String[] input = line.split(", ");
                        parameters[0] = input[0];
                        parameters[1] = input[1];
                        String tempPrice = input[2];
                        parameters[3] = input[3];
                        parameters[4] = input[4];
                        if(input.length>=6){
                            parameters[5] = input[5];
                            if(input.length==7){
                                parameters[6] = input[6];
                            }else{
                                parameters[6]=null;
                            }
                        }else{
                            parameters[5]=df.format(date);
                            parameters[6]=null;
                        }
                        parameters[2]=petShop.discount(tempPrice,parameters[5]);
                        parameters[7] = Integer.toString(IDCount);
                        IDCount+=1;
                        petShop.addAnimal(parameters);
                    }popup("Animals succesfully added");
                }catch(IOException en){
                    popup("File Error");
                }
                finally{
                    try{
                        if(br!=null){
                            br.close();
                        }
                    }catch (IOException ee){
                        popup("File Error");

                    }
                }
            }
        });
        singleAddSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchListModel.clear();
                if(!singleAddGivenName.getText().isEmpty()) {
                    parameters[0] = singleAddGivenName.getText();
                }else {
                    popup("Enter given name");
                    return;
                }
                parameters[1] = singleAddCommonName.getSelectedItem().toString();
                String TempPrice;
                try {
                    Float.parseFloat(singleAddPrice.getText());
                }catch (NumberFormatException e1){
                    popup("Animal price is invalid");
                    return;
                }
                TempPrice = singleAddPrice.getText();
                if(singleAddMale.isSelected()){
                    parameters[3] = "male";
                }else if(singleAddFemale.isSelected()){
                    parameters[3] = "female";
                }else {
                    popup("Select animal gender");
                    return;
                }
                if(!singleAddColour.getText().isEmpty()) {
                    parameters[4] = singleAddColour.getText();
                }else {
                    popup("Enter animal Colour");
                    return;
                }
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
                float totalRev = 0;
                for(int i=0; i<petShop.animalList.size();i++){
                    if(petShop.animalList.get(i).sold &&
                            petShop.strToDate(petShop.animalList.get(i).sellingDate).equals(inDate)){
                        totalRev += petShop.animalList.get(i).getPriceNum();

                    }
                }dayRevResult.setText("£"+Float.toString(totalRev));
                }else{
                    popup("Input date must be in YYYY-MM--DD format");
                }
            }
        });
        monthRevSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(monthFormatMatcher(monthRevEntry.getText())){
                    String[] Mon = monthRevEntry.getText().split("-");
                    float totalRev=0;
                    String[] petMon;
                    for(int i=0;i<petShop.animalList.size();i++){
                        if(petShop.animalList.get(i).sold) {
                            petMon = petShop.animalList.get(i).sellingDate.split("-");
                            if (Mon[0].equals(petMon[0]) && Mon[1].equals(petMon[1])) {
                                totalRev += petShop.animalList.get(i).getPriceNum();

                            }
                        }
                    }Math.floor(totalRev);
                    monthRevResult.setText("£"+Float.toString(totalRev));

                }else{
                    popup("Input month must be in YYYY-MM format");
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
            public void actionPerformed(ActionEvent e) {//TODO fix searcher
                searchListModel.clear();
                searchResults = new ArrayList<>(petShop.animalList);

                //gender filter
                if(searchMale.isSelected()) {
                    searchResults.removeIf(s -> s.getSex().equals("female"));

                }if(searchFemale.isSelected()){
                    searchResults.removeIf(s -> s.getSex().equals("male"));
                }
                //given name filter - will accept name substring inputs
                if(!givenNameSearch.getText().isEmpty()){
                    searchResults.removeIf(s -> !s.getGivenName().toLowerCase()
                            .contains(givenNameSearch.getText().toLowerCase()));
                }
                //common name filter
                if(searchComName.getSelectedIndex()!=0){
                    searchResults.removeIf(s -> !s.getCommonName()
                            .equals(searchComName.getSelectedItem().toString()));
                }
                //colour filter - will only match exact colour inputs, no substrings
                if(searchColour.getSelectedIndex()!=0){
                    searchResults.removeIf(s -> !s.getColour()
                            .equals(searchColour.getSelectedItem().toString()));
                }
                //classification filters
                if(searchClass.getSelectedIndex()!=0){
                    searchResults.removeIf(s -> !s.getAClass()
                    .equals(searchClass.getSelectedItem().toString()));
                }
                if(searchOrder.getSelectedIndex()!=0){
                    searchResults.removeIf(s -> !s.getOrder()
                            .equals(searchOrder.getSelectedItem().toString()));
                }
                if(searchFamily.getSelectedIndex()!=0){
                    searchResults.removeIf(s -> !s.getFamily()
                            .equals(searchFamily.getSelectedItem().toString()));
                }
                if(searchGenus.getSelectedIndex()!=0){
                    searchResults.removeIf(s -> !s.getGenus()
                            .equals(searchGenus.getSelectedItem().toString()));
                }
                if(searchSpecies.getSelectedIndex()!=0){
                    searchResults.removeIf(s -> !s.getSpecies()
                            .equals(searchSpecies.getSelectedItem().toString()));
                }
                //leg count filter
                if(!legMin.getText().isEmpty() || !legMax.getText().isEmpty()){
                    if(!legMin.getText().isEmpty() && !legMax.getText().isEmpty()){
                        searchResults.removeIf(s -> !((s.getLegCount() >= Integer.parseInt(legMin.getText()))
                        &&(s.getLegCount() <= Integer.parseInt(legMax.getText()))));
                    }else if(!legMin.getText().isEmpty()){
                        searchResults.removeIf(s -> !(s.getLegCount() >= Integer.parseInt(legMin.getText())));
                    }else if(!legMax.getText().isEmpty()) {
                        searchResults.removeIf(s -> !(s.getLegCount() <= Integer.parseInt(legMax.getText())));
                    }
                }
                //venom filter
                if(venomYes.isSelected()) {
                    searchResults.removeIf(s -> !(s.getAClass().equals("Reptilia")
                    && s.venomous));
                }
                else if(venomNo.isSelected()) {
                    searchResults.removeIf(s -> !(s.getAClass().equals("Reptilia")
                    && !s.venomous));
                }
                //talking filter
                if(talkYes.isSelected()) {
                    searchResults.removeIf(s -> !(s.getOrder().equals("Psittaciformes")
                    && s.talking));
                }
                if(talkNo.isSelected()) {
                    searchResults.removeIf(s -> !(s.getOrder().equals("Psittaciformes")
                    && !s.talking));
                }
                //output list
                String[] y = new String[searchResults.size()];
                for(int i=0; i<y.length;i++){
                    y[i] = searchResults.get(i).getCommonName() + " - " +
                            searchResults.get(i).getGivenName() + " - "+
                    searchResults.get(i).getSex();
                    searchListModel.addElement(y[i]);
                }
            }
        });


        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                givenNameSearch.setText("");
                searchComName.setSelectedIndex(0);
                searchColour.setSelectedIndex(0);
                searchClass.setSelectedIndex(0);
                searchOrder.setSelectedIndex(0);
                searchFamily.setSelectedIndex(0);
                searchGenus.setSelectedIndex(0);
                searchSpecies.setSelectedIndex(0);
                legMin.setText("");
                legMax.setText("");
                sexSearch.clearSelection();
                venomous.clearSelection();
                talking.clearSelection();
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
        classModel.addElement("-");
        orderModel.removeAllElements();
        orderModel.addElement("-");
        familyModel.removeAllElements();
        familyModel.addElement("-");
        genusModel.removeAllElements();
        genusModel.addElement("-");
        speciesModel.removeAllElements();
        speciesModel.addElement("-");
        comNameMod.removeAllElements();
        comNameMod.addElement("-");
        colourModel.removeAllElements();
        colourModel.addElement("-");
        ArrayList<ArrayList<String>> ls = new ArrayList<>();
        for(int i =0; i<=6; i++){
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
        for(int i=0; i<ls.get(5).size(); i++){
            comNameMod.addElement(ls.get(5).get(i));
        }
        for(int i=0; i<ls.get(6).size(); i++){
            colourModel.addElement(ls.get(6).get(i));
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
