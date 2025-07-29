package models;

public class Usuario {
    private String user;
    private String password;
    private String correo;
    private String rol;

    public Usuario (String user, String correo, String rol , String password ){
        this.user=user;
        this.correo=correo;
        this.rol=rol;
        this.password=password;
    }

    public String getUser() {return user;}

    public String getPassword() {return password;}

    public String getCorreo() {return correo;}

    public String getRol() {return rol;}
}
