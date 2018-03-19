package com.example.sidkathuria14.inventory;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sidkathuria14.inventory.adapters.MyAdapter;
import com.example.sidkathuria14.inventory.interfaces.OnItemClickListener;
import com.example.sidkathuria14.inventory.interfaces.OnLongClickedListener;
import com.example.sidkathuria14.inventory.models.Item;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Header;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.Random;
//import com.lowagie.text.*;
//import com.lowagie.text.pdf.*;

public class MainActivity extends AppCompatActivity {
RecyclerView rv;
MyAdapter adapter;
    ArrayList<Item> myArrayList;
    public static final String TAG = "inventory";
    DataBaseHandler db;
    SharedPreferences sharedpreferences;
  final String filename = "inventory_" + String.valueOf(new  Random().nextInt(100000)) + ".pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



         db = new DataBaseHandler(this);


        rv = (RecyclerView)findViewById(R.id.rv);

        myArrayList = new ArrayList<>();
        for(int i=1;i<=db.getItemsCount();i++) {
            myArrayList.add(db.getItem(i));
        }

        adapter = new MyAdapter(MainActivity.this,myArrayList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(adapter);
        adapter.update(myArrayList,MainActivity.this);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int itemId, View view) {
                Log.d(TAG, "onItemClick: ");
        //        Toast.makeText(MainActivity.this,"clicked",Toast.LENGTH_LONG);
                Intent i = new Intent(MainActivity.this,EditActivity.class);
                i.putExtra("id",myArrayList.get(itemId-1).getId());
                i.putExtra("name",myArrayList.get(itemId-1).getName());
                i.putExtra("description",myArrayList.get(itemId-1).getDescription());
                i.putExtra("quantity",myArrayList.get(itemId-1).getQuantity());
                startActivity(i);
            }
        });

        adapter.setOnLongClickedListener(new OnLongClickedListener() {
            @Override
            public void OnLongClick(int itemId, View view) {
                Toast.makeText(MainActivity.this,"long clicked",Toast.LENGTH_LONG);
            }
        });

        if ((ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)&&(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)) {

            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//
//            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        111);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
//        } else {
            // Permission has already been granted

        }

//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

    getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
////                    adapter.filter("");
////                    listView.clearTextFilter();
//                } else {
////                    adapter.filter(newText);
//                }
//                return true;
                final ArrayList<Item> arrayList = filter(myArrayList, newText);

                adapter.setFilter(arrayList);
                return true;
            }
        });

        return true;
    }
    private ArrayList<Item> filter(ArrayList<Item> items, String query) {
        query = query.toLowerCase();final ArrayList<Item> arrayList = new ArrayList<>();
        for (int i=0;i<items.size();i++) {
            final String text = items.get(i).getName().toLowerCase();
            Log.d(TAG, "filter: text = " + text);
            Log.d(TAG, "filter: query = " + query);
            if (text.contains(query)) {
                arrayList.add(items.get(i));
                Log.d(TAG, "filter: " + items.get(i));
            }
        }
        return arrayList;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.addStock){
            startActivity(new Intent(MainActivity.this,AddActivity.class));
        }
        if(id == R.id.createPdf){

//            sharedpreferences = getSharedPreferences("filename", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedpreferences.edit();
//            editor.putString("key", String.valueOf(number));
//            editor.commit();
            try {
                createPdf();
                Log.d(TAG, "onCreate: " +"try");
                Toast.makeText(this, "pdf stored in external storage", Toast.LENGTH_SHORT).show();
                File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +"/Inventory"+ "/" +  filename);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file),"application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }catch(DocumentException de){
                Log.d(TAG, "onCreate: de");
            }catch(IOException ioe){
                Log.d(TAG, "onCreate: " + "ioe");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void createPdf()
            throws DocumentException, IOException {

        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Inventory";

        File dir = new File(path);
        if(!dir.exists())
            dir.mkdirs();

        File file = new File(dir, filename);
        FileOutputStream fOut = new FileOutputStream(file);


        Document document = new Document();

        PdfWriter.getInstance(document, fOut);

        document.open();
        Log.d(TAG, "createPdf: ");

        Font titleFont = FontFactory.getFont(FontFactory.COURIER_BOLD, 11, BaseColor.BLACK);
        Font subtitleFont = FontFactory.getFont("Times Roman", 9, BaseColor.BLACK);

        document.add(new Paragraph("Database",titleFont));
        document.add(new Paragraph("Here are the details of the inventory",subtitleFont));
        document.add(new Paragraph("",subtitleFont));
        PdfPTable table = new PdfPTable(4);
        table.addCell("id");
        table.addCell("Name");
        table.addCell("description");
        table.addCell("quantity");

for(int i=1;i<=db.getItemsCount();i++){
    table.addCell(String.valueOf(db.getItem(i).getId()));
    table.addCell(db.getItem(i).getName());
    table.addCell(db.getItem(i).getDescription());
    table.addCell(String.valueOf(db.getItem(i).getQuantity()));
}

//        table.addCell("Name1");
//        table.addCell("Place1");
//        table.addCell("RoseIndia1");
//        table.addCell("Delhi1");

        document.add(table);
        // step 5
        document.close();
    }

}


