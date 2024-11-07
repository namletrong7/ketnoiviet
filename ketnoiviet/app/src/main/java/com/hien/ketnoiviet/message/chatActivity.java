package com.hien.ketnoiviet.message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hien.ketnoiviet.Discover.PostActivity;
import com.hien.ketnoiviet.Home.HomeActivity;
import com.hien.ketnoiviet.Personal.UnapprovedPosts;
import com.hien.ketnoiviet.Personal.UpdateInformation;
import com.hien.ketnoiviet.model.Message;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.hien.ketnoiviet.R;
import com.hien.ketnoiviet.adapter.mesageAdapter;
import com.hien.ketnoiviet.ultil.sound;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatActivity extends AppCompatActivity {
    ImageView imgBack;
    RecyclerView recycler_mess;
    EditText edtMess;
    Boolean isCheck ;
    CircleImageView imgSend, imgUser;
    TextView tvUserName;
    mesageAdapter adapter;
    ArrayList<com.hien.ketnoiviet.model.Message> listMessage;
    ArrayList<Message> ListMessage1;
    String senderRoom, receiverRoom;
    FirebaseDatabase database;
    ValueEventListener valueEventListener ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // ánh xạ
        imgBack = (ImageView) findViewById(R.id.imageBack);
        recycler_mess = findViewById(R.id.recycler_mess);
        edtMess = findViewById(R.id.edtMess);
        imgSend = findViewById(R.id.imgSend);
        imgUser = findViewById(R.id.imageUser);
        tvUserName = findViewById(R.id.tvNameUser);
        listMessage = new ArrayList<>();
        ListMessage1 = new ArrayList<>();
        adapter = new mesageAdapter(chatActivity.this, listMessage);
        recycler_mess.setLayoutManager(new LinearLayoutManager(chatActivity.this));
        recycler_mess.setAdapter(adapter);
        //
        String tenNguoiNhan = getIntent().getStringExtra("nameUser");
        tvUserName.setText(tenNguoiNhan);
        String imgUser = getIntent().getStringExtra("imageUser");
        Glide.with(getApplicationContext()).load(imgUser).into((CircleImageView) findViewById(R.id.imageUser));
        String recevierId = getIntent().getStringExtra("phoneUser");
        String senderId = HomeActivity.phone_number_user;

        senderRoom = senderId + recevierId;
        receiverRoom = recevierId + senderId;

        // trở về
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        database = FirebaseDatabase.getInstance();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listMessage.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        Message ms = snapshot1.getValue(Message.class);
                        listMessage.add(ms);
                    }

                    adapter.notifyDataSetChanged();
                    recycler_mess.scrollToPosition(listMessage.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        //---------------------------------------------------------------------------------------
        // lấy dữ liệu tin nhắn từ fire base để đưa ra đoạn chát
        database.getReference().child("chats")
                .child(senderRoom)
                .child("Messages")
                .addValueEventListener(valueEventListener);

        String randomKey = database.getReference().push().getKey();
//------------------------------------------------------------------------------
        imgSend.setOnClickListener(new View.OnClickListener() { // khi nhán gửi thì sẽ gửi tin nhắn vào fire base
            @Override
            public void onClick(View v) {
                sound.playSound(chatActivity.this, R.raw.icon_send_message);
                Date date = new Date();
                String time =String.valueOf(date.getTime());
                String mess = edtMess.getText().toString();
                edtMess.setText("");
                String nguoiGui = HomeActivity.phone_number_user;
                Message ms = new Message(mess, senderId, time, tenNguoiNhan, nguoiGui);
                database.getReference()
                        .child("chats")
                        .child(senderRoom)
                        .child("Messages").push()
                        .setValue(ms).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                database.getReference().child("chats")
                                        .child(receiverRoom)
                                        .child("Messages")
                                        .push()
                                        .setValue(ms).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {

                                            }
                                        });
                            }
                        });

            }
        });
//------------------------------------------------------------------------------
        // nhận thông báo mới nhất từ tin nhắn của người khác
//        database.getReference().child("chats")
//                .child(receiverRoom)
//                .child("Messages")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        ListMessage1.clear();
//                        if (snapshot.exists()) {
//                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
//                                Message ms = snapshot1.getValue(Message.class);
//                                ListMessage1.add(ms);
//
//                            }
//
//                            Thongbaoday(ListMessage1.get(ListMessage1.size() - 1).getSenderName(), ListMessage1.get(ListMessage1.size() - 1).getMessage());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
     event();
    }

    private void Thongbaoday(String userName, String mess) {  // tạo thông báo trên thanh trạng thái của điện thọi

        Intent intent = new Intent(this, UnapprovedPosts.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(getNotificationId(), PendingIntent.FLAG_CANCEL_CURRENT);
        // --------------------------------------------------------------------
        NotificationCompat.Builder builder = new NotificationCompat.Builder(chatActivity.this, "Thông báo");
        builder.setContentTitle(userName);
        builder.setContentText(mess);
        builder.setSmallIcon(R.drawable.icon_message);
        builder.setAutoCancel(true);
        builder.setContentIntent(pendingIntent);


        //------------------------------------------------------------------------

        NotificationManagerCompat manager = NotificationManagerCompat.from(chatActivity.this);
        manager.notify(getNotificationId(), builder.build());
    }
    public void event(){
        findViewById(R.id.btnCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(chatActivity.this, "Tính năng này đang trong quá trình phát triển\n Xin lỗi về điều này", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.btnVideoCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(chatActivity.this, "Tính năng này đang trong quá trình phát triển\n Xin lỗi về điều này", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private int getNotificationId() {
        return (int) new Date().getTime();
    }

    @Override
    protected void onDestroy() {
        database.getReference().removeEventListener(valueEventListener);
        super.onDestroy();
        database=null ;
        valueEventListener=null;
    }
}