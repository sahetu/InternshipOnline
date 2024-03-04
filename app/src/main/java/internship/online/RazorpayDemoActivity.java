package internship.online;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONObject;

public class RazorpayDemoActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    EditText amount;
    Button payNow;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_demo);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        amount = findViewById(R.id.razorpay_demo_amount);
        payNow = findViewById(R.id.razorpay_pay_now);

        payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(amount.getText().toString().trim().equals("")){
                    amount.setError("Amount Required");
                }
                else if(Integer.parseInt(amount.getText().toString().trim())<=0){
                    amount.setError("Valid Amount Required");
                }
                else{
                    startPayment();
                }
            }
        });

    }

    private void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_xsiOz9lYtWKHgF");

        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.icon);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", getResources().getString(R.string.app_name));
            options.put("description", "Reference No. #123456");
            options.put("image", R.mipmap.ic_launcher);
            options.put("send_sms_hash", true);
            options.put("allow_rotation", true);
            options.put("currency", "INR");
            options.put("amount", String.valueOf(Integer.parseInt(amount.getText().toString())*100));//pass amount in currency subunits
            JSONObject preFill = new JSONObject();
            preFill.put("email", sp.getString(ConstantSp.EMAIL, ""));
            preFill.put("contact", sp.getString(ConstantSp.CONTACT, ""));

            options.put("prefill", preFill);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("RESPONSE_RAZORPAY_CATCH", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RazorpayDemoActivity.this);
        builder.setTitle("Payment Success");
        builder.setMessage("Transaction Id : "+s);
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RazorpayDemoActivity.this);
        builder.setTitle("Payment Failed");
        builder.setMessage("Error : "+s);
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}