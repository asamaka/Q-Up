package com.example.android.q_up.data;

import android.provider.BaseColumns;

/**
 * Created by asser on 7/21/16.
 */
public class QueueContract {

    public static final String[] ALL_GUESTS_LIST_PROJECTION = new String[]{
            QueueEntry._ID,
            QueueEntry.COLUMN_NAME,
            QueueEntry.COLUMN_PARTY
    };

    // these indices must match the projection
    public static final int INDEX_QUEUE_ENTRY_ID = 0;
    public static final int INDEX_QUEUE_ENTRY_NAME = 1;
    public static final int INDEX_QUEUE_ENTRY_PARTY = 2;


    /* Inner class that defines the table contents of the queue table */
    public static final class QueueEntry implements BaseColumns {

        public static final String TABLE_NAME = "queue";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_PARTY = "party";

    }
}
