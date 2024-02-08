package internship.online;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class UserDetailActivity extends AppCompatActivity {

    TextView name,gender,email,contact,city;
    SharedPreferences sp;

    SQLiteDatabase sqlDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        sqlDb = openOrCreateDatabase("InternshipOn.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(100))";
        sqlDb.execSQL(tableQuery);

        name = findViewById(R.id.user_detail_name);
        gender = findViewById(R.id.user_detail_gender);
        email = findViewById(R.id.user_detail_email);
        contact = findViewById(R.id.user_detail_contact);
        city = findViewById(R.id.user_detail_city);

        /*name.setText(sp.getString(ConstantSp.USER_DETAIL_NAME,""));
        gender.setText("("+sp.getString(ConstantSp.USER_DETAIL_GENDER,"")+")");
        email.setText(sp.getString(ConstantSp.USER_DETAIL_EMAIL,""));
        contact.setText(sp.getString(ConstantSp.USER_DETAIL_CONTACT,""));
        city.setText(sp.getString(ConstantSp.USER_DETAIL_CITY,""));*/

        String selectQuery = "SELECT * FROM USERS WHERE USERID='"+sp.getString(ConstantSp.USER_DETAIL_ID,"")+"'";
        Cursor cursor = sqlDb.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                String sName = cursor.getString(2);
                String sEmail = cursor.getString(3);
                String sContact = cursor.getString(4);
                String sGender = cursor.getString(6);
                String sCity = cursor.getString(7);

                name.setText(sName);
                email.setText(sEmail);
                contact.setText(sContact);
                city.setText(sCity);
                gender.setText("("+sGender+")");

            }
        }

    }
}