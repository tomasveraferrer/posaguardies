/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 *
 * @author TOMAS
 */
public class JavaFXApplication4 extends Application {
    
    final int MAX_DADA = 10000;
    final int DIES = 5;
    final int HORA = 20;
    int i = 0;
    int cont_h_grup = 0;
    int cont_h_profe = 0;
    int enteroDIA = 0;
    int enteroHORA = 0;
    String valor;
    String matriu[] = new String[MAX_DADA];
    String grups[] = {"E1A","E1B","E1C","E1D",
                        "E2A","E2B","E2C","E2D",
                        "E3A","E3B","E3C","E3D",
                        "E4A","E4B","E4C","E4D",
                        "B1A","B1B","B2A","B2B",
                        "CFA","CFB","CFC"};
    String profes[] = {"AN1","AN2","AN3","AN4",
                        "ES1","ES2","ES3","ES4",
                        "CA1","CA2","CA3","CA4",
                        "MA1","MA2","MA3","MA4",
                        "TE1","TE2","TE3","TE4",
                        "HI1","HI2","HI3","HI4"};
    char SuperMatriu [][][] = new char[grups.length][DIES][HORA];
    char SuperMatriuProfes [][][] = new char[profes.length][DIES][HORA];
    
    // Funció per treure els símbols "
    public String sensecometes(String ambcometes) {
        
        String sensecometes = "";
    
        sensecometes = ambcometes.replace("\"", "");
    
        return sensecometes;
    }
    
    // Classe professor
    class Professor {
        
        int hores_guardia = 4;
        int hores_lectives = 20;
        String codi = "";
        String nom = "";
        String cognom1 = "";
        String cognom2 = "";
        char horari [][] = new char [DIES][HORA];
        
        int comptaforats = 0;
        int temporal = 0;
        boolean foratiniciat = false;
        boolean venimdix = false; 
        
        //Constructor simple
        Professor(String c){
            this.codi = c;
        }
        //Constructor multi
        Professor(String c, String n, String c1, String c2, int hg, int hl){
            this.codi = c;
            this.nom = n;
            this.cognom1 = c1;
            this.cognom2 = c2;
            this.hores_guardia = hg;
            this.hores_lectives = hl;
        }
        //Calculem els forats del professor
        public Boolean creahorariprofe() {
        
            for ( int j = 0; j < profes.length; j++)
                {
                  if (profes[j].equals(this.codi)){ 
                   
                    for ( int m = 0; m < HORA; m++)
                    {
                        
                        for ( int k = 0; k < DIES; k++)
                        {
                            this.horari[k][m] = SuperMatriuProfes[j][k][m];
                        }
                    }
                  } 
                } 
            
        return true;
        }
        //Omple els forats
        public Boolean ompleforats() {
        
        return true;
        }
        
