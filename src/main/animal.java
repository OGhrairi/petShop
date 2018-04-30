package main;
import java.text.DateFormat;
import java.util.*;
import java.text.SimpleDateFormat;
abstract class animal {
    String givenName;
    String commonName;
    String aClass;
    String order;
    String family;
    String genus;
    String species;
    int legCount;
    String price;
    String sex;
    String colour;
    String arrivalDate;
    String sellingDate;
    //String[] parList;
    boolean sold;
    DateFormat df = new SimpleDateFormat("YYYY-MM-DD");

    public animal (String[] parameters){
        parseInfo(parameters);
    }

    //string/array inputs are of form [givenName, commonName, price, sex, colour, arrivalDate, sellingDate]
    private void parseInfo(String[] parList){

        givenName = parList[0];
        commonName = parList[1];
        price = "£"+parList[2];
        sex = parList[3];
        colour = parList[4];
        arrivalDate = parList[5];
        if (parList[6] != null){
            sellingDate = parList[6];
            sold = true;
        }else sold = false;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getCommonName() {
        return commonName;
    }

    public String getAClass() {
        return aClass;
    }

    public String getOrder() {
        return order;
    }

    public String getFamily() {
        return family;
    }

    public String getGenus() {
        return genus;
    }

    public String getSpecies() {
        return species;
    }

    public int getLegCount() {
        return legCount;
    }

    public String getPrice() {
        return price;
    }

    public String getSex() {
        return sex;
    }

    public String getColour() {
        return colour;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getSellingDate() {
        return sellingDate;
    }
}


