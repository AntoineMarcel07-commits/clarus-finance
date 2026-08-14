INSERT INTO app_users(username, display_name, password_hash)
VALUES (
    'admin',
    'Administrador Clarus',
    'pbkdf2_sha256$120000$gwdwYUEM98jth4236lRyBQ==$fDc328dCtqI4v2rO867cibhhAAATz4pzzGKoBvGP784='
)
ON CONFLICT (username) DO NOTHING;

INSERT INTO movements(type, amount, category, movement_date, description)
SELECT 'INCOME', 18500.00, 'Sueldo', CURRENT_DATE - 5, 'Ingreso mensual'
WHERE NOT EXISTS (SELECT 1 FROM movements);

INSERT INTO movements(type, amount, category, movement_date, description)
SELECT 'EXPENSE', 1250.00, 'Alimentación', CURRENT_DATE - 3, 'Supermercado'
WHERE (SELECT COUNT(*) FROM movements) = 1;

INSERT INTO movements(type, amount, category, movement_date, description)
SELECT 'EXPENSE', 620.00, 'Transporte', CURRENT_DATE - 1, 'Gasolina'
WHERE (SELECT COUNT(*) FROM movements) = 2;

INSERT INTO budgets(category, monthly_limit, budget_year, budget_month)
VALUES ('Alimentación', 3500.00, EXTRACT(YEAR FROM CURRENT_DATE), EXTRACT(MONTH FROM CURRENT_DATE))
ON CONFLICT (category, budget_year, budget_month) DO NOTHING;

INSERT INTO budgets(category, monthly_limit, budget_year, budget_month)
VALUES ('Transporte', 2000.00, EXTRACT(YEAR FROM CURRENT_DATE), EXTRACT(MONTH FROM CURRENT_DATE))
ON CONFLICT (category, budget_year, budget_month) DO NOTHING;
