package com.example.Samar.capstonetwo.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.Samar.capstonetwo.R;
import com.example.Samar.capstonetwo.dataBase.MyContract;
import com.squareup.picasso.Picasso;


    public class FavouriteDetailActivity extends AppCompatActivity {


     private TextView titleTextView;
     private ImageView posterImageView;
     private TextView authorTextView;
     private int cursorPosition;
     private Cursor cursor;

     public static final String EXTRA_CURSOR_POSITION="position";
     public FavouriteDetailActivity() {

     }

     @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_detail);

         titleTextView=(TextView)findViewById(R.id.title);
         posterImageView=(ImageView)findViewById(R.id.backDrop);
         authorTextView=(TextView)findViewById(R.id.author);

         int positionWidget=getIntent().getIntExtra(EXTRA_CURSOR_POSITION,-1);
         int positionCursor=getIntent().getIntExtra("cursorPosition", -1);
         if( positionWidget > -1)
         {
             cursorPosition=positionWidget;
         }
         else  {
             cursorPosition = positionCursor;
         }




         try {
             cursor = getContentResolver().query(MyContract.sunRedditEntry.CONTENT_URI,
                     null,
                     null,
                     null,
                     null);

         } catch (Exception e) {
             e.printStackTrace();
         }


         cursor.moveToPosition(cursorPosition);
         int titleIndex = cursor.getColumnIndex(MyContract.sunRedditEntry.COLUMN_SUBRED_TITLE);
         int authorIndex = cursor.getColumnIndex(MyContract.sunRedditEntry.COLUMN_SUBRED_AUTHOR);
         int imageIndex = cursor.getColumnIndex(MyContract.sunRedditEntry.COLUMN_SUBRED_IMAGE);

         String title = cursor.getString(titleIndex);
         String author= cursor.getString(authorIndex);
         String image= cursor.getString(imageIndex);

         Picasso.with(getApplicationContext()).load(image).placeholder(R.drawable.reddit).into(posterImageView);
         titleTextView.setText(title);
         authorTextView.setText(author);


     }



    }


