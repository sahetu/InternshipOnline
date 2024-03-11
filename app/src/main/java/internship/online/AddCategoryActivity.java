package internship.online;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCategoryActivity extends AppCompatActivity {

    CircleImageView imageView,cameraIv;
    EditText category;
    Button submit;

    int REQUEST_CODE_CHOOSE = 123;

    String[] appPermission = {android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public static String[] appPermission33 = {android.Manifest.permission.READ_MEDIA_IMAGES};
    private static final int PERMISSION_REQUEST_CODE = 1240;

    String sSelectedPath = "";

    ApiInterface apiInterface;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        submit = findViewById(R.id.add_category_submit);
        imageView = findViewById(R.id.add_category_image);
        cameraIv = findViewById(R.id.add_category_camera);
        category = findViewById(R.id.add_category_name);

        cameraIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkAndRequestPermission()) {
                    selectImageMethod();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(category.getText().toString().trim().equals("")){
                    category.setError("Category Name Required");
                }
                else if(sSelectedPath==null || sSelectedPath.equals("")){
                    new CommonMethod(AddCategoryActivity.this,"Please Select Image");
                }
                else{
                    pd = new ProgressDialog(AddCategoryActivity.this);
                    pd.setMessage("Please Wait...");
                    pd.setCancelable(false);
                    pd.show();
                    doImageUpload();
                }
            }
        });

    }

    private void doImageUpload() {
        RequestBody namePart = RequestBody.create(MultipartBody.FORM, category.getText().toString());

        File file = new File(sSelectedPath);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("catimage", file.getName(), RequestBody.create(MediaType.parse("image/*"), file));

        Call<GetSignupData> call = apiInterface.addCategoryData(namePart,filePart);
        call.enqueue(new Callback<GetSignupData>() {
            @Override
            public void onResponse(Call<GetSignupData> call, Response<GetSignupData> response) {
                pd.dismiss();
                if(response.code()==200){
                    if(response.body().status){
                        new CommonMethod(AddCategoryActivity.this,response.body().message);
                        onBackPressed();
                    }
                    else{
                        new CommonMethod(AddCategoryActivity.this,response.body().message);
                    }
                }
                else{
                    new CommonMethod(AddCategoryActivity.this,"Server Error Code : "+response.code());
                }
            }

            @Override
            public void onFailure(Call<GetSignupData> call, Throwable t) {
                pd.dismiss();
                new CommonMethod(AddCategoryActivity.this,t.getMessage());
            }
        });
    }

    private void selectImageMethod() {
        FishBun.with(AddCategoryActivity.this)
                .setImageAdapter(new GlideAdapter())
                .setMaxCount(1)
                .isStartInAllView(false)
                .setIsUseDetailView(false)
                .setReachLimitAutomaticClose(true)
                .setSelectCircleStrokeColor(android.R.color.transparent)
                .setActionBarColor(Color.parseColor("#F44336"), Color.parseColor("#F44336"), false)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .startAlbumWithOnActivityResult(REQUEST_CODE_CHOOSE);
    }

    public boolean checkAndRequestPermission() {
        List<String> listPermission = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            for (String perm : appPermission33) {
                if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                    listPermission.add(perm);
                }
            }
            if (!listPermission.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermission.toArray(new String[listPermission.size()]), PERMISSION_REQUEST_CODE);
                return false;
            }
            else{
                return true;
            }
        } else {
            for (String perm : appPermission) {
                if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                    listPermission.add(perm);
                }
            }
            if (!listPermission.isEmpty()) {
                ActivityCompat.requestPermissions(this, listPermission.toArray(new String[listPermission.size()]), PERMISSION_REQUEST_CODE);
                return false;
            }
            else{
                return true;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            HashMap<String, Integer> permissionResult = new HashMap<>();
            int deniedCount = 0;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResult.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }
            if (deniedCount == 0) {
                selectImageMethod();
            } else {
                for (Map.Entry<String, Integer> entry : permissionResult.entrySet()) {
                    String permName = entry.getKey();
                    int permResult = entry.getValue();
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permName)) {
                        /*showDialogPermission("", "This App needs Read External Storage And Location permissions to work whithout and problems.",*/
                        showDialogPermission("", "This App needs Read Storage permissions to work whithout and problems.",
                                "Yes, Grant permissions", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        checkAndRequestPermission();
                                    }
                                },
                                "No, Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finishAffinity();
                                    }
                                }, false);
                    } else {
                        showDialogPermission("", "You have denied some permissions. Allow all permissions at [Setting] > [Permissions]",
                                "Go to Settings", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, "No, Exit app", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        finish();
                                    }
                                }, false);
                        break;
                    }
                }
            }
        }
    }

    public AlertDialog showDialogPermission(String title, String msg, String positiveLable, DialogInterface.OnClickListener positiveOnClickListener, String negativeLable, DialogInterface.OnClickListener negativeOnClickListener, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(isCancelable);
        builder.setMessage(msg);
        builder.setPositiveButton(positiveLable, positiveOnClickListener);
        builder.setNegativeButton(negativeLable, negativeOnClickListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        return alertDialog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_CHOOSE){
            if(data!=null && resultCode==RESULT_OK){
                List<Uri> mSelected = data.getParcelableArrayListExtra(FishBun.INTENT_PATH);
                Log.d("RESPONSE_IMAGE_URI", String.valueOf(mSelected.get(0)));
                sSelectedPath = getImage(mSelected.get(0));
                Log.d("RESPONSE_IMAGE_PATH",sSelectedPath);
                imageView.setImageURI(mSelected.get(0));
            }
            else{
                new CommonMethod(AddCategoryActivity.this,"Something Went Wrong");
            }
        }
    }

    private String getImage(Uri uri) {
        if (uri != null) {
            String path = null;
            String[] s_array = {MediaStore.Images.Media.DATA};
            Cursor c = managedQuery(uri, s_array, null, null, null);
            int id = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (c.moveToFirst()) {
                do {
                    path = c.getString(id);
                }
                while (c.moveToNext());
                //c.close();
                if (path != null) {
                    return path;
                }
            }
        }
        return "";
    }

}