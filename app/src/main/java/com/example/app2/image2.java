package com.example.app2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class image2 extends Activity {
    private ImageView image;
    private Button save;
    private ImageButton back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img);

        image = findViewById(R.id.image);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), smile_activity.class);
                startActivity(intent);
            }
        });

		byte[] byteArray = getIntent().getByteArrayExtra("image");
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
		image.setImageBitmap(bitmap);

		save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(image2.this);

                alert.setTitle("저장 알림");
                alert.setMessage("이 사진을 갤러리에 저장하시겠습니끼?");
                alert.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        image.setDrawingCacheEnabled(true);
                        Bitmap bitmap = image.getDrawingCache();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
                        Date date = new Date();
                        MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, simpleDateFormat.format(date), "");

                        Toast.makeText(getApplicationContext(), "저장되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.show();
            }
        });
    }
}
