package com.example.cafe;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The StoreOrders class holds all placed orders by the store. It can
 * add and remove orders.
 * @author Shane Hoffman, Michael Li
 */
public class StoreOrders implements Customizable, Serializable {
    private ArrayList<Order> orders;

    private static final int MISS = -1;

    /**
     * Creates a StoreOrders object.
     */
    public StoreOrders() {
        this.orders = new ArrayList<>();
    }

    /**
     * Gets the size of the placed orders.
     * @return - store orders size
     */
    public int getSize() {
        return this.orders.size();
    }

    /**
     * Adds an order to the orders array.
     * @param obj - order to be added
     * @return true if added, false otherwise
     */
    public boolean add(Object obj) {
        if(obj instanceof Order) {
            Order order = (Order) obj;
            if(this.orders.add(order))
                return true;
        }
        return false;
    }

    /**
     * Removes an order by the String form of an order.
     * @param obj - String order to be removed
     * @return true if removed, false otherwise
     */
    public boolean remove(Object obj) {
        if(obj instanceof String) {
            String orderString = (String) obj;
            int index = findString(orderString);
            if(index == MISS)
                return false;
            else {
                this.orders.remove(index);
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method for remove to find an order by it's String form.
     * @param itemString - string form of an item
     * @return index of item if found, MISS otherwise
     */
    private int findString(String itemString) {
        for(int i = 0; i < this.orders.size(); i++) {
            if(this.orders.get(i).toString().equals(itemString))
                return i;
        }
        return MISS;
    }

    /**
     * Convert an order at an index to a string.
     * @param index - index of the order
     * @return orderString - toString of the order
     */
    public String convert(int index) {
        Order order = this.orders.get(index);
        return order.toString();
    }

    /**
     * Creates a readable string for the StoreOrders object.
     * @return storeOrdersString - StoreOrders object in a readable string
     */
    @Override
    public String toString() {
        String ordersString = "";
        for(int i = this.orders.size() - 1; i >= 0; i--) {
            ordersString += this.orders.get(i).toString() + "\n";
        }
        return ordersString;
    }

}
