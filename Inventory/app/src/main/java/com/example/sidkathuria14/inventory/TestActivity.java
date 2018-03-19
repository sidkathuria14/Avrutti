package com.example.sidkathuria14.inventory;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestActivity extends AppCompatActivity {
    File file;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        main();
        try {

            String fpath = Environment.getExternalStorageDirectory().getPath() + "hello" + ".pdf";
            file = new File(fpath);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException ioe) {


        }

        try {
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN,
                    12, Font.BOLD, new BaseColor(0, 0, 0));

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsoluteFile()));
            document.open();
            document.add(new Paragraph("My First Pdf !"));
            document.add(new Paragraph("Hello World"));
        } catch (DocumentException de) {
        }
        catch(FileNotFoundException fnfe){

        }
    }

    public static void main(String arg[]) throws Exception {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("tablePDF.pdf"));
        document.open();
        PdfPTable table = new PdfPTable(2);
        table.addCell("Name");
        table.addCell("Place");
        table.addCell("RoseIndia");
        table.addCell("Delhi");
        document.add(table);
        document.close();
    }
}



