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
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The StoreOrderActivity class controls the Store Order GUI. It allows the user
 * to review all placed orders and cancel a selected one.
 * @author Shane Hoffman, Michael Li
 */
public class StoreOrderActivity extends AppCompatActivity {
    StoreOrders storeOrders;

    ListView orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_orders);
        this.storeOrders = (StoreOrders) getIntent().getSerializableExtra("STORE") ;
        orders = findViewById(R.id.orders);
        updateText();
        orders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Sets up event listener for cancel order in the ListView and cancels the selected order.
             * @param parent
             * @param view
             * @param position - position of the selected order
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cancelOrder(position);
            }
        });
    }

    /**
     * When an order in the ListView is clicked an alert is sent. If the user clicks
     * yes the selected order is canceled.
     * @param position - position of the selected order
     */
    private void cancelOrder(int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage(getString(R.string.cancelPopup)).setTitle(getString(R.string.cancelPopupTitle));
        alert.setPositiveButton(getString(R.string.Yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                storeOrders.remove((String) orders.getItemAtPosition(position));
                setAlert(getString(R.string.cancelOrderSuccess));
                updateText();
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
     * Updates the ListView to the current store orders.
     */
    private void updateText() {
        ArrayList<String> list = makeArrayList();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        orders.setAdapter(adapter);
    }

    /**
     * Helper method for the updateText method that turns storeOrders into an
     * ArrayList of Strings.
     * @return
     */
    private ArrayList<String> makeArrayList() {
        ArrayList<String> list = new ArrayList<>();
        for(int i = this.storeOrders.getSize() - 1; i >= 0; i--) {
            list.add(this.storeOrders.convert(i));
        }
        return list;
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
     * If the back button is pressed we return storeOrders in an intent.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("STORE", this.storeOrders);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * If the home button is clicked we return storeOrders in an intent.
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