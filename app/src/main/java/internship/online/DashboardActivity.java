package internship.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView name;
    Button logout,profile;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        //new CommonMethod(DashboardActivity.this,"Hello");

        /*Bundle bundle = getIntent().getExtras();
        String sEmail = bundle.getString("EMAIL");
        String sPassword = bundle.getString("PASSWORD");

        Log.d("RESPONSE",sEmail+"\n"+sPassword);*/

        name = findViewById(R.id.dashboard_name);
        name.setText(sp.getString(ConstantSp.EMAIL,""));

        Log.d("DASHBOARD_DATA",
                sp.getString(ConstantSp.ID,"")+"\n"+
                        sp.getString(ConstantSp.USERNAME,"")+"\n"+
                        sp.getString(ConstantSp.NAME,"")+"\n"+
                        sp.getString(ConstantSp.EMAIL,"")+"\n"+
                        sp.getString(ConstantSp.CONTACT,"")+"\n"+
                        sp.getString(ConstantSp.PASSWORD,"")+"\n"+
                        sp.getString(ConstantSp.GENDER,"")+"\n"+
                        sp.getString(ConstantSp.CITY,"")
                );

        logout = findViewById(R.id.dashboard_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*sp.edit().remove(ConstantSp.ID).commit();
                sp.edit().remove(ConstantSp.NAME).commit();
                sp.edit().remove(ConstantSp.EMAIL).commit();*/

                sp.edit().clear().commit();

                new CommonMethod(DashboardActivity.this, MainActivity.class);
                finish();
            }
        });

        profile = findViewById(R.id.dashboard_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, ProfileActivity.class);
            }
        });

    }
}