package clientesoracleapp;

public class Cliente {
    private String idc;
    private String dni;
    private String nom;
    private String apel;
    private String tel;
    private String corr;
    private String direc;

    // Constructor vacío
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(String idc, String dni, String nom, String apel, String tel, String corr, String direc) {
        this.idc = idc;
        this.dni = dni;
        this.nom = nom;
        this.apel = apel;
        this.tel = tel;
        this.corr = corr;
        this.direc = direc;
    }

    // Getters y Setters
    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getApel() {
        return apel;
    }

    public void setApel(String apel) {
        this.apel = apel;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCorr() {
        return corr;
    }

    public void setCorr(String corr) {
        this.corr = corr;
    }

    public String getDirec() {
        return direc;
    }

    public void setDirec(String direc) {
        this.direc = direc;
    }
}