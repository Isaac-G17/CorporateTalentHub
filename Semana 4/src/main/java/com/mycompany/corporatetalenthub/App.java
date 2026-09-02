/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.corporatetalenthub;

import com.mycompany.corporatetalenthub.modelo.DesempeñoReport;
import com.mycompany.corporatetalenthub.modelo.Empleado;
import com.mycompany.corporatetalenthub.modelo.Desarrollador;
import com.mycompany.corporatetalenthub.modelo.Gerente;
import com.mycompany.corporatetalenthub.modelo.Persona;
import com.mycompany.corporatetalenthub.modelo.Promocionable;
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
            
            cargarEmpleadosDePrueba(empleados, empleadosPorId);

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
 
                        case 8:
                            mostrarOrdenEmpleados(empleados);
                            break;
                            
                        case 9:
                            filtrarPorPuntajeMinimo(scanner, empleados, empleadosPorId);
                            break;
                           
                        case 10:
                            mostrarReporteFindeMes(empleados);
                            break;
                            
                        case 11:
                            mostrarDetallePorRol(empleados);
                            break;
                        
                        case 12:
                            calcularBonoDeAscenso(empleados);
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
                 8. Mostrar Orden de Empleados
                 9. Filtrar por puntaje mínimo
                10. Generar reporte de fin de mes
                11. Mostrar detalles por rol
                12. Calcular bonos de ascenso (Promocionable)                  
                 0. Salir
                """);
    }
    
    private static void cargarEmpleadosDePrueba(
            ArrayList<Empleado> empleados,
            HashMap<String, Empleado> empleadosPorId) {
 
        var empleadosDePrueba = List.of(
                new Desarrollador(1, "Ana García", (byte) 28, 1_800_000.0,
                        new double[]{85.0, 90.0, 78.0},"Java"),
                new Desarrollador(2, "Carlos Pérez", (byte) 34, 3_200_000.0,
                        new double[]{92.0, 88.0, 95.0}, "Python"),
                new Desarrollador(3, "Laura Rodríguez", (byte) 41, 5_500_000.0,
                        new double[]{70.0, 65.0, 72.0},"C#"),
                new Desarrollador(4, "Miguel Torres", (byte) 25, 1_500_000.0,
                        new double[]{60.0, 55.0, 58.0}, "JavaScript"),
                new Gerente(5, "Sofía Ramírez", (byte) 37, 8_000_000.0,
                        new double[]{97.0, 99.0, 96.0}, 25_000_000.0)
        );
 
        for (var empleado : empleadosDePrueba) {
            empleados.add(empleado);
            empleadosPorId.put(String.valueOf(empleado.getId()), empleado);
        }
 
        System.out.println(
                empleados.size() + " empleados de prueba cargados.");
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

        System.out.print("Rol (1 = Desarrollador, 2 = Gerente): ");
        var rol = scanner.nextInt();
        scanner.nextLine();
        
        Empleado empleado;
        
        switch (rol){
            case 1 -> {
                System.out.print("Lenguaje Principal: ");
                var lenguajePrincipal = scanner.nextLine().trim();
                empleado = new Desarrollador(id, nombre, edad, salario, 
                            calificaciones, lenguajePrincipal); 
            }
            case 2 -> {
                System.out.print("Presupuesto mensual que administra: ");
                var presupuestoMensual = scanner.nextDouble();
                scanner.nextLine();
                empleado = new Gerente(id, nombre, edad, salario, 
                            calificaciones, presupuestoMensual);
            }
            default -> {
                System.out.println("Rol no reconocido, Registro cancelado");
                return false;
            }
        }
        
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

    private static void filtrarPorPuntajeMinimo(
            Scanner scanner,
            ArrayList<Empleado> empleados,
            HashMap<String, Empleado> empleadosPorId) {

        if (empleados.isEmpty()) {
            System.out.println("Todavía no hay empleados registrados.");
            return;
        }

        System.out.print("Ingrese el puntaje mínimo de desempeño ( 0 a 100): ");

        var puntajeMinimo = scanner.nextDouble();
        scanner.nextLine();

        if (puntajeMinimo < NOTA_MINIMA || puntajeMinimo > NOTA_MAXIMA) {
            System.out.println("El puntaje mínimo está fuera del rango permitido");
            return;
        }

        var empleadosAntes = empleados.size();

        empleados.removeIf(empleado -> {
            var calificaciones = empleado.getCalificaciones();
        
            var suma = 0.0;

            for (var calificacion : calificaciones) {
                suma += calificacion;
            }

            var promedio = suma / calificaciones.length;
            empleado.setPromedioDesempeno(promedio);

            var noCumplePuntaje = promedio < puntajeMinimo;

            if (noCumplePuntaje) {
                empleadosPorId.remove(String.valueOf(empleado.getId()));
            }

            return noCumplePuntaje;
        });
        
        var empleadosDespues = empleados.size();
        var eliminados = empleadosAntes - empleadosDespues;
        
        System.out.println( eliminados + " Empleado(s) eliminado(s) por no alcanzar el puntaje mínimo");
    }

    private static void mostrarReporte(
            ArrayList<Empleado> empleados) {

        if (empleados.isEmpty()) {
            System.out.println("Todavía no hay empleados registrados.");
            return;
        }

        System.out.println("\nREPORTE DE DESEMPEÑO");
        
        var totalEmpleados = empleados.size();
        var sumaSalarios = 0.0;

        for (var empleado : empleados) {
            sumaSalarios += empleado.getSalario();
        }

        var promedioSalarios = sumaSalarios / totalEmpleados;

        System.out.printf("""
                          Total de empleados: %d%nPromedio de salarios: %.2f%n
                          """, totalEmpleados, promedioSalarios);
        
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


    /*
    * En Java Legacy los extremos se consultaban mediante índices:
    * get(0) y get(size() - 1).
    *
    * Java 21 incorpora getFirst() y getLast(), que expresan directamente
    * la intención. reversed() permite recorrer la secuencia en sentido inverso.
    */
    private static void mostrarOrdenEmpleados(
            List<Empleado> empleados) {

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        var primero = empleados.getFirst();

        var ultimo = empleados.getLast();

        System.out.println("Primer empleado " + primero.getNombre());
        System.out.println("Ultimo empleado " + ultimo.getNombre() + "\n");

        System.out.println("Lista de empleados en orden inverso".toUpperCase());

        for (var empleado : empleados.reversed()) {
            System.out.println("Empleado: " + empleado.getNombre());
        }
    }
    /*
    * Java 8/11 requería comprobar el tipo del objeto mediante instanceof
    * y posteriormente realizar un casting manual para acceder a los
    * atributos y métodos específicos de cada subclase.
    *
    * Java 21 permite utilizar Pattern Matching for switch, donde el tipo
    * del objeto se comprueba directamente en cada case y se obtiene una
    * variable ya tipada, como "des" para Desarrollador o "ger" para Gerente.
    *
    * Esta evolución reduce el código repetitivo, evita el casting manual
    * y mejora la legibilidad y seguridad del código al trabajar con
    * diferentes tipos dentro de una misma jerarquía.
    */
    /*
    * Sintaxis Legacy (Java 8/11): validación de rol mediante instanceof
    * seguido de un casting manual obligatorio. Cada rama repite la
    * comprobación de tipo y, antes de poder invocar un método propio de
    * la subclase, hay que convertir explícitamente la referencia, por
    * ejemplo ((Desarrollador) persona).getLenguajePrincipal(). Si se
    * olvida el cast, el código no compila; si el cast es incorrecto,
    * falla en tiempo de ejecución con ClassCastException.
    */
    private static void validarRolLegacy(Persona persona) {
        if (persona instanceof Desarrollador) {
            var lenguaje = ((Desarrollador) persona).getLenguajePrincipal();
            System.out.printf(
                    "ID: %d | NOMBRE: %s | ROL: Desarrollador | LENGUAJE: %s%n",
                    persona.getId(),
                    persona.getNombre(),
                    lenguaje);
        } else if (persona instanceof Gerente) {
            var presupuesto = ((Gerente) persona).getPresupuestoMensual();
            System.out.printf(
                    "ID: %d | NOMBRE: %s | ROL: Gerente | PRESUPUESTO: %.2f%n",
                    persona.getId(),
                    persona.getNombre(),
                    presupuesto);
        } else {
            System.out.printf(
                    "ID: %d | NOMBRE: %s | ROL: Empleado%n",
                    persona.getId(),
                    persona.getNombre());
        }
    }

    private static void mostrarDetallePorRol(
            ArrayList<Empleado> empleados){

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\nEstilo Legacy (instanceof + casting manual)".toUpperCase());

        for (var empleado : empleados) {
            validarRolLegacy(empleado);
        }

        System.out.println("\nEstilo Moderno (Pattern Matching)".toUpperCase());

        for( var empleado: empleados){

            switch (empleado) {
                case Desarrollador des -> System.out.printf(
                        "ID: %d | NOMBRE: %s | ROL: Desarrollador | LENGUAJE: %s%n",
                        des.getId(),
                        des.getNombre(),
                        des.getLenguajePrincipal());
                case Gerente ger -> System.out.printf(
                        "ID: %d | NOMBRE: %s | ROL: Gerente | PRESUPUESTO: %.2f%n",
                        ger.getId(),
                        ger.getNombre(),
                        ger.getPresupuestoMensual());
                default -> System.out.printf(
                    "ID: %d | NOMBRE: %s | ROL: Empleado%n",
                    empleado.getId(),
                    empleado.getNombre());
            }

        }
    }

    private static void mostrarReporteFindeMes(
            ArrayList<Empleado> empleados){
            
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        
        System.out.println("\nReportes de desempeño (fin de mes)".toUpperCase());
    
        for (var empleado : empleados) {

            var calificaciones = empleado.getCalificaciones();
            var suma = 0.0;

            for (var calificacion : calificaciones) {
                suma += calificacion;
            }

            var promedio = suma / calificaciones.length;

            var feedback = promedio >= PROMEDIO_PARA_PROMOCION
                ? "PROMOVIDO"
                : "NO PROMOVIDO";

            var reporte = new DesempeñoReport(
                empleado.getId(),
                promedio,
                feedback);

            System.out.printf(
                "ID: %d | Promedio: %.2f | Feedback: %s%n",
                reporte.idEmpleado(),
                reporte.promedio(),
                reporte.feedback());
        }
    }
    
    private static void calcularBonoDeAscenso(
            ArrayList<Empleado> empleados){
        
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }
        
        System.out.println("\nBonos de Ascenso".toUpperCase());

        for(var empleado: empleados){
            if(empleado instanceof Promocionable promocionable){
               var bono = promocionable.calcularBonoAscenso();
               promocionable.registrarLog(empleado.getNombre(),bono);
            }
        }

    }
}
