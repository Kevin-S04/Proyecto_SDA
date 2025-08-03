package services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Servicio para gestionar las operaciones del módulo de Transporte.
 * Se encarga de obtener pedidos que están listos para ser entregados y
 * actualizar su estado a "Entregado".
 */
public class Transporte_Services {
    private final MongoCollection<Document> coleccionEstadoPedido;

    /**
     * Constructor que inicializa la conexión con la colección de estados de pedido.
     */
    public Transporte_Services() {
        MongoDatabase db = ConexionDB.getDatabase();
        if (db == null) {
            throw new RuntimeException("No se pudo conectar a MongoDB");
        }
        this.coleccionEstadoPedido = db.getCollection("EstadoPedido");
    }

    /**
     * Obtiene una lista de los IDs de los pedidos que están en estado "Enviado"
     * y, por lo tanto, listos para ser transportados.
     * @return Una lista de Strings con los IDs de los pedidos.
     */
    public List<String> obtenerPedidosEnviados() {
        List<String> pedidosEnviados = new ArrayList<>();
        coleccionEstadoPedido.find(Filters.eq("estado", "Enviado"))
                .forEach(doc -> pedidosEnviados.add(doc.getString("idPedido")));
        return pedidosEnviados;
    }

    /**
     * Actualiza el estado de un pedido a "Entregado" y registra la fecha de entrega.
     * @param idPedido El ID del pedido a actualizar.
     * @return true si la actualización fue exitosa, false en caso de error.
     */
    public boolean marcarComoEntregado(String idPedido) {
        try {
            coleccionEstadoPedido.updateOne(
                    Filters.eq("idPedido", idPedido),
                    Updates.combine(
                            Updates.set("estado", "Entregado"),
                            Updates.set("fechaEntrega", new Date())
                    )
            );
            return true;
        } catch (Exception e) {
            System.err.println("Error al marcar el pedido " + idPedido + " como entregado: " + e.getMessage());
            return false;
        }
    }
}