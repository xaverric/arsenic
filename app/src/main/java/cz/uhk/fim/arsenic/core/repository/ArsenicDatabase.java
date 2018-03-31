package cz.uhk.fim.arsenic.core.repository;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import cz.uhk.fim.arsenic.core.repository.dao.SavedCurrencyDao;
import cz.uhk.fim.arsenic.core.repository.entity.SavedCurrency;

@Database(entities = {SavedCurrency.class}, version = 1)
public abstract class ArsenicDatabase extends RoomDatabase {

    public abstract SavedCurrencyDao savedCurrencyDao();
}
