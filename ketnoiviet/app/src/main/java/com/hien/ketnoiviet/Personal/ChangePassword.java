package com.hien.ketnoiviet.Personal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Intro.MainActivity;
import com.hien.ketnoiviet.Login.ResetPhoneActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;
import com.hien.ketnoiviet.ultil.sound;

import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {
   EditText tvMatKhauCu , tvMatKhauMoi , tvMatKhauMoiNhapLai ;
    Button huy, xacNhan;
    TextView open_forgot_pass;
    String phoneUser ;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        reference= FirebaseDatabase.getInstance().getReference("User");
        // ánh xạ
        tvMatKhauCu=findViewById(R.id.tvMatKhauCu);
        tvMatKhauMoi=findViewById(R.id.tvMatKhauMoi);
        tvMatKhauMoiNhapLai=findViewById(R.id.tvMatKhauMoiNhapLai);
        phoneUser= HomeActivity.phone_number_user ;
        event();
    }

    private void event() {
        huy = findViewById(R.id.cancel_changePass);
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Show_Popup("Bạn có muốn hủy quá trình thay đổi mật khẩu?");
            }
        });

        open_forgot_pass = findViewById(R.id.open_forgot_pass);
        open_forgot_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ChangePassword.this, ResetPhoneActivity.class);
                startActivity(i);
            }
        });

        xacNhan = findViewById(R.id.submit_changePass);
       xacNhan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               changePassword();

           }
       });


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

        sound.playSound(ChangePassword.this, R.raw.thongbao);
        Dialog dialog = new Dialog(ChangePassword.this);

        dialog.setContentView(R.layout.custom_dialog_layout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        TextView title = dialog.findViewById(R.id.content_alert);
        Button ok = dialog.findViewById(R.id.btn_okay);
        Button cancel = dialog.findViewById(R.id.btn_cancel);

        ok.setText("Thoát");
        title.setText(text);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChangePassword.this.finish();
            }
        });
        dialog.show();

    }
    //endregion

    //region Ghi đè nút back trên điện thoại, vô hiệu hóa quay lại màn hình trước
    @Override
    public void onBackPressed() {
        Show_Popup("Bạn có muốn hủy quá trình thay đổi mật khẩu?");
    }
    //endregion
      public void changePassword(){
          String url1=Server.checkPassword ;
          RequestQueue requestQueue1 = Volley.newRequestQueue(ChangePassword.this);
          // check mật khẩu cũ
          StringRequest stringRequest1= new StringRequest(Request.Method.POST, url1,
                  new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response1) {
                          if(response1.toString().equals(tvMatKhauCu.getText().toString())){
                              String url=Server.changePassword ;
                              RequestQueue requestQueue = Volley.newRequestQueue(ChangePassword.this);
                              if(checkConFirmPassWord()==true){
                                  StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                          new Response.Listener<String>() {
                                              @Override
                                              public void onResponse(String response) {

                                                  // nếu mk nhập lại đúng thì thực hiện đổi mật khẩu
                                                  if(response.equals("Done")){
                                                      String phoneUser= HomeActivity.phone_number_user ;
                                                      reference.child(phoneUser).child("password").setValue(tvMatKhauMoi.getText().toString());
                                                      thongBao("Thông Báo","Đổi Mật Khẩu Thành Công");
                                                      Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                                      tvMatKhauCu.setText("");
                                                      tvMatKhauMoi.setText("");
                                                      tvMatKhauMoiNhapLai.setText("");
                                                      finish();
                                                  }else{
                                                      thongBao("Thông Báo","Đổi Mật Khẩu Thất Bại");
                                                      Toast.makeText(ChangePassword.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();

                                                  }
                                              }
                                          },
                                          new Response.ErrorListener() {
                                              @Override
                                              public void onErrorResponse(VolleyError error) {
                                                  thongBao("Thông Báo","Lỗi Mạng");
                                              }
                                          }


                                  ){
                                      @Nullable
                                      @Override
                                      protected Map<String, String> getParams() throws AuthFailureError {
                                          Map<String, String> params = new HashMap<>();
                                          params.put("phoneUser", phoneUser);
                                          params.put("matKhauMoi",tvMatKhauMoi.getText().toString());
                                          return params;


                                      }
                                  };
                                  requestQueue.add(stringRequest);
                              }else{
                                  thongBao("Thông Báo","Mật Khẩu Nhập Lại Không Trùng");
                                  Toast.makeText(ChangePassword.this, "mật khẩu nhập lại không trùng", Toast.LENGTH_SHORT).show();
                              }
                          }else{
                              // mk cũ bị sai
                              thongBao("Thông Báo","Mật khẩu cũ bị sai vui lòng nhập lại");
                              Toast.makeText(ChangePassword.this, "Mật khẩu cũ bị sai vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                          }
                      }
                  },
                  new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {

                      }
                  }


          ){
              @Nullable
              @Override
              protected Map<String, String> getParams() throws AuthFailureError {
                  Map<String, String> params = new HashMap<>();
                  params.put("phoneUser", phoneUser);
                  return params;


              }
          };
          requestQueue1.add(stringRequest1);


      }
   public boolean checkConFirmPassWord(){
        if(tvMatKhauMoi.getText().toString().equals(tvMatKhauMoiNhapLai.getText().toString())){
            return true ;
        }
        else{
            return false ;
        }
   }
public void thongBao(String title, String text){
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
    // Setting Alert Dialog Title
    alertDialogBuilder.setTitle(title);

    // Setting Alert Dialog Message
    alertDialogBuilder.setMessage(text);
    alertDialogBuilder.setCancelable(false);
    alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface arg0, int arg1) {
          return;
        }
    });


    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
}

}