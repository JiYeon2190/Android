package com.example.app2;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;


import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.ImageDownload;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

public class album_activity extends Activity {

	private ImageButton home_btn, diary_btn, graph_btn, album_btn;
	private ImageView image1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.album);

		image1 = findViewById(R.id.image1);

		FirebaseStorage storage = FirebaseStorage.getInstance("gs://detection-73dd9.appspot.com");
		StorageReference storageReference = storage.getReference();
		StorageReference imgReference = storageReference.child("neutral/");

		imgReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
			@Override
			public void onSuccess(ListResult listResult) {
				int i = 0;
				for (StorageReference item : listResult.getItems()) {
					GridLayout list = findViewById(R.id.list);

					ViewGroup.LayoutParams layoutParams1 = image1.getLayoutParams();

					ImageView iv = new ImageView(album_activity.this);
					iv.setLayoutParams(layoutParams1);
					list.addView(iv);

					item.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
						@Override
						public void onComplete(@NonNull Task<Uri> task) {
							if (task.isSuccessful()) {
								Glide.with(album_activity.this).load(task.getResult()).into(iv);

								iv.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View view) {
										ByteArrayOutputStream stream = new ByteArrayOutputStream();
										Bitmap bitmap = ((BitmapDrawable)iv.getDrawable()).getBitmap();
										float scale = (float) (1024/(float)bitmap.getWidth());
										int image_w = (int) (bitmap.getWidth() * scale);
										int image_h = (int) (bitmap.getHeight() * scale);
										Bitmap resize = Bitmap.createScaledBitmap(bitmap, image_w, image_h, true);
										resize.compress(Bitmap.CompressFormat.JPEG, 100, stream);
										byte[] byteArray = stream.toByteArray();

										Intent intent = new Intent(getApplicationContext(), image.class);
										intent.putExtra("image", byteArray);
										startActivity(intent);
									}
								});

								iv.setOnLongClickListener(new View.OnLongClickListener() {
									@Override
									public boolean onLongClick(View view) {
										AlertDialog.Builder alert = new AlertDialog.Builder(album_activity.this);

										alert.setTitle("저장 알림");
										alert.setMessage("이 사진을 갤러리에 저장하시겠습니끼?");
										alert.setPositiveButton("저장", new DialogInterface.OnClickListener() {
											@Override
											public void onClick(DialogInterface dialogInterface, int i) {

												iv.setDrawingCacheEnabled(true);
												Bitmap bitmap = iv.getDrawingCache();
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

										return true;
									}
								});

							} else {
							}
						}
					}).addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception e) {

						}
					});

				}
			}
		});

		//홈 버튼
		home_btn = (ImageButton) findViewById(R.id.vector_ek54);
		home_btn.setOnClickListener(new View.OnClickListener() {
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
		album_btn = findViewById(R.id.vector_ek75);
		album_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), album_activity.class);
				startActivity(intent);
			}
		});

		//웃는 버튼
		TextView smile_btn = (TextView) findViewById(R.id._____ek12);
		smile_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), smile_activity.class);
				startActivity(intent);
			}
		});

		//슬픈 버튼
		TextView sad_btn = (TextView) findViewById(R.id._____ek13);
		sad_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), sad_activity.class);
				startActivity(intent);
			}
		});

		//자는 버튼
		TextView sleep_btn = (TextView) findViewById(R.id._____ek14);
		sleep_btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), sleep_activity.class);
				startActivity(intent);
			}
		});
	}
}

