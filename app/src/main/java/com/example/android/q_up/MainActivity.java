package com.example.android.q_up;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.q_up.data.QueueDbHelper;


public class MainActivity extends AppCompatActivity {

    private ListView allGuestsListView;
    private TextView newGuestNameView;
    private TextView newPartyCountView;
    private GuestListAdapter cursorAdapter;
    private QueueDbHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allGuestsListView = (ListView) this.findViewById(R.id.all_guests_list);
        newGuestNameView = (TextView) this.findViewById(R.id.person_name_text);
        newPartyCountView = (TextView) this.findViewById(R.id.party_count_text);

        allGuestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                long myId = (long) view.getTag();
                boolean success = db.removePerson(myId);
                cursorAdapter.swapCursor(db.getAllNames());
                return success;
            }
        });

        db = new QueueDbHelper(this);
        cursorAdapter = new GuestListAdapter(
                this,
                null,
                0);

        allGuestsListView.setAdapter(cursorAdapter);

        cursorAdapter.swapCursor(db.getAllNames());
    }

    public void addToQ(View view) {
        //insert in db
        int party = 0;
        try {
            party = Integer.parseInt(newPartyCountView.getText().toString());
        } catch (Exception ex) {
            Log.e("Add to Q Error:", "Failed to parse party text to number");
        }

        db.addNewPerson(newGuestNameView.getText().toString(), party);

        cursorAdapter.swapCursor(db.getAllNames());

        //clear UI
        newGuestNameView.setText("");
        newPartyCountView.setText("");
        newPartyCountView.clearFocus();
    }

}
