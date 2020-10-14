package com.example.grooz1;


import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity;

    /**
     * this app displays an order from to order coffee.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * this method is called when the plus button is clicked
     */
    public void increment(View view) {
        if (quantity == 100) {
            // show an error massage as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //Exit this mesthod early because there's nothing left to do
            return;
        }
        quantity++;
        display(quantity);
    }

    /**
     * this method is called when the plus button is clicked
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // show an error massage as a toast
            Toast.makeText(this, "You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            //Exit this mesthod early because there's nothing left to do
            return;
        }
        quantity--;
        display(quantity);
    }

    /**
     * this method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //find the user,s name
        EditText namefield = (EditText) findViewById(R.id.name_field);
        String name = namefield.getText().toString();
        //figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        //figure out if the user wants chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, haschocolate);
        String priceMassage = createOrderSummary(name, price, hasWhippedCream, haschocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //only email apps should handle this
        intent.putExtra(intent.EXTRA_SUBJECT, "Just Java Order For " + name);
        intent.putExtra(intent.EXTRA_TEXT, priceMassage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        // displaymassage(priceMassage);

    }

    /**
     * this method calculatePrice of the order
     *
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @return text summary
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * Create summary of the order
     *
     * @param name            of the customer
     * @param price           of the order
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMassage = "Name: " + name;
        priceMassage += "\nAdd Chocolate?: " + addChocolate;
        priceMassage += "\nAdd whipped cream?: " + addWhippedCream;
        priceMassage += "\nQuantity: " + quantity;
        priceMassage += "\nTotal: $" + price;
        priceMassage += "\n" + getString(R.string.thank_you);
        return priceMassage;
    }

    /**
     * this method display the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quntityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quntityTextView.setText("" + number);
    }
///***
// * this method displays the given text on the screen.
// */
//private void displaymassage(String massage){
//    TextView orderSummaryTextView=(TextView)findViewById(R.id.order_summary_text_view);
//    orderSummaryTextView.setText(massage);
//}
}