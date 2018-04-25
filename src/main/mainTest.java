package main;
import java.util.Arrays;
public class mainTest {
    public mainTest(){}
    public static void main(String[] args){
        String outString="";
        for (int i=0; i<=mainMenu.animalList.size(); i++){
            outString += mainMenu.animalList.get(i).givenName;
        }
        System.out.println(outString);
    }
}
