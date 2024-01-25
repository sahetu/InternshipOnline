package internship.online;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.main_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Login Successfully");
                Log.d("RESPONSE","Login Successfully");
                Log.e("RESPONSE","Login Successfully");
                Log.w("RESPONSE","Login Successfully");

                Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                Snackbar.make(view,"Login Successfully",Snackbar.LENGTH_SHORT).show();
            }
        });
    }
}