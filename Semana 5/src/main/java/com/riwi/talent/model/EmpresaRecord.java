package com.riwi.talent.model;

/**
 * Un Record reduce la verbosidad: Java genera constructor, accesores,
 * equals, hashCode y toString a partir de sus componentes. Sus
 * componentes son inmutables: después de construir el Record no se
 * pueden reasignar. Se usa aquí para los datos fijos de la empresa que
 * se muestran en el banner de inicio (ver ConsoleView).
 */
public record EmpresaRecord(String nombre, String nit, int anioFundacion) {
}
