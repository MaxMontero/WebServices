package maxmontero.com.webservices.POJO;

/**
 * Created by Dell on 8/03/2017.
 */
//pojo de Usuario
public class Usuario {

    private int usuarioId;
    private String nombre;
    private String Twitter;

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTwitter() {
        return Twitter;
    }

    public void setTwitter(String twitter) {
        Twitter = twitter;
    }
}
