package compiladorl3;

public class Tabela {
    private String identificador;
    private int escopo;

    public Tabela(String identificador, int escopo) {
        this.identificador = identificador;
        this.escopo = escopo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public int getEscopo() {
        return escopo;
    }
}
