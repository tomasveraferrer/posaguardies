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
import static javafxapplication4.JavaFXApplication4.matriuGuardies;

/**
 *
 * @author TOMAS
 */
public class JavaFXApplication4 extends Application {

    public final int MAX_DADA = 10000;
    public static int DIES = 5;// variable estàtica per poder accedir des de fora de la classe
    public static int HORA = 20;// variable estàtica per poder accedir des de fora de la classe
    public static int HORES_GUARDIA_DIA = 9; // variable estàtica per poder accedir des de fora de la classe (pati inclós). Número d'hores de guardia per dia
    public int i = 0;
    public int cont_h_grup = 0;
    public int cont_h_profe = 0;
    public int enteroDIA = 0;
    public int enteroHORA = 0;
    public String valor;
    public String matriu[] = new String[MAX_DADA];
    public static String grups[] = {"E1A", "E1B", "E1C", "E1D",
        "E2A", "E2B", "E2C", "E2D",
        "E3A", "E3B", "E3C", "E3D",
        "E4A", "E4B", "E4C", "E4D",
        "B1A", "B1B", "B2A", "B2B",
        "CFA", "CFB", "CFC"};
    public static String profes[] = {"AN1", "AN2", "AN3", "AN4",
        "ES1", "ES2", "ES3", "ES4",
        "CA1", "CA2", "CA3", "CA4",
        "MA1", "MA2", "MA3", "MA4",
        "TE1", "TE2", "TE3", "TE4",
        "HI1", "HI2", "HI3", "HI4"};
    
             
    //creo la matriu de guàrdies
    public static int matriuGuardies[][][] = new int[DIES][HORA][];
        
       
    public char SuperMatriu[][][] = new char[grups.length][DIES][HORA];
    public static char SuperMatriuProfes[][][] = new char[profes.length][DIES][HORA];// variable estàtica per poder accedir des de fora de la classe

