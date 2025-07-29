package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Usuario;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;

public class Users_Services {
    private final MongoCollection<Document> coleccionUsuarios;

    public Users_Services() {
        MongoDatabase db = ConexionDB.getDatabase();
        this.coleccionUsuarios = db.getCollection("Usuarios");
    }

    public Usuario login(String correo, String clave) {
        Document doc = coleccionUsuarios.find(and(eq("correo", correo), eq("clave", clave))).first();

        if (doc != null) {
            String nombre = doc.getString("nombre");
            String rol = doc.getString("rol");
            return new Usuario(nombre, correo, clave, rol);
        } else {
            return null; // usuario no encontrado o clave incorrecta
        }
    }

    public void insertarUsuario(Usuario usuario) {
        Document doc = new Document("nombre", usuario.getUser())
                .append("correo", usuario.getCorreo())
                .append("clave", usuario.getPassword())
                .append("rol", usuario.getRol());
        coleccionUsuarios.insertOne(doc);
        System.out.println("Usuario registrado correctamente.");
    }
}
