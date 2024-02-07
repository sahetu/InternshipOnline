package internship.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    Context context;
    ArrayList<UserList> arrayList;

    public CustomListAdapter(Context context, ArrayList<UserList> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_list,null);

        TextView name = view.findViewById(R.id.custom_list_name);
        TextView gender = view.findViewById(R.id.custom_list_gender);
        TextView email = view.findViewById(R.id.custom_list_email);
        TextView contact = view.findViewById(R.id.custom_list_contact);
        TextView city = view.findViewById(R.id.custom_list_city);

        name.setText(arrayList.get(i).getName());
        gender.setText("("+arrayList.get(i).getGender()+")");
        email.setText(arrayList.get(i).getEmail());
        contact.setText(arrayList.get(i).getContact());
        city.setText(arrayList.get(i).getCity());

        return view;
    }
}
