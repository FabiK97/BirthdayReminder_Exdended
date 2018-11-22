package com.example.birthdayreminder;

import android.arch.persistence.room.Room;
import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppDatabase database;
    private RecyclerView recyclerView;
    private BirthdayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(this, AppDatabase.class, "birthday_db")
                .allowMainThreadQueries()
                .build();

        recyclerView = findViewById(R.id.birthday_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BirthdayAdapter(this ,database.getBirthdayDao(), new BirthdayAdapter.NotifyInterface() { //Hier wird kein Interface übergeben, sondern eine Anonyme Klasse, die das Interface implementiert
            @Override
            public void notifyEvent(int position) { //nur für das Löschen per LongClick nötig
                adapter.removeItem(position);
            }
        });
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                Toast.makeText(MainActivity.this, "id: " + String.valueOf(viewHolder.itemView.getTag()) + ", position: " + String.valueOf(viewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                int id = (int) viewHolder.itemView.getTag();
                database.getBirthdayDao().deleteBirthdayById(id);
                adapter.removeItem(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(recyclerView);



        Toolbar toolbar = (Toolbar) findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add) {
            startActivity(new Intent(this, AddItemActivity.class));
            return true;
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.updateData(database.getBirthdayDao());

    }

}
