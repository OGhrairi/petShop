package main;
import java.util.*;
import java.text.SimpleDateFormat;
abstract class animal {
    String givenName;
    String commonName;
    String price;
    String sex;
    String colour;
    String arrivalDate;
    String sellingDate;
    String[] parList;
    boolean sold;
   // static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public animal(String parameters){
        parseInfo(parameters);
    }


    private void parseInfo(String param){
        parList = param.split(", ");
        givenName = parList[0];
        commonName = parList[1];
        price = "£"+parList[2];
        sex = parList[3];
        colour = parList[4];
        if(parList.length >=6) {
            arrivalDate = parList[5];
        }else{
            Date date = new Date();
            //arrivalDate = mainMenu.df.format(date);
        }
        if(parList.length ==7){
            sellingDate = parList[6];
        }
        if (sellingDate == null){
            sold = false;
        }else sold = true;
    }

}
