package com.example.app2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyAdapter2 extends ArrayAdapter<Board> {
    public MyAdapter2(@NonNull Context context, ArrayList<Board> boardArrayList) {
        super(context, 0, boardArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
        }

        Board board = getItem(position);

        TextView title = listitemView.findViewById(R.id.title);
        TextView content = listitemView.findViewById(R.id.content);
        TextView date = listitemView.findViewById(R.id.date);
        ImageButton img = listitemView.findViewById(R.id.img);

        title.setText(board.getTitle());
        content.setText(board.getContent());
        date.setText(board.getDate());

        Picasso.get().load(board.getImgUrl()).into(img);

        DatabaseReference database2 = FirebaseDatabase.getInstance("https://ibom-b80f4-default-rtdb.firebaseio.com/").getReference();

        listitemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), diary4.class);
                intent.putExtra("id", board.getId());
                getContext().startActivity(intent);
            }
        });

        listitemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());

                alert.setTitle("삭제하시겠습니까?");
                alert.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        database2.child("Diary").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                database2.child("Diary").child(board.getId()).removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
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

        return listitemView;
    }
}
