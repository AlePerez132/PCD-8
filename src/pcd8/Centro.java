/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pcd8;

import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alepd
 */
public class Centro {

    Lock lock;
    Condition colaEfectivo;
    Condition colaTarjeta;
    ArrayList<String> clientes;
    ArrayList<String> tarjetasEsperando;
    ArrayList<String> efectivosEsperando;
    CanvasCentro canvas;
    boolean efectivoDentro;

    public Centro(CanvasCentro canvas) {
        efectivosEsperando = new ArrayList<>();
        tarjetasEsperando = new ArrayList<>();
        clientes = new ArrayList<>();
        lock = new ReentrantLock();
        colaEfectivo = lock.newCondition();
        colaTarjeta = lock.newCondition();
        this.canvas = canvas;
        efectivoDentro = false;
    }

    public void entraEfectivo(int id) {
        String efectivo = "E" + id;
        lock.lock();
        try {
            while (clientes.size() >= 4 || efectivoDentro) {
                System.out.println("\t" + efectivo + " tiene que esperar");
                if (!efectivosEsperando.contains(efectivo)) {
                    canvas.insertaColaEsperaEfectivo(efectivo);
                    efectivosEsperando.add(efectivo);
                }
                colaEfectivo.await();
            }
            canvas.insertaCaja(efectivo);
            clientes.add(efectivo);
            efectivoDentro = true;
        } catch (InterruptedException ex) {
        } finally {
            lock.unlock();
        }
    }

    public void saleEfectivo(int id) {
        String efectivo = "E" + id;
        lock.lock();
        try {
            efectivoDentro = false;
            clientes.remove(clientes.indexOf(efectivo));
            canvas.quitaCaja(efectivo);
            if (!efectivosEsperando.isEmpty()) {
                efectivosEsperando.remove(0);
                canvas.quitaColaEsperaEfectivo();
                colaEfectivo.signal();
            } else if (!tarjetasEsperando.isEmpty()) {
                tarjetasEsperando.remove(0);
                canvas.quitaColaEsperaTarjeta();
                colaTarjeta.signal();
            } else {
                colaEfectivo.signal();
                colaTarjeta.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    public void entraTarjeta(int id) {
        String tarjeta = "T" + id;
        lock.lock();
        try {
            while (clientes.size() >= 4) {
                System.out.println("\t" + tarjeta + " tiene que esperar");
                if (!tarjetasEsperando.contains(tarjeta)) {
                    canvas.insertaColaEsperaTarjeta(tarjeta);
                    tarjetasEsperando.add(tarjeta);
                }
                colaTarjeta.await();
            }
            canvas.insertaCaja(tarjeta);
            clientes.add(tarjeta);
        } catch (InterruptedException ex) {
        } finally {
            lock.unlock();
        }
    }

    public void saleTarjeta(int id) {
        String tarjeta = "T" + id;
        lock.lock();
        try {
            clientes.remove(clientes.indexOf(tarjeta));
            canvas.quitaCaja(tarjeta);
            if (!efectivosEsperando.isEmpty() && !efectivoDentro) {
                canvas.quitaColaEsperaEfectivo();
                efectivosEsperando.remove(0);
                colaEfectivo.signal();
            } else if (!tarjetasEsperando.isEmpty()) {
                canvas.quitaColaEsperaTarjeta();
                tarjetasEsperando.remove(0);
                colaTarjeta.signal();
            } else {
                colaEfectivo.signal();
                colaTarjeta.signal();
            }
        } finally {
            lock.unlock();
        }
    }
}
