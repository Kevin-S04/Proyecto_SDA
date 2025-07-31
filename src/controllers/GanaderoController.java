package controllers;


import models.Producto;
import services.ProductServices;
import views.GanaderoView;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.stream.Collectors;

public class GanaderoController {

    private final GanaderoView ganaderoView;
    private ProductServices productServices;
    private DefaultTableModel tableModel;
    private List<Producto> productosActuales;

    public GanaderoController(GanaderoView ganaderoView){
        this.ganaderoView=ganaderoView;

        configurarTabla();
        configurarListeners();
        cargarDatosIniciales();
    }

    private void configurarTabla() {
        tableModel.addColumn("ID");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Especies");
        tableModel.addColumn("Precio");
        tableModel.addColumn("Stock");
        tableModel.addColumn("Presentación");
        ganaderoView.getTableProductos().setModel(tableModel);
    }

    private void configurarListeners() {
        ganaderoView.getComboEspecie().addActionListener(e -> actualizarProductos());
        ganaderoView.getAlimentosCheckBox().addItemListener(e -> actualizarProductos());
        ganaderoView.getSuplementosCheckBox().addItemListener(e -> actualizarProductos());
        ganaderoView.getBlanceadosCheckBox().addItemListener(e -> actualizarProductos());
        ganaderoView.getBuscarButton().addActionListener(e -> actualizarProductos());
        ganaderoView.getLimipiarButton().addActionListener(e -> limpiarFiltros());
    }

    private void cargarDatosIniciales() {
        // Cargar especies en el combo box
        ganaderoView.getComboEspecie().removeAllItems();
        ganaderoView.getComboEspecie().addItem(""); // Opción vacía para "todas"
        productServices.obtenerEspeciesDisponibles().forEach(esp ->
                ganaderoView.getComboEspecie().addItem(esp));

        // Cargar productos iniciales
        actualizarProductos();
    }

    private void actualizarProductos() {
        String especieSeleccionada = (String) ganaderoView.getComboEspecie().getSelectedItem();
        String tipoSeleccionado = obtenerTipoSeleccionado();
        String textoBusqueda = ganaderoView.getTextBuscar().getText().trim();

        // Obtener productos filtrados
        productosActuales = productServices.obtenerProductosFiltrados(tipoSeleccionado, especieSeleccionada);

        // Aplicar filtro de texto si es necesario
        if (!textoBusqueda.isEmpty()) {
            productosActuales = productosActuales.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                            p.getDescripcion().toLowerCase().contains(textoBusqueda.toLowerCase()))
                    .collect(Collectors.toList());
        }

        actualizarTabla();
    }

    private String obtenerTipoSeleccionado() {
        if (ganaderoView.getAlimentosCheckBox().isSelected()) return "Alimentos";
        if (ganaderoView.getSuplementosCheckBox().isSelected()) return "Suplementos";
        if (ganaderoView.getBlanceadosCheckBox().isSelected()) return "Balanceados";
        return null; // Todos los tipos
    }

    private void actualizarTabla() {
        tableModel.setRowCount(0);

        for (Producto p : productosActuales) {
            tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getTipo(),
                    String.join(", ", p.getEspecie()),
                    String.format("$%.2f", p.getPrecio()),
                    p.getStock(),
                    p.getPresentacion()
            });
        }
    }

    private void limpiarFiltros() {
        ganaderoView.getComboEspecie().setSelectedIndex(0);
        ganaderoView.getAlimentosCheckBox().setSelected(false);
        ganaderoView.getSuplementosCheckBox().setSelected(false);
        ganaderoView.getBlanceadosCheckBox().setSelected(false);
        ganaderoView.getTextBuscar().setText("");
        actualizarProductos();
    }

}