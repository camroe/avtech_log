package com.cmr.avtech.maintenancelog.services;

import com.cmr.avtech.maintenancelog.model.LogEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AvtechPDFTest {

    LogEntry testLogEntry;

    @Before
    public void init() {
        testLogEntry = new LogEntry("N9145V", "Mooney", "M20G", "690011", 5477.7f, 477.7f, "This is a very short Log entry.", "WO-987", null);

    }

    @Test
    public void createPdf() throws IOException {
        AvtechPDF avtechPDF = new AvtechPDF(testLogEntry);
        avtechPDF.createPdf(null);
    }
}