package com.example.app2;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;


import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class diary2_activity extends Activity {
	private ImageButton home_btn, graph_btn, album_btn;
	private ImageView imageView;
	private EditText title;
	private EditText content;
	private TextView save;
	private TextView date;
	private TextView cancel;
	private Button add;

	private static final int REQUEST_CODE = 0;

	private final StorageReference reference = FirebaseStorage.getInstance().getReference();

	private Uri imageUri;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diary2);

		date = findViewById(R.id.date);
		title = findViewById(R.id.title);
		content = findViewById(R.id.content);
		save = findViewById(R.id.save);
		add = findViewById(R.id.add);
		imageView = findViewById(R.id.image0);

		date.setText(getTime());

		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				DatabaseReference database = FirebaseDatabase.getInstance("https://ibom-b80f4-default-rtdb.firebaseio.com/").getReference();

				StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));

				fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
					@Override
					public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
						fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
							@Override
							public void onSuccess(Uri uri) {
								String key = database.child("Diary").push().getKey();
								Board board = new Board(key, title.getText().toString(), content.getText().toString(), date.getText().toString(), uri.toString());
								database.child("Diary").child(key).setValue(board);
							}
						});
					}
				});
				Intent intent = new Intent(getApplicationContext(), diary_activity.class);
				startActivity(intent);
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

		cancel = findViewById(R.id.cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), diary_activity.class);
				startActivity(intent);
			}
		});

		add.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, REQUEST_CODE);
			}
		});

	}

	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				try {
					imageUri = data.getData();
					Picasso.get().load(imageUri).error(R.drawable.ic_ibom_background).into(imageView);
				} catch (Exception e) {

				}
			} else if (resultCode == RESULT_CANCELED) {

			}
		}
	}

	private String getFileExtension(Uri uri) {
		ContentResolver cr = getContentResolver();
		MimeTypeMap mime = MimeTypeMap.getSingleton();

		return mime.getExtensionFromMimeType(cr.getType(uri));
	}

	private String getTime() {
		long now = System.currentTimeMillis();
		Date date = new Date(now);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		String getTime = dateFormat.format(date);
		return getTime;
	}
}

