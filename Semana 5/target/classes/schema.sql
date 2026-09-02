-- Ejecutar manualmente en PostgreSQL (psql, pgAdmin, DBeaver, etc.)
-- Es seguro volver a ejecutar este script: usa IF NOT EXISTS / bloques
-- condicionales, así que no borra ni duplica datos ya existentes.

CREATE TABLE IF NOT EXISTS empleados (
    id                  SERIAL PRIMARY KEY,
    nombre              VARCHAR(100)   NOT NULL,
    edad                INT            NOT NULL,
    salario             NUMERIC(12,2)  NOT NULL,
    lenguaje_principal  VARCHAR(50),
    calificacion1       NUMERIC(5,2)   NOT NULL,
    calificacion2       NUMERIC(5,2)   NOT NULL,
    calificacion3       NUMERIC(5,2)   NOT NULL
);

-- Migración: soporte para la jerarquía sealed Desarrollador / Gerente
-- (columna discriminadora "rol" + columna propia de Gerente).
ALTER TABLE empleados ADD COLUMN IF NOT EXISTS rol VARCHAR(20);
ALTER TABLE empleados ADD COLUMN IF NOT EXISTS presupuesto_mensual NUMERIC(14,2);
ALTER TABLE empleados ALTER COLUMN lenguaje_principal DROP NOT NULL;

-- Los registros creados antes de esta migración no tenían rol: se
-- asumen Desarrollador porque ya tenían lenguaje_principal cargado.
UPDATE empleados SET rol = 'DESARROLLADOR' WHERE rol IS NULL;

ALTER TABLE empleados ALTER COLUMN rol SET NOT NULL;

DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM pg_constraint WHERE conname = 'chk_empleados_rol'
    ) THEN
        ALTER TABLE empleados
            ADD CONSTRAINT chk_empleados_rol CHECK (rol IN ('DESARROLLADOR', 'GERENTE'));
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS consultores_externos (
    id            SERIAL PRIMARY KEY,
    nombre        VARCHAR(100) NOT NULL,
    edad          INT          NOT NULL,
    especialidad  VARCHAR(100) NOT NULL
);
