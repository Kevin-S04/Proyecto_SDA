package models;

public class ProductoInventario {
    private String idProducto; // ID del producto en la colección "Productos"
    private String nombre;
    private int cantidad;

    public ProductoInventario(String idProducto, String nombre, int cantidad) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}