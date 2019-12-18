/*
 * *
 *  * Copyright (C) 2017 Ryan Kay Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.android.hoangduy.medical.viewmodel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.android.hoangduy.medical.persistence.MedicalRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by R_KAY on 8/17/2017.
 */
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
