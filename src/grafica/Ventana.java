/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafica;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;




/**
 *
 * @author ~Antares~
 */
public class Ventana extends JFrame implements ActionListener, ItemListener{
    
    private JButton inicio, parar, pausa;
    private JTextField tAmbiente, tInicial, area, volumen;
    private JLabel ambiente, inicial, elemetos, areaL, volumenL, tempActualL, tempActual ;
    private JComboBox  listaElementos;
    private Elemento  hierro,aluminio,cobre, elementoAux;
    private Vector<Elemento> vecElementos;
    private int indexElement = 0; //por defecto empieza con el elemnto cero (hierro) 
    
    private JTable tabla;
    private DefaultTableModel modelTabla;
    private JScrollPane scroll;
    
    private JPanel grafico, controles, panelTabla;
       
    private JMenuBar menu;
    private JMenu archivo, acerdaDe;
    private JMenuItem salir, creador, conceptoTeorico, funcionamiento;
    
    
    private Container cont;
    private Graficacion graficacion;
    private Graphics g;
    
    Ventana(){
        super("Grafica");
        setVisible(true);
        setSize(1000, 600);
        setLocation(10, 30);
        setResizable(false);
        
        cont = getContentPane();
        cont.setLayout(null);        
        
        initComponentes();
    }
    
    public void initComponentes(){        
        panelMenu();
        panelGrafico();
        panelControles();
        panelTabla();
        initElementos();
    }
    
    public void initElementos(){
        
        hierro = new Elemento(  "Hierro",   7.88,  0.10750119, 0.008); //(450/4186)
        aluminio = new Elemento("Aluminio", 2.7,   0.21022455, 0.022); //(880/4186)
        cobre = new Elemento(   "Cobre",    8.930, 0.09316770, 0.05); //(390/4186)
        
        vecElementos = new Vector<Elemento>(3);
        vecElementos.add(hierro);   //0
        vecElementos.add(aluminio); //1
        vecElementos.add(cobre);    //2
        
        elementoAux = vecElementos.elementAt(indexElement); // se asigna por defecto el primero de la lista (hierro)
    }
    
    private void panelMenu(){        
        menu = new JMenuBar();
        
        archivo = new JMenu("Archivo");
        acerdaDe = new JMenu("Acerca de");
        
        salir = new JMenuItem("Salir");
        salir.addActionListener(this);
        
        creador = new JMenuItem("Desarrollador");
        creador.addActionListener(this);
        
        conceptoTeorico = new JMenuItem("Concepto Teorico");
        conceptoTeorico.addActionListener(this);
        
        funcionamiento = new JMenuItem("Funcionamiento");
        funcionamiento.addActionListener(this);
        
        archivo.add(conceptoTeorico);
        archivo.add(salir);
        
        acerdaDe.add(creador);
        acerdaDe.add(funcionamiento);
        
        menu.add(archivo);
        menu.add(acerdaDe);
        
        menu.setBounds(0, 0, 1000, 30);
        
        cont.add(menu);       
    }
    
    private void panelControles(){
        controles = new JPanel();       
        controles.setLayout(null); //    >>>>>>>>>>>>>>>>>>> aqui el manejador <<<<<<<<<<<<<<<<<<<< 
        controles.setBorder(BorderFactory.createTitledBorder(null,""
               + "Controles",TitledBorder.CENTER,0,null, Color.BLUE));
        controles.setBounds(700,30,290,240);
        
        elemetos = new JLabel("Material");
        elemetos.setBounds(20, 30, 100, 20);
        
        String metales[] = {"Hierro", "Aluminio", "Cobre"};
        listaElementos = new JComboBox(metales);
        listaElementos.setBounds(20, 50, 130, 20);
        listaElementos.addItemListener(this);
        
        areaL = new JLabel("Area (cm°2)");
        areaL.setBounds(20, 70, 130, 20);        
        area = new JTextField();
        area.setBounds(20, 90, 130, 20);
        area.setEnabled(false);
        
        volumenL = new JLabel("Volumen (cm°3)");
        volumenL.setBounds(20, 110, 130, 20);        
        volumen = new JTextField();
        volumen.setBounds(20, 130, 130, 20);
        volumen.setEnabled(false);
        
        ambiente = new JLabel("Temp. Ambiente (°C)");
        ambiente.setBounds(20, 150, 130, 20);
        tAmbiente = new JTextField("");
        tAmbiente.setBounds(20, 170, 130, 20);
        tAmbiente.setEnabled(false);
        
        
        inicial = new JLabel("Temp. Inicial Objeto (°C)"); 
        inicial.setBounds(20, 190, 150, 20);
        tInicial= new JTextField("");
        tInicial.setBounds(20, 210, 130, 20);
        tInicial.setEnabled(false);
        
        inicio = new JButton("Inicio");
        inicio.setBounds(180, 40, 100, 30);
        inicio.addActionListener(this);
        inicio.setEnabled(false);
        
        parar = new JButton("Parar");
        parar.setBounds(180, 80, 100, 30);
        parar.addActionListener(this);
        parar.setEnabled(false);
        
        pausa = new JButton("Pausa");
        pausa.setBounds(180, 120, 100, 30);
        pausa.addActionListener(this);
        pausa.setEnabled(false);   
        
        controles.add(elemetos);
        controles.add(listaElementos);
        controles.add(areaL);
        controles.add(area);
        
        controles.add(volumenL);
        controles.add(volumen);        
          
        controles.add(ambiente);
        controles.add(inicial);
        
        controles.add(tAmbiente);
        controles.add(tInicial);
        
        controles.add(inicio);
        controles.add(parar);
        controles.add(pausa);
        
        cont.add(controles);
    }
    
