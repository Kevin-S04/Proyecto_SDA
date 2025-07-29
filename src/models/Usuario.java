package models;

/**
 * Clase Usuario que representa el modelo de datos para un usuario en el sistema.
 * Contiene información como ID (para MongoDB), nombre, correo, clave (contraseña) y rol.
 */
public class Usuario {
    /** El ID único del usuario, a menudo representado como String para ObjectId de MongoDB. */
    private String id;
    /** El nombre completo o de pila del usuario. */
    private String nombre;
    /** El correo electrónico del usuario, que también se usa como nombre de usuario para el login. */
    private String correo;
    /** La clave (contraseña) del usuario. */
    private String clave;
    /** El rol del usuario en el sistema (ej. "Ganadero", "Admin"). */
    private String rol;

    /**
     * Constructor vacío de la clase Usuario.
     */
    public Usuario() {
    }

    /**
     * Constructor para cuando se recupera un usuario de la base de datos MongoDB.
     * @param id El ID ObjectId del documento en MongoDB.
     * @param nombre El nombre del usuario.
     * @param correo El correo electrónico del usuario.
     * @param clave La clave (contraseña) del usuario.
     * @param rol El rol del usuario.
     */
    public Usuario(String id, String nombre, String correo, String clave, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }

    /**
     * Constructor para cuando se crea un nuevo usuario antes de insertarlo en la base de datos (sin ID inicial).
     * @param nombre El nombre del nuevo usuario.
     * @param correo El correo electrónico del nuevo usuario.
     * @param clave La clave (contraseña) del nuevo usuario.
     * @param rol El rol del nuevo usuario.
     */
    public Usuario(String nombre, String correo, String clave, String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.clave = clave;
        this.rol = rol;
    }

    /**
     * Obtiene el ID del usuario.
     * @return El ID del usuario.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return El nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return El correo electrónico del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Obtiene la clave (contraseña) del usuario.
     * @return La clave del usuario.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Obtiene el rol del usuario.
     * @return El rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el ID del usuario.
     * @param id El nuevo ID del usuario.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre El nuevo nombre del usuario.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param correo El nuevo correo electrónico del usuario.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Establece la clave (contraseña) del usuario.
     * @param clave La nueva clave del usuario.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Establece el rol del usuario.
     * @param rol El nuevo rol del usuario.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene el nombre de usuario (alias para getCorreo()).
     * @return El correo electrónico del usuario, usado como nombre de usuario.
     */
    public String getUsername() {
        return this.correo;
    }

    /**
     * Establece el nombre de usuario (alias para setCorreo()).
     * @param username El correo electrónico a establecer como nombre de usuario.
     */
    public void setUsername(String username) {
        this.correo = username;
    }

    /**
     * Obtiene la contraseña (alias para getClave()).
     * @return La clave del usuario, usada como contraseña.
     */
    public String getPassword() {
        return this.clave;
    }

    /**
     * Establece la contraseña (alias para setClave()).
     * @param password La clave a establecer como contraseña.
     */
    public void setPassword(String password) {
        this.clave = password;
    }
}