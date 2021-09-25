package com.example.cafe;

import java.util.ArrayList;

/**
 * The Coffee class is a subclass of MenuItem. There are different sizes
 * for a coffee and add-ins to the coffee can be added.
 * @author Shane Hoffman, Michael Li
 */
public class Coffee extends MenuItem implements Customizable {
    private ArrayList<Addins> addin;
    private String size;

    private static final double SHORT = 1.99;
    private static final double TALL = 2.49;
    private static final double GRANDE = 2.99;
    private static final double VENTI = 3.49;
    private static final double ADDINS = .20;

    /**
     * Creates a Coffee object.
     * @param quantity - the quantity of the coffee
     * @param size - the size of the coffee
     */
    public Coffee(int quantity, String size) {
        super(quantity);
        this.addin = new ArrayList<>();
        this.size = size;
    }

    /**
     * Adds an addin to the addin array.
     * @param obj - addin to be added
     * @return true if added, false otherwise
     */
    public boolean add(Object obj) {
        if(obj instanceof Addins) {
            Addins addin = (Addins) obj;
            if(this.addin.add(addin))
                return true;
        }
        return false;
    }

    /**
     * Removes an addin from the addin array.
     * @param obj - addin to be removed
     * @return true if removed, false otherwise
     */
    public boolean remove(Object obj) {
        if(obj instanceof Addins) {
            Addins addin = (Addins) obj;
            if(this.addin.remove(addin))
                return true;
        }
        return false;
    }

    /**
     * Calculates the price of the Coffee depending on the
     * size and number of add-ins.
     */
    @Override
    public void itemPrice() {
        double price = this.addin.size() * ADDINS;
        switch (this.size) {
            case "Short":
                price += SHORT;
                break;

            case "Tall":
                price += TALL;
                break;

            case "Grande":
                price += GRANDE;
                break;

            case "Venti":
                price += VENTI;
                break;
        }

        super.setPrice(price);
    }

    /**
     * Compares two coffee objects to see if they have the same size
     * and add-ins.
     * @param obj - coffee object to compare to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Coffee) {
            Coffee coffee = (Coffee) obj;
            if(this.size.equals(coffee.size) && this.addin.equals(coffee.addin)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a readable string of the coffee object.
     * @return coffeeString - coffee object in a readable string
     */
    @Override
    public String toString() {
        if(this.addin.size() == 0)
            return "Coffee --- Size: " + this.size + super.toString() + "\nAdd-ins: [NONE]";
        return "Coffee --- Size: " + this.size + super.toString() + "\nAdd-ins: " + this.addin.toString();
    }

}
