package com.example.admin.learngreendao;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import db.ren.ya.bin.DaoMaster;
import db.ren.ya.bin.DaoSession;
import db.ren.ya.bin.MySQLiteOpenHelper;
import db.ren.ya.bin.Note;
import db.ren.ya.bin.NoteDao;

public class MainActivity extends ListActivity {

    @BindView(R.id.noteInput)
    EditText noteEditeTV;

    private SQLiteDatabase sqLiteDatabase;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private NoteDao noteDao;

    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

//        sqLiteDatabase = new DaoMaster.DevOpenHelper(this, "woca", null).getWritableDatabase();
        sqLiteDatabase = new MySQLiteOpenHelper(this,"woca",null).getWritableDatabase();
        daoMaster = new DaoMaster(sqLiteDatabase);
        daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();

        String textColumn = NoteDao.Properties.Text.columnName;
        String orderBy = textColumn + " COLLATE LOCALIZED ASC";
        cursor = sqLiteDatabase.query(noteDao.getTablename(), noteDao.getAllColumns(), null, null, null, null, orderBy);

        String[] from = {textColumn, NoteDao.Properties.Comment.columnName};
        int[] to = {android.R.id.text1, android.R.id.text2};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from, to);
        setListAdapter(adapter);
    }

    @OnClick(R.id.noteAdd) public void addNote(){
        String noteText = noteEditeTV.getText().toString();
        noteEditeTV.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());
        Note note = new Note(null, noteText, comment, new Date(),"我是test啊","");
        noteDao.insert(note);
        Log.d("DaoExample", "Inserted new note, ID: " + note.getId());

        cursor.requery();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        noteDao.deleteByKey(id) ;
        Log.d("DaoExample", "Deleted note, ID: " + id);
        cursor.requery();
    }
}
