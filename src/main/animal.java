package main;
import java.util.*;
import java.text.SimpleDateFormat;
public class animal {
    String givenName;
    String commonName;
    String price;
    String sex;
    String colour;
    String arrivalDate;
    String sellingDate;
    String[] parList;
    boolean sold;
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    public animal(String parameters){
        parList = parameters.split(", ");
        givenName = parList[0];
        commonName = parList[1];
        price = "£"+parList[2];
        sex = parList[3];
        colour = parList[4];
        if(parList.length >=6) {
            arrivalDate = parList[5];
        }else{
            Date date = new Date();
            arrivalDate = df.format(date);
        }
        if(parList.length ==7){
            sellingDate = parList[6];
        }
        if (sellingDate == null){
            sold = false;
        }else sold = true;
    }
    public void sellAnimal(Date sellDate){
        if(sold = false){
            sold = true;
            sellingDate = df.format(sellDate);
        }
    }
}
