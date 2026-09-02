package com.riwi.talent.view;

import com.riwi.talent.controller.EmpleadoController;
import com.riwi.talent.model.Desarrollador;
import com.riwi.talent.model.DesempeñoReport;
import com.riwi.talent.model.Empleado;
import com.riwi.talent.model.Gerente;
import com.riwi.talent.model.Promocionable;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Submenú de gestión de Empleados (Desarrollador/Gerente). Comparte el
 * mismo Scanner que ConsoleView; toda la interacción con el usuario
 * para esta sección vive aquí, y las operaciones de datos se delegan
 * siempre a EmpleadoController.
 */
public class EmpleadoMenuView {

    private final EmpleadoController empleadoController;
    private final Scanner scanner;

    public EmpleadoMenuView(Scanner scanner) {
        this.empleadoController = new EmpleadoController();
        this.scanner = scanner;
    }

    public void iniciar() {
        var activo = true;

        do {
            mostrarMenu();

            try {
                System.out.print("Seleccione una opción: ");
                var opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> registrarEmpleado();
                    case 2 -> listarEmpleados();
                    case 3 -> actualizarEmpleado();
                    case 4 -> eliminarEmpleado();
                    case 5 -> mostrarReporteDesempeno();
                    case 6 -> mostrarCategoriasSalariales();
                    case 7 -> mostrarOrdenEmpleados();
                    case 8 -> filtrarPorPuntajeMinimo();
                    case 9 -> mostrarReporteFinDeMes();
                    case 10 -> mostrarDetallePorRol();
                    case 11 -> calcularBonoDeAscenso();
                    case 0 -> activo = false;
                    default -> System.out.println("Opción fuera del menú.");
                }
            } catch (InputMismatchException excepcion) {
                System.out.println(
                        "Entrada inválida. Debe escribir un valor numérico del tipo solicitado.");
                scanner.nextLine();
            } catch (RuntimeException excepcion) {
                System.out.println("Ocurrió un error: " + excepcion.getMessage());
            }
        } while (activo);
    }

    private void mostrarMenu() {
        System.out.println("""

                -------------------------------------
                          MENÚ EMPLEADOS
                -------------------------------------
                 1. Registrar Empleado (Desarrollador/Gerente)
                 2. Listar Empleados
                 3. Actualizar Empleado
                 4. Eliminar Empleado
                 5. Mostrar reporte de desempeño
                 6. Mostrar categorías salariales
                 7. Mostrar orden de empleados
                 8. Filtrar por puntaje mínimo
                 9. Generar reporte de fin de mes
                10. Mostrar detalle por rol (Legacy vs Moderno)
                11. Calcular bonos de ascenso (Promocionable)
                 0. Volver al menú principal
                """);
    }

    private void registrarEmpleado() {
        System.out.print("Nombre: ");
        var nombre = scanner.nextLine().trim();

        System.out.print("Edad: ");
        var edad = scanner.nextInt();

        System.out.print("Salario: ");
        var salario = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Calificación trimestre 1 (0 a 100): ");
        var c1 = scanner.nextDouble();

        System.out.print("Calificación trimestre 2 (0 a 100): ");
        var c2 = scanner.nextDouble();

        System.out.print("Calificación trimestre 3 (0 a 100): ");
        var c3 = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Rol (1 = Desarrollador, 2 = Gerente): ");
        var rol = scanner.nextInt();
        scanner.nextLine();

        switch (rol) {
            case 1 -> {
                System.out.print("Lenguaje principal: ");
                var lenguaje = scanner.nextLine().trim();
                empleadoController.registrarDesarrollador(nombre, edad, salario, lenguaje, c1, c2, c3);
            }
            case 2 -> {
                System.out.print("Presupuesto mensual que administra: ");
                var presupuesto = scanner.nextDouble();
                scanner.nextLine();
                empleadoController.registrarGerente(nombre, edad, salario, presupuesto, c1, c2, c3);
            }
            default -> {
                System.out.println("Rol no reconocido, registro cancelado.");
                return;
            }
        }

        System.out.println("Empleado registrado correctamente.");
    }

    private void listarEmpleados() {
        var empleados = empleadoController.obtenerEmpleados();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\nLISTA DE EMPLEADOS");

        for (var empleado : empleados) {
            System.out.printf(
                    "ID: %d | Nombre: %s | Edad: %d | Salario: %.2f%n",
                    empleado.getId(), empleado.getNombre(), empleado.getEdad(), empleado.getSalario());
        }
    }

    private void actualizarEmpleado() {
        System.out.print("ID del empleado a actualizar: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo nombre: ");
        var nombre = scanner.nextLine().trim();

        System.out.print("Nueva edad: ");
        var edad = scanner.nextInt();

        System.out.print("Nuevo salario: ");
        var salario = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Calificación trimestre 1 (0 a 100): ");
        var c1 = scanner.nextDouble();

        System.out.print("Calificación trimestre 2 (0 a 100): ");
        var c2 = scanner.nextDouble();

        System.out.print("Calificación trimestre 3 (0 a 100): ");
        var c3 = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Rol (1 = Desarrollador, 2 = Gerente): ");
        var rol = scanner.nextInt();
        scanner.nextLine();

        switch (rol) {
            case 1 -> {
                System.out.print("Lenguaje principal: ");
                var lenguaje = scanner.nextLine().trim();
                empleadoController.actualizarDesarrollador(id, nombre, edad, salario, lenguaje, c1, c2, c3);
            }
            case 2 -> {
                System.out.print("Presupuesto mensual que administra: ");
                var presupuesto = scanner.nextDouble();
                scanner.nextLine();
                empleadoController.actualizarGerente(id, nombre, edad, salario, presupuesto, c1, c2, c3);
            }
            default -> {
                System.out.println("Rol no reconocido, actualización cancelada.");
                return;
            }
        }

        System.out.println("Empleado actualizado correctamente.");
    }

    private void eliminarEmpleado() {
        System.out.print("ID del empleado a eliminar: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        empleadoController.eliminarEmpleado(id);

        System.out.println("Empleado eliminado correctamente.");
    }

    private void mostrarReporteDesempeno() {
        var empleados = empleadoController.obtenerEmpleados();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\nREPORTE DE DESEMPEÑO");

        for (var empleado : empleados) {
            var promedio = empleado.getPromedioDesempeno();
            var categoria = empleadoController.obtenerCategoriaSalarial(empleado);
            var estado = empleadoController.obtenerEstadoPromocion(empleado);

            System.out.printf(
                    "ID: %d | Nombre: %s | Promedio: %.2f | Estado: %s | Categoría: %s%n",
                    empleado.getId(), empleado.getNombre(), promedio, estado, categoria);
        }
    }

    private void mostrarCategoriasSalariales() {
        System.out.println("""
                Categorías:
                - Menos de $2.000.000: JUNIOR
                - Desde $2.000.000 y menos de $4.000.000: SEMISENIOR
                - Desde $4.000.000 y menos de $7.000.000: SENIOR
                - Desde $7.000.000: LÍDER
                """);
    }

    /*
     * Java Legacy consultaba los extremos mediante índices: get(0) y
     * get(size() - 1). Java 21 incorpora getFirst()/getLast() en
     * SequencedCollection, que expresan directamente la intención, y
     * reversed() para recorrer la lista en sentido inverso sin crear
     * una copia manual.
     */
    private void mostrarOrdenEmpleados() {
        var empleados = empleadoController.obtenerEmpleados();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        var primero = empleados.getFirst();
        var ultimo = empleados.getLast();

        System.out.println("Primer empleado: " + primero.getNombre());
        System.out.println("Último empleado: " + ultimo.getNombre() + "\n");

        System.out.println("LISTA DE EMPLEADOS EN ORDEN INVERSO");

        for (var empleado : empleados.reversed()) {
            System.out.println("Empleado: " + empleado.getNombre());
        }
    }

    private void filtrarPorPuntajeMinimo() {
        System.out.print("Ingrese el puntaje mínimo de desempeño (0 a 100): ");
        var puntajeMinimo = scanner.nextDouble();
        scanner.nextLine();

        var eliminados = empleadoController.filtrarPorPuntajeMinimo(puntajeMinimo);

        System.out.println(eliminados + " empleado(s) eliminado(s) por no alcanzar el puntaje mínimo.");
    }

    private void mostrarReporteFinDeMes() {
        List<DesempeñoReport> reportes = empleadoController.generarReporteFinal();

        if (reportes.isEmpty()) {
            System.out.println("No hay datos para generar el reporte.");
            return;
        }

        System.out.println("\nREPORTE FINAL CONSOLIDADO");

        for (var reporte : reportes) {
            System.out.print("""
                    -------------------------------------
                    ID Empleado : %d
                    Promedio    : %.2f
                    Feedback    : %s
                    -------------------------------------
                    """.formatted(reporte.idEmpleado(), reporte.promedio(), reporte.feedback()));
        }
    }

    /*
     * Java 8/11 requería comprobar el tipo del objeto mediante instanceof
     * y realizar un casting manual para acceder a los atributos propios
     * de cada subclase, repitiendo la comprobación en cada rama.
     *
     * Java 21 permite Pattern Matching for switch: el tipo se comprueba
     * y se obtiene directamente una variable ya tipada ("des", "ger"),
     * sin casting manual. Al ser Empleado una sealed class, el switch es
     * exhaustivo sin necesitar una rama default.
     */
    private void mostrarDetallePorRol() {
        var empleados = empleadoController.obtenerEmpleados();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\nESTILO LEGACY (instanceof + casting manual)");

        for (var empleado : empleados) {
            if (empleado instanceof Desarrollador) {
                var lenguaje = ((Desarrollador) empleado).getLenguajePrincipal();
                System.out.printf(
                        "ID: %d | NOMBRE: %s | ROL: Desarrollador | LENGUAJE: %s%n",
                        empleado.getId(), empleado.getNombre(), lenguaje);
            } else if (empleado instanceof Gerente) {
                var presupuesto = ((Gerente) empleado).getPresupuestoMensual();
                System.out.printf(
                        "ID: %d | NOMBRE: %s | ROL: Gerente | PRESUPUESTO: %.2f%n",
                        empleado.getId(), empleado.getNombre(), presupuesto);
            }
        }

        System.out.println("\nESTILO MODERNO (Pattern Matching)");

        for (var empleado : empleados) {
            switch (empleado) {
                case Desarrollador des -> System.out.printf(
                        "ID: %d | NOMBRE: %s | ROL: Desarrollador | LENGUAJE: %s%n",
                        des.getId(), des.getNombre(), des.getLenguajePrincipal());
                case Gerente ger -> System.out.printf(
                        "ID: %d | NOMBRE: %s | ROL: Gerente | PRESUPUESTO: %.2f%n",
                        ger.getId(), ger.getNombre(), ger.getPresupuestoMensual());
            }
        }
    }

    private void calcularBonoDeAscenso() {
        var empleados = empleadoController.obtenerEmpleados();

        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados.");
            return;
        }

        System.out.println("\nBONOS DE ASCENSO");

        for (var empleado : empleados) {
            if (empleado instanceof Promocionable promocionable) {
                var bono = promocionable.calcularBonoAscenso();
                promocionable.registrarLog(empleado.getNombre(), bono);
            }
        }
    }
}
