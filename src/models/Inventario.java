package models;

import java.util.Date;
import java.util.List;

/**
 * Modelo de datos para representar un registro de inventario.
 * Corresponde a la colección 'Inventario' en MongoDB.
 */
public class Inventario {
    private String id;
    private String idPedido;
    private Date fecha;
    private List<ProductoPedido> productos;

    /**
     * Constructor para inicializar un objeto Inventario.
     * @param id El identificador único del registro de inventario.
     * @param idPedido El ID del pedido asociado a este registro.
     * @param fecha La fecha del registro.
     * @param productos La lista de productos y sus detalles.
     */
    public Inventario(String id, String idPedido, Date fecha, List<ProductoPedido> productos) {
        this.id = id;
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.productos = productos;
    }

    // ... (Getter y Setter para los demás atributos)
    public String getId() { return id; }
    public String getIdPedido() { return idPedido; }
    public Date getFecha() { return fecha; }
    public List<ProductoPedido> getProductos() { return productos; }
}