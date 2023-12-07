package com.example.rupizza;

/**
 * SurfNTurf class inherits from Pizza class and represents a surf 'n turf specialty pizza.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class SurfNTurf extends Pizza {

    /**
     * Default SurfNTurf constructor;
     * constructs the surf 'n turf pizza object and adds necessary toppings. squid, beef, shrimp, sausage
     */
    public SurfNTurf() {
        super();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.SQUID);
        toppings.add(Topping.BEEF);
        toppings.add(Topping.SHRIMP);
        toppings.add(Topping.SAUSAGE);
    }

    /**
     * Returns the price of pizza based on user selection.
     * @return price
     */
    @Override
    public double price() {
        double price = switch (size) {
            case SMALL -> 15.99;
            case MEDIUM -> 16.99;
            case LARGE -> 17.99;
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
     * Returns a string representation of surf 'n turf pizza.
     * @return a string representation of surf 'n turf pizza.
     */
    @Override
    public String toString() {
        String str = "Surf 'N Turf Pizza | " + size.toString();
        if (extraSauce) {
            str += " | Extra Sauce";
        }
        if (extraCheese) {
            str += " | Extra Cheese";
        }
        return str;
    }
}
