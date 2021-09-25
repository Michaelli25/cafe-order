package com.example.cafe;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


/**
 * The MainActivity class controls the Main Menu GUI. It contains
 * four buttons which open separate GUI's. Ordering donuts, ordering coffee,
 * viewing the current order, and viewing the store's orders.
 * @author Shane Hoffman, Michael Li
 */
public class MainActivity extends AppCompatActivity {
    private StoreOrders storeOrders;
    private Order order;

    Button coffeeButton, donutButton, currentOrderButton, storeOrdersButton;

    private static final int coffeeCode = 1;
    private static final int donutCode = 2;
    private static final int orderCode = 3;
    private static final int storeCode = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.storeOrders = new StoreOrders();
        this.order = new Order(this.storeOrders.getSize() + 1);
        coffeeButton = (Button)findViewById(R.id.coffeeButton);
        donutButton = (Button)findViewById(R.id.donutButton);
        currentOrderButton = (Button)findViewById(R.id.currentOrderButton);
        storeOrdersButton = (Button)findViewById(R.id.storeOrdersButton);
        coffeeButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets up event listener for coffee order button and opens the coffee activity.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, OrderCoffeeActivity.class);
                intent.putExtra("ORDER", order);
                startActivityForResult(intent, coffeeCode);
            }
        });

        donutButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets up event listener for donut order button and opens the donut activity.
             * @param v
             */
            @Override
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this, OrderDonutsActivity.class);
                intent.putExtra("ORDER", order);
                startActivityForResult(intent, donutCode);
            }
        });

        currentOrderButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets up event listener for current order button and opens the my order activity.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyOrderActivity.class);
                intent.putExtra("ORDER", order);
                intent.putExtra("STORE", storeOrders);
                startActivityForResult(intent, orderCode);
            }
        });

        storeOrdersButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets up event listener for store orders button and opens the store orders activity.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StoreOrderActivity.class);
                intent.putExtra("STORE", storeOrders);
                startActivityForResult(intent, storeCode);
            }
        });
    }


    /**
     * Retrieves results from the other activities when they finish. This allows for the
     * updating of the current order and store orders.
     * @param requestCode - code to identify the child activity
     * @param resultCode - result code from child activity
     * @param data - intent returned
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode == coffeeCode || requestCode == donutCode) {
                this.order = (Order) data.getSerializableExtra("ORDER");
            }
            else if(requestCode == orderCode) {
                this.order = (Order) data.getSerializableExtra("ORDER");
                this.storeOrders = (StoreOrders) data.getSerializableExtra("STORE");
            }
            else if(requestCode == storeCode) {
                this.storeOrders = (StoreOrders) data.getSerializableExtra("STORE");
            }
        }
    }

}