package internship.online;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        //new CommonMethod(DashboardActivity.this,"Hello");

        Bundle bundle = getIntent().getExtras();
        String sEmail = bundle.getString("EMAIL");
        String sPassword = bundle.getString("PASSWORD");

        Log.d("RESPONSE",sEmail+"\n"+sPassword);

        name = findViewById(R.id.dashboard_name);
        name.setText(sEmail);

    }
}