package main;

import java.util.ArrayList;

public class shop {
    ArrayList<animal> animalList = new ArrayList<animal>();
    int animalCount;

    public shop(){

    }
    void addAnimal(String[] parameters){
        String animalType = parameters[0];
        switch (animalType) {//TODO split each case into creating object and then adding to list
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
        }
        System.out.println(animalList.size());
    }
    private String[] splitter(String parameterString) {
        return parameterString.split(", ");
    }
}




