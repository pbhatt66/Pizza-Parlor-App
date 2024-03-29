package com.example.rupizza;

/**
 * PizzaMaker class represents the type of pizza the user wants to make.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class PizzaMaker {

    /**
     * Creates pizza object based on pizza type.
     * @param pizzaType type of pizza in string
     * @return pizza object
     */
    public static Pizza createPizza(String pizzaType) {
        if (pizzaType == null) {
            return null;
        }

        if (pizzaType.equals("Deluxe")) {
            return new Deluxe();
        }
        else if (pizzaType.equals("Supreme")) {
            return new Supreme();
        }
        else if (pizzaType.equals("Meatzza")) {
            return new Meatzza();
        }
        else if (pizzaType.equals("Seafood")) {
            return new Seafood();
        }
        else if (pizzaType.equals("Pepperoni")) {
            return new Pepperoni();
        }
        else if (pizzaType.equals("Challenge")) {
            return new Challenge();
        }
        else if (pizzaType.equals("Cheesesteak")) {
            return new Cheesesteak();
        }
        else if (pizzaType.equals("Hawaiian")) {
            return new Hawaiian();
        }
        else if (pizzaType.equals("SurfNTurf")) {
            return new SurfNTurf();
        }
        else if (pizzaType.equals("Vegetarian")) {
            return new Vegetarian();
        }
        else {
            return new BuildYourOwn();
        }

    }
}
