package com.example.cafe;

/**
 * The Donut class is a subclass of MenuItem. It holds the flavor
 * of a donut.
 * @author Shane Hoffman, Michael Li
 */
public class Donut extends MenuItem {
    private String flavor;
    private static final double DONUT = 1.39;

    /**
     * Creates a donut object.
     * @param quantity - quantity of the donut
     * @param flavor - flavor of the donut
     */
    public Donut(int quantity, String flavor) {
        super(quantity);
        this.flavor = flavor;
    }

    /**
     * Sets the price of the donut in parent class.
     */
    @Override
    public void itemPrice() {
        super.setPrice(DONUT);
    }

    /**
     * Compares two donut objects to see if they have the same flavor.
     * @param obj - donut object to compare to
     * @return true if the flavors are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Donut) {
            Donut donut = (Donut) obj;
            if(this.flavor.equals(donut.flavor))
                return true;
        }
        return false;
    }

    /**
     * Creates a readable string of the donut object.
     * @return donutString - donut object in a readable string
     */
    @Override
    public String toString() {
        return "Donut Flavor: " + flavor + super.toString();
    }

}
