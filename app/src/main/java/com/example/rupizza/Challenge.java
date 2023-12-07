package com.example.rupizza;

/**
 * Challenge class inherits from Pizza class and represents a challenge specialty pizza.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class Challenge extends Pizza {

    /**
     * Default Challenge constructor;
     * constructs the challenge pizza object and adds a gross topping combination.
     */
    public Challenge() {
        super();
        this.sauce = Sauce.TOMATO;
        toppings.add(Topping.BLACK_OLIVE);
        toppings.add(Topping.CRAB_MEATS);
        toppings.add(Topping.PINEAPPLE);
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
     * Returns a string representation of challenge pizza.
     * @return a string representation of challenge pizza.
     */
    @Override
    public String toString() {
        String str = "Challenge Pizza | " + size.toString();
        if (extraSauce) {
            str += " | Extra Sauce";
        }
        if (extraCheese) {
            str += " | Extra Cheese";
        }
        return str;
    }
}
