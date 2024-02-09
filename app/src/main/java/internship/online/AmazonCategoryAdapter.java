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

public class AmazonCategoryAdapter extends RecyclerView.Adapter<AmazonCategoryAdapter.MyHolder> {

    Context context;
    ArrayList<AmazonCategoryList> arrayList;

    public AmazonCategoryAdapter(Context context, ArrayList<AmazonCategoryList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_amazon_category,parent,false);
        return new MyHolder(view);
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        TextView name,price;
        ImageView imageView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.custom_amazon_category_name);
            price = itemView.findViewById(R.id.custom_amazon_category_price);
            imageView = itemView.findViewById(R.id.custom_amazon_category_image);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.name.setText(arrayList.get(position).getName());
        holder.price.setText(arrayList.get(position).getPrice());
        //holder.imageView.setImageResource(arrayList.get(position).getImage());

        Glide.with(context).load(arrayList.get(position).getImageDynamic()).placeholder(R.drawable.icon).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
