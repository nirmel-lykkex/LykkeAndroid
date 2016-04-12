package com.lykkex.LykkeWallet.gui.utils;

import java.math.BigDecimal;

/**
 * Created by e.kazimirova on 24.03.2016.
 */
public class Calculate {

    public static BigDecimal eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void eatChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eatChar(int ch) {
                if (this.ch == ch) {
                    eatChar();
                    return true;
                }
                return false;
            }

            void eatSpace() {
                while (Character.isWhitespace(ch)) eatChar();
            }

            BigDecimal parse() {
                eatChar();
                BigDecimal x = parseExpression();
                if (pos < str.length()) return BigDecimal.ZERO;
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            BigDecimal parseExpression() {
                BigDecimal x = parseTerm();
                for (;;) {
                    eatSpace();
                    if      (eatChar('+')) x = x.add(parseTerm()); // addition
                    else if (eatChar('-')) x = x.subtract(parseTerm()); // subtraction
                    else return x;
                }
            }

            BigDecimal parseTerm() {
                BigDecimal x = parseFactor();
                for (;;) {
                    eatSpace();
                    if      (eatChar('*')) x = x.multiply(parseFactor()); // multiplication
                    else if (eatChar('/')) x = x.divide(parseFactor()); // division
                    else return x;
                }
            }

            BigDecimal parseFactor() {
                eatSpace();
                if (eatChar('+')) return parseFactor(); // unary plus
                if (eatChar('-')) return new BigDecimal("-"+parseFactor().toString()); // unary minus

                BigDecimal x;
                int startPos = this.pos;
                if (eatChar('(')) { // parentheses
                    x = parseExpression();
                    eatChar(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') eatChar();
                    x = new BigDecimal(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') eatChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = BigDecimal.valueOf(Math.sqrt(x.doubleValue()));
                    else if (func.equals("sin")) x = BigDecimal.valueOf(Math.sin(Math.toRadians
                            (x.doubleValue())));
                    else if (func.equals("cos")) x = BigDecimal.valueOf(Math.cos(Math.toRadians
                            (x.doubleValue())));
                    else if (func.equals("tan")) x = BigDecimal.valueOf(Math.tan(Math.toRadians
                            (x.doubleValue())));
                    else return BigDecimal.ZERO;
                } else {
                    return BigDecimal.ZERO;
                }

                eatSpace();
                if (eatChar('^')) x = BigDecimal.valueOf(
                        Math.pow(x.doubleValue(), parseFactor().doubleValue())); // exponentiation

                return x;
            }
        }.parse();
    }
}
