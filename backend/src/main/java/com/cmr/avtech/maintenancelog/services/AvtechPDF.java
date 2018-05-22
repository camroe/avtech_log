package com.cmr.avtech.maintenancelog.services;

import com.cmr.avtech.maintenancelog.model.LogEntry;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceGray;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AvtechPDF {
    private static final String SRC_MAIN_RESOURCES_IMG_AVTECH_LOGO_PNG = "src/main/resources/img/AvtechLogo.png";
    private LogEntry logEntry;
    private static final String DEFAULT_DESTINATION_DIR = "results";
    private File destinationFile;

    private static final DeviceGray TITLE_BACKGROUND = new DeviceGray(0.9f);
    private static final float PADDING_LEFT = 10f;
    //  private static final String LOG_BOOK_ENTRY = "This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. This is the Log Book Entry. ";
    private static final String LOG_BOOK_ENTRY = "This is the Log Book Entry.";
    public static final Color MY_GREY = new DeviceRgb(240, 240, 240);
    private float titleFontSize = 14f;
    private float normalFontSize = PADDING_LEFT;
    private float smallFontSize = 7;
    private float logEntryFontSize = 6;
    private PdfFont defaultFont;
    private float scale = .040f;
    private int logLimit = 100;
    private final String DEFAULT_FILE_NAME;

    public AvtechPDF(LogEntry logEntry) {
        this.logEntry = logEntry;
        try {
            defaultFont = new PdfFontFactory().createFont(FontConstants.HELVETICA);
        } catch (IOException ioex) {
            //ignore
        }

        DEFAULT_FILE_NAME = DEFAULT_DESTINATION_DIR + "/" + getCurrentDateTimeStamp() + logEntry.getTailNumber() + ".pdf";
        destinationFile = new File(DEFAULT_FILE_NAME);
        destinationFile.getParentFile().mkdir();
    }


    public void createPdf(String fileName) throws IOException {
        PdfWriter pdfWriter = null;
        if (null == fileName || ("".equalsIgnoreCase(fileName)))
            fileName = DEFAULT_FILE_NAME;
        try {
            destinationFile = new File(fileName);

            pdfWriter = new PdfWriter(destinationFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        PdfPage page = pdfDocument.addNewPage();

        Document document = new Document(pdfDocument);


        Image logo = new Image(ImageDataFactory.create(SRC_MAIN_RESOURCES_IMG_AVTECH_LOGO_PNG));
        logo.scaleToFit(logo.getImageWidth() * scale, logo.getImageHeight() * scale);

        //Title Table

        document.add(createHeaderTable());
        document.add(createIdentificationRow());
        document.add(createPermenantEntryNotice());
        document.add(generateLogTextEntryTable());
        document.add(generateFooterText());
        document.add(generateSignatureLine(logo));
        document.close();

    }

    private Paragraph generateSignatureLine(Image logo) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(logo)
                .add("                  SIGNED:")
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setFontSize(smallFontSize);
        return paragraph;
    }

    private Paragraph generateFooterText() {
        //Closing footer
        Paragraph paragraph = new Paragraph();
        LocalDate localDate = LocalDate.now();
        paragraph
                .add("The equipment was removed/installed in a manner that will not interfere with or adversely affect the safe operation of the aircraft. Ground tests " +
                        "were performed, all systems checked operational per manufacturer’s specifications. The aircraft weight and balance was updated. Total current " +
                        "weight does not exceed recommended load as computed in reference to the aircraft maintenance manual. Installation was accomplished " +
                        "in accordance with AC43.13-1B chapters 7, 10, 11, 12, and AC43.13-2B chapters 1, 2, and 14 CFR part 23, subpart F. This installation was determined " +
                        "to be a Minor Alteration in accordance with 14 CFR 1.1, CFR 43 Appendix A, CFR 21.93(A), and FSAW 94-32C.\n" +
                        "The above identified aircraft has been maintained and inspected in accordance with current CFRs and is approved for return to service. Details " +
                        "are on file at this station under work order No. ")
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setFontSize(smallFontSize);
        Text workOrderNumber;
        if ((null == this.logEntry.getWorkOrderNumber()) || "".equalsIgnoreCase(this.logEntry.getWorkOrderNumber())) {


        workOrderNumber = new Text(" WO NOT ENTERED ")
                .setBackgroundColor(Color.YELLOW);
    }
        else
            workOrderNumber = new Text(this.logEntry.getWorkOrderNumber());

        workOrderNumber
                .setUnderline()
                .setFont(defaultFont)
                .setFontSize(smallFontSize);
        paragraph.add(workOrderNumber)
                .add("                   Dated: ");


        Text dated = new Text(DateTimeFormatter.ofPattern("MMM dd, yyy").format(localDate))
                .setUnderline()
                .setFont(defaultFont)
                .setFontSize(smallFontSize);
        paragraph.add(dated);
        return paragraph;
    }

    private Paragraph createPermenantEntryNotice() {
        Paragraph paragraph = new Paragraph();
        paragraph.add("This is a Permanent Logbook Entry:")
                .setFont(defaultFont)
                .setFontSize(smallFontSize)
                .setBold()
                .setTextAlignment(TextAlignment.LEFT);
        return paragraph;
    }

    private Table createIdentificationRow() {
        //Information Line
        float columnWidths[] = new float[]{2, 10, 2, 10, 2, 10, 2, 10, 2, 6, 2, 6};
        Table table = new Table(columnWidths);
        table.setWidthPercent(100);
        table.setFont(defaultFont)
                .setFontSize(smallFontSize)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        Cell cell;
        table.addCell(generateTailNumberLabelCell());
        table.addCell(generateTailNumberCell());

        table.addCell(generateManufacturerLabelCell());
        table.addCell(generateMantufacturerCell());

        table.addCell(generateModelLabelCell());
        table.addCell(generateModelDataCell());

        table.addCell(generateSerialNumberLabelCell());
        table.addCell(generateSerialNumberCell());

        table.addCell(generateTTAFLabelCell());
        table.addCell(generateTTAFCell());

        table.addCell(generateHobbsLabelCell());
        table.addCell(generateHobbsCell());
        return table;
    }


    private Cell generateTailNumberLabelCell() {
        Cell cell;//Tail Number Cell Label
        cell = new Cell();
        cell.add("Tail#:")
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setPaddingRight(0f)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        return cell;
    }

    private Cell generateTailNumberCell() {
        //Tail Number Cell
        Cell cell = new Cell();
        if (((null == this.logEntry.getTailNumber()) || "".equalsIgnoreCase(this.logEntry.getTailNumber()))) {
            cell.add("Not Entered");
        } else {
            cell.add(this.logEntry.getTailNumber());
        }
        cell.setFont(defaultFont)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        return cell;
    }

    private Cell generateManufacturerLabelCell() {
        Cell cell;//Manufacturer Label
        cell = new Cell();
        cell.add("Mfg:")
                .setFont(defaultFont)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setPaddingRight(0f)
                .setPaddingLeft(PADDING_LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        return cell;
    }

    private Cell generateMantufacturerCell() {
        //Manufacturer Data
        Cell cell = new Cell();
        if (((null == this.logEntry.getManufacturer()) || "".equalsIgnoreCase(this.logEntry.getManufacturer()))) {
            cell.add("Not Entered");
        } else {
            cell.add(this.logEntry.getManufacturer());
        }
        cell.setFont(defaultFont)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        return cell;
    }

    private Cell generateModelLabelCell() {
        Cell cell;//Model Label
        cell = new Cell();
        cell.add("Model:")
                .setFont(defaultFont)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setPaddingRight(0f)
                .setPaddingLeft(PADDING_LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        return cell;
    }

    private Cell generateModelDataCell() {
        Cell cell = new Cell();
        if (((null == this.logEntry.getModel()) || "".equalsIgnoreCase(this.logEntry.getModel()))) {
            cell.add("Not Entered");
        } else {
            cell.add(this.logEntry.getModel());
        }
        cell.setFont(defaultFont)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        return cell;
    }

    private Cell generateSerialNumberLabelCell() {
        Cell cell;//Serial Label
        cell = new Cell();
        cell.add("Serial:")
                .setFont(defaultFont)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setPaddingRight(0f)
                .setPaddingLeft(PADDING_LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        return cell;
    }

    private Cell generateSerialNumberCell() {
        Cell cell = new Cell();
        if (((null == this.logEntry.getSerialNumber()) || "".equalsIgnoreCase(this.logEntry.getSerialNumber()))) {
            cell.add("Not Entered");
        } else {
            cell.add(this.logEntry.getSerialNumber());
        }
        cell.setFont(defaultFont)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        return cell;
    }

    private Cell generateTTAFLabelCell() {
        Cell cell;//Total Time on AirFrame (TTAF) Label
        cell = new Cell();
        cell.add("TTAF:")
                .setFont(defaultFont)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setPaddingLeft(PADDING_LEFT)
                .setPaddingRight(0f)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        return cell;
    }

    private Cell generateTTAFCell() {
        Cell cell = new Cell();
        if (null == this.logEntry.getTtaf()) {
            cell.add("Not Entered");
        } else {
            cell.add(String.valueOf(this.logEntry.getTtaf()));
        }
        cell.setFont(defaultFont)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        return cell;
    }

    private Cell generateHobbsLabelCell() {
        Cell cell;//HOBBS Label
        cell = new Cell();
        cell.add("HOBBS:")
                .setFont(defaultFont)
                .setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setPaddingRight(0f)
                .setPaddingLeft(PADDING_LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        return cell;
    }

    private Cell generateHobbsCell() {
        Cell cell = new Cell();
        if (null == this.logEntry.getHobbs()) {
            cell.add("Not Entered");
        } else {
            cell.add(String.valueOf(this.logEntry.getHobbs()));
        }
        cell.setFont(defaultFont)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        return cell;
    }

    private Table generateLogTextEntryTable() {
        Table logEntryTable = new Table(new float[]{1})
                .setWidthPercent(100)
                .setFont(defaultFont)
                .setTextAlignment(TextAlignment.JUSTIFIED)
                .setFontSize(smallFontSize)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setBackgroundColor(MY_GREY);
        Paragraph paragraph = new Paragraph();
        //The Actual Log Book entry
        if (((null == this.logEntry.getLogEntry()) || "".equalsIgnoreCase(this.logEntry.getLogEntry()))) {
            paragraph.add("NO LOG ENTRY MADE ");
        } else {
            paragraph.add("[")
                    .add(this.logEntry.getLogEntry())
                    .add(" End of Official Log Entry.]");
        }
        paragraph
                .setFont(defaultFont)
                .setFontColor(Color.DARK_GRAY)
                .setFontSize(smallFontSize)
                .setBackgroundColor(MY_GREY)
//          .setTextAlignment(TextAlignment.LEFT)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);

        logEntryTable
                .addCell(paragraph);
        return logEntryTable;
    }

    private String getCurrentDateTimeStamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
        LocalDateTime localDateTime = LocalDateTime.now().now();
        return (dtf.format(localDateTime));
    }

    private Table createHeaderTable() {
        //Title Table
        float columnWidths[] = {3, 4, 2, 2};
        Table table = new Table(columnWidths);
        table.setWidthPercent(100);
        table.setFont(defaultFont)
                .setFontSize(normalFontSize)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        Cell cell = new Cell();
        cell.add("AVTECH Services, LLC")
                .setFontSize(titleFontSize)
                .setFont(defaultFont)
                .setHorizontalAlignment(HorizontalAlignment.LEFT)
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(TITLE_BACKGROUND)
                .setTextAlignment(TextAlignment.LEFT);
        table.addCell(cell);
        cell = new Cell();
        cell.add("1887 S. 1800 W., Woods Cross, UT 84087")
                .setFont(defaultFont)
                .setTextAlignment(TextAlignment.CENTER)
                .setBorder(Border.NO_BORDER)
                .setBackgroundColor(TITLE_BACKGROUND)
                .setHorizontalAlignment(HorizontalAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM);
        table.addCell(cell);
        cell = new Cell();
        cell.add("CRS# 7AYR463B")
                .setFont(defaultFont)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setBackgroundColor(TITLE_BACKGROUND)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        table.addCell(cell);
        cell = new Cell();
        cell.add("Form No. AS-002")
                .setFont(defaultFont)
                .setTextAlignment(TextAlignment.RIGHT)
                .setBorder(Border.NO_BORDER)
                .setVerticalAlignment(VerticalAlignment.BOTTOM)
                .setBackgroundColor(TITLE_BACKGROUND)
                .setHorizontalAlignment(HorizontalAlignment.RIGHT);
        table.addCell(cell);
        return table;
    }

}
