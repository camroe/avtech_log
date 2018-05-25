package com.cmr.avtech.maintenancelog.controller;

import com.cmr.avtech.maintenancelog.model.LogEntry;
import com.cmr.avtech.maintenancelog.services.AvtechPDF;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class LogController {


    @RequestMapping(value = "/logEntries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LogEntry> getLogEntries(HttpServletRequest request, HttpServletResponse response) {

    return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @RequestMapping(value = "/logEntry", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> postLogEntries(HttpServletRequest request, HttpServletResponse response) {
//        public ResponseEntity<?> postLogEntries(@RequestBody LogEntry logEntry, HttpServletRequest request, HttpServletResponse response) {

        //Generate PDF based on input
        LogEntry mockLogEntry = new LogEntry("N9145V","Mooney","M20G","690011",5477.7f,477.7f,"This is the log entry","WO485",new Date());
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
            returnBytes =avtechPDF.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(returnBytes,HttpStatus.OK);
    }
}