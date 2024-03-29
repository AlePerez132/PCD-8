/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/AWTForms/Frame.java to edit this template
 */
package pcd8;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class Generador extends java.awt.Frame {

    /**
     * Creates new form Generador
     */
    public Generador() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Exit the Application
     */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        System.exit(0);
    }//GEN-LAST:event_exitForm

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        Generador gnr = new Generador();
        final int alto = 400, ancho = 1200;
        gnr.setSize(ancho, alto);
        gnr.setBackground(Color.black);
        gnr.setLocation(200, 50);

        CanvasCentro canvas = new CanvasCentro(ancho, alto);
        gnr.add(canvas);
        gnr.setVisible(true);

        Centro centro = new Centro(canvas);
        Random random = new Random(System.currentTimeMillis());
        int numRandom;
        final int numHilos = 50;

        ExecutorService poolEfectivo = Executors.newFixedThreadPool(10);
        ExecutorService poolTarjeta = Executors.newFixedThreadPool(10);

        ArrayList<Future<Integer>> resultadosEfectivo = new ArrayList<>();
        ArrayList<Future<Integer>> resultadosTarjeta = new ArrayList<>();

        int totalTiempoEfectivo = 0;
        int totalTiempoTarjeta = 0;
        
        for (int i = 0; i < numHilos; i++) {
            numRandom = random.nextInt(2);
            if (numRandom % 2 == 0) {
                resultadosEfectivo.add(poolEfectivo.submit(new Efectivo(centro)));
            } else {
                resultadosTarjeta.add(poolTarjeta.submit(new Tarjeta(centro)));
            }

            try {
                Thread.sleep(500); // Tiempo de llegada entre clientes
            } catch (InterruptedException e) {
            }
        }

        for (int i = 0; i < resultadosEfectivo.size(); i++) {
            try {
                totalTiempoEfectivo += resultadosEfectivo.get(i).get();
            } catch (InterruptedException ex) {
            } catch (ExecutionException ex) {
            }
        }
        
        for (int i = 0; i < resultadosTarjeta.size(); i++) {
            try {
                totalTiempoTarjeta += resultadosTarjeta.get(i).get();
            } catch (InterruptedException ex) {
            } catch (ExecutionException ex) {
            }
        }
        
        poolEfectivo.shutdown();
        poolTarjeta.shutdown();
        
        System.out.println("\tEfectivo ha tardado " + totalTiempoEfectivo);
        System.out.println("\tTarjeta ha tardado " + totalTiempoTarjeta);

        System.exit(0);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
