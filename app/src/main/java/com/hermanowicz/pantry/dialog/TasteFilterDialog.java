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

package com.hermanowicz.pantry.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.hermanowicz.pantry.R;
import com.hermanowicz.pantry.interfaces.IFilterDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * <h1>TasteFilterDialog</h1>
 * The dialog window needed to set filters by taste to search for products in the pantry.
 *
 * @author  Mateusz Hermanowicz
 * @version 1.0
 * @since   1.0
 */
public class TasteFilterDialog extends AppCompatDialogFragment {

    @BindView(R.id.spinner_taste)
    Spinner spinnerTaste;
    @BindView(R.id.button_clear)
    Button btnClear;

    private IFilterDialogListener dialogListener;
    private String filterTaste;

    public TasteFilterDialog(String filterTaste) {
        this.filterTaste = filterTaste;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Activity activity = getActivity();
        assert activity != null;
        Context context = activity.getApplicationContext();
        Resources resources = context.getResources();

        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppThemeDialog);

        LayoutInflater layoutInflater = activity.getLayoutInflater();

        View view = layoutInflater.inflate(R.layout.dialog_taste, null);

        ButterKnife.bind(this, view);

        String[] tasteArray = resources.getStringArray(R.array.ProductDetailsActivity_taste_array);

        ArrayAdapter<CharSequence> tasteAdapter = ArrayAdapter.createFromResource(context, R.array.ProductDetailsActivity_taste_array, android.R.layout.simple_spinner_item);
        tasteAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTaste.setAdapter(tasteAdapter);

        try {
            for (int i = 1; i < tasteArray.length; i++) {
                if (filterTaste.equals(tasteArray[i])) {
                    spinnerTaste.setSelection(i);
                    spinnerTaste.setBackgroundColor(Color.rgb(200, 255, 200));
                }
            }
        } catch (NullPointerException e) {
            spinnerTaste.setSelection(0);
            spinnerTaste.setBackgroundColor(Color.TRANSPARENT);
        }

        filterTaste = String.valueOf(spinnerTaste.getSelectedItem());

        btnClear.setOnClickListener(view14 -> {
            spinnerTaste.setSelection(0);
            spinnerTaste.setBackgroundColor(Color.TRANSPARENT);
            filterTaste = null;
        });

        spinnerTaste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        builder.setView(view)
                .setTitle(resources.getString(R.string.ProductDetailsActivity_taste))
                .setNegativeButton(resources.getString(R.string.MyPantryActivity_cancel), (dialog, which) -> {
                })
                .setPositiveButton(resources.getString(R.string.MyPantryActivity_set), (dialog, which) -> {
                    filterTaste = String.valueOf(spinnerTaste.getSelectedItem());
                    if (filterTaste.equals(tasteArray[0])) {
                        dialogListener.clearFilterTaste();
                    } else {
                        dialogListener.setFilterTaste(filterTaste);
                    }
                });
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dialogListener = (IFilterDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }
}