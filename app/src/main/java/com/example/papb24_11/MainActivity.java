package com.example.papb24_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.papb24_11.database.Note;
import com.example.papb24_11.database.NoteDao;
import com.example.papb24_11.database.NoteRoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private NoteDao mNoteDao;
    private ExecutorService executorService;
    private ListView listView;
    private Button buttonAddNote, buttonEditNote;
    private EditText etTitle, etDesc,etDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.lv_notes);
        buttonAddNote = findViewById(R.id.btn_add_note);
        buttonEditNote = findViewById(R.id.btn_update_note);
        etTitle = findViewById(R.id.et_title);
        etDesc = findViewById(R.id.et_desc);
        etDate = findViewById(R.id.et_date);
        //untuk menjalankan di background
        executorService = Executors.newSingleThreadExecutor();

        NoteRoomDatabase db = NoteRoomDatabase.getDatabase(this);
        mNoteDao = db.noteDao();
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = etTitle.getText().toString();
                String desc = etDesc.getText().toString();
                String date = etDate.getText().toString();
                insertData(new Note(title,desc,date));
                setEmptyField();

            }
        });
        getAllNotes();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                etTitle.setText(listView.getAdapter().getItem(i).);
                etDesc.setText("");
                etDate.setText("");
            }
        });

    }

private void setEmptyField(){
        etTitle.setText("");
        etDate.setText("");
        etDesc.setText("");
}


    //function get all note
    private void getAllNotes(){
        mNoteDao.getAllNotes().observe(this,notes -> {
            ArrayAdapter<Note>adapter = new ArrayAdapter<Note>
                (this, android.R.layout.simple_list_item_1, notes);
            listView.setAdapter(adapter);

        });
    }

    //function insert data ke room
    private void insertData(Note note){
        executorService.execute(()->mNoteDao.insert(note));
    }

    //function update data
    private void updateData(Note note){}

    //function delete
    private void deleteData(Note note){}

}
