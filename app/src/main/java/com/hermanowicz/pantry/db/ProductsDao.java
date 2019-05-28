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

package com.hermanowicz.pantry.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ProductsDao {
    @Query("SELECT * FROM products WHERE id = (:id)")
    Product getProductById(int id);

    @Query("SELECT * FROM products ORDER BY expirationDate ASC")
    LiveData<List<Product>> getAllProducts();

    @Query("SELECT * FROM products ORDER BY expirationDate ASC")
    List<Product> getAllProductsAsList();

    @Insert
    void insertProductToDB(List<Product> productsArrayList);

    @Query("DELETE FROM products WHERE id = (:id)")
    void deleteProductById(int id);

    @Query("DELETE FROM products")
    void clearDb();
}