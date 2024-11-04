package com.alesandro.olimpiadas.db;

/**
 * Excepción personalizada para los posibles errores con la base de datos
 */
public class DBException extends Exception {
    /**
     * Constructor de la clase al que se le pasa el mensaje de error
     *
     * @param error mensaje de error
     */
    public DBException(String error) {
        super(error);
    }

}
