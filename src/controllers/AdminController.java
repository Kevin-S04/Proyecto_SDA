package controllers;

import models.Producto;
import models.Usuario;
import services.Admin_Services;
import utils.Mensajes;
import views.AdminView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminController implements ActionListener {

    private final AdminView adminView;
    private final Admin_Services adminServices;

    public AdminController(AdminView view, Admin_Services services) {
        this.adminView = view;
        this.adminServices = services;

        // Listeners productos
        adminView.getComboTipo().addActionListener(this);
        adminView.getComboEspecie().addActionListener(this);
        adminView.getBuscarButton().addActionListener(this);
        adminView.getNuevoButton().addActionListener(this);
        adminView.getEditarButton().addActionListener(this);
        adminView.getEliminarButton().addActionListener(this);

        // Listeners usuarios
        adminView.getBtnNuevoU().addActionListener(this);
        adminView.getBtnModificarU().addActionListener(this);
        adminView.getBtnLimpiarU().addActionListener(this);

        // Cargar combos productos
        adminView.cargarTiposYEspecies(
                adminServices.obtenerTiposDisponibles(),
                adminServices.obtenerEspeciesDisponibles()
        );

        // Cargar usuarios al cambiar pestaña
        adminView.getTabbedPane1().addChangeListener(e -> {
            int index = adminView.getTabbedPane1().getSelectedIndex();
            if (index == 1) {
                cargarUsuariosEnTabla();
            }
        });

        // Selección fila usuarios -> llenar formulario

        adminView.getTbUsuarios().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int fila = adminView.getTbUsuarios().getSelectedRow();
                if (fila >= 0) {
                    adminView.getTextIdU().setText((String) adminView.getTbUsuarios().getValueAt(fila, 0));
                    adminView.getTextNombreU().setText((String) adminView.getTbUsuarios().getValueAt(fila, 1));
                    adminView.getTextCorreoU().setText((String) adminView.getTbUsuarios().getValueAt(fila, 2));
                    adminView.getComboRolU().setSelectedItem((String) adminView.getTbUsuarios().getValueAt(fila, 3));
                }
            }
        });

        filtrarProductos();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == adminView.getComboTipo() || src == adminView.getComboEspecie()) {
            filtrarProductos();
        } else if (src == adminView.getBuscarButton()) {
            buscarProductos();
        } else if (src == adminView.getNuevoButton()) {
            crearProducto();
        } else if (src == adminView.getEditarButton()) {
            editarProducto();
        } else if (src == adminView.getEliminarButton()) {
            eliminarProducto();
        } else if (src == adminView.getBtnNuevoU()) {
            crearUsuario();
        } else if (src == adminView.getBtnModificarU()) {
            editarUsuario();
        } else if (src == adminView.getBtnLimpiarU()) {
            adminView.limpiarFormularioUsuario();
        }
    }

    // Productos

    private void filtrarProductos() {
        String tipo = (String) adminView.getComboTipo().getSelectedItem();
        String especie = (String) adminView.getComboEspecie().getSelectedItem();

        if (tipo == null || tipo.isEmpty() || especie == null || especie.isEmpty()) return;

        List<Producto> productosFiltrados = adminServices.obtenerProductosFiltrados(tipo, especie);
        cargarProductosEnTabla(productosFiltrados);
    }

    private void buscarProductos() {
        String texto = adminView.getTextBuscar().getText().trim().toLowerCase();
        List<Producto> todos = adminServices.obtenerProductosFiltrados(
                (String) adminView.getComboTipo().getSelectedItem(),
                (String) adminView.getComboEspecie().getSelectedItem()
        );
        List<Producto> filtrados = todos.stream()
                .filter(p -> p.getNombre().toLowerCase().contains(texto))
                .toList();
        cargarProductosEnTabla(filtrados);
    }

    private void crearProducto() {
        Producto p = adminView.obtenerProductoDelFormulario();
        if (p == null) return;
        if (adminServices.agregarProducto(p)) {
            Mensajes.mostrarInformacion(adminView, "Producto agregado.");
            filtrarProductos();
        } else {
            Mensajes.mostrarError(adminView, "Error al agregar producto.");
        }
    }

    private void editarProducto() {
        int fila = adminView.getTbProducto().getSelectedRow();
        if (fila == -1) {
            Mensajes.mostrarAdvertencia(adminView, "Seleccione un producto para editar.");
            return;
        }
        String id = (String) adminView.getTbProducto().getValueAt(fila, 0);
        Producto editado = adminView.obtenerProductoDelFormulario();
        if (editado == null) return;
        if (adminServices.actualizarProducto(id, editado)) {
            Mensajes.mostrarInformacion(adminView, "Producto actualizado.");
            filtrarProductos();
        } else {
            Mensajes.mostrarError(adminView, "Error al actualizar producto.");
        }
    }

    private void eliminarProducto() {
        int fila = adminView.getTbProducto().getSelectedRow();
        if (fila == -1) {
            Mensajes.mostrarAdvertencia(adminView, "Seleccione un producto para eliminar.");
            return;
        }
        String id = (String) adminView.getTbProducto().getValueAt(fila, 0);
        int confirm = JOptionPane.showConfirmDialog(adminView, "¿Eliminar producto?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (adminServices.eliminarProducto(id)) {
                Mensajes.mostrarInformacion(adminView, "Producto eliminado.");
                filtrarProductos();
            } else {
                Mensajes.mostrarError(adminView, "Error al eliminar producto.");
            }
        }
    }

    private void cargarProductosEnTabla(List<Producto> productos) {
        DefaultTableModel modelo = adminView.getModeloProducto();
        modelo.setRowCount(0);
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getTipo(),
                    String.join(", ", p.getEspecie()),
                    p.getPrecio(),
                    p.getStock(),
                    p.getPresentacion(),
                    p.getDescripcion()
            });
        }
    }

    // Usuarios

    // Usuarios

    private void cargarUsuariosEnTabla() {
        DefaultTableModel modeloUsuarios = adminView.getModeloUsuarios();
        modeloUsuarios.setRowCount(0);
        List<Usuario> usuarios = adminServices.obtenerUsuarios();
        for (Usuario u : usuarios) {
            modeloUsuarios.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getCorreo(),
                    u.getRol()
            });
        }
    }

    private void crearUsuario() {
        Usuario u = adminView.obtenerUsuarioDelFormulario();
        if (u == null) return;
        if (adminServices.agregarUsuario(u)) {
            Mensajes.mostrarInformacion(adminView, "Usuario agregado.");
            cargarUsuariosEnTabla();
            adminView.limpiarFormularioUsuario();
        } else {
            Mensajes.mostrarError(adminView, "Error al agregar usuario.");
        }
    }

    private void editarUsuario() {
        String id = adminView.getTextIdU().getText();
        if (id == null || id.isEmpty()) {
            Mensajes.mostrarAdvertencia(adminView, "Seleccione un usuario para modificar.");
            return;
        }
        Usuario u = adminView.obtenerUsuarioDelFormulario();
        if (u == null) return;
        if (adminServices.actualizarUsuario(id, u)) {
            Mensajes.mostrarInformacion(adminView, "Usuario actualizado.");
            cargarUsuariosEnTabla();
            adminView.limpiarFormularioUsuario();
        } else {
            Mensajes.mostrarError(adminView, "Error al actualizar usuario.");
        }
    }
}