package com.example.android.q_up;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.q_up.data.QueueContract;

/**
 * Created by asser on 7/22/16.
 */

public class GuestListAdapter extends CursorAdapter{

    public GuestListAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.guest_list_item, viewGroup, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //is there a better pattern to do this lookup ?
        long id = cursor.getLong(cursor.getColumnIndex(QueueContract.QueueEntry._ID));
        String name = cursor.getString(cursor.getColumnIndex(QueueContract.QueueEntry.COLUMN_NAME));
        int party = cursor.getInt(cursor.getColumnIndex(QueueContract.QueueEntry.COLUMN_PARTY));

        TextView nameTextView = (TextView) view.findViewById(R.id.name_text_view);
        TextView partyTextView = (TextView) view.findViewById(R.id.party_text_view);

        view.setTag(id);
        nameTextView.setText(name);
        partyTextView.setText("("+party+")");

    }
}
