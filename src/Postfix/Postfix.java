/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Postfix;

import java.io.*;
import java.util.Hashtable;
import Lexer.Lexer;
/**
 *
 * @author JimC
 */
public class Postfix {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        Parser parse = new Parser();
        parse.expr();
        System.out.write('\n');
    }

}

class Parser {

    Lexer.Token token;
    Lexer lexer = new Lexer();
    public Parser() throws IOException {
        token = lexer.scan();
    }

    void expr() throws IOException {
        term();
        while (token.tag!=256 || token.tag!=10) {
            if (token.tag == (char)'+') {
                match(token);
                term();
                System.out.print("+ ");
                continue;
            } else if (token.tag == (char)'-') {
                match(token);
                term();
                System.out.print("- ");
                continue;
            } else {
                return;
            }
        }
    }

    void term() throws IOException {
        factor();
        while (token.tag!=256 || token.tag!=10) {
            if (token.tag == (char)'*') {
                match(token);
                factor();
                System.out.print("* ");
                continue;
            } else if (token.tag == (char)'/') {
                match(token);
                factor();
                System.out.print("/ ");
                continue;
            } else {
                return;
            }
        }
    }
    void factor() throws IOException {
        if ( token.tag==256) {
            Lexer.Num num = (Lexer.Num)token;
            System.out.print(num.value+" ");
            match(token);
        } else {
            throw new Error("syntax error");
        }
    }

    void match(Lexer.Token t) throws IOException {
        if (token == t) {
            token = lexer.scan();
        } else {
            throw new Error("syntax error");
        }
    }
}
