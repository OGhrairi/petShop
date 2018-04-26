package main;

abstract class reptiliaClass extends animal {
    Boolean venomous;

    public reptiliaClass(String parameters) {
        super(parameters);
        aClass = "Reptilia";
        legCount = 0;

    }
}