    // Funció per treure els símbols "
    public String sensecometes(String ambcometes) {

        String sensecometes = "";

        sensecometes = ambcometes.replace("\"", "");

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
                    while (scanner.hasNext()) {
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
                for (int j = 0; j < grups.length; j++) {

                    for (int k = 0; k < DIES; k++) {
                        for (int m = 0; m < HORA; m++) {
                            SuperMatriu[j][k][m] = '-';
                        }
                    }

                }

                // Poblem la SuperMatriuProfes amb '-'
                for (int j = 0; j < grups.length; j++) {

                    for (int k = 0; k < DIES; k++) {
                        for (int m = 0; m < HORA; m++) {
                            SuperMatriuProfes[j][k][m] = '-';
                        }
                    }

                }

                // fem una prova per mostrar  classes per grups i canviar la SuperMatriu
                for (int c = 0; c < grups.length; c++) {
                    System.out.print("\n grup: " + grups[c]);
                    cont_h_grup = 0;

                    for (int p = 0; p < matriu.length; p++) {
                        if (grups[c].equals(matriu[p])) {
                            System.out.print("\n grup: " + matriu[p]
                                    + " profe: " + matriu[(p + 1)]
                                    + " mater: " + matriu[(p + 2)]
                                    + " aula: " + matriu[(p + 3)]
                                    + " dia: " + matriu[(p + 4)]
                                    + " hora: " + matriu[(p + 5)]);

                            cont_h_grup++; // per veure el total de classe per al grup
                            //String enteroString = "5";
                            enteroDIA = Integer.parseInt((matriu[(p + 4)]));
                            enteroHORA = Integer.parseInt((matriu[(p + 5)]));
                            System.out.print("\n | " + enteroDIA + " | " + enteroHORA);
                            
                            //si és abans del pati no fem res, si és després afegim l'hora del pati
                            if (enteroHORA - 1 < 3){
                            SuperMatriu[c][enteroDIA - 1][enteroHORA - 1] = 'X'; // Marco que està ocupat
                            }else{
                                SuperMatriu[c][enteroDIA - 1][enteroHORA] = 'X';
                            }
                        }
                    }
                    System.out.print("\n Total hores setmanals del grup: " + cont_h_grup);
                }

                // Ara imprimim els horaris de grups
                for (int j = 0; j < grups.length; j++) {
                    System.out.print("\n horari de grup: " + grups[j]);
                    System.out.print(" \n ========================================= ");
                    for (int m = 0; m < HORA; m++) {
                        System.out.print("\n");
                        for (int k = 0; k < DIES; k++) {
                            System.out.print(SuperMatriu[j][k][m]);
                        }
                    }

                }

                // fem una prova per mostrar  classes per grups i canviar la SuperMatriuProfes
                for (int c = 0; c < profes.length; c++) {
                    System.out.print("\n profe: " + profes[c]);
                    cont_h_profe = 0;

                    for (int p = 0; p < matriu.length; p++) {
                        if (profes[c].equals(matriu[p])) {
                            System.out.print("\n grup: " + matriu[p]
                                    + " profe: " + matriu[(p)]
                                    + " mater: " + matriu[(p + 1)]
                                    + " aula: " + matriu[(p + 2)]
                                    + " dia: " + matriu[(p + 3)]
                                    + " hora: " + matriu[(p + 4)]);

                            cont_h_profe++; // per veure el total de classe per al grup
                            //String enteroString = "5";
                            enteroDIA = Integer.parseInt((matriu[(p + 3)]));
                            enteroHORA = Integer.parseInt((matriu[(p + 4)]));
                            System.out.print("\n | " + enteroDIA + " | " + enteroHORA);
                            SuperMatriuProfes[c][enteroDIA - 1][enteroHORA - 1] = 'X'; // Marco que està ocupat
                        }
                    }
                    System.out.print("\n Total hores setmanals del profe: " + cont_h_profe);
                }

                // Ara imprimim els horaris de profes
                for (int j = 0; j < profes.length; j++) {

                    System.out.print("\n horari de profe: " + profes[j]);
                    System.out.print(" \n ========================================= ");
                    for (int m = 0; m < HORA; m++) {
                        System.out.print("\n");
                        for (int k = 0; k < DIES; k++) {
                            System.out.print(SuperMatriuProfes[j][k][m]);
                        }
                    }

                }
                
                //Inicialitzo la matriu de guardies amb els possibles nombres de guardians
                //posem 4 profes de guàrdia a totes les hores per defecte
                for ( int m = 0; m < DIES; m++){
                    for ( int r = 0; r < HORES_GUARDIA_DIA; r++){
                        matriuGuardies[m][r] = new int[4];
                    }
                }
                
                //posem 3 profes de guàrdia a 1a hora
                matriuGuardies[0][0] = new int[3];
                matriuGuardies[1][0] = new int[3];
                matriuGuardies[2][0] = new int[3];
                matriuGuardies[3][0] = new int[3];
                matriuGuardies[4][0] = new int[3];
                
                //posem 6 profes de guàrdia a l'hora del pati
                matriuGuardies[0][3] = new int[6];
                matriuGuardies[1][3] = new int[6];
                matriuGuardies[2][3] = new int[6];
                matriuGuardies[3][3] = new int[6];
                matriuGuardies[4][3] = new int[6];
                
            }
        });

        Button btn2 = new Button();
        btn2.setText("creo horari");

        FlowPane root = new FlowPane(10, 10);

        root.getChildren().addAll(btn, btn2);
        root.setAlignment(Pos.CENTER);

        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                //creo un hashmap o alguna cosa similar
                Map<String, Professor> map = new HashMap<>();
                //creo profes

                for (int j = 0; j < profes.length; j++) {

                    System.out.print("\n entro al hashmap el profe: " + profes[j]);
                    Professor profe1 = new Professor(profes[j]);
                    profe1.creahorariprofe();
                    map.put(profes[j], profe1);

                }

                System.out.println("\n Horariiiiii");
                // Imprimimos el Map con un Iterador
                Iterator it = map.keySet().iterator();
                Professor proferecollit;
                while (it.hasNext()) {
                    String key = it.next().toString();
                    proferecollit = map.get(key);
                    System.out.println("Clau: " + key + " -> Valor: " + proferecollit.horari[0][0] + " amb possibles guàrdies: " + proferecollit.hores_guardia);
                    System.out.println("Forats: " + proferecollit.calculaforats());
                    System.out.println("Permanències: " + proferecollit.calculapermanencies());
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
