package com.example.cafe;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * The OrderCoffeeActivity class controls the GUI for ordering coffee. It allows for users
 * to add and remove coffees while seeing a dynamic subtotal. There are coffee
 * sizes, quantity, and add-ins.
 * @author Shane Hoffman, Michael Li
 */
public class OrderCoffeeActivity extends AppCompatActivity implements View.OnClickListener {
    public Order order;
    private RadioButton shortSize, tall, grande, venti;
    private CheckBox cream, syrup, milk, caramel, whipped;
    Spinner spinner;
    private TextView subtotalText;
    private static final DecimalFormat df = new DecimalFormat("####0.00");
    private Button orderButton, removeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_coffee);
        this.order = (Order) getIntent().getSerializableExtra("ORDER");
        shortSize = findViewById(R.id.shortSize);
        tall = findViewById(R.id.tall);
        grande = findViewById(R.id.grande);
        venti = findViewById(R.id.venti);
        cream = findViewById(R.id.cream);
        syrup = findViewById(R.id.syrup);
        milk = findViewById(R.id.milk);
        caramel = findViewById(R.id.caramel);
        whipped = findViewById(R.id.whipped);
        spinner = findViewById(R.id.spinner);
        subtotalText = findViewById(R.id.subtotalText);
        orderButton = findViewById(R.id.coffeeOrderButton);
        removeButton = findViewById(R.id.removeCoffeeButton);
        orderButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);
        updateSubtotal();
    }

    /**
     * A listener for button clicks on the GUI. It's either for adding
     * donuts or removing donuts.
     * @param view
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.coffeeOrderButton:
                addCoffee();
                break;
            case R.id.removeCoffeeButton:
                removeCoffee();
        }
    }

    /**
     * Gets the selected size of the coffee.
     * @return sizeString - size of coffee
     */
    public String getSize() {
        if(shortSize.isChecked())
            return getString(R.string.ShortString);
        if(tall.isChecked())
            return getString(R.string.TallString);
        if(venti.isChecked())
            return getString(R.string.VentiString);
        else
            return getString(R.string.GrandeString);
    }

    /**
     * Creates a Toast message to display to the user.
     * @param text - text displayed in the message
     */
    public void setAlert(String text){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /**
     * Updates the subtotal of the current order.
     */
    public void updateSubtotal() {
        this.order.calculate();
        subtotalText.setText("$" + df.format(order.getSubtotal()));
    }

    /**
     * Add selected addins to coffee.
     * @param coffee - coffee to add addins to
     */
    public void addAddins(Coffee coffee) {
        if(cream.isChecked())
            coffee.add(Addins.CREAM);
        if(milk.isChecked())
            coffee.add(Addins.MILK);
        if(syrup.isChecked())
            coffee.add(Addins.SYRUP);
        if(caramel.isChecked())
            coffee.add(Addins.CARAMEL);
        if(whipped.isChecked())
            coffee.add(Addins.WHIPPED_CREAM);
    }

    /**
     * Adds coffee to the current order.
     */
    public void addCoffee(){
        int quantity = Integer.parseInt((String) spinner.getSelectedItem());
        Coffee coffee = new Coffee(quantity, getSize());
        addAddins(coffee);
        this.order.add(coffee);
        updateSubtotal();
        setAlert(getString(R.string.AddCoffee));
    }

    /**
     * Removes coffee from the current order or decreases the quantity.
     */
    public void removeCoffee(){
        int quantity = Integer.parseInt((String) spinner.getSelectedItem());
        Coffee coffee = new Coffee(quantity, getSize());
        addAddins(coffee);
        if(this.order.remove(coffee)) {
            setAlert(getString(R.string.RemoveCoffee));
            updateSubtotal();
        }
        else
            setAlert(getString(R.string.RemoveFailCoffee));
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