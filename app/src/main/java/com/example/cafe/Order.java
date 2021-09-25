package com.example.cafe;

import java.text.DecimalFormat;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Order class holds the current order and order number. This class
 * can calculate the subtotal, add and remove items from the order.
 * @author Shane Hoffman, Michael Li
 */
public class Order implements Customizable, Serializable {
    private ArrayList<MenuItem> items;
    private double subtotal;
    private int orderNumber;

    private static final int MISS = -1;
    private static final double TAX = .06625;
    private static final DecimalFormat df = new DecimalFormat("####0.00");

    /**
     * Creates an Order object.
     * @param number - the order number
     */
    public Order(int number) {
        items = new ArrayList<>();
        this.orderNumber = number;
    }

    /**
     * Gets an orders subtotal.
     * @return - the orders subtotal
     */
    public double getSubtotal() {
        return this.subtotal;
    }

    /**
     * Gets an orders size.
     * @return - the orders size
     */
    public int getSize() {
        return this.items.size();
    }


    /**
     * Adds an item to the order. If it already exists, increase
     * the quantity of the item.
     * @param obj - MenuItem to add
     * @return true if added, false otherwise
     */
    public boolean add(Object obj) {
        if(obj instanceof MenuItem) {
            MenuItem item = (MenuItem) obj;
            int index = find(item);
            if(index == MISS)
                this.items.add(item);
            else {
                MenuItem found = this.items.get(index);
                found.setQuantity(found.getQuantity() + item.getQuantity());
            }
            return true;
        }
        return false;
    }

    /**
     * Removes an item from the order by String or MenuItem. If
     * the quantity is lower than what the item already in the order
     * has, decrease the quantity.
     * @param obj - MenuItem or String to remove
     * @return true if removed, false otherwise
     */
    public boolean remove(Object obj) {
        if(obj instanceof MenuItem) {
            MenuItem item = (MenuItem) obj;
            int index = find(item);
            if(index == MISS)
                return false;
            else {
                MenuItem found = this.items.get(index);
                if(found.getQuantity() > item.getQuantity())
                    found.setQuantity(found.getQuantity() - item.getQuantity());
                else
                    this.items.remove(item);
                return true;
            }
        }
        if(obj instanceof String) {
            String itemString = (String) obj;
            int index = findString(itemString);
            if(index == MISS)
                return false;
            else {
                this.items.remove(index);
                return true;
            }

        }
        return false;
    }

    /**
     * Find an item by MenuItem object
     * @param item - MenuItem to find
     * @return - index if found, MISS otherwise
     */
    private int find(MenuItem item) {
        for(int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).equals(item))
                return i;
        }
        return MISS;
    }

    /**
     * Find an item by a String of the item.
     * @param itemString - String to find
     * @return - index if found, MISS otherwise
     */
    private int findString(String itemString) {
        for(int i = 0; i < this.items.size(); i++) {
            if(this.items.get(i).toString().equals(itemString))
                return i;
        }
        return MISS;
    }

    /**
     * Calculate subtotal of the order.
     */
    public void calculate() {
        this.subtotal = 0;
        for(int i = 0; i < this.items.size(); i++) {
            this.items.get(i).itemPrice();
            this.subtotal += this.items.get(i).getPrice();
        }
    }

    /**
     * Convert an item at an index to a string.
     * @param index - index of the item
     * @return itemString - toString of the item
     */
    public String convert(int index) {
        MenuItem select = this.items.get(index);
        return select.toString();
    }

    /**
     * Creates a readable string for the Order object.
     * @return orderString - Order object in a readable string
     */
    @Override
    public String toString() {
        String orderString = "~ Order Number " + this.orderNumber + " ~\n\n";
        for(int i = 0; i < this.items.size(); i++) {
            orderString += this.items.get(i).toString() + "\n";
        }
        orderString += "\nTotal: $" + df.format(TAX * this.subtotal + this.subtotal) + "\n";
        return orderString;
    }

}
