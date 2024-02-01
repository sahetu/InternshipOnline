package internship.online;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email,password;
    TextView createAccount;
    SQLiteDatabase sqlDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlDb = openOrCreateDatabase("InternshipOn.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(100))";
        sqlDb.execSQL(tableQuery);

        createAccount = findViewById(R.id.main_create_account);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(MainActivity.this, SignupActivity.class);
            }
        });

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
                    String selectQuery = "SELECT * FROM USERS WHERE (EMAIL='"+email.getText().toString()+"' OR USERNAME='"+email.getText().toString()+"') AND PASSWORD='"+password.getText().toString()+"'";
                    Cursor cursor = sqlDb.rawQuery(selectQuery,null);
                    if(cursor.getCount()>0) {
                    /*System.out.println("Login Successfully");
                    Log.d("RESPONSE","Login Successfully");
                    Log.e("RESPONSE","Login Successfully");
                    Log.w("RESPONSE","Login Successfully");*/
                        //Toast.makeText(MainActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                        new CommonMethod(MainActivity.this, "Login Successfully");
                        //Snackbar.make(view,"Login Successfully",Snackbar.LENGTH_SHORT).show();
                        new CommonMethod(view, "Login Successfully");

                        while (cursor.moveToNext()){
                            String sUserId = cursor.getString(0);
                            String sUsername = cursor.getString(1);
                            String sName = cursor.getString(2);
                            String sEmail = cursor.getString(3);
                            String sContact = cursor.getString(4);
                            String sPassword = cursor.getString(5);
                            String sGender = cursor.getString(6);
                            String sCity = cursor.getString(7);

                            Log.d("RESPONSE_USER",sUserId+"___"+sGender);

                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("EMAIL", sEmail);
                            bundle.putString("PASSWORD", password.getText().toString());

                            intent.putExtras(bundle);
                            startActivity(intent);

                        }

                        //new CommonMethod(MainActivity.this, DashboardActivity.class);
                    }
                    else{
                        new CommonMethod(MainActivity.this,"Login Unsuccessfully");
                    }
                }
            }
        });
    }
}