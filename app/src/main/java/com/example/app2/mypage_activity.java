package com.example.app2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//	import androidx.annotation.Nullable;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class mypage_activity extends Activity {
	private TextView logout;
	private ImageButton home_btn, diary_btn, graph_btn, album_btn, back;
	private FirebaseAuth firebaseAuth;

	//로그아웃
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypage);

		firebaseAuth = FirebaseAuth.getInstance();

		back = findViewById(R.id.back);
		back.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), main_activity.class);
				startActivity(intent);
			}
		});

		logout = findViewById(R.id.logout);
		logout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), login_activity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				firebaseAuth.signOut();
			}
		});

		//전화걸기
		//id로 버튼 찾아서 정의
		Button button = (Button)findViewById(R.id.call);
		//버튼 클릭 했을 때 이벤트
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//전화 어플에 있는 Activity 정보를 넣어 Intent 정의
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:02-2323-3232"));
				startActivity(intent);
			}
		});

		//홈 버튼
		home_btn = (ImageButton) findViewById(R.id.vector_ek22);
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
		album_btn = (ImageButton) findViewById(R.id.vector_ek27);
		album_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), album_activity.class);
				startActivity(intent);
			}
		});

	}
}

