package models;

/**
 * Modelo de datos para representar un producto dentro de un pedido.
 */
public class ProductoPedido {
    private String idProducto;
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;

    /**
     * Constructor para inicializar un objeto ProductoPedido.
     * @param idProducto El ID del producto.
     * @param nombreProducto El nombre del producto.
     * @param cantidad La cantidad solicitada.
     * @param precioUnitario El precio por unidad al momento del pedido.
     */
    public ProductoPedido(String idProducto, String nombreProducto, int cantidad, double precioUnitario) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    // ... (Getter y Setter para los demás atributos)
    public String getIdProducto() { return idProducto; }
    public void setIdProducto(String idProducto) { this.idProducto = idProducto; }
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
}