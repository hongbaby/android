package com.example.hong.interactive;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        String name = getName();
        int price = calculatePrice(quantity, 5);
        Log.v("MainActivity", "The price is " + price);

        boolean hasWhippedCream = checkCreamCheckbox();
        boolean hasChocolate = checkChocolateCheckbox();
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    private String getName() {
        EditText editText = (EditText) findViewById(R.id.name_field);
        return editText.getText().toString();
    }

    private boolean checkCreamCheckbox() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.cream_checkbox);
        if(checkBox.isChecked())
            return true;
        else
            return false;
    }

    private boolean checkChocolateCheckbox() {
        CheckBox checkBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        if(checkBox.isChecked())
            return true;
        else
            return false;
    }

    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));  //int to string
    }

    public void increment(View view) {
        if(quantity == 100)
            return;

        quantity += 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if(quantity == 0)
            return;

        quantity -= 1;
        displayQuantity(quantity);
    }


    private int calculatePrice(int quantity, int priceOfOneCup) {
        if(checkCreamCheckbox())
            priceOfOneCup += 1;

        if(checkChocolateCheckbox())
            priceOfOneCup += 2;

        return quantity * priceOfOneCup;
    }

    private String createOrderSummary(String name, int price, boolean hasWhippedCream, boolean hasChocolate) {
        return "Name: "+ name + "\nAdd whipped cream? " + hasWhippedCream +"\nAdd chocolate? " + hasChocolate
                +"\nQuantity: " + quantity + "\nTotal: $" + price + "\nThank you!";
    }


}
