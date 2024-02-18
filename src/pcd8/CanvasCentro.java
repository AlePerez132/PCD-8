/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pcd8;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

/**
 *
 * @author alepd
 */
public class CanvasCentro extends Canvas {

    private ArrayList<String> colaEsperaEfectivo;
    private ArrayList<String> colaEsperaTarjeta;
    private ArrayList<String> Centro;

    public CanvasCentro(int ancho, int alto) {
        this.setSize(ancho, alto);
        colaEsperaEfectivo = new ArrayList<>();
        colaEsperaTarjeta = new ArrayList<>();
        Centro = new ArrayList<>();
    }

    public void insertaCaja(String cliente) {
        Centro.add(cliente);
        repaint();
    }

    public void quitaCaja(String cliente) {
        Centro.remove(Centro.indexOf(cliente));
        repaint();
    }

    public void insertaColaEsperaEfectivo(String efectivo) {
        colaEsperaEfectivo.add(efectivo);
        repaint();
    }

    public void quitaColaEsperaEfectivo() {
        colaEsperaEfectivo.remove(0);
        repaint();
    }

    public void insertaColaEsperaTarjeta(String tarjeta) {
        colaEsperaTarjeta.add(tarjeta);
        repaint();
    }

    public void quitaColaEsperaTarjeta() {
        colaEsperaTarjeta.remove(0);
        repaint();
    }

    @Override
    public void paint(Graphics g) {

        Font letraChica = new Font("Arial", Font.PLAIN, 30);
        Font letraGrande = new Font("Banschrift", Font.BOLD, 50);
        Image img = createImage(getWidth(), getHeight());
        Graphics og = img.getGraphics();

        og.setFont(letraChica);
        og.setColor(Color.white);

        int x = 310;

        og.drawString("Efectivo en espera: ", 50, 50);

        for (int i = 0; i < colaEsperaEfectivo.size(); i++) {
            og.drawString("" + colaEsperaEfectivo.get(i), x, 50);
            x += 75;

        }

        x = 300;
        og.drawString("Tarjeta en espera: ", 50, 100);
        for (int i = 0; i < colaEsperaTarjeta.size(); i++) {
            og.drawString("" + colaEsperaTarjeta.get(i), x, 100);
            x += 75;
        }

        x = 300;
        for (int i = 0; i < 4; i++) {
            og.drawRect(x, 150, 150, 150);
            x += 150;
        }

        og.setFont(letraGrande);
        x = 330;
        for (int i = 0; i < Centro.size(); i++) {
            og.drawString("" + Centro.get(i), x, 240);
            x = x + 150;
        }

        g.drawImage(img, 0, 0, null);

    }
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }
}
