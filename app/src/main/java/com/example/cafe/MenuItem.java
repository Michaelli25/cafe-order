package com.example.cafe;

import java.io.Serializable;

/**
 * The MenuItem class is the parent class of all items in the Cafe.
 * It holds the quantity of the item and the price.
 * @author Shane Hoffman, Michael Li
 */
public class MenuItem implements Serializable {
    private int quantity;
    private double price;

    /**
     * Creates a MenuItem object.
     * @param quantity - quantity of the item
     */
    public MenuItem(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Used as a template for subclasses.
     */
    public void itemPrice() { }

    /**
     * Gets the price of an item.
     * @return - the price of an item
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Gets the quantity of an item.
     * @return - the quantity of an item
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Sets the quantity of an item.
     * @param quantity - the quantity of an item
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Sets the price of an item.
     * @param price - the price of an item
     */
    public void setPrice(double price) {
        this.price = price * this.quantity;
    }

    /**
     * Creates a readable string for the MenuItem object.
     * @return menuItemString - MenuItem object in a readable string
     */
    @Override
    public String toString() {
        return " --- Quantity: " + this.quantity;
    }

}
