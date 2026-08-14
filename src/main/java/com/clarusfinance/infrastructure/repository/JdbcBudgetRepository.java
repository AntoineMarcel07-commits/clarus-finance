package com.clarusfinance.infrastructure.repository;

import com.clarusfinance.application.exception.DataAccessException;
import com.clarusfinance.domain.model.Budget;
import com.clarusfinance.domain.repository.BudgetRepository;
import com.clarusfinance.infrastructure.db.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class JdbcBudgetRepository implements BudgetRepository {
    private final ConnectionFactory connectionFactory;

    public JdbcBudgetRepository(ConnectionFactory connectionFactory) {
        this.connectionFactory = Objects.requireNonNull(connectionFactory);
    }

    @Override
    public Budget save(Budget budget) {
        return budget.id() == null ? insert(budget) : update(budget);
    }

    private Budget insert(Budget budget) {
        String sql = """
                INSERT INTO budgets(category, monthly_limit, budget_year, budget_month)
                VALUES (?, ?, ?, ?) RETURNING id
                """;
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, budget);
            try (ResultSet result = statement.executeQuery()) {
                result.next();
                return new Budget(result.getLong("id"), budget.category(),
                        budget.monthlyLimit(), budget.period());
            }
        } catch (SQLException exception) {
            if ("23505".equals(exception.getSQLState())) {
                throw new DataAccessException("Ya existe un presupuesto para esa categoría y periodo", exception);
            }
            throw new DataAccessException("No se pudo guardar el presupuesto", exception);
        }
    }

    private Budget update(Budget budget) {
        String sql = """
                UPDATE budgets SET category = ?, monthly_limit = ?, budget_year = ?, budget_month = ?
                WHERE id = ?
                """;
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            bind(statement, budget);
            statement.setLong(5, budget.id());
            statement.executeUpdate();
            return budget;
        } catch (SQLException exception) {
            if ("23505".equals(exception.getSQLState())) {
                throw new DataAccessException("Ya existe un presupuesto para esa categoría y periodo", exception);
            }
            throw new DataAccessException("No se pudo actualizar el presupuesto", exception);
        }
    }

    private void bind(PreparedStatement statement, Budget budget) throws SQLException {
        statement.setString(1, budget.category());
        statement.setBigDecimal(2, budget.monthlyLimit());
        statement.setInt(3, budget.period().getYear());
        statement.setInt(4, budget.period().getMonthValue());
    }

    @Override
    public Optional<Budget> findById(long id) {
        String sql = "SELECT * FROM budgets WHERE id = ?";
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            try (ResultSet result = statement.executeQuery()) {
                return result.next() ? Optional.of(map(result)) : Optional.empty();
            }
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudo consultar el presupuesto", exception);
        }
    }

    @Override
    public List<Budget> findByPeriod(YearMonth period) {
        String sql = "SELECT * FROM budgets WHERE budget_year = ? AND budget_month = ? ORDER BY category";
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, period.getYear());
            statement.setInt(2, period.getMonthValue());
            List<Budget> budgets = new ArrayList<>();
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    budgets.add(map(result));
                }
            }
            return budgets;
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudieron consultar los presupuestos", exception);
        }
    }

    private Budget map(ResultSet result) throws SQLException {
        return new Budget(
                result.getLong("id"),
                result.getString("category"),
                result.getBigDecimal("monthly_limit"),
                YearMonth.of(result.getInt("budget_year"), result.getInt("budget_month")));
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM budgets WHERE id = ?";
        try (Connection connection = connectionFactory.open();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new DataAccessException("No se pudo eliminar el presupuesto", exception);
        }
    }
}