    private void panelTabla(){        
        panelTabla = new JPanel();
        panelTabla.setLayout(null); //    >>>>>>>>>>>>>>>>>>> aqui el manejador <<<<<<<<<<<<<<<<<<<< 
        panelTabla.setBorder(BorderFactory.createTitledBorder(null,""
               + "Tabla",TitledBorder.CENTER,0,null, Color.BLUE));         
        
        panelTabla.setBounds(700,270,290,300);
        
        tempActualL = new JLabel("Temperatura Actual  = ");
        tempActualL.setBounds(20, 20, 150, 30);
        
        tempActual = new JLabel("00.00");
        tempActual.setBounds(160, 20, 150, 30);
        tempActual.setFont(new Font("Arial", Font.BOLD, 26));
        
        modelTabla = new DefaultTableModel();
        modelTabla.addColumn("t(min)");
        modelTabla.addColumn("T(t)");        
        modelTabla.setNumRows(500);  //Tamaño de la tabla
        
        tabla = new JTable(modelTabla);
        tabla.setEnabled(false);
       
        scroll = new JScrollPane(tabla);        
        scroll.setBounds(20, 50, 240, 240);
           
        panelTabla.add(scroll);
        panelTabla.add(tempActualL);
        panelTabla.add(tempActual);

        cont.add(panelTabla);

    }
    
    private void panelGrafico(){
        
        int ancho = 685;
        int alto  = 540;      
        
        grafico = new JPanel();        
        grafico.setLayout(new FlowLayout()); //    >>>>>>>>>>>>>>>>>>> aqui el manejador <<<<<<<<<<<<<<<<<<<< 
        grafico.setBorder(BorderFactory.createTitledBorder(null,""
               + "Ley de Enfriamiento de Newton",TitledBorder.CENTER,0,null, Color.GREEN));
        grafico.setBounds(10, 30, ancho, alto);
        grafico.setBackground(Color.black);         
        cont.add(grafico);
        
    }    
   
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource().equals(inicio)){    
            
            grafico.repaint();
            for (int i = 0;i <modelTabla.getRowCount(); i++) {
                 modelTabla.setValueAt("", i, 0);
                 modelTabla.setValueAt("", i, 1); 
            }        
            
            try{
                double ta = Double.parseDouble(tAmbiente.getText());
                elementoAux.setTempInicial(Double.parseDouble(tInicial.getText()));
                elementoAux.setArea(Double.parseDouble(area.getText()));
                elementoAux.setVolumen(Double.parseDouble(volumen.getText()));
                
                g = getGraphics();
                graficacion = new Graficacion(g, grafico,modelTabla, elementoAux, ta, tempActual);
                
                graficacion.start();
                inicio.setEnabled(false);
                inicio.setEnabled(false);
                parar.setEnabled(true);
                pausa.setEnabled(true);
                tAmbiente.setEditable(false);
                tInicial.setEditable(false);
                area.setEditable(false);
                volumen.setEditable(false);
                listaElementos.setEnabled(false);
            }
                catch(NumberFormatException excepcion){
                      Toolkit.getDefaultToolkit().beep();
                      String msj = "Error en Alguna entrada";
                      JOptionPane.showMessageDialog(rootPane,  msj, "Desarrollo", 0, null);
                }       
    
           
        }
        
        if(e.getSource().equals(parar)){
             tAmbiente.setEditable(true);
             tInicial.setEditable(true);
             area.setEditable(true);
             volumen.setEditable(true);
             graficacion.para();
             parar.setEnabled(false);
             pausa.setEnabled(false);
             inicio.setEnabled(true);
             listaElementos.setEnabled(true);    
        }
        
        if(e.getSource().equals(pausa)){
            
            if (pausa.getText().equals("Pausa")){   
                graficacion.pausa();                
                pausa.setText("Reanudar");
                parar.setEnabled(false);
            }
            else{
                graficacion.reanuda();
                pausa.setText("Pausa");
                parar.setEnabled(true);
            }  
        }
        
        if(e.getSource().equals(creador)){
            String msj = "Creado por:\n\n\nManuel Alejandro Doncel Castro\n         Universidad del Valle\n               sede Tulua";
            JOptionPane.showMessageDialog(rootPane,  msj, "Desarrollo", WIDTH, null);
        }
        
    }


    public void itemStateChanged(ItemEvent e){
            
       if ( e.getStateChange() == ItemEvent.SELECTED ){
           inicio.setEnabled(true);
           area.setEnabled(true);
           tInicial.setEnabled(true);
           tAmbiente.setEnabled(true);
           volumen.setEnabled(true);
           indexElement = listaElementos.getSelectedIndex();
           System.out.println("Seleccionó el item " + indexElement + " en la lista");
           elementoAux = vecElementos.elementAt(indexElement);
       }

    }
}

   
