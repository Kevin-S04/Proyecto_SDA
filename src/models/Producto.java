package models;

import java.util.List;
import java.util.ArrayList; // Para inicializar listas vacías

public class Producto {
    // MongoDB _id puede ser un String en Java si lo mapeas así
    private String id;
    private String nombre;
    private String tipo;
    private List<String> especie;
    private double precio;
    private int  stock;
    private String presentacion;
    private String descripcion;

    // Constructor vacío
    public Producto() {
    }

    // Constructor completo
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

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public List<String> getEspecie() {
        return especie;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setEspecie(List<String> especie) {
        this.especie = especie;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
