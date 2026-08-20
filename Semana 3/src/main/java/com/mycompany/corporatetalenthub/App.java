/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.corporatetalenthub;

import com.mycompany.corporatetalenthub.modelo.Empleado;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author cohorte5
 */
public class App {

    private static final int CANTIDAD_TRIMESTRES = 3;
    private static final double NOTA_MINIMA = 0.0;
    private static final double NOTA_MAXIMA = 100.0;
    private static final double PROMEDIO_PARA_PROMOCION = 80.0;

    /*
    * List.of() y Map.of() crean colecciones inmutables introducidas
    * desde Java 9. Son apropiadas para datos de configuración porque
    * no permiten agregar, eliminar ni modificar elementos después
    * de su creación, reduciendo cambios accidentales durante la ejecución.
    *
    * A diferencia de un ArrayList tradicional, estas colecciones no
    * permiten operaciones como add() o remove(). Si se necesita una
    * colección modificable, debe utilizarse una implementación mutable
    * como ArrayList.
     */
    private static final List<String> TECNOLOGIAS = List.of(
            "Java",
            "JavaScript",
            "Python",
            "SQL",
            "HTML",
            "CSS"
    );
    private static final Map<Integer, String> SEDES = Map.of(
            1, "Barranquilla",
            2, "Bogotá",
            3, "Medellín",
            4, "Cali"
    );

    public static void main(String[] args) {
        try (var scanner = new Scanner(System.in)) {
            var empleados = new ArrayList<Empleado>();
            var empleadosPorId = new HashMap<String, Empleado>();
            var sistemaActivo = true;

            do {
                mostrarMenu();

                try {
                    System.out.print("Seleccione una opción: ");
                    var opcion = scanner.nextInt();
                    scanner.nextLine(); // Consume el salto de línea pendiente.

                    /*
                     * Switch tradicional, compatible con Java 8.
                     * Cada case necesita break para impedir el fall-through. Si se
                     * olvida, Java continúa ejecutando el siguiente case. La Switch
                     * Expression moderna con -> no tiene ese riesgo por defecto y,
                     * además, puede producir directamente un valor.
                     */
                    switch (opcion) {
                        case 1:

                            var registrado = registrarEmpleado(
                                    scanner,
                                    empleados,
                                    empleadosPorId);

                            break;

                        case 2:

                            listarEmpleados(empleados);

                            break;

                        case 3:
                            buscarEmpleado(scanner, empleadosPorId);

                            break;

                        case 4:
                            eliminarEmpleado(scanner, empleados, empleadosPorId);

                            break;

                        case 5:
                            mostrarTecnologiasYSedes(TECNOLOGIAS, SEDES);
                            break;
                        case 6:
                            mostrarReporte(empleados);

                            break;

                        case 7:
                            mostrarCategoriasSalariales();
                            break;

                        case 0:
                            sistemaActivo = false;
                            System.out.println("Sesión finalizada.");
                            break;

                        default:
                            System.out.println("Opción fuera del menú.");
                            break;
                    }
                } catch (InputMismatchException excepcion) {
                    System.out.println(
                            "Entrada inválida. Debe escribir un valor numérico "
                            + "del tipo solicitado.");

                    // Descarta la entrada que provocó la excepción. Sin esta línea,
                    // Scanner intentaría leer el mismo dato inválido nuevamente.
                    scanner.nextLine();

                    /*
                     * Java 8 ya entrega el tipo de excepción y el stack trace. Las
                     * versiones modernas mejoraron especialmente algunos diagnósticos,
                     * como Helpful NullPointerExceptions desde Java 14, indicando qué
                     * referencia era null en una expresión. Esto no significa que el
                     * mensaje de toda InputMismatchException sea siempre más detallado;
                     * por eso la aplicación muestra un mensaje comprensible al usuario.
                     */
                }
            } while (sistemaActivo);
        }
    }

    private static void mostrarMenu() {
        System.out.println("""

                =====================================
                     CORPORATE TALENT HUB
                =====================================
                 1. Registrar empleado
                 2. Listar empleados
                 3. Buscar empleado por ID
                 4. Eliminar empleado
                 5. Consultar tecnologías y sedes          
                 6. Mostrar reporte de desempeño
                 7. Consultar categorías salariales
                 0. Salir
                """);
    }

    private static void mostrarTecnologiasYSedes(
            List<String> tecnologias,
            Map<Integer, String> sedes) {

        System.out.println("TECNOLOGÍAS");

        for (var tecnologia : tecnologias) {
            System.out.println("- " + tecnologia);
        }

        System.out.println("SEDES");

        for (var sede : sedes.entrySet()) {
            System.out.println(
                    sede.getKey() + ". " + sede.getValue());
        }
    }

