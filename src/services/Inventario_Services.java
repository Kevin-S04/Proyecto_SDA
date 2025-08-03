package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import models.Inventario;
import models.ProductoPedido;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Inventario_Services {
    private final MongoCollection<Document> coleccionInventario;
    private final MongoCollection<Document> coleccionEstadoPedido;
    private final MongoCollection<Document> coleccionProductos;

    public Inventario_Services() {
        MongoDatabase db = ConexionDB.getDatabase();
        if (db == null) {
            throw new RuntimeException("No se pudo conectar a MongoDB");
        }
        this.coleccionInventario = db.getCollection("Inventario");
        this.coleccionEstadoPedido = db.getCollection("EstadoPedido");
        this.coleccionProductos = db.getCollection("Productos");
    }

    /**
     * Obtiene los pedidos que están "En bodega" (estado inicial)
     * @return Una lista de objetos Inventario
     */
    public List<Inventario> obtenerPedidosEnBodega() {
        List<Inventario> pedidos = new ArrayList<>();
        // Buscar los pedidos que tienen el estado "En bodega"
        for (Document doc : coleccionEstadoPedido.find(Filters.eq("estado", "En bodega"))) {
            String idPedido = doc.getString("idPedido");
            Document docInventario = coleccionInventario.find(Filters.eq("idPedido", idPedido)).first();

            if (docInventario != null) {
                List<ProductoPedido> productos = new ArrayList<>();
                List<Document> productosDocs = docInventario.getList("productos", Document.class);
                for (Document prodDoc : productosDocs) {
                    productos.add(new ProductoPedido(
                            prodDoc.getString("idProducto"),
                            prodDoc.getString("nombreProducto"),
                            prodDoc.getInteger("cantidad"),
                            prodDoc.getDouble("precioUnitario")
                    ));
                }
                pedidos.add(new Inventario(
                        docInventario.getObjectId("_id").toHexString(),
                        docInventario.getString("idPedido"),
                        docInventario.getDate("fecha"),
                        productos
                ));
            }
        }
        return pedidos;
    }

    /**
     * Marca un pedido como "Enviado" y actualiza el stock de los productos.
     * @param idPedido El ID del pedido a procesar
     * @return true si la operación fue exitosa, false en caso contrario
     */
    public boolean procesarEnvio(String idPedido) {
        try {
            // 1. Actualizar el estado del pedido
            coleccionEstadoPedido.updateOne(
                    Filters.eq("idPedido", idPedido),
                    Updates.set("estado", "Enviado")
            );

            // 2. Obtener la lista de productos del pedido para actualizar el stock
            Document inventarioDoc = coleccionInventario.find(Filters.eq("idPedido", idPedido)).first();
            if (inventarioDoc != null) {
                List<Document> productosDocs = inventarioDoc.getList("productos", Document.class);
                for (Document prodDoc : productosDocs) {
                    String idProducto = prodDoc.getString("idProducto");
                    int cantidad = prodDoc.getInteger("cantidad");

                    // Decrementar el stock del producto
                    coleccionProductos.updateOne(
                            Filters.eq("_id", new ObjectId(idProducto)),
                            Updates.inc("stock", -cantidad) // Decrementa la cantidad
                    );
                }
            }
            return true;
        } catch (Exception e) {
            System.err.println("Error al procesar el envío del pedido: " + idPedido + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}