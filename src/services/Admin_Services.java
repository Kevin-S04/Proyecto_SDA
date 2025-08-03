package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import models.Producto;
import models.Usuario;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class Admin_Services {

    private final MongoCollection<Document> coleccionProductos;
    private final MongoCollection<Document> coleccionUsuarios;

    public Admin_Services(){
        MongoDatabase db = ConexionDB.getDatabase();
        if (db == null) {
            throw new RuntimeException("No se pudo conectar a MongoDB");
        }
        this.coleccionProductos = db.getCollection("Inventario");
        this.coleccionUsuarios = db.getCollection("Usuarios");
    }

    private Producto documentToProducto(Document doc) {
        Producto p = new Producto();
        p.setId(doc.getObjectId("_id").toString());
        p.setNombre(doc.getString("nombre"));
        p.setTipo(doc.getString("tipo"));
        p.setEspecie(doc.getList("especie", String.class));
        p.setPrecio(doc.getDouble("precio"));
        p.setStock(doc.getInteger("stock"));
        p.setPresentacion(doc.getString("presentacion"));
        p.setDescripcion(doc.getString("descripcion"));
        return p;
    }

    private Document productoToDocument(Producto p) {
        return new Document("nombre", p.getNombre())
                .append("tipo", p.getTipo())
                .append("especie", p.getEspecie())
                .append("precio", p.getPrecio())
                .append("stock", p.getStock())
                .append("presentacion", p.getPresentacion())
                .append("descripcion", p.getDescripcion());
    }

    public List<Producto> obtenerProductosFiltrados(String tipo, String especie) {
        List<Bson> filtros = new ArrayList<>();
        if (tipo != null && !tipo.isEmpty()) filtros.add(Filters.eq("tipo", tipo));
        if (especie != null && !especie.isEmpty()) filtros.add(Filters.eq("especie", especie));
        Bson filtro = filtros.isEmpty() ? new Document() : Filters.and(filtros);

        List<Producto> productos = new ArrayList<>();
        coleccionProductos.find(filtro).forEach(doc -> productos.add(documentToProducto(doc)));
        return productos;
    }

    public List<String> obtenerTiposDisponibles() {
        return coleccionProductos.distinct("tipo", String.class).into(new ArrayList<>());
    }

    public List<String> obtenerEspeciesDisponibles() {
        return coleccionProductos.distinct("especie", String.class).into(new ArrayList<>());
    }

    public boolean agregarProducto(Producto producto) {
        try {
            coleccionProductos.insertOne(productoToDocument(producto));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarProducto(String id, Producto productoActualizado) {
        try {
            Bson filtro = Filters.eq("_id", new ObjectId(id));
            Bson actualizacion = Updates.combine(
                    Updates.set("nombre", productoActualizado.getNombre()),
                    Updates.set("tipo", productoActualizado.getTipo()),
                    Updates.set("especie", productoActualizado.getEspecie()),
                    Updates.set("precio", productoActualizado.getPrecio()),
                    Updates.set("stock", productoActualizado.getStock()),
                    Updates.set("presentacion", productoActualizado.getPresentacion()),
                    Updates.set("descripcion", productoActualizado.getDescripcion())
            );
            coleccionProductos.updateOne(filtro, actualizacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProducto(String id) {
        try {
            coleccionProductos.deleteOne(Filters.eq("_id", new ObjectId(id)));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Usuarios

    public List<Usuario> obtenerUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        for (Document doc : coleccionUsuarios.find()) {
            String id = doc.getObjectId("_id").toString();
            String nombre = doc.getString("nombre");
            String correo = doc.getString("correo");
            String rol = doc.getString("rol");
            usuarios.add(new Usuario(id, nombre, correo, rol));
        }
        return usuarios;
    }

    public boolean agregarUsuario(Usuario usuario) {
        try {
            Document doc = new Document("nombre", usuario.getNombre())
                    .append("correo", usuario.getCorreo())
                    .append("rol", usuario.getRol());
            coleccionUsuarios.insertOne(doc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarUsuario(String id, Usuario usuarioActualizado) {
        try {
            Bson filtro = Filters.eq("_id", new ObjectId(id));
            Bson actualizacion = Updates.combine(
                    Updates.set("nombre", usuarioActualizado.getNombre()),
                    Updates.set("correo", usuarioActualizado.getCorreo()),
                    Updates.set("rol", usuarioActualizado.getRol())
            );
            coleccionUsuarios.updateOne(filtro, actualizacion);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}