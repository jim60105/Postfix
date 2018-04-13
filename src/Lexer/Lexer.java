/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lexer;

import java.io.IOException;
import java.util.Hashtable;

/**
 *
 * @author JimC
 */
public class Lexer {

    public int line = 1;
    private char peek = ' ';
    private Hashtable words = new Hashtable();

    public class Token {

        public final int tag;

        public Token(int t) {
            tag = t;
        }
    }

    public class Tag {

        public final static int NUM = 256, ID = 257, TRUE = 258, FALSE = 259;
    }

    public class Num extends Token {

        public final int value;

        public Num(int v) {
            super(Tag.NUM);
            value = v;
        }
    }

    public class Word extends Token {

        public final String lexeme;

        public Word(int t, String s) {
            super(t);
            lexeme = new String(s);
        }
    }

    void reserve(Word t) {
        words.put(t.lexeme, t);
    }

    public Lexer() {
        reserve(new Word(Tag.TRUE, "true"));
        reserve(new Word(Tag.FALSE, "false"));
    }

    public Token scan() throws IOException {
        for (;; peek = (char) System.in.read()) {
            if (peek == ' ' || peek == '\t') {
                continue;
            } else if (peek == '\n') {
                line = line + 1;
                break;
            } else {
                break;
            }
        }
        if (Character.isDigit(peek)) {
            int v = 0;
            do {
                v = v * 10 +Character.digit(peek, 10);
                peek = (char) System.in.read();
            } while (Character.isDigit(peek));
            return new Num(v);
        }
        if (Character.isLetter(peek)) {
            StringBuffer b = new StringBuffer();
            do {
                b.append(peek);
                peek = (char) System.in.read();
            } while (Character.isLetterOrDigit(peek));
            String s = b.toString();
            Word w = (Word) words.get(s);
            if (w != null) {
                return w;
            }
            w = new Word(Tag.ID, s);
            words.put(s, w);
            return w;
        }
        Token t = new Token(peek);
        if(peek!=10){
            peek = (char) System.in.read();
        }
        return t;
    }
}
