package com.example.android.q_up.data;

import android.provider.BaseColumns;

/**
 * Created by asser on 7/21/16.
 */
public class QueueContract {

    /* Inner class that defines the table contents of the queue table */
    public static final class QueueEntry implements BaseColumns {

        public static final String TABLE_NAME = "queue";

        public static final String COLUMN_NAME = "name";

        public static final String COLUMN_PARTY = "party";

        //TODO: add this to the database when a new person is added
        public static final String COLUMN_TIME = "time";

    }
}
