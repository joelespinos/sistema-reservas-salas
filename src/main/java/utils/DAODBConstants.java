package utils;

/**
 * Clase que contiene las constantes de conexión a la base de datos.
 * Esta clase es utilizada por los DAODB para establecer la conexión con la base de datos.
 */
public class DAODBConstants {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/reservations-system";
    public static final String USER = "root";
    public static final String PASSWD = "";
}
