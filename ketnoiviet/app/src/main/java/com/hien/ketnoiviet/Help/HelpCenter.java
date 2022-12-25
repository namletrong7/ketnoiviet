package com.hien.ketnoiviet.Help;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.rpc.Help;
import com.hien.ketnoiviet.R;

import org.w3c.dom.Text;

public class HelpCenter extends AppCompatActivity {

    Toolbar close_helpCenter;
    TextView tvHuongDanSuDung ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        tvHuongDanSuDung=(TextView) findViewById(R.id.tvHuongDanSuDung);
        event();
    }

    private void event() {
        close_helpCenter = findViewById(R.id.close_helpCenter);
        close_helpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpCenter.this.finish();
            }
        });
        tvHuongDanSuDung.setText("Đây là hướng dẫn sử dụng của app kết nối việt");
    }
}