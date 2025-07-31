package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import com.mongodb.client.model.Filters;
import models.Producto;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;


public class ProductServices {
    private final MongoCollection<Document> coleccionProductos;

    public ProductServices() {
        MongoDatabase db = ConexionDB.getDatabase();
        if (db == null) {
            throw new RuntimeException("No se pudo conectar a MongoDB");
        }
        this.coleccionProductos = db.getCollection("Productos");
    }

    /**
     * Convierte un Document de MongoDB a un objeto Producto
     */
    private Producto documentToProducto(Document doc) {
        Producto producto = new Producto();
        producto.setId(doc.getObjectId("_id").toString());
        producto.setNombre(doc.getString("nombre"));
        producto.setTipo(doc.getString("tipo"));
        producto.setEspecie(doc.getList("especie", String.class));
        producto.setPrecio(doc.getDouble("precio"));
        producto.setStock(doc.getInteger("stock"));
        producto.setPresentacion(doc.getString("presentacion"));
        producto.setDescripcion(doc.getString("descripcion"));
        return producto;
    }

    /**
     * Convierte un Producto a Document para MongoDB
     */
    private Document productoToDocument(Producto producto) {
        return new Document()
                .append("nombre", producto.getNombre())
                .append("tipo", producto.getTipo())
                .append("especie", producto.getEspecie())
                .append("precio", producto.getPrecio())
                .append("stock", producto.getStock())
                .append("presentacion", producto.getPresentacion())
                .append("descripcion", producto.getDescripcion());
    }

    /**
     * Obtiene todos los productos
     */
    public List<Producto> obtenerTodosProductos() {
        List<Producto> productos = new ArrayList<>();
        coleccionProductos.find().forEach(doc -> productos.add(documentToProducto(doc)));
        return productos;
    }

    /**
     * Obtiene productos filtrados por tipo y especie
     */
    public List<Producto> obtenerProductosFiltrados(String tipo, String especie) {
        List<Bson> filtros = new ArrayList<>();

        if (tipo != null && !tipo.isEmpty()) {
            filtros.add(Filters.eq("tipo", tipo));
        }

        if (especie != null && !especie.isEmpty()) {
            filtros.add(Filters.in("especie", especie));
        }

        Bson filtro = filtros.isEmpty() ? new Document() : Filters.and(filtros);

        List<Producto> productos = new ArrayList<>();
        coleccionProductos.find(filtro).forEach(doc -> productos.add(documentToProducto(doc)));
        return productos;
    }

    /**
     * Obtiene todas las especies disponibles (para cargar el JComboBox)
     */
    public List<String> obtenerEspeciesDisponibles() {
        return coleccionProductos.distinct("especie", String.class).into(new ArrayList<>());
    }

    /**
     * Obtiene todos los tipos disponibles (para los checkboxes)
     */
    public List<String> obtenerTiposDisponibles() {
        return coleccionProductos.distinct("tipo", String.class).into(new ArrayList<>());
    }
}