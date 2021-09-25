package com.example.cafe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * The OrderDonutsActivity class controls the GUI for ordering donuts. It allows for users
 * to add and remove donuts while seeing a dynamic subtotal. There are nine donut flavors.
 * @author Shane Hoffman, Michael Li
 */
public class OrderDonutsActivity extends AppCompatActivity implements View.OnClickListener{
    private Order order;
    private Spinner spinner3;
    private Spinner spinner2;
    private TextView subtotalText2;
    private Button orderDonutsBut, removeDonutsBut;
    private static final DecimalFormat df = new DecimalFormat("####0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_donuts);
        this.order = (Order) getIntent().getSerializableExtra("ORDER");
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        orderDonutsBut = findViewById(R.id.addDonuts);
        removeDonutsBut = findViewById(R.id.removeDonuts);
        orderDonutsBut.setOnClickListener(this);
        removeDonutsBut.setOnClickListener(this);
        subtotalText2 = findViewById(R.id.subtotalText2);
        updateSubtotal();
    }

    /**
     * A listener for button clicks on the GUI. It's either for adding
     * donuts or removing donuts.
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addDonuts:
                addDonut();
                break;
            case R.id.removeDonuts:
                removeDonut();
        }
    }

    /**
     * Updates the subtotal of the current order.
     */
    public void updateSubtotal() {
        order.calculate();
        subtotalText2.setText("$" + df.format(order.getSubtotal()));
    }

    /**
     * Creates a Toast message to display to the user.
     * @param text - text displayed in the message
     */
    public void setAlert(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Adds a donut to the current order.
     */
    public void addDonut() {
        String selectedFlavor = (String) spinner3.getSelectedItem();
        Donut donut = new Donut((Integer.parseInt((String) spinner2.getSelectedItem())), selectedFlavor);
        this.order.add(donut);
        updateSubtotal();
        setAlert(getString(R.string.AddDonut));
    }

    /**
     * Removes a donut from the current order or decreases the quantity.
     */
    public void removeDonut() {
        String selectedFlavor = (String) spinner3.getSelectedItem();
        Donut donut = new Donut((Integer.parseInt((String) spinner2.getSelectedItem())), selectedFlavor);
        if(this.order.remove(donut)) {
            updateSubtotal();
            setAlert(getString(R.string.RemoveDonut));
        }
        else
            setAlert(getString(R.string.RemoveFail));
    }

    /**
     * If the back button is pressed we return order in an intent.
     */
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("ORDER", order);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * If the home button is clicked we return order in an intent.
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