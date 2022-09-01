package manager;

import db.DBConnectionProvider;
import model.Event;
import model.EventType;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class EventManager {

    private final Connection connection;

    public EventManager() {
        connection = DBConnectionProvider.getInstance().getConnection();
    }

    public void add(Event event) throws SQLException {
        String query = "insert into event(name,place,is_online,price,event_type) values(?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, event.getName());
        preparedStatement.setString(2, event.getPlace());
        preparedStatement.setBoolean(3, event.isOnline());
        preparedStatement.setDouble(4, event.getPrice());
        preparedStatement.setString(5, String.valueOf(event.getEventType()));
        preparedStatement.executeUpdate();

        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (resultSet.next()) {
            int id = 0;
            try {
                id = resultSet.getInt(1);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            event.setId(id);
        }
    }


    public List<Event> getAll() throws SQLException {
        Statement statement = null;
            statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM event");
        List<Event> events = new LinkedList<>();
        while (resultSet.next()) {
            try {
                events.add(getEventFromResultSet(resultSet));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return events;
    }

    public Event getById(int id) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM event WHERE id = " + id);
        Event event = null;
        if (resultSet.next()) {
            event = getEventFromResultSet(resultSet);
        }
        return event;
    }

    private Event getEventFromResultSet(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getInt("id"));
        event.setName(resultSet.getString("name"));
        event.setPlace(resultSet.getString("place"));
        event.setOnline(resultSet.getBoolean("is_online"));
        event.setPrice(resultSet.getDouble("price"));
        event.setEventType(EventType.valueOf(resultSet.getString("event_type")));
        return event;
    }
}
