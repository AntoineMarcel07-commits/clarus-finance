package com.clarusfinance.domain.repository;

import com.clarusfinance.domain.model.Budget;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface BudgetRepository {
    Budget save(Budget budget);
    Optional<Budget> findById(long id);
    List<Budget> findByPeriod(YearMonth period);
    void deleteById(long id);
}
