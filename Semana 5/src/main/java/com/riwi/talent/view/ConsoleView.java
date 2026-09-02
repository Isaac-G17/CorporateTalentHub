package com.riwi.talent.view;

import com.riwi.talent.model.EmpresaRecord;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Menú central de la aplicación. Solo orquesta la navegación entre los
 * submenús de Empleados y Consultores Externos, y muestra la
 * información general de la empresa. Toda la interacción con Scanner
 * de la aplicación pasa por esta clase y por los submenús a los que
 * delega, nunca por el Controller ni por el Modelo.
 */
public class ConsoleView {

    private static final EmpresaRecord EMPRESA =
            new EmpresaRecord("Riwi Corporate Talent Hub", "900123456-7", 2020);

    /*
     * List.of() y Map.of() crean colecciones inmutables (Java 9+),
     * apropiadas para datos de configuración: no permiten add()/remove()
     * después de creadas, reduciendo cambios accidentales en ejecución.
     */
    private static final List<String> TECNOLOGIAS = List.of(
            "Java", "JavaScript", "Python", "SQL", "HTML", "CSS");

    private static final Map<Integer, String> SEDES = Map.of(
            1, "Barranquilla", 2, "Bogotá", 3, "Medellín", 4, "Cali");

    private final Scanner scanner;
    private final EmpleadoMenuView empleadoMenuView;
    private final ConsultorMenuView consultorMenuView;

    public ConsoleView() {
        this.scanner = new Scanner(System.in);
        this.empleadoMenuView = new EmpleadoMenuView(scanner);
        this.consultorMenuView = new ConsultorMenuView(scanner);
    }

    public void iniciar() {
        System.out.printf(
                "%s (NIT %s, fundada en %d)%n",
                EMPRESA.nombre(), EMPRESA.nit(), EMPRESA.anioFundacion());

        var activo = true;

        do {
            mostrarMenu();

            try {
                System.out.print("Seleccione una opción: ");
                var opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> empleadoMenuView.iniciar();
                    case 2 -> consultorMenuView.iniciar();
                    case 3 -> mostrarTecnologiasYSedes();
                    case 0 -> {
                        activo = false;
                        System.out.println("Sesión finalizada.");
                    }
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

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("""

                =====================================
                     CORPORATE TALENT HUB
                =====================================
                 1. Gestión de Empleados
                 2. Gestión de Consultores Externos
                 3. Consultar tecnologías y sedes
                 0. Salir
                """);
    }

    private void mostrarTecnologiasYSedes() {
        System.out.println("TECNOLOGÍAS");

        for (var tecnologia : TECNOLOGIAS) {
            System.out.println("- " + tecnologia);
        }

        System.out.println("SEDES");

        for (var sede : SEDES.entrySet()) {
            System.out.println(sede.getKey() + ". " + sede.getValue());
        }
    }
}
