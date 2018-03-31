package cz.uhk.fim.arsenic.core.repository.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import cz.uhk.fim.arsenic.core.repository.entity.SavedCurrency;

@Dao
public interface SavedCurrencyDao {

    @Query("SELECT * FROM saved_currency ORDER BY rank")
    List<SavedCurrency> findAll();

    @Query("SELECT * FROM saved_currency  WHERE id = :id")
    SavedCurrency findCurrencyById(String id);

    @Insert
    void saveCurrency(SavedCurrency savedCurrency);

    @Delete
    void deleteCurrency(SavedCurrency savedCurrency);
}
