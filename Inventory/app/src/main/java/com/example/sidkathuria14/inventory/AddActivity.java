package com.example.sidkathuria14.inventory;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sidkathuria14.inventory.models.Item;

public class AddActivity extends AppCompatActivity {
EditText etName,etDescription,etQuanitity;
Button uploadImage;
ImageView imgView;
String name,description;
int quantity;
Uri uri_path;
public static final String TAG = "db";
    public static final int PICK_IMAGE_REQUEST = 123;
TextView tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etName = (EditText)findViewById(R.id.etName);
        etDescription = (EditText)findViewById(R.id.etDescription);
        etQuanitity = (EditText)findViewById(R.id.etQuantity);
tvAddress = (TextView)findViewById(R.id.tvAddress);
tvAddress.setVisibility(View.INVISIBLE);
        final DataBaseHandler db = new DataBaseHandler(this);


        ((Button)findViewById(R.id.btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                description = etDescription.getText().toString();
                quantity = Integer.parseInt(etQuanitity.getText().toString());
                if(!(uri_path != null)){
                    Toast.makeText(AddActivity.this, "please upload an image first", Toast.LENGTH_SHORT).show();
                }
                db.addItem( new Item(name,description,quantity,uri_path.toString())
                );
                startActivity(new Intent(AddActivity.this,MainActivity.class));
                Log.d(TAG, "onClick: " + Integer.parseInt(String.valueOf(db.getItemsCount())));
                Log.d(TAG, "onClick: " + name + " " + description + " " + quantity);

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


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            uri_path = data.getData();
            tvAddress.setVisibility(View.VISIBLE);
            tvAddress.setText(String.valueOf(uri_path));
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
