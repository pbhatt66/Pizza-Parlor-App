package com.example.rupizza;

/**
 * Hawaiian class inherits from Pizza class and represents a hawaiian specialty pizza.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class Hawaiian extends Pizza {

    /**
     * Default Hawaiian constructor;
     * constructs the hawaii pizza object and adds necessary toppings.
     */
    public Hawaiian() {
        super();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.HAM);
        toppings.add(Topping.PINEAPPLE);
    }

    /**
     * Returns the price of pizza based on user selection.
     * @return price
     */
    @Override
    public double price() {
        double price = switch (size) {
            case SMALL -> 11.99;
            case MEDIUM -> 12.99;
            case LARGE -> 13.99;
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
     * Returns a string representation of hawaiian pizza.
     * @return a string representation of hawaiian pizza.
     */
    @Override
    public String toString() {
        String str = "Hawaiian Pizza | " + size.toString();
        if (extraSauce) {
            str += " | Extra Sauce";
        }
        if (extraCheese) {
            str += " | Extra Cheese";
        }
        return str;
    }
}
