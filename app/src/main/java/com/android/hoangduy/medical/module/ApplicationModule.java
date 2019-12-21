package com.android.hoangduy.medical.module;

import android.app.Application;

import com.android.hoangduy.medical.MedicationApplication;

import dagger.Module;
import dagger.Provides;


@Module
public class ApplicationModule {
    private final MedicationApplication application;

    public ApplicationModule(MedicationApplication application) {
        this.application = application;
    }

    @Provides
    MedicationApplication provideRoomApplication() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }
}
