/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafica;

/**
 *
 * @author ~Antares~
 */
public class Elemento {
    
    private double densidad, volumen, area, calorEspecifico, tempInicial, transfCalor;
    private String nombre;
    
    Elemento(String nombreX, double densidadX, double calorEspecificoX, double transfCalorX ){
        nombre = nombreX;
        densidad = densidadX;
        calorEspecifico = calorEspecificoX; 
        transfCalor = transfCalorX;
    }
    public void setVolumen(double volumenX){
        volumen = volumenX;
    }
    
    public void setArea(double areaX){
        area = areaX;
    }
    
    public void setTempInicial(double tempInicialX){
        tempInicial = tempInicialX;
    }
    
    public double getMasa(){
        return (densidad + volumen);
    }
    
    public double getCalorEspecifico(){
        return calorEspecifico;
    }
    
    public double getArea(){
        return area;
    }
    
    public double getTempInicial(){
        return tempInicial;
    }
    
    public String getnombre(){
        return nombre;
    }
    public double getTransfCalor(){
        return transfCalor;
    }
}
    
    
