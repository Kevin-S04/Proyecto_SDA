package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import models.Usuario;
import org.bson.Document;
import org.bson.types.ObjectId; // Esta importación sigue siendo necesaria si usas ObjectId en algún lugar, aunque no directamente en los filtros mostrados.
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.MongoException;
import com.mongodb.client.result.UpdateResult;

/**
 * Clase Users_Services que proporciona métodos para interactuar con la colección de usuarios en MongoDB.
 * Incluye funcionalidades para autenticación (login), registro de usuarios y actualización de contraseñas.
 */
public class Users_Services {
    /** Colección de MongoDB donde se almacenan los documentos de usuarios. */
    private final MongoCollection<Document> coleccionUsuarios;

    /**
     * Constructor de Users_Services.
     * Inicializa la conexión a la base de datos y obtiene la colección de "Usuarios".
     * Lanza una RuntimeException si la base de datos no puede ser obtenida.
     */
    public Users_Services() {
        MongoDatabase db = ConexionDB.getDatabase();
        if (db == null) {
            throw new RuntimeException("No se pudo conectar a la base de datos MongoDB.");
        }
        this.coleccionUsuarios = db.getCollection("Usuarios");
    }

    /**
     * Intenta autenticar a un usuario por correo, clave y rol.
     * Realiza una búsqueda en la colección de usuarios para encontrar un documento que coincida
     * con los credenciales proporcionados.
     * @param correo El correo electrónico del usuario (usado como nombre de usuario para el login).
     * @param clave La contraseña del usuario.
     * @param rol El rol seleccionado por el usuario.
     * @return Un objeto {@link models.Usuario} si la autenticación es exitosa y se encuentra un usuario,
     * o {@code null} en caso contrario (credenciales incorrectas o error de base de datos).
     */
    public Usuario login(String correo, String clave, String rol) {
        try {
            Document doc = coleccionUsuarios.find(and(
                    eq("correo", correo),
                    eq("clave", clave),
                    eq("rol", rol)
            )).first();

            if (doc != null) {
                String id = doc.getObjectId("_id").toHexString();
                String nombre = doc.getString("nombre");
                String foundRol = doc.getString("rol");
                return new Usuario(id, nombre, correo, clave, foundRol);
            } else {
                return null;
            }
        } catch (MongoException e) {
            System.err.println("Error de MongoDB al intentar login: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Inserta un nuevo usuario en la colección de MongoDB.
     * Antes de insertar, verifica si el correo electrónico ya existe para evitar duplicados.
     * @param usuario El objeto {@link models.Usuario} con los datos del nuevo usuario a insertar.
     * @return {@code true} si el usuario fue registrado correctamente, {@code false} si hubo un error
     * (ej. correo ya existe, problema de base de datos).
     */
    public boolean insertarUsuario(Usuario usuario) {
        try {
            if (correoExiste(usuario.getCorreo())) {
                System.out.println("Error: El correo " + usuario.getCorreo() + " ya se encuentra registrado.");
                return false;
            }

            Document doc = new Document("nombre", usuario.getNombre())
                    .append("correo", usuario.getCorreo())
                    .append("clave", usuario.getClave()) // ATENCIÓN DE SEGURIDAD: ¡APLICAR HASHING DE CLAVES AQUÍ ANTES DE GUARDAR!
                    .append("rol", usuario.getRol());

            coleccionUsuarios.insertOne(doc);
            System.out.println("Usuario " + usuario.getCorreo() + " registrado correctamente.");
            return true;
        } catch (MongoException e) {
            System.err.println("Error de MongoDB al insertar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza la contraseña de un usuario en la base de datos.
     * Requiere el correo electrónico del usuario para identificarlo.
     * @param correo El correo electrónico del usuario cuya contraseña se va a actualizar.
     * @param nuevaClave La nueva contraseña a establecer.
     * @return {@code true} si la contraseña se actualizó correctamente (se encontró y modificó un usuario),
     * {@code false} en caso contrario (usuario no encontrado o error de base de datos).
     */
    public boolean actualizarContrasena(String correo, String nuevaClave) {
        try {
            UpdateResult result = coleccionUsuarios.updateOne(eq("correo", correo),
                    set("clave", nuevaClave));

            if (result.getModifiedCount() > 0) {
                System.out.println("Contraseña del usuario " + correo + " actualizada correctamente.");
                return true;
            } else {
                System.out.println("No se encontró al usuario " + correo + " para actualizar la contraseña.");
                return false;
            }
        } catch (MongoException e) {
            System.err.println("Error de MongoDB al actualizar contraseña: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Verifica si un correo electrónico ya existe en la base de datos de usuarios.
     * Utilizado principalmente antes de un nuevo registro para evitar duplicados.
     * @param correo El correo a verificar.
     * @return {@code true} si el correo existe en la colección, {@code false} en caso contrario
     * o si ocurre un error de base de datos.
     */
    public boolean correoExiste(String correo) {
        try {
            return coleccionUsuarios.find(eq("correo", correo)).first() != null;
        } catch (MongoException e) {
            System.err.println("Error de MongoDB al verificar existencia de correo: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}