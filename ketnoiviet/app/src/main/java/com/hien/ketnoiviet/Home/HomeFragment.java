package com.hien.ketnoiviet.Home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hien.ketnoiviet.Others.SearchPlace;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.adapter.countAllLikeAdapter;
import com.hien.ketnoiviet.message.messageActivity;
import com.hien.ketnoiviet.model.Like;
import com.hien.ketnoiviet.model.Post;
import com.hien.ketnoiviet.ultil.CheckConnection;
import com.hien.ketnoiviet.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    public String like = "0";   // số lượng like của bài viể
    TextView home_all,home_follow;   //
    private static final String FILE_NAME = "myFile";
    ListView list_post_home, list_post_home_follow;  // listView chứa các bài viết của mọi người và của người mình theo dõi
    public ArrayList<Post> arrayPost  // danh sách tất cả bài post của tất cả mọi người
            ,arrayPostFl;  // danh sách bài post của những người mình đang theo dõi
    public PostAdapter postAdapter,postAdapterFl;
    ImageButton search_button_home,imgBtnMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        list_post_home = view.findViewById(R.id.list_post_home);
        list_post_home_follow = view.findViewById(R.id.list_post_home_follow);
        list_post_home_follow.setVisibility(View.GONE);

        imgBtnMessage = view.findViewById(R.id.imgBtnMessage);
        imgBtnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), messageActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
            }
        });

      // set adapter chi listView chứ tất cả các bài post
        arrayPost = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), arrayPost);
        list_post_home.setAdapter(postAdapter);
// set adapter chi listView chứ tất cả các bài post  mà mình theo dõi
        arrayPostFl = new ArrayList<>();
        postAdapterFl = new PostAdapter(getContext(), arrayPostFl);
        list_post_home_follow.setAdapter(postAdapterFl);

        GetDataPost();
        search_button_home = view.findViewById(R.id.search_button_home);
        search_button_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SearchPlace.class));
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_right);
            }
        });

        home_all = view.findViewById(R.id.home_all);
        home_follow = view.findViewById(R.id.home_follow);
     // khi click vào phần đang theo dõi
        home_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_follow.setTextColor(getResources().getColor(R.color.purple_primary));
                home_follow.setBackgroundResource(R.color.background);
                home_follow.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                home_all.setTextColor(getResources().getColor(R.color.black));
                home_all.setBackgroundResource(R.color.white);
                home_all.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                GetDataPostFollow();
                list_post_home.setVisibility(View.GONE);
                list_post_home_follow.setVisibility(View.VISIBLE);
            }
        });
        // khi click vào phần dành tất cả
        home_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                home_all.setTextColor(getResources().getColor(R.color.purple_primary));
                home_all.setBackgroundResource(R.color.background);
                home_all.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                home_follow.setTextColor(getResources().getColor(R.color.black));
                home_follow.setBackgroundResource(R.color.white);
                home_follow.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                GetDataPost();
                list_post_home_follow.setVisibility(View.GONE);
                list_post_home.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }
    private void getTotalLike(int id) {  // ;lấy số lượng người like bài viết
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getTotalLike, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                Toast.makeText(getApplicationContext(), "" + response, Toast.LENGTH_SHORT).show();
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    like = String.valueOf(jsonArray.length());
//                    Toast.makeText(getContext(), " " + like, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckConnection.ShowToast_Short(getContext(), "Lỗi kết nối dữ liệu...");
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idpost", String.valueOf(id));  // truyền đi id của bài post
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void GetDataPost() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Server.getPost, null,
            new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    arrayPost.clear();
                    if(response != null){
                        for(int i=0;i<response.length();i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
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
                                getTotalLike(idpost);
                                arrayPost.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        postAdapter.notifyDataSetChanged();
//                        Toast.makeText(getContext(), "Độ dài:" + arrayPost.size(), Toast.LENGTH_SHORT).show();
                    }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
//                    CheckConnection.ShowToast_Short(getContext(), "Lỗi kết nối dữ liệu..." + error.toString());
                }
            }
        );
        requestQueue.add(jsonArrayRequest);
    }


 // lấy dữ liệu bài post theo follow
    private void GetDataPostFollow() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getPostByFollow, response -> {

            arrayPostFl.clear();
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

                    arrayPostFl.add(new Post(idpost, nameplace, province, district, ward, address, description, content, image1, image2, image3, image4, phoneuser, datepost, status,nameuser,imageuser));
                    postAdapterFl.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> CheckConnection.ShowToast_Short(getContext(), "Lỗi kết nối dữ liệu..." + error.toString())){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phonenumberr",HomeActivity.phone_number_user);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

//    public void changeToMessage(){
//        imgBtnMessage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getContext(), messageActivity.class));
//
//            }
//        });
//    }
}
