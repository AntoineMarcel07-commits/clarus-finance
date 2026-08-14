package com.clarusfinance.support;

import com.clarusfinance.domain.model.Budget;
import com.clarusfinance.domain.model.Movement;
import com.clarusfinance.domain.model.UserAccount;
import com.clarusfinance.domain.repository.BudgetRepository;
import com.clarusfinance.domain.repository.MovementRepository;
import com.clarusfinance.domain.repository.UserRepository;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public final class TestRepositories {
    private TestRepositories() {
    }

    public static final class Movements implements MovementRepository {
        private final Map<Long, Movement> data = new HashMap<>();
        private final AtomicLong sequence = new AtomicLong(1);

        @Override
        public Movement save(Movement movement) {
            long id = movement.id() == null ? sequence.getAndIncrement() : movement.id();
            Movement saved = new Movement(id, movement.type(), movement.amount(),
                    movement.category(), movement.date(), movement.description());
            data.put(id, saved);
            return saved;
        }

        @Override
        public Optional<Movement> findById(long id) {
            return Optional.ofNullable(data.get(id));
        }

        @Override
        public List<Movement> findBetween(LocalDate start, LocalDate end) {
            return data.values().stream()
                    .filter(movement -> !movement.date().isBefore(start) && !movement.date().isAfter(end))
                    .sorted(Comparator.comparing(Movement::date).reversed())
                    .toList();
        }

        @Override
        public List<Movement> findRecent(int limit) {
            return data.values().stream()
                    .sorted(Comparator.comparing(Movement::date).reversed())
                    .limit(limit)
                    .toList();
        }

        @Override
        public void deleteById(long id) {
            data.remove(id);
        }
    }

    public static final class Budgets implements BudgetRepository {
        private final Map<Long, Budget> data = new HashMap<>();
        private final AtomicLong sequence = new AtomicLong(1);

        @Override
        public Budget save(Budget budget) {
            long id = budget.id() == null ? sequence.getAndIncrement() : budget.id();
            Budget saved = new Budget(id, budget.category(), budget.monthlyLimit(), budget.period());
            data.put(id, saved);
            return saved;
        }

        @Override
        public Optional<Budget> findById(long id) {
            return Optional.ofNullable(data.get(id));
        }

        @Override
        public List<Budget> findByPeriod(YearMonth period) {
            return data.values().stream().filter(budget -> budget.period().equals(period)).toList();
        }

        @Override
        public void deleteById(long id) {
            data.remove(id);
        }
    }

    public static final class Users implements UserRepository {
        private final List<UserAccount> users = new ArrayList<>();

        public Users(UserAccount... initialUsers) {
            users.addAll(List.of(initialUsers));
        }

        @Override
        public Optional<UserAccount> findByUsername(String username) {
            return users.stream().filter(user -> user.username().equals(username)).findFirst();
        }
    }
}
