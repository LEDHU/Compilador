package compiladorl3;

public class Sintatico3 {
    private final Lexico lexico;
    private Token token;
    private LDELista<String> lista;

    public Sintatico3(Lexico lexico) {
        this.lexico = lexico;
        this.lista = new LDELista<>();
    }

    public void S() {
        this.token = this.lexico.nextToken();
        if (!token.getLexema().equals("main")) {
            throw new RuntimeException("Oxe, cadê o main?");
        }

        this.token = this.lexico.nextToken();
        if (!token.getLexema().equals("(")) {
            throw new RuntimeException("Abre o parêntese do main, cabra!");
        }

        this.token = this.lexico.nextToken();
        if (!token.getLexema().equals(")")) {
            throw new RuntimeException("Fecha o parêntese do main, cabra!");
        }
        this.token = this.lexico.nextToken();

        this.B();
        if (this.token.getTipo() == Token.TIPO_FIM_CODIGO) {
            System.out.println("O código tá massa! Arretado! Tu botou pra torar!");
        } else {
            throw new RuntimeException("Oxe, deu bronca perto do fim do programa.");
        }
    }

    private void B() {
        if (!this.token.getLexema().equals("{")) {
            throw new RuntimeException("Oxe, era pra ter um \"{\" perto de " + this.token.getLexema());
        }
        //adiciona na lista dupla as chaves
        lista.addLast(this.token.getLexema());

        this.token = this.lexico.nextToken();
        this.CS();
        if (!this.token.getLexema().equals("}")) {
            throw new RuntimeException("Oxe, era pra ter um \"}\" perto de " + this.token.getLexema());
        }

        //exclui todos indentificadores dentro das chaves, excluindo a chave
        lista.removerEspecial("{");

        this.token = this.lexico.nextToken();
    }

    private void CS() {
        if (this.token.getTipo() == Token.TIPO_IDENTIFICADOR ||
                this.token.getLexema().equals("int") ||
                this.token.getLexema().equals("float") ||
                this.token.getLexema().equals("if") ||
                this.token.getLexema().equals("while") ||
                this.token.getLexema().equals("else")) {

            this.C();
            this.CS();
        }
    }

    private void C() {
        if (this.token.getTipo() == Token.TIPO_IDENTIFICADOR) {
            this.ATRIBUICAO();
        } else if (this.token.getLexema().equals("int") ||
                this.token.getLexema().equals("float")) {
            this.DECLARACAO();
        } else if (this.token.getLexema().equals("if")) {
            this.CONDICIONAL();
        } else if (this.token.getLexema().equals("while")) {
            this.LACODEREPETICAO();
        } else if (this.token.getLexema().equals("else")) {
            this.SENAO();
        } else {
            throw new RuntimeException("Oxe, era pra ter declarado um comando perto de: " + this.token.getLexema());
        }
    }

