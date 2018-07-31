package com.example.Samar.capstonetwo.dataBase;

import android.net.Uri;
import android.provider.BaseColumns;


public class MyContract {


    public static final String AUTHORITY = "com.example.Samar.capstonetwo.free";


    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_SUBRED = "subRed";


    public static final class sunRedditEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_SUBRED).build();

        public static final String TABLE_NAME = "subRed";
        public static final String COLUMN_SUBRED_AUTHOR = "author";
        public static final String COLUMN_SUBRED_TITLE = "title";
        public static final String COLUMN_SUBRED_IMAGE = "image";
        public static final String COLUMN_SUBRED_ID= "id";



    }
}
