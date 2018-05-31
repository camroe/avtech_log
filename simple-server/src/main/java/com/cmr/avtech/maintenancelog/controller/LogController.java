package com.cmr.avtech.maintenancelog.controller;

import com.cmr.avtech.maintenancelog.model.LogEntry;
import com.cmr.avtech.maintenancelog.services.AvtechPDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LogController {
Logger log = LoggerFactory.getLogger(LogController.class);

    @RequestMapping(value = "/logEntries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LogEntry>> getLogEntries(HttpServletRequest request, HttpServletResponse response) {
        List<LogEntry> logEntries = new ArrayList<>();
        LogEntry mockLogEntry = new LogEntry("N9145V", "Mooney", "M20G", "690011", 5477.7f, 477.7f, "This is the log entry", "WO485", new Date().toString());
        logEntries.add(mockLogEntry);
        mockLogEntry = new LogEntry("N66SEX", "Mooney", "M20G", "690012", 477.7f, 77.7f, "This is the log entry", "WO485", new Date().toString());
        logEntries.add(mockLogEntry);
        mockLogEntry = new LogEntry("NXXXXX", "Mooney", "M20G", "690011", 54747.7f, 4747.7f, "This is the log entry", "WO485", new Date().toString());
        logEntries.add(mockLogEntry);
        return new ResponseEntity<>(logEntries, HttpStatus.OK);
    }

    @RequestMapping(value = "/logEntry", method = RequestMethod.POST, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> postLogEntries(HttpServletRequest request,
                                                 @RequestBody LogEntry logEntry,
                                                 HttpServletResponse response) {
        log.info("\nReceived Log Entry:\n" + logEntry.toString());

        //Generate PDF based on input


        LogEntry mockLogEntry = new LogEntry("N9145V", "Mooney", "M20G", "690011", 5477.7f, 477.7f, "This is the log entry", "WO485", new Date().toString());
        log.info(mockLogEntry.toString());
        AvtechPDF avtechPDF = new AvtechPDF(mockLogEntry);

        try {
            avtechPDF.createPdf("results");
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //return the pdf bytes
        byte[] returnBytes;
        try {
            returnBytes = avtechPDF.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(returnBytes, HttpStatus.OK);
    }
}