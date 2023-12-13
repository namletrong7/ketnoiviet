package com.hien.ketnoiviet.adapter;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.databinding.ItemReceiveBinding;
import com.hien.ketnoiviet.databinding.ItemSentBinding;
import com.hien.ketnoiviet.model.Message;
import com.hien.ketnoiviet.model.Post;

import java.util.ArrayList;

public class mesageAdapter  extends RecyclerView.Adapter{
   Context context ;
   ArrayList<Message> listMessage;
   final int ITEM_SENT=1 ;
    final int ITEM_RECIVER=2 ;
    public mesageAdapter (  Context context,ArrayList<Message> listMessage){
     this.context=context;
     this.listMessage=listMessage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       if(viewType==ITEM_SENT){
           View view= LayoutInflater.from(context).inflate(R.layout.item_sent,parent,false);
           return new sentViewHolder(view);
       }
     else{
           View view= LayoutInflater.from(context).inflate(R.layout.item_receive,parent,false);
           return new receiverViewHolder(view);
       }



    }

    @Override
    public int getItemViewType(int position) {
        String id = HomeActivity.phone_number_user;
        Message ms = listMessage.get(position);
        if(id.equals(ms.getSenderId())){
            return ITEM_SENT;
        }else {
            return ITEM_RECIVER ;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
          Message ms = listMessage.get(position);
          if(holder.getClass()==sentViewHolder.class){
              sentViewHolder viewHolder = (sentViewHolder) holder;
            viewHolder.binding.tvSendMess.setText(ms.getMessage());

           }else{
               receiverViewHolder viewHolder = (receiverViewHolder) holder;
              viewHolder.binding.tvRecevierMess.setText(ms.getMessage());
          }
    }


    @Override
    public int getItemCount() {
        if (listMessage != null) { // Kiểm tra xem danh sách có null không
            return listMessage.size();
        } else {
            return 0; // Trả về 0 nếu danh sách là null
        }
    }




    public class sentViewHolder extends RecyclerView.ViewHolder{
         ItemSentBinding binding ;
        public sentViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= ItemSentBinding.bind(itemView);

        }
    }
    public class receiverViewHolder extends RecyclerView.ViewHolder{
          ItemReceiveBinding binding ;

        public receiverViewHolder(@NonNull View itemView) {
            super(itemView);
            binding= ItemReceiveBinding.bind(itemView);
        }
    }
}
