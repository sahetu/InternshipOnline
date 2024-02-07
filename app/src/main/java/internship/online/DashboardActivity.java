package internship.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    TextView name;
    Button logout,profile,deleteProfile,userList,userCustomList;
    SharedPreferences sp;
    SQLiteDatabase sqlDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        sqlDb = openOrCreateDatabase("InternshipOn.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(100))";
        sqlDb.execSQL(tableQuery);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        //new CommonMethod(DashboardActivity.this,"Hello");

        /*Bundle bundle = getIntent().getExtras();
        String sEmail = bundle.getString("EMAIL");
        String sPassword = bundle.getString("PASSWORD");

        Log.d("RESPONSE",sEmail+"\n"+sPassword);*/

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
                String deleteQuery = "DELETE FROM USERS WHERE USERID='"+sp.getString(ConstantSp.ID,"")+"'";
                sqlDb.execSQL(deleteQuery);
                new CommonMethod(DashboardActivity.this,"Profile Deleted Successfully");

                sp.edit().clear().commit();

                new CommonMethod(DashboardActivity.this, MainActivity.class);
                finish();
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

    }

    @Override
    protected void onResume() {
        super.onResume();
        name.setText(sp.getString(ConstantSp.EMAIL,""));
    }
}