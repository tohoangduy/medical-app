package com.android.hoangduy.medical.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.hoangduy.medical.persistence.MedicalRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomViewModelFactory implements ViewModelProvider.Factory {
    private final MedicalRepository repository;

    @Inject
    public CustomViewModelFactory(MedicalRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainMenuViewModel.class))
            return (T) new MainMenuViewModel(repository);
        else if (modelClass.isAssignableFrom(AddMedicationViewModel.class))
            return (T) new AddMedicationViewModel(repository);
        else if (modelClass.isAssignableFrom(AddSymptomViewModel.class))
            return (T) new AddSymptomViewModel(repository);
        else {
            throw new IllegalArgumentException("ViewModel Not Found");
        }
    }
}
