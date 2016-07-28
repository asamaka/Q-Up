package com.example.android.q_up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.q_up.data.QueueDbHelper;


public class MainActivity extends AppCompatActivity {

    private RecyclerView allGuestsListView;
    private TextView newGuestNameView;
    private TextView newPartyCountView;
    private GuestListAdapter cursorAdapter;
    private QueueDbHelper dbHelper;

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
                dbHelper.removePerson(myId);
                cursorAdapter.swapCursor(dbHelper.getAllNames());
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(allGuestsListView);


        dbHelper = new QueueDbHelper(this);

        cursorAdapter = new GuestListAdapter(dbHelper.getAllNames());

        allGuestsListView.setAdapter(cursorAdapter);

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

        dbHelper.addNewPerson(newGuestNameView.getText().toString(), party);

        cursorAdapter.swapCursor(dbHelper.getAllNames());

        //clear UI
        newGuestNameView.setText("");
        newPartyCountView.setText("");
        newPartyCountView.clearFocus();
    }

}
