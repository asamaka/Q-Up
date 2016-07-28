package com.example.android.q_up;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.q_up.data.QueueContract;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private Cursor cursor;

    public GuestListAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        if(!cursor.moveToPosition(position))
            return;

        long id = cursor.getLong(QueueContract.INDEX_QUEUE_ENTRY_ID);
        String name = cursor.getString(QueueContract.INDEX_QUEUE_ENTRY_NAME);
        int party = cursor.getInt(QueueContract.INDEX_QUEUE_ENTRY_PARTY);
        holder.itemView.setTag(id);
        holder.nameTextView.setText(name);
        holder.partyTextView.setText("(" + party + ")");
    }


    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if(cursor!=null)
            cursor.close();
        cursor = newCursor;
        this.notifyDataSetChanged();
    }



    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Will display the guest name
        TextView nameTextView;
        // Will display the party number
        TextView partyTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         *
         * @param itemView The View that you inflated in
         *                 {@link GuestListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partyTextView = (TextView) itemView.findViewById(R.id.party_text_view);
        }

    }
}
