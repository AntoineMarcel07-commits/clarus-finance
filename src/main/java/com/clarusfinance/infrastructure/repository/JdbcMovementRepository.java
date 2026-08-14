package com.clarusfinance.infrastructure.repository;

import com.clarusfinance.application.exception.DataAccessException;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.MovementType;
import com.clarusfinance.domain.repository.MovementRepository;
import com.clarusfinance.infrastructure.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class JdbcMovementRepository implements MovementRepository {
    private final ConnectionFactory connectionFactory;

    public JdbcMovementRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory);
    }

    @Override
    public Movement save(Movement movement) {
        return movement.id() == null ? insert(movement) : update(movement);
    }

    private Movement insert(Movement movement) {
        String sql = """
                INSERT INTO movements(type, amount, category, movement_date, description)
                VALUES (?, ?, ?, ?, ?) RETURNING id
                """;
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, movement);
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                return new Movement(result.getLong("id"), movement.type(), movement.amount(),
                        movement.category(), movement.date(), movement.description());
            }
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudo guardar el movimiento", exception);
        }
    }

    private Movement update(Movement movement) {
        String sql = """
                UPDATE movements SET type = ?, amount = ?, category = ?, movement_date = ?, description = ?
                WHERE id = ?
                """;
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, movement);
            statement.setLong(6, movement.id());
            statement.executeUpdate();
            return movement;
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudo actualizar el movimiento", exception);
        }
    }

    private void bind(PreparedStatement statement, Movement movement) throws SQLException {
        statement.setString(1, movement.type().name());
        statement.setBigDecimal(2, movement.amount());
        statement.setString(3, movement.category());
        statement.setDate(4, Date.valueOf(movement.date()));
        statement.setString(5, movement.description());
    }

    @Override
    public Optional<Movement> findById(long id) {
        String sql = "SELECT * FROM movements WHERE id = ?";
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? Optional.of(map(result)) : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudo consultar el movimiento", exception);
        }
    }

    @Override
    public List<Movement> findBetween(LocalDate start, LocalDate end) {
        String sql = "SELECT * FROM movements WHERE movement_date BETWEEN ? AND ? ORDER BY movement_date DESC, id DESC";
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));
            return list(statement);
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudieron consultar los movimientos", exception);
        }
    }

    @Override
    public List<Movement> findRecent(int limit) {
        String sql = "SELECT * FROM movements ORDER BY movement_date DESC, id DESC LIMIT ?";
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, limit);
            return list(statement);
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudieron consultar los movimientos recientes", exception);
        }
    }

    private List<Movement> list(PreparedStatement statement) throws SQLException {
        List<Movement> movements = new ArrayList<>();
        try (ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                movements.add(map(result));
            }
        }
        return movements;
    }

    private Movement map(ResultSet result) throws SQLException {
        return new Movement(
                result.getLong("id"),
                MovementType.valueOf(result.getString("type")),
                result.getBigDecimal("amount"),
                result.getString("category"),
                result.getDate("movement_date").toLocalDate(),
                result.getString("description"));
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM movements WHERE id = ?";
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudo eliminar el movimiento", exception);
        }
    }
}
