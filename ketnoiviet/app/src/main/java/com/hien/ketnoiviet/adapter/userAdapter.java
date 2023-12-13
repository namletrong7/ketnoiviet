package com.hien.ketnoiviet.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.databinding.RowConversationBinding;
import com.hien.ketnoiviet.message.chatActivity;
import com.hien.ketnoiviet.model.User;
import com.hien.ketnoiviet.ultil.Server;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder> {
     Context context ;
     ArrayList<User> listUser ;


    public userAdapter(Context context, ArrayList<User> listUser){
       this.context=context;
       this.listUser=listUser;

    }
    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_conversation,parent,false);


        return new userViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
       User user= listUser.get(position);
       holder.binding.tvNameUser.setText(user.getNameuser());
       holder.binding.tvLassMsg.setText("KETNOIVIET chúc mừng năm mới");
       String anhDaiDien= Server.userget+user.getImageuser();
       Glide.with(context).load(anhDaiDien)
                .into(holder.binding.imgUser);
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(context, chatActivity.class);
               intent.putExtra("nameUser",user.getNameuser());
               intent.putExtra("imageUser", Server.userget+user.getImageuser());
               intent.putExtra("phoneUser", user.getPhonenumber());
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        if (listUser != null) { // Kiểm tra xem danh sách có null không
            return listUser.size();
        } else {
            return 0; // Trả về 0 nếu danh sách là null
        }
    }

    public class userViewHolder extends RecyclerView.ViewHolder{
    RowConversationBinding binding;
        public userViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= RowConversationBinding.bind(itemView);

        }
    }


}
