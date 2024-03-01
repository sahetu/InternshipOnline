package internship.online;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    Button login;
    EditText email,password;
    TextView createAccount;
    SQLiteDatabase sqlDb;
    SharedPreferences sp;

    ApiInterface apiInterface;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

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
                    //sqliteLogin(view);
                    if(new ConnectionDetector(MainActivity.this).networkConnected()){
                        //new loginAsync().execute();
                        pd = new ProgressDialog(MainActivity.this);
                        pd.setMessage("Please Wait...");
                        pd.setCancelable(false);
                        pd.show();
                        doLoginRetrofit();
                    }
                    else{
                        new ConnectionDetector(MainActivity.this).networkDisconnected();
                    }
                }
            }
        });
    }

    private void doLoginRetrofit() {
        Call<GetLoginData> call = apiInterface.getLoginData(email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<GetLoginData>() {
            @Override
            public void onResponse(Call<GetLoginData> call, Response<GetLoginData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        new CommonMethod(MainActivity.this,response.body().message);
                        GetLoginData data = response.body();
                        for(int i=0;i<data.userData.size();i++){
                            sp.edit().putString(ConstantSp.ID,data.userData.get(i).userid).commit();
                            sp.edit().putString(ConstantSp.USERNAME,data.userData.get(i).userName).commit();
                            sp.edit().putString(ConstantSp.NAME,data.userData.get(i).name).commit();
                            sp.edit().putString(ConstantSp.EMAIL,data.userData.get(i).email).commit();
                            sp.edit().putString(ConstantSp.CONTACT,data.userData.get(i).contact).commit();
                            sp.edit().putString(ConstantSp.PASSWORD,"").commit();
                            sp.edit().putString(ConstantSp.CITY,data.userData.get(i).city).commit();
                            sp.edit().putString(ConstantSp.GENDER,data.userData.get(i).gender).commit();
                        }

                        new CommonMethod(MainActivity.this, DashboardActivity.class);
                    }
                    else{
                        new CommonMethod(MainActivity.this,response.body().message);
                    }
                }
                else{
                    new CommonMethod(MainActivity.this,"Server Error Code : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetLoginData> call, Throwable t) {
                pd.dismiss();
                new CommonMethod(MainActivity.this,t.getMessage());
                Log.d("RESPONSE_ERROR",t.getMessage());
            }
        });
    }

    private void sqliteLogin(View view) {
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

                sp.edit().putString(ConstantSp.ID,sUserId).commit();
                sp.edit().putString(ConstantSp.USERNAME,sUsername).commit();
                sp.edit().putString(ConstantSp.NAME,sName).commit();
                sp.edit().putString(ConstantSp.EMAIL,sEmail).commit();
                sp.edit().putString(ConstantSp.CONTACT,sContact).commit();
                sp.edit().putString(ConstantSp.PASSWORD,sPassword).commit();
                sp.edit().putString(ConstantSp.CITY,sCity).commit();
                sp.edit().putString(ConstantSp.GENDER,sGender).commit();

                            /*Intent intent = new Intent(MainActivity.this, DashboardActivity.class);

                            Bundle bundle = new Bundle();
                            bundle.putString("EMAIL", sEmail);
                            bundle.putString("PASSWORD", password.getText().toString());

                            intent.putExtras(bundle);
                            startActivity(intent);*/

            }
            new CommonMethod(MainActivity.this, DashboardActivity.class);
        }
        else{
            new CommonMethod(MainActivity.this,"Login Unsuccessfully");
        }
    }

    private class loginAsync extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("email",email.getText().toString());
            hashMap.put("password",password.getText().toString());
            return new MakeServiceCall().MakeServiceCall(ConstantSp.BASE_URL+"login.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getBoolean("Status")){
                    new CommonMethod(MainActivity.this,object.getString("Message"));

                    JSONArray array = object.getJSONArray("UserData");
                    for(int i=0;i<array.length();i++){
                        JSONObject jsonObject = array.getJSONObject(i);

                        sp.edit().putString(ConstantSp.ID,jsonObject.getString("userid")).commit();
                        sp.edit().putString(ConstantSp.USERNAME,jsonObject.getString("userName")).commit();
                        sp.edit().putString(ConstantSp.NAME,jsonObject.getString("name")).commit();
                        sp.edit().putString(ConstantSp.EMAIL,jsonObject.getString("email")).commit();
                        sp.edit().putString(ConstantSp.CONTACT,jsonObject.getString("contact")).commit();
                        sp.edit().putString(ConstantSp.PASSWORD,"").commit();
                        sp.edit().putString(ConstantSp.CITY,jsonObject.getString("city")).commit();
                        sp.edit().putString(ConstantSp.GENDER,jsonObject.getString("gender")).commit();
                    }

                    new CommonMethod(MainActivity.this, DashboardActivity.class);
                }
                else{
                    new CommonMethod(MainActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}