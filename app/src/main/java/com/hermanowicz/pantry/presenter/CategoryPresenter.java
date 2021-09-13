/*
 * Copyright (c) 2019-2021
 * Mateusz Hermanowicz - All rights reserved.
 * My Pantry
 * https://www.mypantry.eu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hermanowicz.pantry.presenter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.preference.PreferenceManager;

import com.hermanowicz.pantry.db.category.Category;
import com.hermanowicz.pantry.interfaces.CategoryDbResponse;
import com.hermanowicz.pantry.interfaces.CategoryView;
import com.hermanowicz.pantry.model.AppSettingsModel;
import com.hermanowicz.pantry.model.CategoryModel;
import com.hermanowicz.pantry.util.PremiumAccess;

import java.util.List;

/**
 * <h1>CategoryPresenter</h1>
 * Presenter for CategoriesActivity
 *
 * @author  Mateusz Hermanowicz
 */

public class CategoryPresenter implements CategoryDbResponse {

    private final CategoryView view;
    private final CategoryModel model;
    private final PremiumAccess premiumAccess;
    private final AppSettingsModel appSettingsModel;
    private final CategoryDbResponse dbResponse;

    public CategoryPresenter (@NonNull CategoryView view, @NonNull Context context
            , @NonNull CategoryDbResponse dbResponse){
        this.view = view;
        this.model = new CategoryModel(context);
        this.appSettingsModel = new AppSettingsModel(PreferenceManager.
                getDefaultSharedPreferences(context));
        this.premiumAccess = new PremiumAccess(context);
        this.dbResponse = dbResponse;

        model.setDatabaseMode(getDatabaseMode());
        if(getDatabaseMode().equals("local")){
            model.setOfflineDbCategoryList();
        }
    }

    public void updateCategoryListView(){
        List<Category> categoryList = model.getCategoryList();
        int categoryListSize = categoryList.size();
        view.updateCategoryViewAdapter(categoryList);
        view.showEmptyCategoryListStatement(categoryListSize == 0);
    }

    public void addCategory(@NonNull Category category) {
        if(model.addCategory(category)) {
            view.onSuccessAddNewCategory();
            view.setOnlineDbCategoryList(dbResponse);
            if(getDatabaseMode().equals("local")){
                model.setOfflineDbCategoryList();
            }
            updateCategoryListView();
        }
        else
            view.showErrorAddNewCategory();
    }

    public void navigateToMainActivity() {
        view.navigateToMainActivity();
    }

    public boolean isPremium() {
        return premiumAccess.isPremium();
    }

    public List<Category> getCategoryList() {
        return model.getCategoryList();
    }

    public void setOnlineCategoryList(@NonNull List<Category> categoryList) {
        if(getDatabaseMode().equals("online")) {
            model.setCategoryList(categoryList);
            view.showEmptyCategoryListStatement(categoryList.size() == 0);
        }
        else
            model.setOfflineDbCategoryList();
    }

    public String getDatabaseMode(){
        return appSettingsModel.getDatabaseMode();
    }

    @Override
    public void onResponse(List<Category> categoryList) {
        model.setCategoryList(categoryList);
    }
}