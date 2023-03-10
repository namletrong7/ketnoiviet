package com.hien.ketnoiviet.Discover;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.Others.PostDetail;
import com.hien.ketnoiviet.Personal.UnapprovedPosts;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.DistrictAdapter;
import com.hien.ketnoiviet.adapter.ProvinceAdapter;
import com.hien.ketnoiviet.adapter.WardAdapter;
import com.hien.ketnoiviet.model.District;
import com.hien.ketnoiviet.model.Province;
import com.hien.ketnoiviet.model.Ward;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostActivity extends AppCompatActivity {

   // Acrtiviy  post b??i vi???t

    //region Address Khai b??o

    TextView province_post,ward_post,district_post, province_close_post,district_close_post,ward_close_post;
    // khai b??o c??c frame ch??? t???nh huy???n x??
    FrameLayout framelayout_province;// frame laytu
    FrameLayout framelayout_district;
    FrameLayout framelayout_ward;

    //--------------------------------------
    // listView hi???n th??? danh s??ch t???nh
    ListView listview_province;
    // danh d??ch tinnh
    ArrayList<Province> mangprovince;
    // khai b??o adapter cho listView t???nh
    ProvinceAdapter provinceAdapter;
    int     idtinh   = 0;
    String  nametinh = "";
    String  codetinh = "";
    int     id_tinh = 0;
//---------------------------------------
    // listView ch??? danh s??ch huy???n
    ListView listview_district;
    // danh s??ch huy???n
    ArrayList<District> mangdistrict;
    // khai b??o adapter huy???n
    DistrictAdapter districtAdapter;
    int     idhuyen   = 0;
    String  namehuyen = "";
    String  prefixhuyen = "";
    int     provinceidhuyen = 0;
    int     id_huyen = 0;
 ///---------------------------
    // khai b??o
    ListView listview_ward;
    ArrayList<Ward> mangward;
    WardAdapter wardAdapter;
    int     idphuong   = 0;
    String  namephuong = "";
    String  prefixphuong = "";
    int     provinceidphuong = 0;
    int     districtid = 0;
    //endregion
    LinearLayout c1, c2, c3, c4;
    ImageView i1, i2, i3, i4;
    private EditText tenDiaDiem, diaChi, moTa, noiDung;
    Button xacNhan, huyBo, dang_post;  // c??c button n??t h???y b???, x??c nh???n, ????ng
    Bitmap bitmap;
    String encodeImage1,encodeImage3,encodeImage2,encodeImage4;
    Toolbar post_backto_home;  // toolbar
    private static final String FILE_POST = "isPost";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        anhXa();

        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            event();
            GetDuLieuProvince();
//            isPost();
        }else {
            //Show_Toast("B???n h??y ki???m tra l???i k???t n???i!", R.drawable.icon_toast_warning);
            Show_SnackBar(R.drawable.icon_toast_warning,"B???n h??y ki???m tra l???i k???t n???i!", "Th??? l???i");
            return;
        }


        //region ?????y ???nh
        //select image 1
        i1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        //select image 2
        i2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select image"),2 );
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        //select image 3
        i3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select image"),3 );
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        //select image 4
        i4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withActivity(PostActivity.this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent, "Select image"),4 );
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }


                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        //endregion va bai viet va
    }

    private void huyBoBackground() {
        c1.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        c2.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        c3.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        c4.setBackgroundResource(R.drawable.custom_dashed_boder_none);
    }

    private void postContent() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleformat = new SimpleDateFormat("dd/MM/yyyy");
        Format f = new SimpleDateFormat("dd/MM/yyyy");
        String datecreate = f.format(new Date());

        String NameOfPlace = tenDiaDiem.getText().toString().trim();
        String Address = diaChi.getText().toString().trim();
        String Description = moTa.getText().toString().trim();
        String Content = noiDung.getText().toString().trim();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest request = new StringRequest(Request.Method.POST, Server.addPost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Done")){
                    sound.playSound(getApplicationContext(),R.raw.thongbao);
                    Show_Toast("B??i vi???t ??ang ???????c ch??? duy???t.", R.drawable.icon_check_success);
                    Thongbaoday();
//                    Toast.makeText(getApplicationContext(), "Th??m b??i vi???t th??nh c??ng", Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(() -> {
                        PostActivity.this.finish();
                        startActivity(getIntent());
                    },4000);
