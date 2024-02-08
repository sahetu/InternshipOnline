package internship.online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

public class UserRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<UserList> arrayList;
    SQLiteDatabase sqlDb;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_recycler);
        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        sqlDb = openOrCreateDatabase("InternshipOn.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(100))";
        sqlDb.execSQL(tableQuery);

        recyclerView = findViewById(R.id.user_recyler_view);

        //Display Data In List
        //recyclerView.setLayoutManager(new LinearLayoutManager(UserRecyclerActivity.this));

        //Display Data In Grid
        //recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        //Display Data In Grid
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        String selectQuery = "SELECT * FROM USERS";
        Cursor cursor = sqlDb.rawQuery(selectQuery,null);
        if(cursor.getCount()>0){
            arrayList = new ArrayList<>();
            while (cursor.moveToNext()){
                String sUserId = cursor.getString(0);
                String sUsername = cursor.getString(1);
                String sName = cursor.getString(2);
                String sEmail = cursor.getString(3);
                String sContact = cursor.getString(4);
                String sGender = cursor.getString(6);
                String sCity = cursor.getString(7);

                UserList list = new UserList();
                list.setId(sUserId);
                list.setUserName(sUsername);
                list.setName(sName);
                list.setEmail(sEmail);
                list.setContact(sContact);
                list.setGender(sGender);
                list.setCity(sCity);

                arrayList.add(list);
            }
            UserRecyclerAdapter adapter = new UserRecyclerAdapter(UserRecyclerActivity.this, arrayList);
            recyclerView.setAdapter(adapter);
        }
    }
}