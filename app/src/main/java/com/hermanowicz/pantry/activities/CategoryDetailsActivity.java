package com.hermanowicz.pantry.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.widget.Toast;

import com.hermanowicz.pantry.R;
import com.hermanowicz.pantry.databinding.ActivityCategoryDetailsBinding;
import com.hermanowicz.pantry.db.Category;
import com.hermanowicz.pantry.db.CategoryDb;
import com.hermanowicz.pantry.db.ProductDb;
import com.hermanowicz.pantry.interfaces.CategoryDetailsView;
import com.hermanowicz.pantry.models.CategoryModel;
import com.hermanowicz.pantry.models.DatabaseOperations;
import com.hermanowicz.pantry.presenters.CategoryDetailsPresenter;

public class CategoryDetailsActivity extends AppCompatActivity implements CategoryDetailsView {

    private ActivityCategoryDetailsBinding binding;
    private CategoryDetailsPresenter presenter;
    private Context context;
    private int categoryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        binding = ActivityCategoryDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.buttonUpdateCategory.setOnClickListener(view -> onClickUpdateCategory());
        binding.buttonDeleteCategory.setOnClickListener(view -> onClickDeleteCategory());

        initListeners();

        Intent categoryIntent = getIntent();
        categoryId = categoryIntent.getIntExtra("category_id", 0);
        DatabaseOperations databaseOperations = new DatabaseOperations(ProductDb.getInstance(context), CategoryDb.getInstance(this));
        presenter = new CategoryDetailsPresenter(this, new CategoryModel(databaseOperations));
        presenter.setCategoryId(categoryId);
    }

    private void initListeners(){
        binding.categoryName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                presenter.isCategoryNameCorrect(binding.categoryName.getText().toString());
            }
        });

        binding.categoryDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                presenter.isCategoryDescriptionCorrect(binding.categoryDescription.getText().toString());
            }
        });
    }

    private void onClickUpdateCategory(){
        Category category = presenter.getCategory(categoryId);
        category.setName(binding.categoryName.getText().toString());
        category.setDescription(binding.categoryDescription.getText().toString());
        presenter.updateCategory(category);
    }

    private void onClickDeleteCategory() {
        new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AppThemeDialog))
                .setMessage(R.string.CategoryDetailsActivity_category_delete_warning)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> presenter.deleteCategory(categoryId))
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void showErrorOnUpdateProduct() {
        Toast.makeText(context, getString(R.string.Error_wrong_category_data), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCategoryUpdated() {
        Toast.makeText(context, getString(R.string.CategoryDetailsActivity_category_was_saved), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showCategoryDetails(Category category) {
        binding.categoryName.setText(category.getName());
        binding.categoryDescription.setText(category.getDescription());
    }

    @Override
    public void showCategoryNameError() {
        binding.categoryName.setError(getText(R.string.Error_char_counter));
    }

    @Override
    public void showCategoryDescriptionError() {
        binding.categoryDescription.setError(getText(R.string.Error_char_counter));
    }

    @Override
    public void updateNameCharCounter(int charCounter, int maxChar) {
        binding.nameCharCounter.setText(String.format("%s: %d/%d", getText(R.string.General_char_counter).toString(), charCounter, maxChar));
    }

    @Override
    public void updateDescriptionCharCounter(int charCounter, int maxChar) {
        binding.descriptionCharCounter.setText(String.format("%s: %d/%d", getText(R.string.General_char_counter).toString(), charCounter, maxChar));
    }

    @Override
    public void navigateToCategoriesActivity() {
        Intent intent = new Intent (getApplicationContext(), CategoriesActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, @NonNull KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            presenter.navigateToCategoriesActivity();
        }
        return super.onKeyDown(keyCode, event);
    }
}