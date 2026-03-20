/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1programacion;

/**
 *
 * @author juanmarroquinaquino
 */
import javax.swing.*;
import java.awt.*;
public class ArbolPanel extends JPanel {
    
    Nodo raiz;

    public ArbolPanel(Nodo raiz) {
        this.raiz = raiz;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        dibujarArbol(g, raiz, getWidth() / 2, 50, getWidth() / 4);
    }

    private void dibujarArbol(Graphics g, Nodo nodo, int x, int y, int offset) {
        if (nodo == null) return;

       
        g.drawOval(x - 20, y - 20, 40, 40);

        
        FontMetrics fm = g.getFontMetrics();
        int ancho = fm.stringWidth(nodo.valor);
        g.drawString(nodo.valor, x - ancho / 2, y + 5);

        
        if (nodo.izq != null) {
            g.drawLine(x, y, x - offset, y + 70);
            dibujarArbol(g, nodo.izq, x - offset, y + 70, offset / 2);
        }

        
        if (nodo.der != null) {
            g.drawLine(x, y, x + offset, y + 70);
            dibujarArbol(g, nodo.der, x + offset, y + 70, offset / 2);
        }
    }
    
}
