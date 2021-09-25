package com.example.cafe;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The MyOrderActivity class controls the My Orders GUI. It allows the user
 * to review their current order and remove a selected item. It can also
 * place the current order and add it to the store orders.
 * @author Shane Hoffman, Michael Li
 */
public class MyOrderActivity extends AppCompatActivity {
    private StoreOrders storeOrders;
    private Order order;
    private static final DecimalFormat df = new DecimalFormat("####0.00");
    private static final double TAX = .06625;

    Button placeOrder;
    ListView items;
    private TextView subtotalText3, taxText, totalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        this.order = (Order) getIntent().getSerializableExtra("ORDER");
        this.storeOrders = (StoreOrders) getIntent().getSerializableExtra("STORE") ;
        items = findViewById(R.id.items);
        updateText();

        subtotalText3 = findViewById(R.id.subtotalText3);
        taxText = findViewById(R.id.taxText);
        totalText = findViewById(R.id.totalText);
        placeOrder = findViewById(R.id.placeOrder);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            /**
             * Sets up event listener for place order button and calls the placeOrder method.
             * @param v
             */
            @Override
            public void onClick(View v) {
                placeOrder();
            }
        });
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Sets up event listener for remove item in the ListView and removes the selected item.
             * @param parent
             * @param view
             * @param position - position of the selected item
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                removeItem(position);
            }
        });
        updateTotals();
    }

    /**
     * Updates the subtotal, tax, and total of the current order.
     */
    private void updateTotals() {
        this.order.calculate();
        double subtotal = order.getSubtotal();
        subtotalText3.setText("$" + df.format(subtotal));
        taxText.setText("$" + df.format(subtotal * TAX));
        totalText.setText("$" + df.format(subtotal + subtotal * TAX));
    }

    /**
     * Updates the ListView to the current order.
     */
    private void updateText() {
        ArrayList<String> list = makeArrayList();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        items.setAdapter(adapter);
    }

    /**
     * Helper method for the updateText method that turns order into an
     * ArrayList of Strings.
     * @return
     */
    private ArrayList<String> makeArrayList() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = 0; i < this.order.getSize(); i++) {
            list.add(this.order.convert(i));
        }
        return list;
    }

    /**
     * When an item in the ListView is clicked an alert is sent. If the user clicks
     * yes the selected item is removed.
     * @param position - position of the selected item
     */
    private void removeItem(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(getString(R.string.deleteMessage)).setTitle(R.string.deleteTitle);
        alert.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                order.remove((String) items.getItemAtPosition(position));
                setAlert(getString(R.string.removeOrderSuccess));
                updateText();
                updateTotals();
            }
        });
        alert.setNegativeButton(getString(R.string.No), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();

    }

    /**
     * Places the current order adding it to the storeOrders.
     */
    private void placeOrder() {
        if(this.order.getSize() == 0)
            setAlert(getString(R.string.placeOrderFail));
        else {
            this.storeOrders.add(this.order);
            this.order = new Order(this.storeOrders.getSize() + 1);
            setAlert(getString(R.string.placeOrderSuccess));
            updateText();
            updateTotals();
        }
    }

    /**
     * Creates a Toast message to display to the user.
     * @param text - text displayed in the message
     */
    private void setAlert(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * If the back button is pressed we return order and StoreOrders in an intent.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("ORDER", this.order);
        intent.putExtra("STORE", this.storeOrders);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * If the home button is clicked we return order and StoreOrders in an intent.
     * @param item
     * @return true if home button clicked, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}