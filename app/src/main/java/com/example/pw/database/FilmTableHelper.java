package com.example.pw.database;

        import android.provider.BaseColumns;

public class FilmTableHelper implements BaseColumns {


        public static final String TABLE_NAME = "film";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "descritpion";
        public static final String IMAGEPATH = "image_path";
        public static final String BACKDROPPATH = "backdrop_path";
        public static final String API_ID = "api_id";
        public static final String TO_SEE = "to_see";
        public static final String SEEN = "seen";

        public static final String CREATE = "CREATE TABLE " + TABLE_NAME +
                "(" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , "+
                TITLE + " TEXT , " +
                DESCRIPTION + " TEXT , " +
                IMAGEPATH + " TEXT , " +
                BACKDROPPATH + " TEXT , " +
                TO_SEE + " TEXT , " +
                SEEN + " TEXT , " +
                API_ID +" TEXT);";


}
