public class Bicho {

    private int salud;
    private String iniciales;

    public Bicho(int salud, String iniciales) {
        this.salud = salud;
        this.iniciales = iniciales;
    }
    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getSalud() {
        return this.salud;
    }

    public void setIniciales(String iniciales) {
        this.iniciales = iniciales;
    }

    public String getIniciales() {
        return this.iniciales;
    }

}