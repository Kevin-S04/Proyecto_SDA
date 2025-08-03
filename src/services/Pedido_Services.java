package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoException;
import org.bson.Document;
import org.bson.types.ObjectId;
import models.Pedido;
import models.Producto;
import models.ProductoPedido;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Pedido_Services {
    private final MongoCollection<Document> coleccionProductos;
    private final MongoCollection<Document> coleccionVentas;
    private final MongoCollection<Document> coleccionInventario;
    private final MongoCollection<Document> coleccionEstadoPedido;

    public Pedido_Services() {
        MongoDatabase db = ConexionDB.getDatabase();
        if (db == null) {
            throw new RuntimeException("No se pudo conectar a la base de datos MongoDB.");
        }
        this.coleccionProductos = db.getCollection("Productos");
        this.coleccionVentas = db.getCollection("Ventas");
        this.coleccionInventario = db.getCollection("Inventario");
        this.coleccionEstadoPedido = db.getCollection("EstadoPedido");
    }

    public List<Producto> obtenerProductosDisponibles() {
        List<Producto> productos = new ArrayList<>();
        try {
            for (Document doc : coleccionProductos.find()) {
                productos.add(new Producto(
                        doc.getObjectId("_id").toHexString(),
                        doc.getString("nombre"),
                        doc.getString("tipo"),
                        doc.getList("especie", String.class),
                        doc.getDouble("precio"),
                        doc.getInteger("stock"),
                        doc.getString("presentacion"),
                        doc.getString("descripcion")
                ));
            }
        } catch (MongoException e) {
            System.err.println("Error al obtener los productos: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    public boolean guardarPedido(Pedido pedido) {
        try {
            Document docVenta = new Document("idUsuario", pedido.getIdUsuario())
                    .append("fechaPedido", pedido.getFechaPedido())
                    .append("total", pedido.getTotal());

            List<Document> productosDocs = new ArrayList<>();
            for (ProductoPedido pp : pedido.getProductos()) {
                productosDocs.add(new Document("idProducto", pp.getIdProducto())
                        .append("nombreProducto", pp.getNombreProducto())
                        .append("cantidad", pp.getCantidad())
                        .append("precioUnitario", pp.getPrecioUnitario()));
            }
            docVenta.append("productos", productosDocs);
            coleccionVentas.insertOne(docVenta);

            Document docInventario = new Document("idPedido", docVenta.getObjectId("_id").toHexString())
                    .append("fecha", pedido.getFechaPedido())
                    .append("productos", productosDocs);
            coleccionInventario.insertOne(docInventario);

            Document docEstado = new Document("idPedido", docVenta.getObjectId("_id").toHexString())
                    .append("estado", "En bodega");
            coleccionEstadoPedido.insertOne(docEstado);

            return true;
        } catch (MongoException e) {
            System.err.println("Error al guardar el pedido: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}