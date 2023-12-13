package com.hien.ketnoiviet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Others.PersonalOtherInfo;
import com.hien.ketnoiviet.Others.PostDetail;
import com.hien.ketnoiviet.Personal.PersonalInfo;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.model.notification;
import com.hien.ketnoiviet.ultil.Server;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class notifiAdapter extends RecyclerView.Adapter<notifiAdapter.ViewHolder>{

        Context context;
        ArrayList<notification> list ;

public notifiAdapter(Context context, ArrayList<notification> list) {
        this.context = context;
        this.list = list;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notifi,parent,false);
        return new ViewHolder(view);
        }


@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    notification notifi = list.get(position);
    String imgUser = Server.userget + notifi.getAvatarUser();
    Glide.with(context).load(imgUser).into(holder.imgUser);
    holder.ly_notifi.setBackgroundColor(notifi.getHasRead().equals("true")?Color.parseColor("#ffffff"):Color.parseColor("#B0C4DE"));
    String userName = notifi.getNameUser();
    holder.timeNotifi.setText(setTimeNotifi(Long.parseLong(notifi.getTimeNotifi())));


    String contentNotifiComment = userName + " đã bình luận về một bài viết bạn quan tâm ";
    SpannableString spannableString = new SpannableString(contentNotifiComment);
    StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
    spannableString.setSpan(boldSpan, 0, userName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    String contentNotifiLike = userName + " đã like bài viết mà bạn quan tâm ";
    SpannableString spannableString1 = new SpannableString(contentNotifiLike);
    StyleSpan boldSpan1 = new StyleSpan(Typeface.BOLD);
    spannableString1.setSpan(boldSpan1, 0, userName.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

    if(notifi.getType()==0) {// thông báo comment
        holder.tvContent.setText(spannableString);
        holder.imgTypeNotfi.setImageResource(R.drawable.icon_type_comment);
    }else if(notifi.getType()==1){
        holder.tvContent.setText(spannableString1);
        holder.imgTypeNotfi.setImageResource(R.drawable.like);
    }


    // hành động khi nhấn vô thoog báo nè
    holder.ly_notifi.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(notifi.getHasRead().equals("false")){  // thực hiện cho thông báo thanh đã đọc
                // chuyển idReadpost về đã đọc ngay
                     setHasRead(notifi.getIdReadNotifi());
                Toast.makeText(context, notifi.getIdReadNotifi(), Toast.LENGTH_SHORT).show();
            }
                Intent intent = new Intent(context, PostDetail.class);
                intent.putExtra("idpost", notifi.getIdPost());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
        }
    });

}

    @Override
    public int getItemCount() {
        if (list != null) { // Kiểm tra xem danh sách có null không
            return list.size();
        } else {
            return 0; // Trả về 0 nếu danh sách là null
        }
    }
     public String setTimeNotifi(Long timeNotifi){
                 long currentTime = System.currentTimeMillis();
                 long elapsedTime = currentTime - timeNotifi;

                 if (elapsedTime < 60000) { // Dưới 1 phút
                     return "Vừa xong";
                 } else if (elapsedTime < 3600000) { // Dưới 1 giờ
                     long minutes = elapsedTime / 60000;
                     return minutes + " phút trước";
                 } else if (elapsedTime < 86400000) { // Dưới 1 ngày
                     long hours = elapsedTime / 3600000;
                     return hours + " giờ trước";
                 } else { // Trên 1 ngày
                     SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                     return "Ngày " + dateFormat.format(new Date(timeNotifi));
                 }
             }




    public void setHasRead(String idreadNotifi){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.setReadNotifi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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
                param.put("idReadNotifi", idreadNotifi+"");
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
          CircleImageView imgUser ;
          TextView tvContent, timeNotifi ;
          LinearLayout ly_notifi;
          ImageView imgTypeNotfi ;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
         imgUser = itemView.findViewById(R.id.img_user_comment);
         tvContent = itemView.findViewById(R.id.contentNotifi);
         ly_notifi=itemView.findViewById(R.id.ly_notifi);
         timeNotifi=itemView.findViewById(R.id.timeNotifi);
         imgTypeNotfi=itemView.findViewById(R.id.icon_type_notifi);

    }
}
}

