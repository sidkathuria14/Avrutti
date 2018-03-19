package com.example.sidkathuria14.inventory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sidkathuria14.inventory.models.Item;

import java.io.IOException;

public class EditActivity extends AppCompatActivity {
    EditText etName,etDescription,etQuanitity;
    ImageView imgView;
    String name,description;
    int quantity,id;
    public static final String TAG = "inventory";
    public static final int PICK_IMAGE_REQUEST = 123;
    Uri uri_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etName = (EditText)findViewById(R.id.etName);
        etDescription = (EditText)findViewById(R.id.etDescription);
        etQuanitity = (EditText)findViewById(R.id.etQuantity);

       name =  getIntent().getStringExtra("name");
       description = getIntent().getStringExtra("description");
       quantity = getIntent().getIntExtra("quantity",-1);
       id = getIntent().getIntExtra("id",0);

        etName.setText(name);
        etDescription.setText(description);
        etQuanitity.setText(String.valueOf(quantity));

        final DataBaseHandler db = new DataBaseHandler(this);

        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                description = etDescription.getText().toString();
                quantity = Integer.parseInt(etQuanitity.getText().toString());
                if(!(uri_path != null)){
                    Toast.makeText(EditActivity.this, "please upload an image first", Toast.LENGTH_SHORT).show();
                }
                db.updateItem(new Item(id,name,description,quantity,String.valueOf(uri_path)));
                startActivity(new Intent(EditActivity.this,MainActivity.class));
                Log.d(TAG, "onClick: update");
            }
        });
        ((Button)findViewById(R.id.uploadImage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
// Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        ((Button)findViewById(R.id.btnDelete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                description = etDescription.getText().toString();
                quantity = Integer.parseInt(etQuanitity.getText().toString());
                db.deleteItem(new Item(id,name,description,quantity));
                startActivity(new Intent(EditActivity.this,MainActivity.class));
                Log.d(TAG, "onClick: " + "deleted");
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

             uri_path = data.getData();
            Log.d(TAG, "onActivityResult: " + String.valueOf(uri_path));
//            try {
////                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                // Log.d(TAG, String.valueOf(bitmap));
//
////                ImageView imageView = (ImageView) findViewById(R.id.imageView);
////                imageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }
}
