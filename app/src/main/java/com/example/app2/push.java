package com.example.app2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

public class push extends Activity {
    private ImageButton diary_btn, graph_btn, album_btn;
    private ImageButton back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.push);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main_activity.class);
                startActivity(intent);
            }
        });

        //다이어리 버튼
        diary_btn = (ImageButton) findViewById(R.id.vector_ek23);
        diary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), diary_activity.class);
                startActivity(intent);
            }
        });

        //그래프 버튼
        graph_btn = (ImageButton) findViewById(R.id.vector_ek26);
        graph_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), graph_activity.class);
                startActivity(intent);
            }
        });

        //앨범 버튼
        album_btn = (ImageButton) findViewById(R.id.vector_ek27);
        album_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), album_activity.class);
                startActivity(intent);
            }
        });
    };
}