        //Calculem els forats del professor l'horari ha d'estar creat prèviament
        public int calculaforats() {
          
            char bb;
            for ( int k = 0; k < DIES; k++)
                    {

                        this.temporal = 0;
                        this.foratiniciat = false;
                        this.venimdix = false; 
                        
                        for ( int m = 0; m < HORA; m++)
                        {
                            bb = this.horari[k][m];
                            
                        //    System.out.print("\n dades abans: "+" | "+bb+" | "+this.foratiniciat+" | "+this.venimdix+"\n");    

                            //si es blanc i no hem començat no el comptem
                            if (bb == '-' && this.foratiniciat == false && this.venimdix == false){
                                this.foratiniciat = false;
                                this.venimdix = false;
                                //System.out.print("\n logica: _00");    

                            }else 
                            //si es blanc i forat no iniciat i venim d'X --> obrim forat i comptem i venim d'X false
                                if (bb == '-' && this.foratiniciat == false && this.venimdix == true){
                                    this.foratiniciat = true;
                                    this.venimdix = false;
                                    this.temporal++;
                                    //System.out.print("\n logica: _01"); 
                                }else
                            //si es blanc i hi ha forat i no venim d'X --> es forat i el comptem
                                    if (bb == '-' && this.foratiniciat == true && this.venimdix == false){
                                        this.foratiniciat = true;
                                        this.venimdix = false;
                                        this.temporal++;
                                        //System.out.print("\n logica: _10"); 
                                    }else
                            //si es blanc i estem en forat i venim d'X --> venim d'X false i omptem forat         
                                        if (bb == '-' && this.foratiniciat == true && this.venimdix == true){
                                            this.foratiniciat = true;
                                            this.venimdix = false;
                                            this.temporal++;
                                            //System.out.print("\n logica: _11"); 
                                        }else
                            //si hi ha classe no hi havia forat i no venim d'X --> venim d'X i no comptem forat                
                                            if (bb != '-' && this.foratiniciat == false && this.venimdix == false){
                                                this.foratiniciat = false;
                                                this.venimdix = true;
                                                //System.out.print("\n logica: X00"); 
                                            }else
                            //si hi ha classe no hi havia forat i sí venim d'X --> venim d'X i no comptem forat                    
                                                if (bb != '-' && this.foratiniciat == false && this.venimdix == true){
                                                   this.foratiniciat = false;
                                                   this.venimdix = true; 
                                                   //System.out.print("\n logica: X01"); 
                                                }else
                            //si hi ha classe i hi havia forat i no venim d'X --> no comptem forat, venim d'X, incrementem comptador globali this.temporal a 0                       
                                                    if (bb != '-' && this.foratiniciat == true && this.venimdix == false){
                                                        this.foratiniciat = false;
                                                        this.venimdix = true;
                                                        this.comptaforats = this.comptaforats + this.temporal;
                                                        this.temporal = 0;
                                                        //System.out.print("\n logica: X10"); 
                                                    }else{
                            //si hi ha classe i hi ha havia forat i venim d'X --> cas extrany i no fem res                           
                                                        //System.out.print("\n logica: X11"); 
                                                    }
                      //System.out.print("\n trec el comptador: " + this.comptaforats +" dades despres: "+" | "+bb+" | "+this.foratiniciat+" | "+this.venimdix+"\n");    
                        }
                    }
            
        return this.comptaforats;
        }
        
    }
    
