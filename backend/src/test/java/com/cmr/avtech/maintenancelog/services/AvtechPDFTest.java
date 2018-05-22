package com.cmr.avtech.maintenancelog.services;

import com.cmr.avtech.maintenancelog.model.LogEntry;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class AvtechPDFTest {

    LogEntry testLogEntry;

    @Before
    public void init() {
        testLogEntry = new LogEntry(null, null, null, null, null, 0f, "This is a very short Log entry.", null, null);

    }

    @Test
    public void createPdf() throws IOException {
        AvtechPDF avtechPDF = new AvtechPDF(testLogEntry);
        avtechPDF.createPdf("src/test/results");
    }
}