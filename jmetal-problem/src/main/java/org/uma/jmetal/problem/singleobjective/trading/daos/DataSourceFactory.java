package org.uma.jmetal.problem.singleobjective.trading.daos;

/**
 * Created by des01c7 on 22-03-19.
 */

import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by root on 12-04-17.
 */
public class DataSourceFactory {

    static private final Logger logger = (Logger) LoggerFactory.getLogger(DataSourceFactory.class);

    private static final DataSourceFactory instance = new DataSourceFactory();

    Connection connection = null;

    private static final String DRIVER = "org.postgresql.Driver";
    private static final String PREFIX = "jdbc:postgresql://";
    private static final String HOST = "localhost";
    private static final String PORT = "5432";
    private static final String DB = "strategies";

    private static final String USER = "trader";
    private static final String PASS = "1q2w3e";

    /**
     * Constructor privado para el Singleton del Factory.
     */
    private DataSourceFactory() {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(PREFIX + "://" + HOST + ":" + PORT + "/" + DB, USER, PASS);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al conectarse a BD", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error al obtener una conexión", e);
        }
    }

    public static DataSourceFactory getInstance() {
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
