package com.android.hoangduy.medical.module;

import android.app.Application;

import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.android.hoangduy.medical.model.dao.MedicationDao;
import com.android.hoangduy.medical.model.dao.SymptomDao;
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
        ).build();
    }

    @Provides
    @Singleton
    MedicalRepository providePatientTrackingRepository(MedicationDao medicationDao, SymptomDao symptomDao) {
        return new MedicalRepository(medicationDao, symptomDao);
    }

    @Provides
    @Singleton
    MedicationDao provideMedicationDao(MedicalDB database) {
        return database.medicationDao();
    }

    @Provides
    @Singleton
    SymptomDao provideSymptomDao(MedicalDB database) {
        return database.symptomDao();
    }

    @Provides
    @Singleton
    MedicalDB provideListItemDatabase(Application application) {
        return database;
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(MedicalRepository repository) {
        return new CustomViewModelFactory(repository);
    }
}
