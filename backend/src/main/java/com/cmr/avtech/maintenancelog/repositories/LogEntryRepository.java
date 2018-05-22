package com.cmr.avtech.maintenancelog.repositories;

import com.cmr.avtech.maintenancelog.model.LogEntry;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogEntryRepository extends CrudRepository<LogEntry, Long> {

    List<LogEntry> findAllByWorkOrderNumber(String workOrderNumber);

    List<LogEntry> findAllByTailNumber(String tailNumber);

    List<LogEntry> findAllByManufacturerAndModel(String manufacturer, String model);

    List<LogEntry> findAllBySerialNumber(String serialNumber);
}
