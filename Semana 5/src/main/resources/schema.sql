-- Ejecutar manualmente en PostgreSQL
-- (psql, pgAdmin, DBeaver, etc.)

-- Es seguro volver a ejecutar el script:
-- IF NOT EXISTS evita crear nuevamente las tablas.

CREATE TABLE IF NOT EXISTS empleados (
    id                  SERIAL PRIMARY KEY,
    nombre              VARCHAR(100)   NOT NULL,
    edad                INT            NOT NULL,
    salario             NUMERIC(12,2)  NOT NULL,
    lenguaje_principal  VARCHAR(50),
    calificacion1       NUMERIC(5,2)   NOT NULL,
    calificacion2       NUMERIC(5,2)   NOT NULL,
    calificacion3       NUMERIC(5,2)   NOT NULL,
    rol                 VARCHAR(20)    NOT NULL,
    presupuesto_mensual NUMERIC(14,2),

    CONSTRAINT chk_empleados_rol
        CHECK (rol IN ('DESARROLLADOR', 'GERENTE'))
);

CREATE TABLE IF NOT EXISTS consultores_externos (
    id           SERIAL PRIMARY KEY,
    nombre       VARCHAR(100) NOT NULL,
    edad         INT          NOT NULL,
    especialidad VARCHAR(100) NOT NULL
);
