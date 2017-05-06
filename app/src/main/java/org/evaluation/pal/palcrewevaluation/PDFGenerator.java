package org.evaluation.pal.palcrewevaluation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Section;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;

/**
 * Created by carlo on 2/17/2017.
 */

public class PDFGenerator {
    private File folder;

    private static String FILE = "sample.pdf";
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private static Document document;

    public PDFGenerator(){
        try {
            createDocument();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createDocument() throws FileNotFoundException{
        try{

            folder = new File(Environment.getExternalStoragePublicDirectory("PALEvaluations"), "evaluations");
            if(!folder.exists())
                folder.mkdir();

            Date date = new Date();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(date);
            File pdf = new File(folder + "/" + timeStamp + ".pdf");
            OutputStream output = new FileOutputStream(pdf);

            document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, output);

        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void addMetaData(Document document) {
        document.addTitle("Employee Evaluation");
        document.addSubject("Employee Evaluation");
        document.addAuthor("PAL App");
        document.addCreator("PAL App");
    }

    public void openDocument(){
        document.open();
    }

    public void closeDocument(){
        document.close();
    }


    public void createTable(int columnCount, ArrayList<String[]> rows) throws DocumentException {
        PdfPTable table = new PdfPTable(columnCount);

        for(String[] row : rows){
            for(int i = 0; i < columnCount; i++){
                PdfPCell cell = new PdfPCell(new Phrase(row[i]));
                table.addCell(cell);

            }
        }

        document.add(table);
    }

    public void createInformationTable(Image image, String name, String id, String employmentStatus, String acRegistry, String flightNumber, String sector, String rater, String sla, String checkType) throws DocumentException {
        PdfPTable table = new PdfPTable(10);
        table.setTotalWidth(Utilities.millimetersToPoints(200));
        table.setLockedWidth(true);
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);

        table.addCell(getCell(4, 30, Element.ALIGN_BOTTOM, image));
        table.addCell(getCell(6, 30, Element.ALIGN_CENTER,"Cabin Crew Inflight Performance Evaluation" , ""));

        table.addCell(getCell(3, 20, Element.ALIGN_LEFT, "Name:", name));
        table.addCell(getCell(2, 20, Element.ALIGN_LEFT, "ID Number:", id));
        table.addCell(getCell(5, 20, Element.ALIGN_LEFT, "Employment Status:", employmentStatus));

        table.addCell(getCell(5, 10, Element.ALIGN_LEFT, "AC Number:", ""));
        table.addCell(getCell(5, 10, Element.ALIGN_LEFT, "Date:", ""));

        table.addCell(getCell(5, 10, Element.ALIGN_LEFT, "AC Registry:", acRegistry));
        table.addCell(getCell(2, 10, Element.ALIGN_LEFT, "Flight Number:", flightNumber));
        table.addCell(getCell(3, 10, Element.ALIGN_LEFT, "Sector:", sector));


        table.addCell(getCell(7, 10, Element.ALIGN_LEFT, "Rater:", rater));
        table.addCell(getCell(3, 10, Element.ALIGN_LEFT, "SLA:", sla));

        table.addCell(getCell(10, 10, Element.ALIGN_LEFT, "Type of Check:", checkType));

        document.add(table);
    }

    public void createTableForDimension(Dimension dimension) throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(90);

        PdfPCell titleCell = new PdfPCell(new Phrase(dimension.getDimensionName()));
        titleCell.setBorder(PdfPCell.NO_BORDER);
        table.addCell(titleCell);
        table.completeRow(); 

        for (Row row : dimension.getRowList()) {
            if(row.getClass() == Aspect.class){
                Aspect a = (Aspect) row;
                table.addCell(a.getRowName());
                table.addCell(String.valueOf(a.getScore()));
            }
            table.completeRow();
        }
        document.add(table);
    }

    public void createGradeSummary(String checkType, String safetyRating, String serviceRating) throws DocumentException{
        Rectangle rect = new Rectangle(100,100,100,100);
        document.add(rect);
    }


    private PdfPCell getCell(int cm, int height, int alignment, String label, String text) {
        PdfPCell cell = new PdfPCell();
        cell.setColspan(cm);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setFixedHeight(20);
        Paragraph p = new Paragraph(
                String.format("%s", label),
                new Font(Font.FontFamily.HELVETICA, 8));

        p.add(" " + text);

        p.setAlignment(alignment);
//        Paragraph p2 = new Paragraph(
//                String.format("%s", text),
//                new Font(Font.FontFamily.HELVETICA, 8));
//        p2.setAlignment(Element.ALIGN_BOTTOM);



        cell.addElement(p);
        //cell.addElement(p2);
        cell.setFixedHeight(height);
        return cell;
    }

    private PdfPCell getCell(int cm, int height, int alignment, Image i){
        PdfPCell cell = new PdfPCell(i, true);
        cell.setColspan(cm);
        cell.setUseAscender(true);
        cell.setUseDescender(true);
        cell.setFixedHeight(height);
        return cell;
    }

    public void addImage(Image image){

        if (image != null){
            try {
                document.add(image);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    private static void createTable(Section subCatPart)
    throws BadElementException {
        PdfPTable table = new PdfPTable(3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 2"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Table Header 3"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        table.addCell("1.0");
        table.addCell("1.1");
        table.addCell("1.2");
        table.addCell("2.1");
        table.addCell("2.2");
        table.addCell("2.3");

        subCatPart.add(table);
    }

    private static void createList(Section subCatPart) {
        List list = new List(true, false, 10);
        list.add(new ListItem("First point"));
        list.add(new ListItem("Second point"));
        list.add(new ListItem("Third point"));
        subCatPart.add(list);
    }

    public static void addEmptyLine(int number) throws DocumentException {
        for (int i = 0; i < number; i++) {
            document.add(new Paragraph(" "));
        }
    }
}
