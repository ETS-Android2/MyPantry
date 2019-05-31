/*
 * Copyright (c) 2019
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

package com.hermanowicz.pantry.interfaces;

import androidx.lifecycle.LiveData;

import com.hermanowicz.pantry.db.Product;

import java.util.ArrayList;
import java.util.List;

public interface IMyPantryActivityView {
    void openFilterDialog(String typeOfDialog);

    void setFilterIcon(int position);

    void clearFilterIcon(int position);

    void showEmptyPantryStatement();

    void clearFilterIcons();

    void navigateToMainActivity();

    void updateSelectsRecyclerViewAdapter();

    void updateProductsRecyclerViewAdapter();

    void onPrintProducts(ArrayList<String> textToQRCodeList, ArrayList<String> namesOfProductsList, ArrayList<String> expirationDatesList);

    void onDeleteProducts(List<Product> productList);

    LiveData<List<Product>> getProductLiveData();
}
