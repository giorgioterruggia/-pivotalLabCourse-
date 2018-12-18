package io.pivotal.pal.tracker.io.pivotal.pal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.temporal.Temporal;
import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
public class TimeEntryController {

    private TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {

        this.timeEntryRepository = timeEntryRepository;
    }

    @RequestMapping
    public ResponseEntity<TimeEntry> read(long nonExistentTimeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(nonExistentTimeEntryId);
        if(timeEntry == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND);
        } else {
            ResponseEntity.status(HttpStatus.OK).body(timeEntry);
        }
                
    }

    @RequestMapping
    public ResponseEntity update(long timeEntryId, TimeEntry expected) {
        return null;
    }

    @RequestMapping
    public ResponseEntity<TimeEntry> delete(long timeEntryId) {
        return null;
    }

    @RequestMapping
    public ResponseEntity<List<TimeEntry>> list() {
        return null;
    }

    @RequestMapping()
    public ResponseEntity<TimeEntry> create(TimeEntry timeEntryToCreate) {
        TimeEntry timeEntry = this.timeEntryRepository.create(timeEntryToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(timeEntry);

    }

}
