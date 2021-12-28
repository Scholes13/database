package com.example.database.Data.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.databasempii.Data.DAO.MahasiswaDAO;
import com.example.databasempii.Data.Model.Mahasiswa;

@Database(entities = {Mahasiswa.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MahasiswaDAO userDao();
}
