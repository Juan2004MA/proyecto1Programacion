/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1programacion;

/**
 *
 * @author juanmarroquinaquino
 */
import java.util.*;
import javax.swing.*;

public class ExpresionApp {

    
    public static boolean validar(String expr) {
        return expr.matches("[a-zA-Z0-9+\\-*/^() ]+");
    }

   
    public static Set<Character> obtenerVariables(String expr) {
        Set<Character> vars = new HashSet<>();
        for (char c : expr.toCharArray()) {
            if (Character.isLetter(c)) {
                vars.add(c);
            }
        }
        return vars;
    }

   
    public static int prioridad(char op) {
        if (op == '+' || op == '-') return 1;
        if (op == '*' || op == '/') return 2;
        if (op == '^') return 3;
        return 0;
    }

    
    public static List<String> infixToPostfix(String expr) {
        List<String> salida = new ArrayList<>();
        Stack<Character> pila = new Stack<>();

        StringTokenizer tokens = new StringTokenizer(expr, "+-*/^() ", true);

        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim();
            if (token.isEmpty()) continue;

            char c = token.charAt(0);

            if (Character.isLetterOrDigit(c)) {
                salida.add(token);
            } else if (c == '(') {
                pila.push(c);
            } else if (c == ')') {
                while (!pila.isEmpty() && pila.peek() != '(') {
                    salida.add(String.valueOf(pila.pop()));
                }
                pila.pop();
            } else {
                while (!pila.isEmpty() && prioridad(pila.peek()) >= prioridad(c)) {
                    salida.add(String.valueOf(pila.pop()));
                }
                pila.push(c);
            }
        }

        while (!pila.isEmpty()) {
            salida.add(String.valueOf(pila.pop()));
        }

        return salida;
    }

   
    public static double evaluar(List<String> postfix, Map<Character, Double> valores) {
        Stack<Double> pila = new Stack<>();

        for (String token : postfix) {
            char c = token.charAt(0);

            if (Character.isLetter(c)) {
                pila.push(valores.get(c));
            } else if (Character.isDigit(c)) {
                pila.push(Double.parseDouble(token));
            } else {
                double b = pila.pop();
                double a = pila.pop();

                switch (c) {
                    case '+': pila.push(a + b); break;
                    case '-': pila.push(a - b); break;
                    case '*': pila.push(a * b); break;
                    case '/': pila.push(a / b); break;
                    case '^': pila.push(Math.pow(a, b)); break;
                }
            }
        }

        return pila.pop();
    }

    
    public static Nodo construirArbol(List<String> postfix) {
        Stack<Nodo> pila = new Stack<>();

        for (String token : postfix) {
            Nodo n = new Nodo(token);

            if ("+-*/^".contains(token)) {
                n.der = pila.pop();
                n.izq = pila.pop();
            }

            pila.push(n);
        }

        return pila.pop();
    }

    
    public static void preorden(Nodo n) {
        if (n != null) {
            System.out.print(n.valor + " ");
            preorden(n.izq);
            preorden(n.der);
        }
    }

    public static void inorden(Nodo n) {
        if (n != null) {
            inorden(n.izq);
            System.out.print(n.valor + " ");
            inorden(n.der);
        }
    }

    public static void postorden(Nodo n) {
        if (n != null) {
            postorden(n.izq);
            postorden(n.der);
            System.out.print(n.valor + " ");
        }
    }

    // MAIN
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese expresión: ");
        String expr = sc.nextLine();

        if (!validar(expr)) {
            System.out.println("Expresión inválida");
            return;
        }

        Set<Character> vars = obtenerVariables(expr);
        Map<Character, Double> valores = new HashMap<>();

        for (char v : vars) {
            System.out.print(v + " = ");
            valores.put(v, sc.nextDouble());
        }

        List<String> postfix = infixToPostfix(expr);

        System.out.println("\nPostfija: " + String.join(" ", postfix));

        double resultado = evaluar(postfix, valores);
        System.out.println("Resultado: " + resultado);

        Nodo raiz = construirArbol(postfix);

        System.out.print("\nPreorden: ");
        preorden(raiz);

        System.out.print("\nInorden: ");
        inorden(raiz);

        System.out.print("\nPostorden: ");
        postorden(raiz);

        //esto es para que muestre el arbol:)
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Árbol de Expresión");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new ArbolPanel(raiz));
            frame.setVisible(true);
        });
    }
}
