package com.clarusfinance.domain.repository;

import com.clarusfinance.domain.model.Movement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MovementRepository {
    Movement save(Movement movement);
    Optional<Movement> findById(long id);
    List<Movement> findBetween(LocalDate start, LocalDate end);
    List<Movement> findRecent(int limit);
    void deleteById(long id);
}
