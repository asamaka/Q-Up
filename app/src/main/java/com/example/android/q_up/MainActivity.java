package com.example.android.q_up;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.q_up.data.QueueContract;
import com.example.android.q_up.data.QueueDbHelper;


public class MainActivity extends AppCompatActivity {

    private RecyclerView allGuestsListView;
    private TextView newGuestNameView;
    private TextView newPartyCountView;
    private GuestListAdapter cursorAdapter;
    private QueueDbHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allGuestsListView = (RecyclerView) this.findViewById(R.id.all_guests_list);
        newGuestNameView = (TextView) this.findViewById(R.id.person_name_text);
        newPartyCountView = (TextView) this.findViewById(R.id.party_count_text);
        allGuestsListView.setLayoutManager(new LinearLayoutManager(this));


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                long myId = (long) viewHolder.itemView.getTag();
                removePerson(myId);
                cursorAdapter.swapCursor(getAllNames());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(allGuestsListView);


        dbHelper = new QueueDbHelper(this);
        db = dbHelper.getWritableDatabase();

        cursorAdapter = new GuestListAdapter(getAllNames());

        allGuestsListView.setAdapter(cursorAdapter);

    }

    @Override
    protected void onDestroy (){
        super.onDestroy();
        if(db!=null)
            db.close();
    }

    public void addToQ(View view) {
        //check for values first
        if (newGuestNameView.getText().length() == 0 || newPartyCountView.getText().length() == 0)
            return;
        //default party count to 1
        int party = 1;
        try {
            //newPartyCountView inputType="number", so this should always work
            party = Integer.parseInt(newPartyCountView.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e("addToQ format error", "Failed to parse party text to number" + ex.getMessage());
        }

        addNewPerson(newGuestNameView.getText().toString(), party);

        cursorAdapter.swapCursor(getAllNames());

        //clear UI
        newGuestNameView.setText("");
        newPartyCountView.setText("");
        newPartyCountView.clearFocus();
    }


    public Cursor getAllNames() {
        return db.query(
                QueueContract.QueueEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    public long addNewPerson(String name, int party) {
        ContentValues cv = new ContentValues();
        cv.put(QueueContract.QueueEntry.COLUMN_NAME, name);
        cv.put(QueueContract.QueueEntry.COLUMN_PARTY, party);
        return db.insert(QueueContract.QueueEntry.TABLE_NAME, null, cv);
    }

    public boolean removePerson(long id) {
        return db.delete(QueueContract.QueueEntry.TABLE_NAME, QueueContract.QueueEntry._ID + "=" + id, null)>0;
    }

}
