package main;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class shop {
    ArrayList<animal> animalList = new ArrayList<>();
    int animalCount = 0;
    ArrayList<transLog> log = new ArrayList<transLog>();
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.UK);
    public shop() {
    }
    void addAnimal(String[] parameters){
        String animalType = parameters[1];
        animalCount += 1;
        switch (animalType) {
            case "Dog": animalList.add(new dog(parameters));
                break;
            case "Cat": animalList.add(new cat(parameters));
                break;
            case "Rabbit": animalList.add(new rabbit(parameters));
                break;
            case "Golden Hamster": animalList.add(new goldenHamster(parameters));
                break;
            case "Roborovski Hamster": animalList.add(new roborvskiHamster(parameters));
                break;
            case "Guinea Pig": animalList.add(new guineaPig(parameters));
                break;
            case "Edward's Fig Parrot": animalList.add(new figParrot(parameters));
                break;
            case "Norwegian Blue": animalList.add(new norwegianBlue(parameters));
                break;
            case "Hyacinth Macaw": animalList.add(new hyacinthMacaw(parameters));
                break;
            case "Yellow Canary": animalList.add(new yellowCanary(parameters));
                break;
            case "Goldfish": animalList.add(new goldfish(parameters));
                break;
            case "Koi": animalList.add(new koi(parameters));
                break;
            case "Common Barbel": animalList.add(new barbel(parameters));
                break;
            case "Boa Constrictor": animalList.add(new boaConstrictor(parameters));
                break;
            case "Corn Snake": animalList.add(new cornSnake(parameters));
                break;
            case "Black-necked Spitting Cobra": animalList.add(new spittingCobra(parameters));
                break;


        }    //string/array inputs are of form [givenName, commonName, price, sex, colour, arrivalDate, sellingDate]
    }
    public Calendar strToDate(String dateIn){
        //input must be in string format "YYYY-MM-DD"
        String[] ds = dateIn.split("-");
        Calendar outDate = Calendar.getInstance();
        outDate.set(Integer.parseInt(ds[0]),(Integer.parseInt(ds[1])-1),Integer.parseInt(ds[2]));
        return outDate;
    }
    public String dateToStr(Calendar dateIn){
        String outStr = (df.format(dateIn.getTime()));
        return outStr;
    }
}
class transLog{
    BigDecimal price;
    String SaleDate;
    public transLog(BigDecimal price, String SaleDate) {
        this.price = price;
        this.SaleDate = SaleDate;
    }



}




