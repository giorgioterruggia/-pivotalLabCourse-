package io.pivotal.pal.tracker;

import com.mysql.cj.api.mysqla.result.Resultset;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        this.jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        TimeEntry newTimeEntry = null;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String INSERT_SQL = "insert into time_entries(project_id, user_id, date, hours) values (?,?,?,?)";
        int sqlReturnCode = jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(INSERT_SQL, new String[] {"id"});
                        ps.setLong(1, timeEntry.getProjectId());
                        ps.setLong(2, timeEntry.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                        ps.setLong(4, timeEntry.getHours());

                        return ps;
                    }
                },
                keyHolder);

        newTimeEntry = find(keyHolder.getKey().longValue());
        return newTimeEntry;
    }

    @Override
    public TimeEntry find(Long id) {
        String SELECT_SQL = "select * from time_entries where id = ?";

        TimeEntry returnedValue = jdbcTemplate.query(SELECT_SQL,new Object [ ]{id},extractor);

        return returnedValue;
    }

    @Override
    public List<TimeEntry> list() {
        List<TimeEntry> ret = jdbcTemplate.query("SELECT ID, PROJECT_ID, USER_ID, DATE, HOURS FROM time_entries",mapper);
        return ret;
    }

    @Override
    public TimeEntry update(Long id, TimeEntry timeEntry) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String UPDATE_SQL = "update time_entries set project_id = ?, user_id = ?, date = ?, hours = ? where id = ?";
        int sqlReturnCode = jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement(UPDATE_SQL, new String[] {"id"});
                        ps.setLong(1, timeEntry.getProjectId());
                        ps.setLong(2, timeEntry.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                        ps.setLong(4, timeEntry.getHours());
                        ps.setLong(5,id);

                        return ps;
                    }
                },
                keyHolder);

        return find(id);
    }

    @Override
    public void delete(Long id) {
        String DELETE_SQL = "delete from time_entries where id = ?";
        jdbcTemplate.update(DELETE_SQL,new Object[]{id});
    }

    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}

