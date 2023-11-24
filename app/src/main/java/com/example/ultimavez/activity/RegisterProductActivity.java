package com.example.ultimavez.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
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

public class RegisterProductActivity extends AppCompatActivity {

    private EditText productName, productDescription, productValue;
    private Spinner productCategory;
    private Button btnSave, btnSelectImage;
    private ImageView productImage;
    SharedPreferences sharedPreferences;

    private final ProductService productService = new ProductService();

    private static final int IMAGE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_product);
        getSupportActionBar().hide();

        inicializarComponentes();
        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        btnSave.setOnClickListener(it -> saveProduct());
        btnSelectImage.setOnClickListener(it -> selectImage());
    }

    private void inicializarComponentes() {
        productName = findViewById(R.id.editTextProductName);
        productDescription = findViewById(R.id.editTextDescription);
        productValue = findViewById(R.id.editTextValue);
        productCategory = findViewById(R.id.spCategoria);
        btnSave = findViewById(R.id.btnSave);
        btnSelectImage = findViewById(R.id.btnSelectImage);
        productImage = findViewById(R.id.imageProduct);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        productCategory.setAdapter(adapter);
    }

    private void saveProduct() {
        Product product = buildFromInput();
        long sellerId = sharedPreferences.getLong("userId", 0);
        Result<Product> result = productService.saveProduct(product, sellerId);

        if (!result.isValid()) {
            showErrors(result.getErrors());
        } else {
            showSuccess();
        }
    }

    private Product buildFromInput() {


        String name = productName.getText().toString().toLowerCase(Locale.ROOT);
        CategoryEnum category = CategoryEnum.valueOf(productCategory.getSelectedItem().toString().toUpperCase(Locale.ROOT));
        String description = productDescription.getText().toString().toLowerCase(Locale.ROOT);
        double price = productValue.getText().toString().equals("") ? 0.0 : Double.parseDouble(productValue.getText().toString());
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



    @SuppressLint("IntentReset")
    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");

        startActivityForResult(intent, IMAGE_PICKER_REQUEST);
    }

    private void showErrors(List<String> notifications) {
        ErrorInflator.showErrors(notifications, this);
    }

    private void showSuccess() {
        SuccessFragment successDialog = new SuccessFragment("Cadastro realizado com sucesso", SellerHomePageActivity.class);
        successDialog.show(getSupportFragmentManager(), null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICKER_REQUEST) {
                Uri selectedImage = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                    ExifInterface exif = new ExifInterface(inputStream);

                    int orientation = exif.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    InputStream rotatedInputStream = getContentResolver().openInputStream(selectedImage);
                    Bitmap bitmap = BitmapFactory.decodeStream(rotatedInputStream);

                    Matrix matrix = new Matrix();
                    matrix.postRotate(getRotationDegrees(orientation));

                    Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                    productImage.setImageBitmap(rotatedBitmap);

                    inputStream.close();
                    rotatedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private float getRotationDegrees(int orientation) {
        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return 90f;
            case ExifInterface.ORIENTATION_ROTATE_180:
                return 180f;
            case ExifInterface.ORIENTATION_ROTATE_270:
                return 270f;
            default:
                return 0f;
        }
    }

}