    private void DECLARACAO() {
        if (!(this.token.getLexema().equals("int") || this.token.getLexema().equals("float"))) {
            throw new RuntimeException("Tu vacilou na declaração de variável. Perto de: " + this.token.getLexema());
        }

        this.token = this.lexico.nextToken();

        if (this.token.getTipo() != Token.TIPO_IDENTIFICADOR) {
            throw new RuntimeException("Tu vacilou na declaração de variável. Perto de: " + this.token.getLexema());
        }

        //pesquisar na lista se ja existe e adiciona na lista
        this.JADECLAROU(this.token.getLexema());
        lista.addLast(this.token.getLexema());

        this.token = this.lexico.nextToken();

        if (!this.token.getLexema().equals(";")) {
            throw new RuntimeException("Tu vacilou na declaração de variável. Perto de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
    }

    private void ATRIBUICAO() {
        if (this.token.getTipo() != Token.TIPO_IDENTIFICADOR) {
            throw new RuntimeException("Erro na atribuição. Perto de: " + this.token.getLexema());
        }

        this.NAODECLAROU(this.token.getLexema());

        this.token = this.lexico.nextToken();
        if (this.token.getTipo() != Token.TIPO_OPERADOR_ATRIBUICAO) {
            throw new RuntimeException("Erro na atribuição. Perto de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();
        this.E();
        if (!this.token.getLexema().equals(";")) {
            throw new RuntimeException("Erro na atribuição. Perto de: " + this.token.getLexema());
        }
        this.token = this.lexico.nextToken();

    }

    private void LACODEREPETICAO() {
        this.token = this.lexico.nextToken();
        if (!this.token.getLexema().equals("(")) {
            throw new RuntimeException("Era pra ter aberto o parêntese");
        }

        this.token = this.lexico.nextToken();
        if (!(this.token.getTipo() == Token.TIPO_IDENTIFICADOR || this.token.getTipo() == Token.TIPO_INTEIRO)) {
            throw new RuntimeException("Era pra ter um inteiro ou um identificador");
        }

        this.NAODECLAROU(this.token.getLexema());

        this.token = this.lexico.nextToken();
        if (this.token.getTipo() != Token.TIPO_OPERADOR_RELACIONAL) {
            throw new RuntimeException("Era pra ter um operador relacional");
        }

        this.token = this.lexico.nextToken();
        if (!(this.token.getTipo() == Token.TIPO_IDENTIFICADOR || this.token.getTipo() == Token.TIPO_INTEIRO)) {
            throw new RuntimeException("Era pra ter um inteiro ou um identificador");
        }

        this.NAODECLAROU(this.token.getLexema());

        this.token = this.lexico.nextToken();
        if (!this.token.getLexema().equals(")")) {
            throw new RuntimeException("Era pra ter fechado o parêntese");
        }
        this.token = this.lexico.nextToken();

        this.B();
    }

    private void CONDICIONAL() {
        this.token = this.lexico.nextToken();
        if (!this.token.getLexema().equals("(")) {
            throw new RuntimeException("Era pra ter aberto o parêntese");
        }

        this.token = this.lexico.nextToken();
        if (!(this.token.getTipo() == Token.TIPO_IDENTIFICADOR || this.token.getTipo() == Token.TIPO_INTEIRO)) {
            throw new RuntimeException("Era pra ter um inteiro ou um identificador");
        }

        this.NAODECLAROU(this.token.getLexema());

        this.token = this.lexico.nextToken();
        if (this.token.getTipo() != Token.TIPO_OPERADOR_RELACIONAL) {
            throw new RuntimeException("Era pra ter um operador relacional");
        }

        this.token = this.lexico.nextToken();
        if (!(this.token.getTipo() == Token.TIPO_IDENTIFICADOR || this.token.getTipo() == Token.TIPO_INTEIRO)) {
            throw new RuntimeException("Era pra ter um inteiro ou um identificador");
        }

        this.NAODECLAROU(this.token.getLexema());

        this.token = this.lexico.nextToken();
        if (!this.token.getLexema().equals(")")) {
            throw new RuntimeException("Era pra ter fechado o parêntese");
        }
        this.token = this.lexico.nextToken();

        this.B();
    }

    private void SENAO() {
        this.token = this.lexico.nextToken();
        this.B();
    }

    private void JADECLAROU(String id){
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR) {
            if (lista.buscarObjeto(id)) {
                throw new RuntimeException("Tu já declarou a variável: " + this.token.getLexema());
            }
        }
    }

    private void NAODECLAROU(String id){
        if(this.token.getTipo() == Token.TIPO_IDENTIFICADOR){
            if(!lista.buscarObjeto(id)){
                throw new RuntimeException("Tu não declarou a variável: " + this.token.getLexema());
            }
        }
    }

    private void E() {
        this.T();
        this.El();
    }

    private void El() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.OP();
            this.T();
            this.El();
        }
    }

    private void T() {
        if (this.token.getTipo() == Token.TIPO_IDENTIFICADOR ||
                this.token.getTipo() == Token.TIPO_INTEIRO ||
                this.token.getTipo() == Token.TIPO_REAL) {
            this.token = this.lexico.nextToken();
        } else {
            throw new RuntimeException("Era pra ser um identificador ou número perto de " + this.token.getLexema());
        }
    }

    private void OP() {
        if (this.token.getTipo() == Token.TIPO_OPERADOR_ARITMETICO) {
            this.token = this.lexico.nextToken();
        } else {
            throw new RuntimeException("Era pra ser um operador aritmético (+,-,/,*) perto de " + this.token.getLexema());
        }
    }
}
