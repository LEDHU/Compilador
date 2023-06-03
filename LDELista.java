package compiladorl3;

public class LDELista<T extends Comparable<T>>{
    private LDENode<T> head;
    private LDENode<T> tail;
    private int qtd;

    public boolean isEmpty() {
        return this.head == null && this.tail == null && this.qtd == 0;
    }

    public void addFirst(T valor){
        LDENode<T> novo = new LDENode<>(valor);
        LDENode<T> aux = this.head;

        if(isEmpty())
            this.tail = novo;

        else{
            novo.setNext(aux);
            aux.setPrevious(novo);
        }
        this.head = novo;
        this.qtd++;
    }

    public void addLast(T valor){
        LDENode<T> novo = new LDENode<>(valor);
        LDENode<T> aux = this.tail;

        if(isEmpty())
            this.head = novo;

        else{
            aux.setNext(novo);
            novo.setPrevious(aux);
        }
        this.tail = novo;
        this.qtd++;

    }

    public boolean remove(T valor){
        if(isEmpty()) {
            System.out.println("Lista vazia");
            return false;
        }
        else if(this.qtd == 1){
            if(this.head.getInfo().compareTo(valor) == 0){
                this.head = null;
                this.tail = null;
                this.qtd--;
                return true;
            }
            else {
                System.out.println("Nao pertence a lista");
                return false;
            }
        }
        else{
            if(this.head.getInfo().compareTo(valor) == 0){
                this.head = this.head.getNext();
                this.head.setPrevious(null);
                this.qtd--;
                return true;
            }
            else if(this.tail.getInfo().compareTo(valor) == 0){
                this.tail = this.tail.getPrevious();
                this.tail.setNext(null);
                this.qtd--;
                return true;
            }
            else{
                LDENode<T> aux;
                aux = this.head.getNext();
                int cont = 0;
                while(cont < this.qtd){
                    if(aux.getInfo().compareTo(valor) == 0){
                        aux.getPrevious().setNext(aux.getNext());
                        aux.getNext().setPrevious(aux.getPrevious());
                        this.qtd--;
                        return true;
                    }
                    else{
                        aux = aux.getNext();
                        cont++;
                    }
                }
            }
        }
        return false;
    }

    public void exibirAoContrario() {
        LDENode<T> aux;
        if (this.isEmpty())
            System.out.println("Lista vazia");

        else {
            aux = this.tail;
            while (aux != null) {
                System.out.print(aux.getInfo() + " ");
                System.out.println();
                aux = aux.getPrevious();
            }
        }
    }

    public LDENode<T> buscar(T valor) { // Busca sequencial simples
        LDENode<T> aux;
        if (!this.isEmpty()){
            aux = this.head;
            while (aux != null) {
                if (aux.getInfo().compareTo(valor) == 0)
                    return aux;
                else
                    aux = aux.getNext();
            }
        }
        return null;
    }

    public boolean removerFinal(){
        if(this.tail == null)
            return false;
        if(this.qtd == 1){
            this.tail = null;
            this.head = null;
            this.qtd--;
            return true;
        }
        this.tail = this.tail.getPrevious();
        this.tail.setNext(null);
        this.qtd--;
        return true;
    }

    public boolean removerInicio(){
        if(this.head == null)
            return false;
        if(this.qtd == 1){
            this.tail = null;
            this.head = null;
            this.qtd--;
            return true;
        }
        this.head = this.tail.getNext();
        this.head.setPrevious(null);
        return true;
    }


    public void exibiGeral(){
        LDENode<T> aux;

        if(isEmpty())
            System.out.println("Lista vazia");
        else{
            aux = this.head;
            for(int i = 0; i < qtd; i++){
                System.out.println(aux.getInfo());
                aux = aux.getNext();
            }
        }
    }

    public int numero(){
        LDENode<T> aux = this.head;
        int num = 0;
        while (aux != null){
            num++;
            aux = aux.getNext();
        }
        return num;
    }

    public void removerEspecial(T valor){
        LDENode<T> no = buscarPorTras(valor);
        if(no != null){
            if(no != this.head) {
                no = no.getPrevious();
                no.setNext(null);
                this.tail = no;
                this.qtd = numero();
            }
            else{
                this.tail = null;
                this.head = null;
                this.qtd = 0;
            }
        }
    }

    public LDENode<T> buscarPorTras(T valor) { // Busca sequencial simples
        LDENode<T> aux;
        if (!this.isEmpty()){
            aux = this.tail;
            while (aux != null) {
                if (aux.getInfo().compareTo(valor) == 0)
                    return aux;
                else
                    aux = aux.getPrevious();
            }
        }
        return null;
    }

    public boolean buscarObjeto(T valor) { // Busca sequencial simples
        LDENode<T> aux;
        if (!this.isEmpty()){
            aux = this.head;
            while (aux != null) {
                if (aux.getInfo().compareTo(valor) == 0)
                    return true;
                else
                    aux = aux.getNext();
            }
        }
        return false;
    }

}
