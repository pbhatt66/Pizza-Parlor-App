package com.example.rupizza;

/**
 * Cheesesteak class inherits from Pizza class and represents a cheesesteak specialty pizza.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class Cheesesteak extends Pizza {

    /**
     * Default Cheesesteak constructor;
     * constructs the cheesesteak pizza object and adds necessary toppings.
     */
    public Cheesesteak() {
        super();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.GREEN_PEPPER);
        toppings.add(Topping.ONION);
        toppings.add(Topping.JALAPENO);
        toppings.add(Topping.BEEF);
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
     * Returns a string representation of cheesesteak pizza.
     * @return a string representation of cheesesteak pizza.
     */
    @Override
    public String toString() {
        String str = "Cheesesteak Pizza | " + size.toString();
        if (extraSauce) {
            str += " | Extra Sauce";
        }
        if (extraCheese) {
            str += " | Extra Cheese";
        }
        return str;
    }
}
