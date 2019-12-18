package com.android.hoangduy.medical.component;

import android.app.Application;

import com.android.hoangduy.medical.fragment.AddMedicationFrgmt;
import com.android.hoangduy.medical.fragment.AddSymptomsFrgmt;
import com.android.hoangduy.medical.fragment.MainFrgmt;
import com.android.hoangduy.medical.fragment.SummaryFrgmt;
import com.android.hoangduy.medical.module.ApplicationModule;
import com.android.hoangduy.medical.module.RoomModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, RoomModule.class})
public interface ApplicationComponent {

    void inject(MainFrgmt mainFrgmt);
    void inject(AddMedicationFrgmt addMedicationFrgmt);
    void inject(AddSymptomsFrgmt addSymptomsFrgmt);
    void inject(SummaryFrgmt summaryFrgmt);

    Application application();

}