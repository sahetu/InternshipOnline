package internship.online;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;

import java.util.ArrayList;

public class UserSimpleListActivity extends AppCompatActivity {

    ListView listView;
    String[] cityArray = {"Ahmedabad","Vadodara","Surat","Rajkot","Rajasthan","Surendranagar"};

    ArrayList<String> arrayList;

    SQLiteDatabase sqlDb;

    AutoCompleteTextView autoCompleteTextView;
    MultiAutoCompleteTextView multiAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_simple_list);

        sqlDb = openOrCreateDatabase("InternshipOn.db",MODE_PRIVATE,null);
        String tableQuery = "CREATE TABLE IF NOT EXISTS USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME VARCHAR(50),NAME VARCHAR(50),EMAIL VARCHAR(50),CONTACT BIGINT(10),PASSWORD VARCHAR(12),GENDER VARCHAR(6),CITY VARCHAR(100))";
        sqlDb.execSQL(tableQuery);

        listView = findViewById(R.id.user_simple_listview);

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

                arrayList.add(sUserId+"\n"+sUsername+"\n"+sName+"\n"+sEmail+"\n"+sContact+"\n"+sGender+"\n"+sCity);
            }
            ArrayAdapter adapter = new ArrayAdapter(UserSimpleListActivity.this, android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //new CommonMethod(UserSimpleListActivity.this,arrayList.get(i));
                AlertDialog.Builder builder = new AlertDialog.Builder(UserSimpleListActivity.this);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("Selected User Data");
                builder.setMessage(arrayList.get(i));
                builder.setPositiveButton("Dismis", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                /*builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new CommonMethod(UserSimpleListActivity.this,"Negative");
                    }
                });
                builder.setNeutralButton("Rate Us", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new CommonMethod(UserSimpleListActivity.this,"Neutral");
                    }
                });*/
                builder.setCancelable(false);
                builder.show();
            }
        });

        autoCompleteTextView = findViewById(R.id.user_simple_auto);
        ArrayAdapter autoAdapter = new ArrayAdapter(UserSimpleListActivity.this, android.R.layout.simple_list_item_1,cityArray);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(autoAdapter);

        multiAutoCompleteTextView = findViewById(R.id.user_simple_multiauto);
        ArrayAdapter multiAdapter = new ArrayAdapter(UserSimpleListActivity.this, android.R.layout.simple_list_item_1,cityArray);
        multiAutoCompleteTextView.setThreshold(1);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiAutoCompleteTextView.setAdapter(multiAdapter);

    }
}