CREATE TABLE IF NOT EXISTS schema_versions (
    version VARCHAR(20) PRIMARY KEY,
    applied_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS app_users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    display_name VARCHAR(100) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS movements (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(10) NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
    amount NUMERIC(14, 2) NOT NULL CHECK (amount > 0),
    category VARCHAR(60) NOT NULL,
    movement_date DATE NOT NULL,
    description VARCHAR(180) NOT NULL DEFAULT '',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS budgets (
    id BIGSERIAL PRIMARY KEY,
    category VARCHAR(60) NOT NULL,
    monthly_limit NUMERIC(14, 2) NOT NULL CHECK (monthly_limit > 0),
    budget_year INTEGER NOT NULL CHECK (budget_year BETWEEN 2000 AND 2200),
    budget_month INTEGER NOT NULL CHECK (budget_month BETWEEN 1 AND 12),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uq_budget_category_period UNIQUE (category, budget_year, budget_month)
);

CREATE INDEX IF NOT EXISTS idx_movements_date ON movements(movement_date DESC);
CREATE INDEX IF NOT EXISTS idx_movements_category ON movements(category);
CREATE INDEX IF NOT EXISTS idx_budgets_period ON budgets(budget_year, budget_month);

INSERT INTO schema_versions(version) VALUES ('1.0.0')
ON CONFLICT (version) DO NOTHING;
