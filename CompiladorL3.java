/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladorl3;

public class CompiladorL3 {

    public static void main(String[] args) {
        // TODO code application logic here
        Lexico lexico = new Lexico("C:/Users/Luise/Documents/Católica/2023.1/Compilador/compilador-main/CompiladorL3/src/compiladorl3/codigo.txt");
        Sintatico3 sintatico3 = new Sintatico3(lexico);
        sintatico3.S();

        Token t;
        while((t = lexico.nextToken()) != null) {
            System.out.println(t);
        }
    }
}