package com.example.rupizza;

/**
 * Vegetarian class inherits from Pizza class and represents a vegetarian specialty pizza.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class Vegetarian extends Pizza {

    /**
     * Default Vegetarian constructor;
     * constructs the vegetarian pizza object and adds necessary toppings.
     */
    public Vegetarian() {
        super();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.ONION);
        toppings.add(Topping.MUSHROOM);
        toppings.add(Topping.BLACK_OLIVE);
        toppings.add(Topping.JALAPENO);
    }

    /**
     * Returns the price of pizza based on user selection.
     * @return price
     */
    @Override
    public double price() {
        double price = switch (size) {
            case SMALL -> 13.99;
            case MEDIUM -> 14.99;
            case LARGE -> 15.99;
        };
        if (extraSauce) {
            price += 1;
        }
        if (extraCheese) {
            price += 1;
        }
        return price;
    }

    /**
     * Returns a string representation of vegetarian pizza.
     * @return a string representation of vegetarian pizza.
     */
    @Override
    public String toString() {
        String str = "Vegetarian Pizza | " + size.toString();
        if (extraSauce) {
            str += " | Extra Sauce";
        }
        if (extraCheese) {
            str += " | Extra Cheese";
        }
        return str;
    }
}
