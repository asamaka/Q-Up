package com.example.android.q_up;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.q_up.data.QueueContract;

import java.util.ArrayList;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder>{

    private ArrayList<GuestInfo> guestList;

    public GuestListAdapter(Cursor cursor){
        super();
        swapCursor(cursor);
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        GuestViewHolder viewholder = new GuestViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {

        Log.d("Binding","Just called the bind view holder method!");
        GuestInfo guest = guestList.get(position);
        if(guest==null)
            return;
        long id = guest.id;
        String name = guest.name;
        int party = guest.party;

        holder.itemView.setTag(id);
        holder.nameTextView.setText(name);
        holder.partyTextView.setText("("+party+")");
    }


    @Override
    public int getItemCount() {
        return guestList.size();
    }

    public void swapCursor(Cursor newCursor) {
        guestList = new ArrayList<>();
        while(newCursor.moveToNext()){
            //is there a better pattern to do this lookup ?
            long id = newCursor.getLong(newCursor.getColumnIndex(QueueContract.QueueEntry._ID));
            String name = newCursor.getString(newCursor.getColumnIndex(QueueContract.QueueEntry.COLUMN_NAME));
            int party = newCursor.getInt(newCursor.getColumnIndex(QueueContract.QueueEntry.COLUMN_PARTY));
            guestList.add(new GuestInfo(id,name,party));
        }
        newCursor.close();
        this.notifyDataSetChanged();

    }

    class GuestInfo{
        public GuestInfo(long i,String n,int p){
            id = i;
            name = n;
            party = p;
        }
        long id;
        String name;
        int party;
    }

    class GuestViewHolder extends RecyclerView.ViewHolder{

        // Will display the guest name
        TextView nameTextView;
        // Will display the party number
        TextView partyTextView;

        /**
         * Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews and set an onClickListener to listen for clicks. Those will be handled in the
         * onClick method below.
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
