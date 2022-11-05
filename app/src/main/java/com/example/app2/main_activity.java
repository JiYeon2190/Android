package com.example.app2;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.app2.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.StringTokenizer;

public class main_activity extends Activity {
	private TextView cds;
	private TextView ppm;
	private TextView temp;
	private Button opencvCamera;

	DatabaseReference mydb;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		cds = findViewById(R.id.cds);
		ppm = findViewById(R.id.ppm);
		temp = findViewById(R.id.temp);
		opencvCamera = findViewById(R.id.camera1);

		mydb = FirebaseDatabase.getInstance("https://ibom-b80f4-default-rtdb.firebaseio.com/").getReference();
		mydb.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(@NonNull DataSnapshot snapshot) {
				Double cdsData = snapshot.child("cds").getValue(Double.class);
				Double ppmData = snapshot.child("ppm").getValue(Double.class);
				Double tempData = snapshot.child("temp").getValue(Double.class);;

				cds.setText(cdsData + " lux");
				ppm.setText(String.format("%.2f", ppmData) + " ppm");
				temp.setText(tempData + " °C");
			}

			@Override
			public void onCancelled(@NonNull DatabaseError error) {

			}
		});

		WebView webView = (WebView)findViewById(R.id.webView);
		webView.setWebViewClient(new WebViewClient());
		webView.setBackgroundColor(255);

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webView.getSettings().setUseWideViewPort(true);
		webView.getSettings().setLoadWithOverviewMode(true);

		// 자신이 실행시킨 스트리밍의 주소를 넣어주세요.
		webView.loadUrl("http://192.168.137.110:8081/");

		//관찰하기 버튼
		opencvCamera.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), opencv_activity.class);
				startActivity(intent);
			}
		});

		//마이페이지 버튼
		ImageButton mypage_btn = (ImageButton) findViewById(R.id.ek18);
		mypage_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), mypage_activity.class);
				startActivity(intent);
			}
		});

		//push 버튼
		ImageButton push_btn = (ImageButton) findViewById(R.id.vector_ek20);
		push_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), push.class);
				startActivity(intent);
			}
		});

		//홈 버튼
		ImageButton home_btn = (ImageButton) findViewById(R.id.vector_ek22);
		home_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), main_activity.class);
				startActivity(intent);
			}
		});

		//다이어리 버튼
		ImageButton diary_btn = (ImageButton) findViewById(R.id.vector_ek23);
		diary_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), diary_activity.class);
				startActivity(intent);
			}
		});

		//그래프 버튼
		ImageButton graph_btn = (ImageButton) findViewById(R.id.vector_ek26);
		graph_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), graph_activity.class);
				startActivity(intent);
			}
		});

		//앨범 버튼
		ImageButton album_btn = (ImageButton) findViewById(R.id.vector_ek27);
		album_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getApplicationContext(), album_activity.class);
				startActivity(intent);
			}
		});
	}
}



