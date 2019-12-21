package com.android.hoangduy.medical.module;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.android.hoangduy.medical.model.dao.MedicationDao;
import com.android.hoangduy.medical.model.dao.SymptomDao;
import com.android.hoangduy.medical.model.dao.SymptomTrackingDao;
import com.android.hoangduy.medical.persistence.MedicalDB;
import com.android.hoangduy.medical.persistence.MedicalRepository;
import com.android.hoangduy.medical.viewmodel.CustomViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private final MedicalDB database;

    public RoomModule(Application application) {
        this.database = Room.databaseBuilder(
                application,
                MedicalDB.class,
                "Medical.db"
        )
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    MedicalRepository provideMedicalRepository(
            MedicationDao medicationDao,
            SymptomDao symptomDao,
            SymptomTrackingDao symptomTrackingDao
    ) {
        return new MedicalRepository(medicationDao, symptomDao, symptomTrackingDao);
    }

    @Provides
    @Singleton
    MedicalDB getDatabase(Application application) {
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(MedicalRepository repository) {
        return new CustomViewModelFactory(repository);
    }


    @Provides
    @Singleton
    MedicationDao provideMedicationDao(MedicalDB database) {
        return database.getMedicationDao();
    }

    @Provides
    @Singleton
    SymptomDao provideSymptomDao(MedicalDB database) {
        return database.getSymptomDao();
    }

    @Provides
    @Singleton
    SymptomTrackingDao provideSymptomTrackingDao(MedicalDB database) {
        return database.getSymptomTrackingDao();
    }
}
