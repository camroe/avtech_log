package com.cmr.avtech.maintenancelog.controller;

import com.cmr.avtech.maintenancelog.model.LogEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class ApiController {

    Logger log = LoggerFactory.getLogger(ApiController.class);

    /**
     * GET
     */
    @RequestMapping(value = "/logEntries", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<LogEntry[]> getAccount(HttpServletRequest request, HttpServletResponse response) {

        log.info("Request to get LogEntries made.");
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }
}
