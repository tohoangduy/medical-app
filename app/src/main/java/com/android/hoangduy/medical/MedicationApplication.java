package com.android.hoangduy.medical;

import android.app.Application;

import com.android.hoangduy.medical.component.ApplicationComponent;
import com.android.hoangduy.medical.component.DaggerApplicationComponent;
import com.android.hoangduy.medical.module.ApplicationModule;
import com.android.hoangduy.medical.module.RoomModule;

public class MedicationApplication extends Application {
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .roomModule(new RoomModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}