    @Override
    public void start(Stage primaryStage) {
                
        Button btn = new Button();
        btn.setText("Importo CSV");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Començo a importar");
                
                           
                //Get scanner instance
                Scanner scanner;
                try {
                //    compte amb la codificació, millor UTF-8
                    scanner = new Scanner(new File("dades/horaris.csv"));

                //Set the delimiter used in file
                scanner.useDelimiter(";");
                //Get all tokens and store them in some data structure
                //I am just printing them
                while (scanner.hasNext())
                {                   
                    valor = scanner.next();
                //    System.out.print("\n(" + i +") ");
                //    System.out.print(valor + " ");
                    matriu[i] = sensecometes(valor);
                    i = i + 1;
                }

                //Do not forget to close the scanner 
                scanner.close();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(JavaFXApplication4.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                // Poblem la SuperMatriu amb '-'
                for ( int j = 0; j < grups.length; j++)
                {
                                    
                    for ( int k = 0; k < DIES; k++)
                    {
                        for ( int m = 0; m < HORA; m++)
                        {
                            SuperMatriu[j][k][m] = '-';
                        }
                    }
                   
                } 
                
                // Poblem la SuperMatriuProfes amb '-'
                for ( int j = 0; j < grups.length; j++)
                {
                                    
                    for ( int k = 0; k < DIES; k++)
                    {
                        for ( int m = 0; m < HORA; m++)
                        {
                            SuperMatriuProfes[j][k][m] = '-';
                        }
                    }
                   
                } 
                
                // fem una prova per mostrar  classes per grups i canviar la SuperMatriu
                for ( int c = 0; c < grups.length; c++)
                {
                  System.out.print("\n grup: " + grups[c]);    
                  cont_h_grup = 0;
                  
                    for ( int p = 0; p < matriu.length; p++)
                    {
                        if (grups[c].equals(matriu [p]))
                        {
                            System.out.print("\n grup: " + matriu [p] + 
                                    " profe: " + matriu [(p+1)] +
                                    " mater: " + matriu [(p+2)] +
                                    " aula: " + matriu [(p+3)] +
                                    " dia: " + matriu [(p+4)] +
                                    " hora: " + matriu [(p+5)] );
                            
                            cont_h_grup ++; // per veure el total de classe per al grup
                            //String enteroString = "5";
                            enteroDIA = Integer.parseInt((matriu [(p+4)]));
                            enteroHORA = Integer.parseInt((matriu [(p+5)]));
                            System.out.print("\n | " + enteroDIA + " | " + enteroHORA);
                            SuperMatriu[c][enteroDIA-1][enteroHORA-1] = 'X'; // Marco que està ocupat
                        }
                    }
                    System.out.print("\n Total hores setmanals del grup: " + cont_h_grup);
                }
                
                // Ara imprimim els horaris de grups
                 
                for ( int j = 0; j < grups.length; j++)
                {
                   System.out.print("\n horari de grup: " + grups[j]);
                   System.out.print(" \n ========================================= ");
                    for ( int m = 0; m < HORA; m++)
                    {
                        System.out.print("\n");
                        for ( int k = 0; k < DIES; k++)
                        {
                            System.out.print(SuperMatriu[j][k][m]);
                        }
                    }
                   
                } 
                 
                // fem una prova per mostrar  classes per grups i canviar la SuperMatriuProfes
                for ( int c = 0; c < profes.length; c++)
                {
                  System.out.print("\n profe: " + profes[c]);    
                  cont_h_profe = 0;
                  
                    for ( int p = 0; p < matriu.length; p++)
                    {
                        if (profes[c].equals(matriu [p]))
                        {
                            System.out.print("\n grup: " + matriu [p] + 
                                    " profe: " + matriu [(p)] +
                                    " mater: " + matriu [(p+1)] +
                                    " aula: " + matriu [(p+2)] +
                                    " dia: " + matriu [(p+3)] +
                                    " hora: " + matriu [(p+4)] );
                            
                            cont_h_profe ++; // per veure el total de classe per al grup
                            //String enteroString = "5";
                            enteroDIA = Integer.parseInt((matriu [(p+3)]));
                            enteroHORA = Integer.parseInt((matriu [(p+4)]));
                            System.out.print("\n | " + enteroDIA + " | " + enteroHORA);
                            SuperMatriuProfes[c][enteroDIA-1][enteroHORA-1] = 'X'; // Marco que està ocupat
                        }
                    }
                    System.out.print("\n Total hores setmanals del profe: " + cont_h_profe);
                }
        
                // Ara imprimim els horaris de profes
                 
                for ( int j = 0; j < profes.length; j++)
                {
                   
                   System.out.print("\n horari de profe: " + profes[j]);
                   System.out.print(" \n ========================================= ");
                    for ( int m = 0; m < HORA; m++)
                    {
                        System.out.print("\n");
                        for ( int k = 0; k < DIES; k++)
                        {
                            System.out.print(SuperMatriuProfes[j][k][m]);
                        }
                    }
                   
                } 
                
            }
        });
        
        Button btn2 = new Button();
        btn2.setText("creo horari");
        
        FlowPane root = new FlowPane(10,10);
        
        root.getChildren().addAll(btn,btn2);
        root.setAlignment(Pos.CENTER);
        
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                //creo un hashmap o alguna cosa similar
                Map<String, Professor> map = new HashMap<>();
                //creo profes
                
                for ( int j = 0; j < profes.length; j++)
                {
                   
                   System.out.print("\n entro al hashmap el profe: " + profes[j]);
                   Professor profe1 = new Professor(profes[j]);
                   profe1.creahorariprofe();
                   map.put(profes[j],profe1);	
                   
                } 
                
                              
                System.out.println("\n Horariiiiii");
                // Imprimimos el Map con un Iterador
                Iterator it = map.keySet().iterator();
                Professor proferecollit;
                while(it.hasNext()){
                  String key = it.next().toString();
                  proferecollit = map.get(key);
                  System.out.println("Clau: " + key + " -> Valor: " + proferecollit.horari[0][0] + " amb possibles guàrdies: " + proferecollit.hores_guardia);
                  System.out.println("Forats: " + proferecollit.calculaforats());
                }
                /* 
                Professor proferecollit = map.get("TE1");
                int val = proferecollit.calculaforats();
                System.out.println("Professor: TE1  " + val);
                */
            }
        });
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Importo horaris a veure què...");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
