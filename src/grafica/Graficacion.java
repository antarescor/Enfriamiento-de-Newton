/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafica;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.TableModel;

/**
 *
 * @author ~Antares~
 */
public class Graficacion extends Thread{
    
    private double k;//0.051167849;//0.373; //0.02596623222;
    private double T_Objeto;
    private double Ta;
    private static double transfCalor; //0.019;
    
    private TableModel tablaMod;
    private JLabel tempActual;

    private int ejeXi, ejeYi, ejeXf, ejeYf, limite;
    private Thread hilo;
    
    private Graphics g;
    private JPanel grafico;
    private boolean sigo = true;
    private boolean dibujo = true;
    
    
    Graficacion(Graphics x, JPanel graficoX, TableModel tablaX, Elemento elementoX, double ta, JLabel tempActualX){
        Ta = ta;
        T_Objeto = elementoX.getTempInicial(); 
        
        transfCalor = elementoX.getTransfCalor();
        
        k = (transfCalor * elementoX.getArea())/(elementoX.getMasa()*elementoX.getCalorEspecifico());
        System.out.println("La constante K del elemento de "+elementoX.getnombre()+" es "+k);
        
        grafico = graficoX;
        g = x;
        limite = grafico.getWidth()-40;
        tablaMod = tablaX;
        tempActual = tempActualX;
        
        hilo = new Thread();               
    }
    
     public void ejeX(){
         //pintar eje X          
         
         g.setColor(Color.GREEN);
         g.drawString("t(min)", grafico.getWidth()-30, 580);
         g.setColor(Color.YELLOW);
         g.drawLine(45,540,grafico.getWidth(),540);
         
         int xi, xf, yi, yf, esp, largo;
         esp=10;
         largo=5;
         xi=55; yi=540;         
         xf=xi; yf=yi+largo; 
         
         while(xf <= grafico.getWidth()) {
            if(xf%15==0){
                largo=10;
                yf=yi+largo;            }
            else{
                largo=5;
                yf=yi+largo;
            }
            g.drawLine(xi, yi, xf, yf);
            xi+=esp;
            xf=xi;
        }
        int posX=42;
        for (int i = 0; posX <=  grafico.getWidth(); i+=15){
            g.setColor(Color.green);
            g.drawString(""+i/3, posX-4, 565);
            posX+=30;
        }
         
    }
     public void ejeY(){
         //pintar eje Y         
         
         g.setColor(Color.red);
         g.drawString("T(t)", 20, 80);
         g.setColor(Color.YELLOW);
         g.drawLine(45,90,45,540);
         
         int xi, xf, yi, yf, esp;
         esp=10;
         xi=40; yi=90;         
         xf=xi+5; yf=5+yi;
         
         while(yf<540) {
            g.setColor(Color.YELLOW);
            g.drawLine(xi, yi, xf, yi);
            yi+=esp;
            yf=yi;            
        }
        int pix = 0;
        for (int i = 540; i >= 90; i--){
            if(pix % 50 == 0){
                g.setColor(Color.red);
                g.drawString(""+pix/5, 25, i+5);                
            }
            pix++;
        }
    }
    
     public void run() { 
         
         System.out.println("entro a Run");         
         int di=5;          
         int despX = 45;
         int despY = grafico.getHeight();         
         
         int contTabla = 1;
         
         tablaMod.setValueAt(0, 0, 0);
         tablaMod.setValueAt(T_Objeto, 0, 1);
         
         int intervaloTime=1;
         int auxTabla = 0;
         double factorG = 5;
         double vlRealX = 0;// guardan los valores reales de la formula en X
         double vlRealY = (Math.exp(Math.toRadians(-vlRealX * k)) * (T_Objeto - Ta)) + Ta; //calcula el rpimer valor de Y      
         
         ejeXi = (int)vlRealX/3;
         ejeYi = -(int)(vlRealY  * factorG);
        
         while(sigo){
              
            vlRealX = intervaloTime;
            vlRealY = (Math.exp(Math.toRadians(-vlRealX *k)) * (T_Objeto - Ta)) + Ta;
            
            ejeXf = (int)vlRealX/3;
            ejeYf = -(int)(vlRealY * factorG);
            
            tempActual.setText(""+Math.rint(vlRealY*10000)/10000);//Math.ceil(vlRealY)); 
            
              
            if (dibujo){
                g.setColor(Color.RED);                
                g.drawLine(ejeXi+despX, ejeYi+despY, ejeXf+despX, ejeYf+despY);
                if (intervaloTime% 90 == 0){
                        g.setColor(Color.WHITE);
                        g.fillOval(ejeXi+despX-2, ejeYi+despY-2, di, di);               
                    }
            }                
            
            if (auxTabla == (int)vlRealX/18){
                tablaMod.setValueAt((int)vlRealX/18, contTabla, 0);
                tablaMod.setValueAt(Math.rint(vlRealY*10000)/10000, contTabla, 1); 
                contTabla++;
                auxTabla++;
            }
            
            ejeXi = ejeXf;  
            ejeYi = ejeYf;
             if (ejeXf == limite){
                 dibujo = false; 
             }
                 
            System.out.println("pinto en ("+ejeXi+","+ejeYi+")"+"!!!!!  t(s), T(t): ("+ejeXi+"),("+ejeYi+")");
            intervaloTime++;  
            
            if (T_Objeto > Ta){
                if (vlRealY < Ta + 0.05)
                sigo = false;
            }
            else {
                if (vlRealY > Ta - 0.05)
                sigo = false;
            }         
             try{
                hilo.sleep(10); //creo que unos 1500 segundos                   
                }
                catch(InterruptedException excepcion){
                      System.out.println("no se pudo dormir");
                }
               
                 g.setColor(Color.CYAN);
                 g.drawLine(45, grafico.getHeight()-(int)(Ta * factorG), grafico.getWidth(), grafico.getHeight()-(int)(Ta * factorG));//linea de la temperatura ambiente
                 g.drawString("Ta = "+Ta, grafico.getWidth()-60, grafico.getHeight()-(int)(Ta * factorG)-5); 
            ejeX();
            ejeY();            
         }    
    }
    
     public void para(){
        sigo=false;
    }
    
     public void pausa(){
        this.suspend();
    }
    
     public void reanuda(){
        this.resume();
    }
    
   
   
    
    
}
