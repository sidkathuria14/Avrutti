package com.example.sidkathuria14.inventory;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

/**
 * Created by sidkathuria14 on 12/3/18.
 */

public class tablePDF{
    public static void main(String arg[])throws Exception{
        Document document=new Document();
        PdfWriter.getInstance(document,new FileOutputStream("tablePDF.pdf"));
        document.open();
        PdfPTable table=new PdfPTable(2);
        table.addCell("Name");
        table.addCell("Place");
        table.addCell("RoseIndia");
        table.addCell("Delhi");
        document.add(table);
        document.close();
    }
}