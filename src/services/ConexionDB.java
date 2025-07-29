package services;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;


public class ConexionDB {

    private  static MongoClient mongoClient=null;

    private static final String CONNECTION_STRING="mongodb+srv://simbanaalexis2004:12345@adcluster.hz5cw.mongodb.net/?retryWrites=true&w=majority&appName=ADCluster";

    private  static final  String DATABASE_NAME="SDA";

    private ConexionDB(){}

    public static MongoClient getMongoClient(){
        if (mongoClient == null) {
            try {
                mongoClient = MongoClients.create(CONNECTION_STRING);
                System.out.println("\nConexión a MongoDB Atlas establecida con éxito.");
            } catch (MongoException e) {
                System.err.println("\nError al conectar con MongoDB Atlas: " + e.getMessage());
                // Considera relanzar la excepción o manejarla de forma más robusta en una aplicación real.
            }
        }
        return mongoClient;
    }

    public static MongoDatabase getDatabase() {
        if (getMongoClient() != null) {
            return getMongoClient().getDatabase(DATABASE_NAME);
        }
        return null;
    }

    public static void closeConnection() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null; // Establecer a null para permitir una futura reconexión si es necesario
            System.out.println("Conexión a MongoDB cerrada.");
        }
    }
}
