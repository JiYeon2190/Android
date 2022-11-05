package com.example.app2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_activity extends Activity {
	private EditText id;
	private EditText passwd;
	private Button loginBtn;

	private FirebaseAuth firebaseAuth;
	private FirebaseAuth.AuthStateListener firebaseAuthListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		firebaseAuth = FirebaseAuth.getInstance();

		id = findViewById(R.id.id);
		passwd = findViewById(R.id.passwd);
		loginBtn = findViewById(R.id.loginBtn);

		loginBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				loginUser(id.getText().toString(), passwd.getText().toString());
			}
		});

		firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				FirebaseUser user = firebaseAuth.getCurrentUser();
				if (user != null) {
					Intent intent = new Intent(login_activity.this, main_activity.class);
					startActivity(intent);
					finish();
				} else {}
			}
		};
	}

	public void loginUser (String email, String password) {
		firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(@NonNull Task<AuthResult> task) {
				if (task.isSuccessful()) {
					FirebaseUser user = firebaseAuth.getCurrentUser();
					updateUI(user);
				} else {
					updateUI(null);
				}
			}
		});
	}

	protected void onStart() {
		super.onStart();
		FirebaseUser currentUser = firebaseAuth.getCurrentUser();
		updateUI(currentUser);
	}

	protected void onStop() {
		super.onStop();
		if(firebaseAuthListener != null) {
			firebaseAuth.removeAuthStateListener(firebaseAuthListener);
		}
	}

	private void updateUI(FirebaseUser user) {
		if(user != null) {
			Intent intent = new Intent(this, main_activity.class);
			startActivity(intent);
			finish();
		}
	}
}
	
	