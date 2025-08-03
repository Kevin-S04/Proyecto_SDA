package models;

import java.util.List;
import java.util.Date;

/**
 * Modelo de datos para representar un pedido realizado por un ganadero.
 * Corresponde a la colección 'Ventas' en MongoDB.
 */
public class Pedido {
    private String id;
    private String idUsuario;
    private Date fechaPedido;
    private double total;
    private List<ProductoPedido> productos;

    /**
     * Constructor para inicializar un objeto Pedido.
     * @param id El identificador único del pedido.
     * @param idUsuario El ID del usuario que realizó el pedido.
     * @param fechaPedido La fecha y hora en que se realizó el pedido.
     * @param total El costo total del pedido.
     * @param productos La lista de productos y sus detalles.
     */
    public Pedido(String id, String idUsuario, Date fechaPedido, double total, List<ProductoPedido> productos) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fechaPedido = fechaPedido;
        this.total = total;
        this.productos = productos;
    }

    // ... (Getter y Setter para los demás atributos)
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public Date getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(Date fechaPedido) { this.fechaPedido = fechaPedido; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public List<ProductoPedido> getProductos() { return productos; }
    public void setProductos(List<ProductoPedido> productos) { this.productos = productos; }
}