package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if(quantity ==100){
            Toast.makeText(this,"You cannot order more than 100 Coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if(quantity == 1){
            Toast.makeText(this,"You cannot have less than 1 Coffee.",Toast.LENGTH_SHORT).show();
            return ;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText text = findViewById(R.id.edit_text);
        String value = text.getText().toString();

        CheckBox whippedCream = findViewById(R.id.whipped_cream_checkBox);
        CheckBox choclate = findViewById(R.id.chocolate_checkbox);
        boolean hasChoclate = choclate.isChecked();
        boolean hasWhippedCream = whippedCream.isChecked();

        int price = calculatePrice(hasChoclate,hasWhippedCream);
        String pricemessage = createOrderSummary(value,price,hasWhippedCream,hasChoclate);

            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this

            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for "+value );
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);

        }


    }

    private String createOrderSummary(String name, int price,boolean addWhippedCream,boolean addchoclate) {
        String pricemessage ="Name : "+name;
        pricemessage += "\nAdd whipped Cream = "+addWhippedCream;
        pricemessage += "\nAdd Choclate = "+addchoclate;
         pricemessage += "\nQuantity : "+ quantity;
          pricemessage +="\nTotal :$" +price +"\nThankYou !";

        return pricemessage;
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice(Boolean AddChoclate,Boolean addWhippedCream) {
        int baseprice =5;
        if(addWhippedCream ){
            baseprice += 1;
        }
        if(AddChoclate){
            baseprice += 2;
        }
        return quantity * baseprice;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}