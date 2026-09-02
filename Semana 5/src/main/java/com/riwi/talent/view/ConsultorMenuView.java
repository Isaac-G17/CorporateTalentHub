package com.riwi.talent.view;

import com.riwi.talent.controller.ConsultorExternoController;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Submenú de gestión de Consultores Externos. Comparte el mismo Scanner
 * que ConsoleView; toda la interacción con el usuario para esta sección
 * vive aquí, y las operaciones de datos se delegan siempre a
 * ConsultorExternoController.
 */
public class ConsultorMenuView {

    private final ConsultorExternoController consultorController;
    private final Scanner scanner;

    public ConsultorMenuView(Scanner scanner) {
        this.consultorController = new ConsultorExternoController();
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
                    case 1 -> registrarConsultor();
                    case 2 -> listarConsultores();
                    case 3 -> actualizarConsultor();
                    case 4 -> eliminarConsultor();
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
                        MENÚ CONSULTORES EXTERNOS
                -------------------------------------
                 1. Registrar Consultor Externo
                 2. Listar Consultores Externos
                 3. Actualizar Consultor Externo
                 4. Eliminar Consultor Externo
                 0. Volver al menú principal
                """);
    }

    private void registrarConsultor() {
        System.out.print("Nombre: ");
        var nombre = scanner.nextLine().trim();

        System.out.print("Edad: ");
        var edad = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Especialidad: ");
        var especialidad = scanner.nextLine().trim();

        consultorController.registrarConsultor(nombre, edad, especialidad);

        System.out.println("Consultor externo registrado correctamente.");
    }

    private void listarConsultores() {
        var consultores = consultorController.obtenerConsultores();

        if (consultores.isEmpty()) {
            System.out.println("No hay consultores externos registrados.");
            return;
        }

        System.out.println("\nLISTA DE CONSULTORES EXTERNOS");

        for (var consultor : consultores) {
            System.out.printf(
                    "ID: %d | Nombre: %s | Edad: %d | Especialidad: %s%n",
                    consultor.getId(), consultor.getNombre(), consultor.getEdad(), consultor.getEspecialidad());
        }
    }

    private void actualizarConsultor() {
        System.out.print("ID del consultor a actualizar: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nuevo nombre: ");
        var nombre = scanner.nextLine().trim();

        System.out.print("Nueva edad: ");
        var edad = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nueva especialidad: ");
        var especialidad = scanner.nextLine().trim();

        consultorController.actualizarConsultor(id, nombre, edad, especialidad);

        System.out.println("Consultor externo actualizado correctamente.");
    }

    private void eliminarConsultor() {
        System.out.print("ID del consultor a eliminar: ");
        var id = scanner.nextInt();
        scanner.nextLine();

        consultorController.eliminarConsultor(id);

        System.out.println("Consultor externo eliminado correctamente.");
    }
}
