package com.hien.ketnoiviet.Others;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Discover.PostActivity;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Login.LoginActivity;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.adapter.RecyclerViewAdapterPost;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalOtherInfo extends AppCompatActivity {

    Toolbar toolbar_personal_info_ct;
    String phonenumber_user = "";  // sdt c???a ng?????i d??ng ????
    ImageView cover_personal_info_ct;
    CircleImageView image_personal_info_ct,image_personal_info_ot_mini;
    TextView username_personal_info_ct,status_personal_info_ct,hometown_personal_ct,gender_personal_ct,datecreate_personal_ct,birthday_personal_ct,phonenumber_personal_ct,email_personal_ct,follow_info_ot;
    int     id       = 0;
    String  name     = "";
    String  date     = "";
    String  sex      = "";
    String  img = "";
    String  bia = "";
    String  mail = "";
    String  phone = "";
    String  stt = "";
    String  home = "";
    String url_image = "";
    String url_cover = "";
    int status = 0;
    public ArrayList<Post> arrayPostInfot; // danh s??ch b??i vi???t c???a ng?????i ????
    public RecyclerViewAdapterPost recyclerViewAdapterPostt; // adapter b??i vi???t c???a ng?????i ????

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_other_info);
        getphone();  //l???y sdt ng?????i d??ng ????
        anhxa();
        event();
        getData();  // l???y  th??ng tin ng?????i d??ng
        getPost();   // l???y c??c b??i ????ng c???a ng?????i d??ng n??y
   //     personal();  // l???y ???nh ng?????i d??ng
        getStatus();  // l???y tr???ng th??i xem c?? theo d??i ng?????i d??ng n??y hay kh??ng
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL, false);
        RecyclerView recyclerView = findViewById(R.id.listbaiviet_personalot_rcv);
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapterPostt = new RecyclerViewAdapterPost(this, arrayPostInfot);
        recyclerView.setAdapter(recyclerViewAdapterPostt);
    }

    private void checkstt() {
        if (status == 1) {
            follow_info_ot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_unfollow, 0, 0, 0);
            follow_info_ot.setText("??ang theo d??i");
        }
        if (status == 0) {
            follow_info_ot.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icon_follow, 0, 0, 0);
            follow_info_ot.setText("Theo d??i");
        }
    }

    private void getStatus() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.checkfollow
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "Tr???ng th??i: " + response, Toast.LENGTH_SHORT).show();
                status = Integer.parseInt(response);
                checkstt();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("users", HomeActivity.phone_number_user);
                params.put("userfollow", phonenumber_user);
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void anhxa() {
        cover_personal_info_ct = (ImageView) findViewById(R.id.cover_personal_info_ot);
        image_personal_info_ct = (CircleImageView) findViewById(R.id.image_personal_info_ot);
        image_personal_info_ot_mini = findViewById(R.id.image_personal_info_ot_mini);
        username_personal_info_ct = findViewById(R.id.username_personal_info_ot);
        status_personal_info_ct = findViewById(R.id.status_personal_info_ot);
        hometown_personal_ct = findViewById(R.id.hometown_personal_ot);
        gender_personal_ct = findViewById(R.id.gender_personal_ot);
        datecreate_personal_ct = findViewById(R.id.datecreate_personal_ot);
        birthday_personal_ct = findViewById(R.id.birthday_personal_ot);
//        phonenumber_personal_ct = findViewById(R.id.phonenumber_personal_ct);
        email_personal_ct = findViewById(R.id.email_personal_ot);
        toolbar_personal_info_ct = findViewById(R.id.toolbar_personal_info_ot);
        arrayPostInfot = new ArrayList<>();
        follow_info_ot = findViewById(R.id.follow_info_ot);
    }

    private void getphone() {
        // s??? ??i???n tho???i ng?????i d??ng t??? activity kh??c
        Intent intent = this.getIntent();
        phonenumber_user = intent.getStringExtra("phoneuser");
    }

    private void event() {
        // khi nh???n v??o thanh toolbarr th?? ta k???t th??c activity n??y
        toolbar_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalOtherInfo.this.finish();
            }
        });
        image_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
                intent.putExtra("avatar",  url_image);
                startActivity(intent);
            }
        });
        cover_personal_info_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // khi nh???n v??o ???nh n??y s??? l???y ???????ng d???n ???nh ???? ????? xem
                Intent intent = new Intent(getApplicationContext(), imgFitScreen.class);
                intent.putExtra("cover",  url_cover);
                startActivity(intent);
            }
        });
        follow_info_ot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status == 0){
                    Show_Popup("B???n c?? mu???n theo d??i ng?????i d??ng n??y!");
                }
                if (status == 1){
                    Show_Popup("B???n c?? mu???n h???y theo d??i ng?????i d??ng n??y!");
                }
            }
        });
    }

    private void Follow() {  // th???c hi???n follow
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, Server.addfollow
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("users", HomeActivity.phone_number_user); // ng?????i th???c hi???n theo d??i
                params.put("userfollow", phonenumber_user); // ng?????i ???????c theo d??i
                return params;
            }
        };
        requestQueue.add(request);
    }

    private void personal() {  // l???y th??ng tin ???nh ?????i di???n v?? ???nh b??a c???a ng?????i d??ng
        String phone_number_person = HomeActivity.phone_number_user;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getuserinfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String  img = jsonObject.getString("imageuser");  //l???y ???????ng d???n ???nh ?????i di???n c???a ng?????i ????
                      String img_cover =jsonObject.getString("cover");  // l???y ???????ng d???n ???nh b??a c???a ng?????i d??ng ????
                        String url_image = Server.userget + img;
                        String url_imgCover = Server.userget + img_cover;
                    Glide.with(getApplicationContext()).load(url_image).into(image_personal_info_ot_mini);
                //  Glide.with(getApplicationContext()).load(url_imgCover).into(cover_personal_info_ct);
                    Picasso.get().load(url_imgCover).into(cover_personal_info_ct);

                    
//
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), "L???i k???t n???i d??? li???u..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phone_number",phone_number_person);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getuserinfo, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    int     id       = jsonObject.getInt("idusers");
                    name     = jsonObject.getString("nameuser");
                    date     = jsonObject.getString("birthday");
                    sex      = jsonObject.getString("gender");
                    img = jsonObject.getString("imageuser");
                    bia = jsonObject.getString("cover");
                    mail = jsonObject.getString("email");
                    phone = jsonObject.getString("phonenumber");
                    stt = jsonObject.getString("status");
                    home = jsonObject.getString("hometown");
                    int     dollar = jsonObject.getInt("money");
                    String  create = jsonObject.getString("datecreate");
                    String  pw = jsonObject.getString("password");


                    url_image = Server.userget + img;
                    url_cover = Server.userget + bia;
                    if (date.isEmpty()){
                        birthday_personal_ct.setText("Sinh nh???t: B?? m???t");
                    }else {
                        String[] dates = date.split("/");
                        String date1 = dates[0];
                        String date2 = dates[1];
                        String date3 = dates[2];
                        birthday_personal_ct.setText("Sinh nh???t ng??y " + date1 + " th??ng " + date2 + ", " + date3);
                    }
                    username_personal_info_ct.setText(name);
