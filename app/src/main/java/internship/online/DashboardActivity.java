package internship.online;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    TextView name;
    Button category,logout,profile,deleteProfile,userList,userCustomList,userRecycler,amazonRecycler,activityFragment,tabDemo,navigationDrawer,bottomNav,currentLocation,razorpayDemo;
    SharedPreferences sp;
    SQLiteDatabase sqlDb;

    ApiInterface apiInterface;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        sqlDb = openOrCreateDatabase("InternshipOn.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(100))";
        sqlDb.execSQL(tableQuery);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        //new CommonMethod(DashboardActivity.this,"Hello");

        /*Bundle bundle = getIntent().getExtras();
        String sEmail = bundle.getString("EMAIL");
        String sPassword = bundle.getString("PASSWORD");

        Log.d("RESPONSE",sEmail+"\n"+sPassword);*/

        category = findViewById(R.id.dashboard_category);
        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, CategoryActivity.class);
            }
        });

        name = findViewById(R.id.dashboard_name);

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

        razorpayDemo = findViewById(R.id.dashboard_razorpay);
        razorpayDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, RazorpayDemoActivity.class);
            }
        });

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

        deleteProfile = findViewById(R.id.dashboard_delete_profile);

        deleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //deleteSqlite();
                if(new ConnectionDetector(DashboardActivity.this).networkConnected()){
                    //new deleteAsync().execute();
                    pd = new ProgressDialog(DashboardActivity.this);
                    pd.setMessage("Please Wait...");
                    pd.setCancelable(false);
                    pd.show();
                    doDeleteRetrofit();
                }
                else{
                    new ConnectionDetector(DashboardActivity.this).networkDisconnected();
                }
            }
        });

        userList = findViewById(R.id.dashboard_user_list);
        userList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, UserSimpleListActivity.class);
            }
        });

        userCustomList = findViewById(R.id.dashboard_custom_user_list);
        userCustomList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, UserCustomListActivity.class);
            }
        });

        userRecycler = findViewById(R.id.dashboard_user_recycler);
        userRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, UserRecyclerActivity.class);
            }
        });

        amazonRecycler = findViewById(R.id.dashboard_amazon_category);
        amazonRecycler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, AmazonCategoryActivity.class);
            }
        });

        activityFragment = findViewById(R.id.dashboard_activity_fragment);
        activityFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this,ActivityToFragmentActivity.class);
            }
        });

        tabDemo = findViewById(R.id.dashboard_tab);
        tabDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this,TabDemoActivity.class);
            }
        });

        navigationDrawer = findViewById(R.id.dashboard_nav);
        navigationDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, NavDemoActivity.class);
            }
        });

        bottomNav = findViewById(R.id.dashboard_bottom_nav);
        bottomNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this, BottomNavActivity.class);
            }
        });

        currentLocation = findViewById(R.id.dashboard_google_map);
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(DashboardActivity.this,MapsActivity.class);
            }
        });

    }

    private void doDeleteRetrofit() {
        Call<GetSignupData> call = apiInterface.deleteProfileData(sp.getString(ConstantSp.ID,""));
        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        new CommonMethod(DashboardActivity.this,response.body().message);
                        sp.edit().clear().commit();

                        new CommonMethod(DashboardActivity.this, MainActivity.class);
                        finish();
                    }
                    else{
                        new CommonMethod(DashboardActivity.this,response.body().message);
                    }
                }
                else{
                    new CommonMethod(DashboardActivity.this,"Server Error Code : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                new CommonMethod(DashboardActivity.this,t.getMessage());
            }
        });
    }

    private void deleteSqlite() {
        String deleteQuery = "DELETE FROM USERS WHERE USERID='"+sp.getString(ConstantSp.ID,"")+"'";
        sqlDb.execSQL(deleteQuery);
        new CommonMethod(DashboardActivity.this,"Profile Deleted Successfully");

        sp.edit().clear().commit();

        new CommonMethod(DashboardActivity.this, MainActivity.class);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        name.setText(sp.getString(ConstantSp.EMAIL,""));
    }

    private class deleteAsync extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(DashboardActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("userId",sp.getString(ConstantSp.ID,""));
            return new MakeServiceCall().MakeServiceCall(ConstantSp.BASE_URL+"deleteProfile.php",MakeServiceCall.POST,hashMap);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                if(object.getBoolean("Status")){
                    new CommonMethod(DashboardActivity.this,object.getString("Message"));
                    sp.edit().clear().commit();

                    new CommonMethod(DashboardActivity.this, MainActivity.class);
                    finish();
                }
                else{
                    new CommonMethod(DashboardActivity.this,object.getString("Message"));
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}