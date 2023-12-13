package com.hien.ketnoiviet.Home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hien.ketnoiviet.Others.NewsDetail;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.NewsAdapter;
import com.hien.ketnoiviet.adapter.PostAdapter;
import com.hien.ketnoiviet.adapter.notifiAdapter;
import com.hien.ketnoiviet.model.Comment;
import com.hien.ketnoiviet.model.News;
import com.hien.ketnoiviet.model.Ward;
import com.hien.ketnoiviet.model.notification;
import com.hien.ketnoiviet.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotifyFragment extends Fragment {


    View view ;
    RecyclerView ry_notifi ;
    ArrayList<notification> listNotifi ;
    notifiAdapter adapter ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_notify, container, false);
//        listview_title = view.findViewById(R.id.listview_tintuc);
//        linearLayout_dangbaisc = view.findViewById(R.id.linearLayout_dangbaisc);
//        linearLayout_dangbaisc.setVisibility(View.GONE);
//
//        if (HomeActivity.isPost.equals(1)){
//            linearLayout_dangbaisc.setVisibility(View.VISIBLE);
//        }
//  /// set adapter cho listView các bài viết ở phần thông báo
//        listview_title.setAdapter(HomeActivity.newsAdapter);

        anhXa();
        getListNotifi();
        return view;


    }
    public void anhXa(){
        ry_notifi = view.findViewById(R.id.ryNotifi);
        listNotifi = new ArrayList();
        adapter = new notifiAdapter(getContext(),listNotifi);
        ry_notifi.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

// Gán LinearLayoutManager cho RecyclerView
        ry_notifi.setLayoutManager(layoutManager);
    }
    public void getListNotifi(){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.getNotifi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listNotifi.clear();
                if (!response.isEmpty()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            notification notifi = new notification();
                            notifi.setIdNotifi(jsonObject.getString("idNotifi"));
                            notifi.setIdPost(jsonObject.getInt("idPost"));
                            notifi.setType(jsonObject.getInt("type"));
                            notifi.setIdReadNotifi(jsonObject.getString("idReadPost"));
                            notifi.setAvatarUser(jsonObject.getString("avatarUser"));
                            notifi.setNameUser(jsonObject.getString("nameuser"));
                            notifi.setHasRead(String.valueOf(jsonObject.getBoolean("hasRead")));
                            notifi.setTimeNotifi(jsonObject.getString("timeNotifi"));
                            listNotifi.add(notifi);

                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }//php Thang oi
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                CheckConnection.ShowToast_Short(getApplicationContext(), "Lỗi kết nối dữ liệu..." + error.toString());
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("phoneUser", HomeActivity.phone_number_user);
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onResume() {
        getListNotifi();
        super.onResume();
    }
}

