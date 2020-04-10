package com.example.youdisenextlevel.Controller;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.youdisenextlevel.Model.Database.ImageManage;
import com.example.youdisenextlevel.Model.Products;
import com.example.youdisenextlevel.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddProductActivity extends AppCompatActivity {

    private String categoryName,nameProduct,descriptionProduct,priceProduct, saveCurrentDate, imageUrl;
    private EditText inputProductName, inputProductDescription, inputProductPrice;
    private ImageView inputProductImage;
    private Button addNewProductButton;

    private static final int galleryPick = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        categoryName = getIntent().getExtras().get("category").toString();
        Toast.makeText(this, categoryName, Toast.LENGTH_SHORT).show();

        initView();

        inputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        addNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

    }

    private void validation() {
        nameProduct = inputProductName.getText().toString().trim();
        descriptionProduct = inputProductDescription.getText().toString().trim();
        priceProduct = inputProductPrice.getText().toString().trim();

        if (imageUri == null){
            Toast.makeText(this, getString(R.string.mandatory_image_uri), Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nameProduct)){
            inputProductName.setError(getString(R.string.empty_name_product));
            inputProductName.requestFocus();
        }
        else if (TextUtils.isEmpty(descriptionProduct)){
            inputProductDescription.setError(getString(R.string.empty_description_product));
            inputProductDescription.requestFocus();
        }
        else if (TextUtils.isEmpty(priceProduct)){
            inputProductPrice.setError(getString(R.string.empty_price_product));
            inputProductPrice.requestFocus();
        }
        else{
            storeProduct();
        }
    }

    private void storeProduct() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        saveCurrentDate =  currentDate.format(calendar.getTime());

        SimpleDateFormat imageDate = new SimpleDateFormat("yyyyMMddHHmmss");
        imageUrl =  imageDate.format(calendar.getTime())+ "." + ImageManage.extention(getContentResolver(),imageUri);

        int price = Integer.parseInt(priceProduct);
        Products products = new Products(nameProduct, categoryName, descriptionProduct, price, saveCurrentDate, imageUrl);
        boolean isAdded = products.insertProduct();
        if (isAdded){
            ImageManage.addImage(
                    imageUri,
                    imageUrl,
                    taskSnapshot -> {System.out.println("image insert ok"); });
            Toast.makeText(this, getString(R.string.product_added), Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, getString(R.string.error_addproduct), Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        inputProductName = (EditText) findViewById(R.id.product_name2);
        inputProductDescription = (EditText) findViewById(R.id.product_description2);
        inputProductPrice = (EditText) findViewById(R.id.product_price2);
        inputProductImage = (ImageView) findViewById(R.id.select_product_image2);
        addNewProductButton = (Button) findViewById(R.id.add_new_product_btn2);

        inputProductName.setText("Yamaha");
        inputProductDescription.setText("joli");
        inputProductPrice.setText("10000");
    }

    private void openGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, galleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == galleryPick && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            inputProductImage.setImageURI(imageUri);
        }
    }
}
