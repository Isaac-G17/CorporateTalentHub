# Corporate Talent Hub

Proyecto de práctica en Java desarrollado semana a semana durante el curso, simulando un sistema de gestión de talento humano. Cada carpeta `Semana N` es un proyecto Maven independiente que retoma y evoluciona el dominio de la semana anterior.

## Progresión por semana

- **[Semana 1](Semana%201)** — Fundamentos de POO: clase `Empleado`, encapsulamiento, `record` básico (`EmpresaRecord`), Text Blocks y operadores.
- **[Semana 2](Semana%202)** — Arrays y matrices (empleados y calificaciones), `Scanner`, validaciones de entrada, manejo de excepciones y `switch` tradicional.
- **[Semana 3](Semana%203)** — Colecciones (`ArrayList`, `HashMap`), colecciones inmutables (`List.of`, `Map.of`), `switch` expressions modernas y generación de reportes.
- **[Semana 4](Semana%204)** — POO avanzada: `sealed classes` (jerarquía protegida `Persona`/`Empleado`/`Desarrollador`/`Gerente`/`ConsultorExterno`), `record` inmutable para reportes de desempeño, Pattern Matching for `instanceof`/`switch` (elimina el casting manual) e interfaz `Promocionable` con método `default`.
- **[Semana 5](Semana%205)** — Persistencia relacional (JDBC + PostgreSQL) y arquitectura MVC: la jerarquía de Semana 4 se persiste con `PreparedStatement` y `try-with-resources`, organizada en paquetes `model` / `controller` / `view`. Incluye instrucciones de configuración de base de datos — ver el README de esa carpeta.

## Requisitos generales

- JDK 21+
- Maven 3.9+
- PostgreSQL (solo necesario para Semana 5)

Cada semana se compila y ejecuta de forma independiente desde su propia carpeta:

```bash
cd "Semana N"
mvn compile
mvn exec:java
```
