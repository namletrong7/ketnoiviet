package com.hien.ketnoiviet.message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.userAdapter;
import com.hien.ketnoiviet.model.User;
import com.hien.ketnoiviet.model.follower;
import com.hien.ketnoiviet.ultil.Server;
import com.hien.ketnoiviet.ultil.networkChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class messageActivity extends AppCompatActivity {
    ImageView imgBack ;
    Button btnCheck ;
    FirebaseDatabase database ;
    ArrayList<User> listUser ;
    ArrayList<follower> listFollow ;

     userAdapter adapter ;
     RecyclerView recycler_conversation;

    // hiển thị danh sách người bạn để thực hiện nhắn tin với bạn đó
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
       // anh xa
       Toolbar toolbar =findViewById(R.id.toolbar);
       imgBack=(ImageView) findViewById(R.id.imgBack);
        btnCheck=(Button) findViewById(R.id.btnCheck);
       listUser=new ArrayList<>();
        listFollow=new ArrayList<>();

        recycler_conversation=findViewById(R.id.recycler_conversation);
        adapter=new userAdapter(messageActivity.this, listUser);
        recycler_conversation.setAdapter(adapter);
//      adapter = new userAdapter(messageActivity.this, listUser);
//        recycler_conversation.setAdapter(adapter);
        database = FirebaseDatabase.getInstance();
        checkFollow();
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Toast.makeText(messageActivity.this, "Thoát app", Toast.LENGTH_SHORT).show();
            }
        });
        btnCheck.setVisibility(View.GONE);



    }

  public void checkFollow(){  // sẽ lọc ra những người mà mik follow và hiển thị đoan chát với người đó

           RequestQueue requestQueue=Volley.newRequestQueue(messageActivity.this);
           StringRequest stringRequest= new StringRequest(Request.Method.POST, Server.getListFollow,
                   new Response.Listener<String>() {
                       @Override
                       public void onResponse(String response) {
                      //     Toast.makeText(messageActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                           try {
                               JSONArray jsonArray = new JSONArray(response);
                               for(int i=0 ; i<jsonArray.length();i++){
                                   JSONObject ob=jsonArray.getJSONObject(i);
                                   int id = ob.getInt("id");
                                   String users = ob.getString("users");
                                   String usF=ob.getString("userfollow");
                                   follower fl = new follower(id,users, usF);
                                   listFollow.add(fl);
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

              //láy dưz liệu từ firebase đưa vào list người dùng
                           database.getReference().child("User").addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {
                                   if (snapshot.exists()) {
                                       listUser.clear();
                                       for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                           User user = new User();
                                           user.setIdusers(Integer.parseInt(snapshot1.child("idusers").getValue().toString()));
                                           user.setNameuser(snapshot1.child("nameuser").getValue(String.class).toString());
                                           user.setBirthday(snapshot1.child("birthday").getValue().toString());
                                           user.setGender(snapshot1.child("gender").getValue().toString());
                                           user.setImageuser(snapshot1.child("imageuser").getValue().toString());
                                           user.setEmail(snapshot1.child("email").getValue().toString());
                                           user.setPhonenumber(snapshot1.child("phonenumber").getValue().toString());
                                           String phone = snapshot1.child("phonenumber").getValue().toString();
                                           user.setStatus(snapshot1.child("status").getValue().toString());
                                           user.setHometown(snapshot1.child("hometown").getValue().toString());
                                           user.setMoney(Integer.parseInt(snapshot1.child("money").getValue().toString()));
                                           user.setDatecreate(snapshot1.child("datecreate").getValue().toString());
                                           user.setPassword(snapshot1.child("password").getValue().toString());
                                           user.setCover(snapshot1.child("cover").getValue().toString());

                                       for(follower fl :listFollow){// chỉ thêm những người dùng được theo dõi vào danh sách người dùng để nhắn tin
                                           if(fl.getUserFollow().equals(phone)){
                                               listUser.add(user);
                                               adapter=new userAdapter(messageActivity.this, listUser);
                                               recycler_conversation.setAdapter(adapter);
                                           }

                                       }

                                       }
                                       adapter.notifyDataSetChanged();
                                   }
                               }
                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           });














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
                   params.put("phonenumber",HomeActivity.phone_number_user);
                   return params;
               }
           };
           requestQueue.add(stringRequest);
  }



    com.hien.ketnoiviet.ultil.networkChangeListener networkChangeListener = new networkChangeListener();


}