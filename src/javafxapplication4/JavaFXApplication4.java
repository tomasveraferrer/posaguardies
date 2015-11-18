/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
   char SuperMatriu [][][] = new char[grups.length][DIES][HORA];
    
    public String sensecometes(String ambcometes) {
        
        String sensecometes = "";
    //    if (ambcometes.length() > 0){
        sensecometes = ambcometes.replace("\"", "");
    //    }
        return sensecometes;
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
                
                // Poblem la SuperMatriu amb ' '
                for ( int j = 0; j < grups.length; j++)
                {
                                    
                    for ( int k = 0; k < DIES; k++)
                    {
                        for ( int m = 0; m < HORA; m++)
                        {
                            SuperMatriu[j][k][m] = ' ';
                        }
                    }
                   
                } 
                
                // fem una prova per mostrar  classes per grups i canviar la SuperMatriu d'acord
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
                
                // Ara imprimim els horaris
                // 
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
                 
            
        
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Importo csv");
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