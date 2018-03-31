package cz.uhk.fim.arsenic.core.repository;

import android.arch.persistence.room.Room;
import android.content.Context;

public class ApplicationDatabase {

    private static ArsenicDatabase arsenicDatabase;

    public static void initDatabase(Context context) {
        if (arsenicDatabase == null) {
            arsenicDatabase = Room.databaseBuilder(context, ArsenicDatabase.class, "arsenic_db").build();
        }
    }

    public static ArsenicDatabase getArsenicDatabase() {
        return arsenicDatabase;
    }
}
