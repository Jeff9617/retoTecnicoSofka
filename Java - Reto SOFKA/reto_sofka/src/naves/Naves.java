package naves;

public abstract class Naves {

    private String nombre;
    private String desplazamiento;
    private String velocidad;
    private String mision;

    public Naves(){}

    public Naves(String nombre, String desplazamiento, String velocidad, String mision) {
        this.nombre = nombre;
        this.desplazamiento = desplazamiento;
        this.velocidad = velocidad;
        this.mision = mision;
    }

    public abstract Object seleccionarNave(Object nave);

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesplazamiento() {
        return desplazamiento;
    }

    public void setDesplazamiento(String desplazamiento) {
        this.desplazamiento = desplazamiento;
    }

    public String getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(String velocidad) {
        this.velocidad = velocidad;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }
}
