package internship.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyHolder> {

    Context context;
    ArrayList<NotificationList> arrayList;

    public NotificationAdapter(Context context, ArrayList<NotificationList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public NotificationAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_notification,parent,false);
        return new NotificationAdapter.MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView message,date;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.custom_notification_message);
            date = itemView.findViewById(R.id.custom_notification_date);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.MyHolder holder, int position) {
        holder.message.setText(arrayList.get(position).getMessage());
        holder.date.setText(arrayList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}

