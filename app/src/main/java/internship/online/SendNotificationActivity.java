package internship.online;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendNotificationActivity extends AppCompatActivity {

    EditText message;
    Button submit;

    ApiInterface apiInterface;
    ProgressDialog pd;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        submit = findViewById(R.id.send_notification_submit);
        message = findViewById(R.id.send_notification_message);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(message.getText().toString().trim().equals("")){
                    message.setError("Message Required");
                }
                else{
                    pd = new ProgressDialog(SendNotificationActivity.this);
                    pd.setMessage("Please Wait...");
                    pd.setCancelable(false);
                    pd.show();
                    sendMessage();
                }
            }
        });

    }

    private void sendMessage() {
        Call<GetSignupData> call = apiInterface.sendNotificationData(message.getText().toString());
        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        new CommonMethod(SendNotificationActivity.this,response.body().message);
                        onBackPressed();
                    }
                    else{
                        new CommonMethod(SendNotificationActivity.this,response.body().message);
                    }
                }
                else{
                    new CommonMethod(SendNotificationActivity.this,"Server Error Code : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                new CommonMethod(SendNotificationActivity.this,t.getMessage());
            }
        });
    }
}