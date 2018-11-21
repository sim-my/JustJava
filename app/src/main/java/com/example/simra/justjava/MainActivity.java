package com.example.simra.justjava;


        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.CheckBox;
        import android.widget.TextView;
        import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    int quantity = 0;

    public void submitOrder(View view) {
        boolean whippedCream = whippedCreamCheck();
        boolean chocolateChips = chocolateChipsCheck();
        String nameOfPerson = inputName();
        String pricemessage = generateSummary(whippedCream, chocolateChips, nameOfPerson);
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("message/rfc822");
        String subject = "Coffee Order To " + nameOfPerson;
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "Maximum order of the coffee is 100.", Toast.LENGTH_SHORT).show();

        } else {
            quantity = quantity + 1;
            display(quantity);
        }

    }


    public void decrement(View view) {
        if (quantity == 0) {
            Toast.makeText(this, "Minimum order of the coffee is 0 cup.", Toast.LENGTH_SHORT).show();
        } else {
            quantity = quantity - 1;
            display(quantity);

        }

    }

    /**
     * This method calculates the price of the cups of the coffee.
     */
    private int calculatePrice() {

        int basePrice=5;
        if (whippedCreamCheck()) {
            basePrice = basePrice+ 3;
        }
        if (chocolateChipsCheck()) {
            basePrice = basePrice+2;
        }
        return basePrice * quantity;
    }

    /**
     * This method generates the summary of the customers.
     */
    private String generateSummary(boolean whippedCream, boolean chocolateChips, String name) {
        int price = calculatePrice();
        String priceMessage =getString(R.string.customerName,name);
        priceMessage = priceMessage + "\n"+getString(R.string.needWhippedCream) + whippedCream;
        priceMessage = priceMessage + "\n"+getString(R.string.needChocolatChips) + chocolateChips;
        priceMessage = priceMessage + "\n"+getString(R.string.qty) + quantity;
        priceMessage = priceMessage + "\n"+getString(R.string.total) + price;
        priceMessage = priceMessage + "\n"+getString(R.string.thank);
        return priceMessage;
    }

    private String inputName() {
        TextView name = (TextView) findViewById(R.id.inputName);
        return name.getText().toString();
    }

    private boolean whippedCreamCheck() {
        CheckBox whippedCream = (CheckBox) findViewById(R.id.whippedCream);
        return whippedCream.isChecked();
    }

    private boolean chocolateChipsCheck() {
        CheckBox hasChocolateChips = (CheckBox) findViewById((R.id.chocolateChips));
        return hasChocolateChips.isChecked();
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}
