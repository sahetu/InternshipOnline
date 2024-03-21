package internship.online;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    FloatingActionButton add;
    RecyclerView recyclerView;

    ArrayList<NotificationList> arrayList;

    ApiInterface apiInterface;
    ProgressDialog pd;
    SharedPreferences sp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        sp = getSharedPreferences(ConstantSp.PREF,MODE_PRIVATE);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        add = findViewById(R.id.notification_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(NotificationActivity.this, SendNotificationActivity.class);
            }
        });

        recyclerView = findViewById(R.id.notification_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(NotificationActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected void onResume() {
        super.onResume();
        pd = new ProgressDialog(NotificationActivity.this);
        pd.setMessage("Please Wait...");
        pd.setCancelable(false);
        pd.show();
        getData();
    }

    private void getData() {
        Call<GetNotificationData> call = apiInterface.getNotificationData(sp.getString(ConstantSp.ID,""));
        call.enqueue(new Callback<GetNotificationData>() {
            @Override
            public void onResponse(Call<GetNotificationData> call, Response<GetNotificationData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        arrayList = new ArrayList<>();
                        for(int i=0;i<response.body().notificationData.size();i++){
                            NotificationList list = new NotificationList();
                            list.setId(response.body().notificationData.get(i).id);
                            list.setMessage(response.body().notificationData.get(i).message);
                            list.setDate(response.body().notificationData.get(i).createdDate);
                            arrayList.add(list);
                        }
                        NotificationAdapter adapter = new NotificationAdapter(NotificationActivity.this,arrayList);
                        recyclerView.setAdapter(adapter);
                    }
                    else{
                        new CommonMethod(NotificationActivity.this,response.body().message);
                    }
                }
                else{
                    new CommonMethod(NotificationActivity.this,"Server Error Code : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetNotificationData> call, Throwable t) {
                pd.dismiss();
                new CommonMethod(NotificationActivity.this,t.getMessage());
            }
        });
    }
}