//                    isPost();
                }else {
                    Toast.makeText(PostActivity.this, "B??i vi???t c???a b???n ???? post th??nh c??ng v?? ?????i ph?? duy???t t??? admin", Toast.LENGTH_SHORT).show();
                    Show_Toast("????ng b??i kh??ng th??nh c??ng.", R.drawable.icon_error);
//                    Toast.makeText(getApplicationContext(), "Th??m b??i vi???t kh??ng th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "L???i" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("nameplace", NameOfPlace.trim());
                params.put("province", province_post.getText().toString().trim());
                params.put("district", district_post.getText().toString().trim());
                params.put("ward", ward_post.getText().toString().trim());
                params.put("address", Address.trim());
                params.put("description", Description.trim());
                params.put("content", Content.trim());
                params.put("image1", encodeImage1);
                params.put("image2", encodeImage2);
                params.put("image3", encodeImage3);
                params.put("image4", encodeImage4);
                params.put("phoneuser", HomeActivity.phone_number_user);
                params.put("datepost", datecreate);
                params.put("status", "0");
                return params;
            }
        };

        requestQueue.add(request);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //for image1
        if(requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                i1.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage1 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            c1.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        }
        //for image2
        if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                i2.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage2 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            c2.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        }
        //for image3
        if(requestCode == 3 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                i3.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage3 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            c3.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        }
        //for image4
        if(requestCode == 4 && resultCode == RESULT_OK && data != null){
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                i4.setImageBitmap(bitmap);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] imageBytes = stream.toByteArray();
                encodeImage4 = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            c4.setBackgroundResource(R.drawable.custom_dashed_boder_none);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void event() {
        //region Address
        province_post.setOnClickListener(new View.OnClickListener() {  // khi nh???n v??o n?? s??? hi???n th??? chon t???nh th??nh
            @Override
            public void onClick(View view) {
                String dulieutinh = province_post.getText().toString().trim();
                if(dulieutinh.equals("")){  // khi m?? nh???n v??o c??i textView d?? n?? s??? heienr th??? l??n ph???n ch???n t???nh th??nh
                    listview_province.setVisibility(View.VISIBLE);   // cho listView t???nh th??nh hi???n l??n
                    framelayout_province.setVisibility(View.VISIBLE);
                }
                else {  // n???u d?? ch???n t???nh n??o ???? r???i m?? nh???n ch???n v??o ph???n ch???n t???nh th?? clear t???nh huy???n x?? ???? l???y tr?????c ????
                    district_post.setText("");
                    ward_post.setText("");
                    mangdistrict.clear();
                    mangward.clear();
                     /// lmaf xu???t hi???n ph???n ch???n t???nh
                    listview_province.setVisibility(View.VISIBLE);
                    framelayout_province.setVisibility(View.VISIBLE);
                }
            }
        });
        province_close_post.setOnClickListener(new View.OnClickListener() {  // t???t ph???n ch???n t???nh
            @Override
            public void onClick(View view) {  // ????ng ph???n ch???n t???nh
                framelayout_province.setVisibility(View.GONE);
            }
        });

        listview_province.setOnItemClickListener(new AdapterView.OnItemClickListener() {   // khi m?? listView t???nh hi???n l??n ta nh???n ch???n 1 t???nh n??o ??o
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tinh = mangprovince.get(i).getName().toString();  // l???y t??n t???nh t??? listVew ???? dc ch???n
                province_post.setText(tinh);  // set t??n t???nh b???ng c??i v???a ch???n
                id_tinh = mangprovince.get(i).getId(); // l???y id c???a t???nh
                framelayout_province.setVisibility(View.GONE);   // ch???n xong th?? ta th???c hi???n t???t ch???n t???nh
            }
        });

        //---------------------------------------------------------------
        district_post.setOnClickListener(new View.OnClickListener() {  // khi nh???n v??o ph???n ch???n huy???n
            @Override
            public void onClick(View view) {
                String isTinhNull = province_post.getText().toString().trim();
                if(isTinhNull.equals("")){  // n???u ch??a ch???n t???nh th??nh n??o th?? ta
                    //Show_Toast("Vui l??ng ch???n t???nh/th??nh ph???!", R.drawable.icon_toast_warning);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui l??ng ch???n t???nh/th??nh ph???!", "????ng");
                }else {   /// n???u ???? ch???n t???nh th??nh n??o ???? th?? ta show c??i listview ch???n t???nh th??nh ra
                    GetDuLieuDistrict();
                    listview_district.setVisibility(View.VISIBLE);
                    framelayout_district.setVisibility(View.VISIBLE);
                }
            }
        });
        district_close_post.setOnClickListener(new View.OnClickListener() {  // nh???n ????? t???t listView ch???n huy???n
            @Override
            public void onClick(View view) {
                framelayout_district.setVisibility(View.GONE);
            }
        });

        listview_district.setOnItemClickListener(new AdapterView.OnItemClickListener() {// khi th???c hi???n ch???n item tr??n listView huy???n
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String huyen = mangdistrict.get(i).getName().toString();  // th???c hi???n l???y t??n huy???n khi clik v??o v??? tr?? i vs m???ng huy???n
                district_post.setText(huyen);   // set t??n huy???n cho textView v???a ch???n
                id_huyen = mangdistrict.get(i).getId();  // l???y id c???a huy???n v???a dc click v??o
                framelayout_district.setVisibility(View.GONE);  // ch???n xong th?? ta th???c hi???n t???t layout ch???n huy???n
            }
        });
        //----------------------------------------------------------------------------------
        // nh???ng ph???n chuy???n x?? t????ng t??? huy???n v?? t??nh
        ward_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String isHuyenNull = district_post.getText().toString().trim();
                if(isHuyenNull.equals("")){  // khi m?? huy???n chua m?? ta nh???n v??o c??i textView ch???n t???nh
                    //Show_Toast("Vui l??ng ch???n qu???n/huy???n!", R.drawable.icon_toast_warning);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui l??ng ch???n qu???n/huy???n!", "????ng");
                }else {  // huy???n ???? dc ch???n thi
                    ward_post.setText("");   // x??a x?? ???? dc ch???n tr?????c ????? ch???n l???i
                    mangward.clear();    // x??a m???ng x?? tr?????c ???? do c?? th??? thay ?????i huy???n
                    GetDuLieuWard();   // c???p nh???p l???i m???ng x?? n???u huy???n thay ?????i
                    listview_ward.setVisibility(View.VISIBLE);   // xuaasrt hi???n ph???n listView x??
                    framelayout_ward.setVisibility(View.VISIBLE);
                }
            }
        });
        ward_close_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                framelayout_ward.setVisibility(View.GONE);
            }
        });
        listview_ward.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String phuong = mangward.get(i).getName().toString();
                ward_post.setText(phuong);
                framelayout_ward.setVisibility(View.GONE);
            }
        });



        //khi ta th???c hi???n nh???n vao th??nh toolbar
        post_backto_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("B???n c?? mu???n h???y b??i vi???t? Qu?? tr??nh n??y kh??ng th??? ho??n t??c!");
            }
        });
        // khi nh??n v??o n??t h???y
        huyBo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("B???n c?? mu???n h???y b??i vi???t? Qu?? tr??nh n??y kh??ng th??? ho??n t??c!");
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Th??ng b??o","Th??ng b??o", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Thongbaoday();  // th??ng b??o tr??n thanh tr???ng th??i c???a ??i???n tho???i
                sound.playSound(PostActivity.this, R.raw.toast);

            }
        });
        dang_post.setOnClickListener(new View.OnClickListener() {   // n??t ????ng b??i
            @Override
            public void onClick(View view) {

                //Ki???m tra ??i???u ki???n ?????
                if(tenDiaDiem.getText().toString().equals("") ||
                        diaChi.getText().toString().equals("") ||
                        moTa.getText().toString().equals("") ||
                        noiDung.getText().toString().equals("") ||
                        province_post.getText().toString().trim().equals("") ||
                        district_post.getText().toString().trim().equals("")){
                    //Show_Toast("Vui l??ng ??i???n ????? th??ng tin!", R.drawable.icon_toast_warning);
                    Show_SnackBar(R.drawable.icon_toast_warning, "Vui l??ng ??i???n ????? th??ng tin!", "????ng");
                    sound.playSound(PostActivity.this, R.raw.toast);
                }
                else {  // khi ch???n ko ????? h??nh ???nh
                    if (encodeImage1 == null || encodeImage2 == null || encodeImage3 == null || encodeImage4 == null){
                        Show_Toast("Vui l??ng ch???n ????? h??nh ???nh!", R.drawable.icon_toast_warning);
                        Show_SnackBar(R.drawable.icon_toast_warning,"Vui l??ng ch???n ????? h??nh ???nh!", "????ng");
                        sound.playSound(PostActivity.this, R.raw.toast);
                    }
                    else {   // khi m???i th??ng tin ???? th???c hi???n th??nh c??ng thi ta th???c hi???n ????ng b??i
                        huyBoBackground();  // h???y b??? backgprung
                        postContent();    // ????ng b??i
                        sound.playSound(PostActivity.this, R.raw.post_like_cmt);
                    }
                }

            }
        });
    }

    private void Thongbaoday() {  // t???o th??ng b??o tr??n thanh tr???ng th??i c???a ??i???n th???i

        Intent intent = new Intent(this, UnapprovedPosts.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(getNotificationId(),PendingIntent.FLAG_CANCEL_CURRENT);
     // --------------------------------------------------------------------
        NotificationCompat.Builder builder = new NotificationCompat.Builder(PostActivity.this, "Th??ng b??o");
        builder.setContentTitle("????ng b??i vi???t th??nh c??ng.");
        builder.setContentText("B??i vi???t ??ang ???????c ch??? qu???n tr??? vi??n duy???t!");
        builder.setSmallIcon(R.drawable.play_store_512_round);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);
     //------------------------------------------------------------------------

        NotificationManagerCompat manager = NotificationManagerCompat.from(PostActivity.this);
        manager.notify(getNotificationId(),builder.build());
    }

    private int getNotificationId() {
        return  (int) new Date().getTime();
    }


    //region Address Code
    // l???y d??? li???u t???nh
    private void GetDuLieuProvince() {   //l???y d??? li???u t???nh t??? php v?? ????a v??o list t???nh
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.province, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                        if(response != null){
                            for(int i=0;i<response.length();i++){
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    idtinh = jsonObject.getInt("id");
                                    nametinh = jsonObject.getString("name");
                                    codetinh = jsonObject.getString("code");
                                    mangprovince.add(new Province(idtinh, nametinh, codetinh));
                                    provinceAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        CheckConnection.ShowToast_Short(getApplicationContext(), "L???i k???t n???i d??? li???u..." + error.toString());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuWard() {  // l???y d??? li???u x??  t??? php ????? v??o m???ng ch???a c??c x??
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.ward, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idphuong = jsonObject.getInt("id");
                        namephuong = jsonObject.getString("name");
                        prefixphuong = jsonObject.getString("prefix");
                        provinceidphuong = jsonObject.getInt("provinceid");
                        districtid = jsonObject.getInt("districtid");
                        mangward.add(new Ward(idphuong,namephuong,prefixphuong,provinceidphuong,districtid));
                        wardAdapter.notifyDataSetChanged();  // c???p l???i s??? thay ?????i trong adpater
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "L???i k???t n???i d??? li???u..." + error.toString());
            }
        }){
            @Nullable
            @Override
            // try???n id c??? huy???n ng fie php
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idhuyen",String.valueOf(id_huyen));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }



    private void GetDuLieuDistrict() {  // l???y d??? li???u huy???n ????? v??? m???ng huy???n t??? id t???nh
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.district, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        idhuyen = jsonObject.getInt("id");
                        namehuyen = jsonObject.getString("name");
                        prefixhuyen = jsonObject.getString("prefix");
                        provinceidhuyen = jsonObject.getInt("provinceid");
                        mangdistrict.add(new District(idhuyen,namehuyen,prefixhuyen,provinceidhuyen));
                        districtAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getApplicationContext(), "L???i k???t n???i d??? li???u..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idtinh",String.valueOf(id_tinh));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    //endregion

    private void anhXa() {
        //region Anh xa Address
        ward_post = findViewById(R.id.ward_post);
      //
        listview_ward = findViewById(R.id.listview_ward);// x??
        framelayout_ward = findViewById(R.id.framelayout_ward); // layout n??y ch???a v??ng hi???n th??? ch???n x??
        ward_close_post = findViewById(R.id.ward_close_post);  // n??t t???t ch???n x??
        framelayout_ward.setVisibility(View.GONE);  // m???c ?????nh l?? layout n??y s??? b??? t???t ??i
        listview_ward.setVisibility(View.GONE);   // lisvView n??y s??? b??? t???t ??i
        mangward = new ArrayList<>();  // danh s??ch ch???a x??
        wardAdapter = new WardAdapter(mangward, getApplicationContext());  // adapter cho listView ch???a c??c x??
        listview_ward.setAdapter(wardAdapter);  // x??t adapter

        // v??ng huy???n t????ng t??? nh?? x?? ??? tr??nn
        district_post = findViewById(R.id.district_post);
        listview_district = findViewById(R.id.listview_district);
        framelayout_district = findViewById(R.id.framelayout_district);
        district_close_post = findViewById(R.id.district_close_post);
        framelayout_district.setVisibility(View.GONE);
        listview_district.setVisibility(View.GONE);
        mangdistrict = new ArrayList<>();
        districtAdapter = new DistrictAdapter(mangdistrict, getApplicationContext());
        listview_district.setAdapter(districtAdapter);
      // v??ng t???nh
        province_post = findViewById(R.id.province_post);
        listview_province = findViewById(R.id.listview_province);
        framelayout_province = findViewById(R.id.framelayout_province);
        province_close_post = findViewById(R.id.province_close_post);
        framelayout_province.setVisibility(View.GONE);
        listview_province.setVisibility(View.GONE);
        mangprovince = new ArrayList<>();
        provinceAdapter = new ProvinceAdapter(mangprovince, getApplicationContext());
        listview_province.setAdapter(provinceAdapter);
        //endregion
        //m???y c??i layout ch???a ch???n ???nh
        c1 =  findViewById(R.id.addPhoto1);
        c2 =  findViewById(R.id.addPhoto2);
        c3 =  findViewById(R.id.addPhoto3);
        c4 =  findViewById(R.id.addPhoto4);

        i1 = (ImageView) findViewById(R.id.img1);
        i2 = (ImageView) findViewById(R.id.img2);
        i3 = (ImageView) findViewById(R.id.img3);
        i4 = (ImageView) findViewById(R.id.img4);

        tenDiaDiem = (EditText) findViewById(R.id.ten_dia_diem);
        diaChi = (EditText) findViewById(R.id.dia_chi);
        moTa = (EditText) findViewById(R.id.mo_ta);
        noiDung = (EditText) findViewById(R.id.noi_dung);

        xacNhan = (Button) findViewById(R.id.xac_nhan_post);
        huyBo = (Button) findViewById(R.id.huy_bo_post);
        dang_post = (Button) findViewById(R.id.dang_post);

        post_backto_home = findViewById(R.id.post_backto_home);
    }

    //region Custom a toast
    public final void Show_Toast(String t, int s){
        LayoutInflater inflater = getLayoutInflater();

        View layout = inflater.inflate(R.layout.toast,
                findViewById(R.id.toast_layout_root));

        ImageView image = layout.findViewById(R.id.image);
        image.setImageResource(s);
        TextView text = layout.findViewById(R.id.text);
        text.setText(t);//
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 20);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
    //endregion


    public  final void Show_SnackBar( int i, String t, String a){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_snackbar,findViewById(R.id.snack_constraint), false);

        ImageView icon = layout.findViewById(R.id.im_icon);
        icon.setImageResource(i);
        TextView text = layout.findViewById(R.id.tv_message);
        text.setText(t.toString());
        TextView action = layout.findViewById(R.id.tv_action);
        action.setText(a.toString());

        View view = findViewById(R.id.layout_add_post);
        int duration = 5000;
        String mess = "";
        Snackbar s = Snackbar.make(view, mess, duration);

        s.getView().setBackgroundColor(Color.TRANSPARENT);
        Snackbar.SnackbarLayout sl = (Snackbar.SnackbarLayout) s.getView();
        sl.setPadding(0,0,0,0);

        //for dismiss
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s.dismiss();
            }
        });
        sl.addView(layout,0);
        s.show();
    }

    //region Custom a notification
    public void Show_Popup(String text){

        sound.playSound(PostActivity.this, R.raw.thongbao);

        Dialog dialog = new Dialog(PostActivity.this);

        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.content_alert);
        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setText("Tho??t");

        title.setText(text);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostActivity.this.finish();
            }
        });
        dialog.show();
    }
    //endregion

    //region Ghi ???? n??t back tr??n ??i???n tho???i, v?? hi???u h??a quay l???i m??n h??nh tr?????c
    @Override
    public void onBackPressed() {
        Show_Popup("B???n c?? mu???n h???y b??i vi???t? Qu?? tr??nh n??y kh??ng th??? ho??n t??c!");
    }
    //endregion

    //region check internet
    com.hien.ketnoiviet.ultil.networkChangeListener networkChangeListener = new networkChangeListener();
    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener,filter);
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
    //endregion


}