    private static boolean registrarEmpleado(
            Scanner scanner,
            ArrayList<Empleado> empleados,
            HashMap<String, Empleado> empleadosPorId) {

        System.out.print("ID positivo: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        if (id <= 0) {
            System.out.println("El ID debe ser mayor que cero.");
            return false;
        }

        var claveId = String.valueOf(id);

        if (empleadosPorId.containsKey(claveId)) {
            System.out.println("Ya existe un empleado con ese ID.");
            return false;
        }

        System.out.print("Nombre: ");
        var nombre = scanner.nextLine().trim();

        if (nombre.isBlank()) {
            System.out.println("El nombre no puede estar vacío.");
            return false;
        }

        System.out.print("Edad entre 18 y 100: ");
        var edadIngresada = scanner.nextInt();

        if (edadIngresada < 18 || edadIngresada > 100) {
            System.out.println("La edad está fuera del rango permitido.");
            scanner.nextLine();
            return false;
        }

        // Scanner entrega un int; después de validar el rango se convierte a byte.
        var edad = (byte) edadIngresada;

        System.out.print("Salario mayor que cero: ");
        var salario = scanner.nextDouble();

        if (salario <= 0) {
            System.out.println("El salario debe ser mayor que cero.");
            scanner.nextLine();
            return false;
        }

        var calificaciones = new double[CANTIDAD_TRIMESTRES];

        for (var trimestre = 0;
                trimestre < CANTIDAD_TRIMESTRES;
                trimestre++) {

            System.out.printf(
                    "Calificación del trimestre %d (0 a 100): ",
                    trimestre + 1);

            var calificacion = scanner.nextDouble();

            if (calificacion < NOTA_MINIMA
                    || calificacion > NOTA_MAXIMA) {

                System.out.println(
                        "La calificación está fuera del rango permitido.");

                scanner.nextLine();
                return false;
            }

            calificaciones[trimestre] = calificacion;
        }

        scanner.nextLine();

        var empleado = new Empleado(id, nombre, edad, salario, calificaciones);

        empleados.add(empleado);

        empleadosPorId.put(claveId, empleado);

        System.out.println("Empleado registrado correctamente.");

        return true;
    }

    private static void listarEmpleados(
            ArrayList<Empleado> empleados) {

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\nLISTA DE EMPLEADOS");

        for (var empleado : empleados) {
            System.out.printf(
                    "ID: %d | Nombre: %s | Edad: %d | Salario: %.2f%n",
                    empleado.getId(),
                    empleado.getNombre(),
                    empleado.getEdad(),
                    empleado.getSalario()
            );
        }
    }

    private static void buscarEmpleado(
            Scanner scanner,
            HashMap<String, Empleado> empleadosPorId) {

        System.out.print("Ingrese el ID del empleado: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        var claveId = String.valueOf(id);

        var empleado = empleadosPorId.get(claveId);

        if (empleado == null) {
            System.out.println("No existe un empleado con ese ID.");
            return;
        }

        System.out.printf(
                "ID: %d | Nombre: %s | Edad: %d | Salario: %.2f%n",
                empleado.getId(),
                empleado.getNombre(),
                empleado.getEdad(),
                empleado.getSalario()
        );
    }

    private static void eliminarEmpleado(
            Scanner scanner,
            ArrayList<Empleado> empleados,
            HashMap<String, Empleado> empleadosPorId) {

        System.out.print("Ingrese el ID del empleado que desea eliminar: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        var claveId = String.valueOf(id);

        var empleado = empleadosPorId.get(claveId);

        if (empleado == null) {
            System.out.println("No existe un empleado con ese ID.");
            return;
        }

        empleados.remove(empleado);
        empleadosPorId.remove(claveId);

        System.out.println(
                "Empleado " + empleado.getNombre()
                + " eliminado correctamente.");
    }

    private static void mostrarReporte(
            ArrayList<Empleado> empleados) {

        if (empleados.isEmpty()) {
            System.out.println("Todavía no hay empleados registrados.");
            return;
        }

        System.out.println("\nREPORTE DE DESEMPEÑO");

        for (var empleado : empleados) {
            var calificaciones = empleado.getCalificaciones();
            var suma = 0.0;

            // Los dos for forman el recorrido anidado de la matriz.
            for (var calificacion : calificaciones) {
                suma += calificacion;
            }

            var promedio = suma / calificaciones.length;
            empleado.setPromedioDesempeno(promedio);

            /*
             * Casting explícito de double a int. Se elimina la parte decimal, no
             * se redondea: 89.99 se convierte en 89. Esto implica pérdida de precisión.
             */
            var puntajeSimplificado = (int) promedio;

            // Operador ternario: condición ? resultadoSiTrue : resultadoSiFalse.
            var estadoPromocion = promedio >= PROMEDIO_PARA_PROMOCION
                    ? "PROMOVIDO"
                    : "NO PROMOVIDO";

            var categoria = obtenerCategoriaSalarial(
                    empleado.getSalario());

            System.out.printf(
                    "ID: %d | Nombre: %s | Promedio: %.2f | "
                    + "Simplificado: %d | Estado: %s | Categoría: %s%n",
                    empleado.getId(),
                    empleado.getNombre(),
                    promedio,
                    puntajeSimplificado,
                    estadoPromocion,
                    categoria);
        }
    }

    public static String obtenerCategoriaSalarial(double salario) {
        var rango = determinarRangoSalarial(salario);

        /*
         * Switch Expression moderna. La flecha evita el fall-through y el switch
         * devuelve un valor, por lo que no se necesita asignar y usar break en cada case.
         */
        return switch (rango) {
            case 1 ->
                "JUNIOR";
            case 2 ->
                "SEMISENIOR";
            case 3 ->
                "SENIOR";
            case 4 ->
                "LÍDER";
            default ->
                throw new IllegalArgumentException(
                        "Rango salarial no reconocido: " + rango);
        };
    }

    private static int determinarRangoSalarial(double salario) {
        if (salario < 2_000_000.0) {
            return 1;
        } else if (salario < 4_000_000.0) {
            return 2;
        } else if (salario < 7_000_000.0) {
            return 3;
        } else {
            return 4;
        }
    }

    private static void mostrarCategoriasSalariales() {
        System.out.println("""
                Categorías:
                - Menos de $2.000.000: JUNIOR
                - Desde $2.000.000 y menos de $4.000.000: SEMISENIOR
                - Desde $4.000.000 y menos de $7.000.000: SENIOR
                - Desde $7.000.000: LÍDER
                """);
    }
}
