package com.system.utils;

/**
 * Clase que contiene las constantes de conexión a la base de datos.
 * Esta clase es utilizada por los DAODB para establecer la conexión con la base de datos.
 */
public class DAODBSConstants {
    public static final String DRIVER = "org.postgresql.Driver";
    public static final String URL = "jdbc:postgresql://localhost:5432/titanicdb";
    public static final String USER = "postgres";
    public static final String PASSWD = "daw1";
}
