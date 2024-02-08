package internship.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserRecyclerAdapter extends RecyclerView.Adapter<UserRecyclerAdapter.MyHolder> {

    Context context;
    ArrayList<UserList> arrayList;

    public UserRecyclerAdapter(Context context, ArrayList<UserList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_card_recycler,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name,email,contact,gender,city;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.custom_card_recycler_name);
            email = itemView.findViewById(R.id.custom_card_recycler_email);
            contact = itemView.findViewById(R.id.custom_card_recycler_contact);
            gender = itemView.findViewById(R.id.custom_card_recycler_gender);
            city = itemView.findViewById(R.id.custom_card_recycler_city);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.email.setText(arrayList.get(position).getEmail());
        holder.contact.setText(arrayList.get(position).getContact());
        holder.city.setText(arrayList.get(position).getCity());
        holder.gender.setText("("+arrayList.get(position).getGender()+")");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
