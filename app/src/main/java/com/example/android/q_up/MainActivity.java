package com.example.android.q_up;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks {

    private static final String[] COLUMNS_TO_BE_BOUND = new String[]{
            QueueContract.QueueEntry.COLUMN_NAME,
            QueueContract.QueueEntry.COLUMN_PARTY
    };
    private static final int[] LAYOUT_ITEMS_TO_FILL = new int[]{
            android.R.id.text1,
            android.R.id.text2
    };
    private ListView allGuestsListView;
    private TextView newGuestNameView;
    private TextView newPartyCountView;
    private SimpleCursorAdapter cursorAdapter;
    private QueueDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allGuestsListView = (ListView) this.findViewById(R.id.all_guests_list);
        newGuestNameView = (TextView) this.findViewById(R.id.person_name_text);
        newPartyCountView = (TextView) this.findViewById(R.id.party_count_text);

        db = new QueueDbHelper(this);
        cursorAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.two_line_list_item,
                null,
                COLUMNS_TO_BE_BOUND,
                LAYOUT_ITEMS_TO_FILL,
                0);

        allGuestsListView.setAdapter(cursorAdapter);


        getSupportLoaderManager().initLoader(0, null, this);

    }

    public void addToQ(View view) {
        //insert in db
        db.addNewPerson(newGuestNameView.getText().toString(), Integer.parseInt(newPartyCountView.getText().toString()));

        //Do i need this ?
        cursorAdapter.notifyDataSetChanged();

        //clear current data
        newGuestNameView.setText("");
        newPartyCountView.setText("");
        newGuestNameView.requestFocus();
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this, null, null, null, null, null) {
            @Override
            public Cursor loadInBackground() {
                return db.getAllNames();
            }
        };
    }

    @Override
    public void onLoadFinished(Loader loader, Object data) {
        cursorAdapter.swapCursor((Cursor) data);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        cursorAdapter.swapCursor(null);
    }
}
