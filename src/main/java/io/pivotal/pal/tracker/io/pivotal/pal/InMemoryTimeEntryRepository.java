package io.pivotal.pal.tracker.io.pivotal.pal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTimeEntryRepository  implements  TimeEntryRepository{

    private HashMap<Long, TimeEntry> repolist = new HashMap<>();
    private Long counter= 1L;



    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(this.counter);
        this.repolist.put(this.counter, timeEntry);
        this.counter++;
        return new TimeEntry(timeEntry);
    }

    @Override
    public TimeEntry find(long id) {
        return (TimeEntry) repolist.get(id);
    }

    @Override
    // todo: change return object
    public List<TimeEntry> list() {


        return new ArrayList<TimeEntry>(this.repolist.values());
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        this.repolist.put(id,timeEntry);
        return this.find(id);
    }

    @Override
    public void delete(long id) {
        this.repolist.remove(id);
    }
}
