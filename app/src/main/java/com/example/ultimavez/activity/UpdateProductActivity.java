package com.example.ultimavez.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ultimavez.R;
import com.example.ultimavez.fragment.ErrorInflator;
import com.example.ultimavez.fragment.SuccessFragment;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Product;
import com.example.ultimavez.model.enums.CategoryEnum;
import com.example.ultimavez.service.ProductService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class UpdateProductActivity extends AppCompatActivity {

    private EditText searchedProduct, productName, productDescription, productValue;
    private Spinner productCategory;
    private Button btnSave, btnSelectImage, btnSearch, btnDelete;
    private ImageView productImage;

    private static final int IMAGE_PICKER_REQUEST = 1;
    private final ProductService productService = new ProductService();
    private Product productToBeUpdated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_product);
        getSupportActionBar().hide();

        inicializarComponentes();
        
        btnSearch.setOnClickListener(it -> findProduct());
        btnSelectImage.setOnClickListener(it -> selectImage());
        btnSave.setOnClickListener(it -> updateProduct());
        btnDelete.setOnClickListener(it -> deleteProduct());
    }

    private void inicializarComponentes() {
        searchedProduct = findViewById(R.id.editTextProductSearchName);
        productName = findViewById(R.id.editTextProductNewName);
        productDescription = findViewById(R.id.editTextNewDescription);
        productValue = findViewById(R.id.editTextNewValue);
        productCategory = findViewById(R.id.spNewCategoria);
        btnSave = findViewById(R.id.btnNewSave);
        btnSelectImage = findViewById(R.id.btnNewSelectImage);
        btnSearch = findViewById(R.id.btnSearchUpdPr);
        btnDelete = findViewById(R.id.btnDelete);
        productImage = findViewById(R.id.imageNewProduct);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(adapter);
    }

    private void findProduct() {
        String productName = searchedProduct.getText().toString().toLowerCase(Locale.ROOT);
        
        Optional<Product> foundProduct = productService.findProduct(productName);
        if (!foundProduct.isPresent()) {
            ErrorInflator.showErrors("Produto não encontrado", this);
        } else {
            productToBeUpdated = foundProduct.get();
            setProductInfoOnDisplay(foundProduct.get());
        }
    }

    private void setProductInfoOnDisplay(Product product) {
        productName.setText(product.getName());
        productDescription.setText(product.getDescription());
        productValue.setText(String.valueOf(product.getPrice()));

        String category = product.getCategory().toString();
        int categoryIndex = getCategoryIndex(category);
        productCategory.setSelection(categoryIndex);

        Drawable productDrawable = byteToDrawable(product.getImage());
        productImage.setImageDrawable(productDrawable);
    }

    private int getCategoryIndex(String category) {
        String[] categoryOptions = {"Gourmet", "Vegano", "Premium", "Especiais"};

        for (int i = 0; i < categoryOptions.length; i++) {
            if (categoryOptions[i].equalsIgnoreCase(category)) {
                return i;
            }
        }

        return 0;
    }

    private Drawable byteToDrawable(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        return new BitmapDrawable(getResources(), bitmap);
    }

    @SuppressLint("IntentReset")
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");

        startActivityForResult(intent, IMAGE_PICKER_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICKER_REQUEST) {
                Uri selectedImage = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    productImage.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateProduct() {
        if (productToBeUpdated == null) {
            return;
        }
        
        Product product = buildFromInput();
        product.setId(productToBeUpdated.getId());
        Result<Product> result = productService.updateProduct(product);

        if (!result.isValid()) {
            showErrors(result.getErrors());
        } else {
            showSuccess("Produto atualizado com sucesso");
        }
    }


    private void deleteProduct() {
        if (productToBeUpdated == null) {
            return;
        }

        Result<Product> result = productService.deleteProduct(productToBeUpdated.getId());


        if (!result.isValid()) {
            showErrors(result.getErrors());
        } else {
            showSuccess("Produto deletado com sucesso");
        }
    }

    private Product buildFromInput() {
        String name = productName.getText().toString().toLowerCase(Locale.ROOT);
        CategoryEnum category = CategoryEnum.valueOf(productCategory.getSelectedItem().toString().toUpperCase(Locale.ROOT));
        String description = productDescription.getText().toString().toLowerCase(Locale.ROOT);
        double price = Double.parseDouble(productValue.getText().toString());
        byte[] image = getImageAsByteArray();

        return new Product(name, category, description, price, image);
    }

    private byte[] getImageAsByteArray() {
        try {
            Bitmap originalBitmap = getOriginalBitmap();
            if (originalBitmap == null) {
                return null;
            }

            Bitmap resizedBitmap = resizeBitmap(originalBitmap, 200, 200);
            return convertBitmapToByteArray(resizedBitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private Bitmap getOriginalBitmap() {
        BitmapDrawable bitmapDrawable = (BitmapDrawable) productImage.getDrawable();
        return (bitmapDrawable != null) ? bitmapDrawable.getBitmap() : null;
    }

    private Bitmap resizeBitmap(Bitmap originalBitmap, int newWidth, int newHeight) {
        return Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, false);
    }

    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 95, stream);
        return stream.toByteArray();
    }

    private void showErrors(List<String> notifications) {
        ErrorInflator.showErrors(notifications, this);
    }

    private void showSuccess(String message) {
        SuccessFragment successDialog = new SuccessFragment(message, SellerHomePageActivity.class);
        successDialog.show(getSupportFragmentManager(), null);
    }

}
