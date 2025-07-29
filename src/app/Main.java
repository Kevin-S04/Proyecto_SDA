package app;
import com.mongodb.client.MongoDatabase;
import services.ConexionDB;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        MongoDatabase db = ConexionDB.getDatabase();

        if (db != null) {
            System.out.println("Nombre de la base de datos: " + db.getName());
        }

    }
}