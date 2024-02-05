package internship.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(sp.getString(ConstantSp.ID,"").equals("")){
                    new CommonMethod(SplashActivity.this, MainActivity.class);
                }
                else{
                    new CommonMethod(SplashActivity.this, DashboardActivity.class);
                }
                finish();
            }
        },1000);
    }
}