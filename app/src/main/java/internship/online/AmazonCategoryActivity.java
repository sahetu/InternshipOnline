package internship.online;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;

public class AmazonCategoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String[] nameArray = {"Top Mirrorless Cameras","Monitors","Top Selling Dell Keyboard","Best Selling Security","Printers"};
    String[] priceArray = {"Shop Now!","From ₹9999","From ₹229","From ₹29","From ₹10999"};
    int[] imageArray = {R.drawable.camera,R.drawable.monitor,R.drawable.keyboard,R.drawable.quickheal,R.drawable.printer};
    String[] imageDynamicArray = {
            "https://rukminim2.flixcart.com/image/312/312/knyxqq80/dslr-camera/r/y/x/digital-camera-eos-m50-mark-ii-eos-m50-mark-ii-canon-original-imag2gzkexzqhyhu.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/312/312/ko8xtow0/monitor/t/a/y/d24-20-66aekac1in-lenovo-original-imag2qwzazcdmqtb.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/612/612/xif0q/keyboard/j/b/7/-original-imagw4ztgrgm9jby.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/612/612/xif0q/security-software/j/h/l/-original-imagtvhesdvt6qhv.jpeg?q=70",
            "https://rukminim2.flixcart.com/image/612/612/xif0q/printer/s/c/g/-original-imagzyb5kqpfsf2h.jpeg?q=70"
    };

    ArrayList<AmazonCategoryList> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amazon_category);

        recyclerView = findViewById(R.id.amazon_recyclerview);

        //recyclerView.setLayoutManager(new LinearLayoutManager(AmazonCategoryActivity.this));

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));

        arrayList = new ArrayList<AmazonCategoryList>();
        for(int i=0;i<nameArray.length;i++){
            AmazonCategoryList list = new AmazonCategoryList();
            list.setName(nameArray[i]);
            list.setPrice(priceArray[i]);
            list.setImage(imageArray[i]);
            list.setImageDynamic(imageDynamicArray[i]);
            arrayList.add(list);
        }
        AmazonCategoryAdapter adapter = new AmazonCategoryAdapter(AmazonCategoryActivity.this,arrayList);
        recyclerView.setAdapter(adapter);
    }
}