package com.example.payment;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.app.Activity;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Rozarpay extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = "Rozarpay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rozarpay);

        Checkout.preload(getApplicationContext());

        Button button = findViewById(R.id.pay_bttn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });
    }

    public void startPayment() {
        // Instantiate Checkout
        Checkout checkout = new Checkout();

        // Set the key ID for the Checkout instance
        checkout.setKeyID("<YOUR_KEY_ID>");

        // Set your logo here
        checkout.setImage(R.drawable.logo);

        // Reference to current activity
        final Activity activity = this;

        // Pass your payment options to the Razorpay Checkout as a JSONObject
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "http://example.com/image/rzp.jpg");
            options.put("order_id", "order_DBJOWzybf0sJbb"); // from response of step 3
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000"); // pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact", "9988776655");

            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d(TAG, "Payment successful: " + s);
    }

    @Override
    public void onPaymentError(int code, String response) {
        Log.e(TAG, "Payment failed: " + code + " " + response);
    }
}