//                    phonenumber_personal_ct.setText(phone);
                    if (mail.isEmpty()){
                        email_personal_ct.setText("Email: B?? m???t");
                    }else {
                        email_personal_ct.setText(mail + " ");
                    }

                    status_personal_info_ct.setText(stt + " ");
                    datecreate_personal_ct.setText(create + " ");
                    String[]  creates = create.split("/");
                    String create1 = creates[0];
                    String create2 = creates[1];
                    String create3 = creates[2];
                    datecreate_personal_ct.setText("???? tham gia v??o th??ng " + create1 + " n??m " + create3);
                    if (home.isEmpty()){
                        hometown_personal_ct.setText("?????n t??? " + "B?? m???t");
                    }else {
                        hometown_personal_ct.setText("?????n t??? " + home + " ");
                    }
                    if (sex.isEmpty()){
                        gender_personal_ct.setText("Gi???i t??nh: " + "B?? m???t");
                    }else {
                        gender_personal_ct.setText("Gi???i t??nh: " + sex + " ");
                    }
                    Glide.with(getApplicationContext()).load(url_image).into(image_personal_info_ct);
                    Glide.with(getApplicationContext()).load(url_cover).into(cover_personal_info_ct);
                    toolbar_personal_info_ct.setTitle(name);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), "L???i k???t n???i d??? li???u..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phone_number",phonenumber_user);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void getPost() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostByPhoneUser, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                Log.d("response", response);
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int idpost = jsonObject.getInt("id");
                        String nameplace = jsonObject.getString("nameplace");
                        String province  =  jsonObject.getString("province");
                        String district  =  jsonObject.getString("district");
                        String ward  =  jsonObject.getString("ward");
                        String address  =  jsonObject.getString("address");
                        String description = jsonObject.getString("description");
                        String content = jsonObject.getString("content");
                        String image1 = jsonObject.getString("image1");
                        String image2 = jsonObject.getString("image2");
                        String image3 = jsonObject.getString("image3");
                        String image4 = jsonObject.getString("image4");
                        String phoneuser = jsonObject.getString("phoneuser");
                        String datepost = jsonObject.getString("datepost");
                        int status = jsonObject.getInt("status");
                        String nameuser = jsonObject.getString("nameuser");
                        String imageuser = jsonObject.getString("imageuser");

//                              Toast.makeText(getContext(), "B??i vi???t:" + idpost + "\n" + nameplace + "\n" + address + "\n" + image1 + "\n" + phoneuser + "\n" + datepost + "\n" + status + "\n", Toast.LENGTH_SHORT).show();
                        arrayPostInfot.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                        recyclerViewAdapterPostt.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), "L???i k???t n???i d??? li???u..." + error.toString());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phoneuser",phonenumber_user);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

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


    //region Custom a notification
    public void Show_Popup(String text){

        sound.playSound(PersonalOtherInfo.this, R.raw.thongbao);

        Dialog dialog = new Dialog(PersonalOtherInfo.this);

        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.content_alert);
        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setText("?????ng ??");

        title.setText(text);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Follow();
                sound.playSound(PersonalOtherInfo.this, R.raw.post_like_cmt);
                PersonalOtherInfo.this.finish();
                startActivity(getIntent());
            }
        });
        dialog.show();
    }
    //endregion

}