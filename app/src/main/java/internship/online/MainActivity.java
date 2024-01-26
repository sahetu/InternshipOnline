package internship.online;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.main_username);
        password = findViewById(R.id.main_password);

        login = findViewById(R.id.main_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.getText().toString().trim().equals("")){
                    email.setError("Email Id Required");
                }
                else if(password.getText().toString().trim().equals("")){
                    password.setError("Password Required");
                }
                else if(password.getText().toString().trim().length()<6){
                    password.setError("Min. 6 Char Password Required");
                }
                else {
                    /*System.out.println("Login Successfully");
                    Log.d("RESPONSE","Login Successfully");
                    Log.e("RESPONSE","Login Successfully");
                    Log.w("RESPONSE","Login Successfully");*/
                    //Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                    new CommonMethod(MainActivity.this, "Login Successfully");
                    //Snackbar.make(view,"Login Successfully",Snackbar.LENGTH_SHORT).show();
                    new CommonMethod(view, "Login Successfully");
                    Intent intent = new Intent(MainActivity.this,DashboardActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("EMAIL",email.getText().toString());
                    bundle.putString("PASSWORD",password.getText().toString());

                    intent.putExtras(bundle);
                    startActivity(intent);

                    //new CommonMethod(MainActivity.this, DashboardActivity.class);
                }
            }
        });
    }
}