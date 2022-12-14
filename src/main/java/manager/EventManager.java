package manager;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class EventManager {

    private final Connection connection;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public EventManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void add(Event event) {
        try {
            String query = "insert into event(name,place,is_online,price,event_type) values(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, event.getName());
            preparedStatement.setString(2, event.getPlace());
            preparedStatement.setBoolean(3, event.isOnline());
            preparedStatement.setDouble(4, event.getPrice());
            preparedStatement.setString(5, String.valueOf(event.getEventType()));
            preparedStatement.setString(6, sdf.format(event.getEventDate()));
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                int id = 0;
                id = resultSet.getInt(1);
                event.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public List<Event> getAll() throws SQLException, ParseException {
        Statement statement = null;
        statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM event");
        List<Event> events = new LinkedList<>();
        while (resultSet.next()) {
            events.add(getEventFromResultSet(resultSet));
        }
        return events;
    }

    public Event getById(int id) throws SQLException, ParseException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM event WHERE id = " + id);
        Event event = null;
        if (resultSet.next()) {
            event = getEventFromResultSet(resultSet);
        }
        return event;
    }

    private Event getEventFromResultSet(ResultSet resultSet) {
        Event event = null;
        try {
            event = new Event();
            event.setId(resultSet.getInt("id"));
            event.setName(resultSet.getString("name"));
            event.setPlace(resultSet.getString("place"));
            event.setOnline(resultSet.getBoolean("is_online"));
            event.setPrice(resultSet.getDouble("price"));
            event.setEventType(EventType.valueOf(resultSet.getString("event_type").toUpperCase()));
            event.setEventDate(resultSet.getString("event_date") == null ? null : sdf.parse(resultSet.getString("event_date")));
        } catch (SQLException e) {

        } catch (ParseException e) {
            e.printStackTrace();
        }


        return event;
    }
}
