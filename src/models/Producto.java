package models;

import java.util.List;

/**
 * Modelo de datos para representar un producto en el sistema.
 * Corresponde a la colección 'Productos' en MongoDB.
 */
public class Producto {
    private String id;
    private String nombre;
    private String tipo;
    private List<String> especie;
    private double precio;
    private int stock;
    private String presentacion;
    private String descripcion;

    /**
     * Constructor por defecto.
     */
    public Producto() {}

    /**
     * Constructor para inicializar un objeto Producto con todos sus atributos.
     * @param id El identificador único del producto.
     * @param nombre El nombre del producto.
     * @param tipo El tipo de producto (e.g., Alimento, Suplemento).
     * @param especie La lista de especies para las que es el producto.
     * @param precio El precio del producto.
     * @param stock La cantidad disponible en inventario.
     * @param presentacion La presentación del producto (e.g., 50kg, 1L).
     * @param descripcion Una descripción detallada del producto.
     */
    public Producto(String id, String nombre, String tipo, List<String> especie,
                    double precio, int stock, String presentacion, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.especie = especie;
        this.precio = precio;
        this.stock = stock;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el identificador del producto.
     * @return El ID del producto.
     */
    public String getId() { return id; }
    /**
     * Establece el identificador del producto.
     * @param id El ID del producto.
     */
    public void setId(String id) { this.id = id; }

    // ... (Métodos Getter y Setter para los demás atributos con Javadoc similar)
    // Para no alargar la respuesta, los omito, pero seguirían este patrón.
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public List<String> getEspecie() { return especie; }
    public void setEspecie(List<String> especie) { this.especie = especie; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}