/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pcd8;

import static java.lang.Thread.sleep;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

/**
 *
 * @author alepd
 */
public class Efectivo implements Callable<Integer> {

    private Centro centro;

    public Efectivo(Centro centro) {
        this.centro = centro;
    }

    @Override
    public Integer call() throws Exception {
        int id = (int) Thread.currentThread().getId();
        Random r = new Random(System.currentTimeMillis());
        int tiempo = (r.nextInt(3) + 1) * 1000;
        System.out.println("Empieza efectivo " + id);
        centro.entraEfectivo(id);
        System.out.println("Efectivo " + id + " está pagando");
        sleep(tiempo);
        centro.saleEfectivo(id);
        System.out.println("Efectivo " + id + " ha terminado");
        return tiempo;
    }

}
