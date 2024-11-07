package com.hien.ketnoiviet.Personal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Intro.MainActivity;
import com.hien.ketnoiviet.Login.ResetPasswordActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.User;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingPersonal extends AppCompatActivity {

    ToggleButton toggle_setting;
    TextView change_pass_setting;
    Switch switch_theme_setting;

    //thông tin cá nhân
    TextView tvLocation , tvSex, tvBirthday , tvEmail ;
    // trạng thái của các thông tin cá nhân
    TextView tvStatusLocation , tvStatusSex, tvStatusBirthday , tvStatusEmail ;
    // icon các trạng  thái thông tin cá nhân
    ImageView imgLocation , imgSex  , imgBirthDay, imgEmail ;

   String phonenumber_user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personal);
        phonenumber_user = HomeActivity.phone_number_user;
        anhxa();
        event();
        getInforUser();
    }

    private void event() {
        change_pass_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ChangePassword.class));
            }
        });
        switch_theme_setting.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton,
                                                 boolean b) {
                        if (switch_theme_setting.isChecked()){
                            Toast.makeText(getApplicationContext(), "Tối", Toast.LENGTH_SHORT).show();
                            switch_theme_setting.setText("Tối");
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Sáng", Toast.LENGTH_SHORT).show();
                            switch_theme_setting.setText("Sáng");
                        }
                    }
                });
        CompoundButton.OnCheckedChangeListener listener =
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (toggle_setting.isChecked()){
                            Toast.makeText(getApplicationContext(), "Tiếng Anh", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Tiếng Việt", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

        toggle_setting.setOnCheckedChangeListener(listener);
    }

    private void anhxa() {
        change_pass_setting = findViewById(R.id.change_pass_setting);
        switch_theme_setting = findViewById(R.id.switch_theme_setting);
        toggle_setting = findViewById(R.id.toggle_setting);
        toggle_setting.setChecked(false);


        tvLocation =findViewById(R.id.tvLocation);
        tvSex =findViewById(R.id.tvSex);
        tvBirthday =findViewById(R.id.tvBirthday);
        tvEmail =findViewById(R.id.tvEmail);

        tvStatusLocation =findViewById(R.id.tvStatusLocation);
        tvStatusSex =findViewById(R.id.tvStatusSex);
        tvStatusBirthday =findViewById(R.id.tvBirthday);
        tvStatusEmail =findViewById(R.id.tvStatusEmail);


        imgLocation =findViewById(R.id.ImgLocation);
        imgSex =findViewById(R.id.imgSex);
        imgBirthDay =findViewById(R.id.imgBirth);
        imgEmail =findViewById(R.id.imgEmail);

    }
    // cal API thực hiện lấy  thông tin cá nhân
   private  void getInforUser(){
       RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
       StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getuserinfo, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONArray jsonArray = new JSONArray(response);
                   JSONObject jsonObject = jsonArray.getJSONObject(0);

                   tvBirthday.setText("Ngày sinh: "+jsonObject.getString("birthday"));
                   tvEmail.setText("Email: "+jsonObject.getString("email"));
                   tvLocation.setText("Quê quán: "+jsonObject.getString("hometown"));
                   tvSex.setText("Giới tính: "+jsonObject.getString("gender"));

               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       }, error -> CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString())){
           @Nullable
           @Override
           protected Map<String, String> getParams() throws AuthFailureError {
               HashMap<String, String> param = new HashMap<String, String>();
               param.put("phone_number",phonenumber_user); // gửi số điện thoại người dùng tới file php để láy thông tin
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
}