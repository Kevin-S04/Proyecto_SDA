package services;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;

/**
 * Clase ConexionDB que gestiona la conexión a la base de datos MongoDB Atlas.
 * Implementa el patrón Singleton para asegurar una única instancia de MongoClient.
 */
public class ConexionDB {

    /** Instancia estática de MongoClient para la conexión a MongoDB. */
    private static MongoClient mongoClient = null;

    /** Cadena de conexión URI para MongoDB Atlas. */
    private static final String CONNECTION_STRING = "mongodb+srv://simbanaalexis2004:12345@adcluster.hz5cw.mongodb.net/?retryWrites=true&w=majority&appName=ADCluster";

    /** Nombre de la base de datos a la que se conectará. */
    private static final String DATABASE_NAME = "SDA";

    /**
     * Constructor privado para aplicar el patrón Singleton y evitar instanciación externa.
     */
    private ConexionDB() {}

    /**
     * Obtiene una instancia del MongoClient. Si no existe, crea una nueva conexión a MongoDB Atlas.
     * @return La instancia de MongoClient conectada.
     */
    public static MongoClient getMongoClient() {
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                System.out.println("\nConexión a MongoDB Atlas establecida con éxito.");
            } catch (MongoException e) {
                System.err.println("\nError al conectar con MongoDB Atlas: " + e.getMessage());
            }
        }
        return mongoClient;
    }

    /**
     * Obtiene la instancia de MongoDatabase a partir del MongoClient.
     * @return La instancia de MongoDatabase, o null si el cliente no está conectado.
     */
    public static MongoDatabase getDatabase() {
        if (getMongoClient() != null) {
            return getMongoClient().getDatabase(DATABASE_NAME);
        }
        return null;
    }

    /**
     * Cierra la conexión activa a MongoDB si existe.
     */
    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
}