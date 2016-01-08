/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 *
 * @author TOMAS
 */
public class Professor {
    
        //variables de classe
        int hores_guardia = 4;
        int hores_lectives = 20;
        String codi = "";
        String nom = "";
        String cognom1 = "";
        String cognom2 = "";
        String departament = "";
        int DIES = JavaFXApplication4.DIES;
        int HORA = JavaFXApplication4.HORA;
        char horari[][] = new char[DIES][HORA];
        
        int comptapermanencies = 0;
        int comptaforats = 0;
        int temp_forats = 0;
        int temporal = 0;
        int temporal2 = 0;
        int terminals [][] = new int[DIES][2]; // per cada dia, posició 0 és 1a hora i 1, última hora de permanència
        boolean foratiniciat = false;
        boolean venimdix = false; 
        
        Map<String, String> llista_forats = new HashMap<>();
        
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
        
            for ( int j = 0; j < JavaFXApplication4.profes.length; j++)
                {
                  if (JavaFXApplication4.profes[j].equals(this.codi)){ 
                   
                    for ( int m = 0; m < HORA; m++)
                    {
                        
                        for ( int k = 0; k < DIES; k++)
                        {
                            this.horari[k][m] = JavaFXApplication4.SuperMatriuProfes[j][k][m];
                        }
                    }
                  } 
                } 
            
        return true;
        }
        
        //Omple els forats
        public Boolean ompleforats() {
            
            String[] parts;
            String part1;
            String part2;
            String part3;
            int dia;
            int hora;
            int nombreguardies = this.hores_guardia;
            
            Iterator ite = llista_forats.keySet().iterator();
                String dadallarga;
                while (ite.hasNext() && nombreguardies > 0) {
                    String key = ite.next().toString();
                    dadallarga = llista_forats.get(key);
                    System.out.println("Clau: " + key + " -> Valor: " + dadallarga);
                    
                    parts = dadallarga.split(":");
                    part1 = parts[0]; // codi professor
                    part2 = parts[1]; // dia 1 = dilluns
                    part3 = parts[2]; // hora 1 = 8a9
                    dia = Integer.parseInt(part2);
                    hora = Integer.parseInt(part3);
                    //canviem i restem 1 per adaptar-nos a la matriu horari si no és pati (ara és 4)
                    if (hora != 4){
                        dia--;
                        hora--;
                        this.horari[dia][hora]= 'G';
                        nombreguardies--; 
                    }
                    
                    
                }
        return true;    
        }
        
        //Calculem els forats del professor l'horari ha d'estar creat prèviament
        public int calculaforats() {
          
            this.temporal2 = 0;
            char bb;
            
            for ( int k = 0; k < DIES; k++)
                    {

                        this.temporal = 0;
                        this.foratiniciat = false;
                        this.venimdix = false;
                        boolean hempassatperpati= false;
                        
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
                            //si es blanc i forat no iniciat i venim d'X --> obrim forat i comptem (si no es pati) i venim d'X false
                                if (bb == '-' && this.foratiniciat == false && this.venimdix == true){
                                    this.foratiniciat = true;
                                    this.venimdix = false;
                                    if (m != 3){
                                    this.temporal++; 
                                    }                                       
                                    //System.out.print("\n logica: _01"); 
                                }else
                            //si es blanc i hi ha forat i no venim d'X --> es forat i el comptem
                                    if (bb == '-' && this.foratiniciat == true && this.venimdix == false){
                                        this.foratiniciat = true;
                                        this.venimdix = false;
                                        if (m != 3){
                                        this.temporal++;
                                        }else{
                                            hempassatperpati= true; //marquem que estem en un forat per evitar comptar més endavant
                                        }
                                        //System.out.print("\n logica: _10"); 
                                    }else
                            //si es blanc i estem en forat i venim d'X --> venim d'X false i omptem forat         
                                        if (bb == '-' && this.foratiniciat == true && this.venimdix == true){
                                            this.foratiniciat = true;
                                            this.venimdix = false;
                                            if (m != 3){
                                            this.temporal++; 
                                            } 
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
                                                        
                                                        if (hempassatperpati){
                                                            this.temporal++;
                                                        }
                                                        
                                                        if (this.temporal > 0){
                                                                    for (int g = 0; g <this.temporal; g++ ){
                                                                        String dada0 = Integer.toString(this.temporal2 + g);
                                                                        String dada1 = Integer.toString(k+1); 
                                                                        String dada2 = Integer.toString(m-g);
                                                                        llista_forats.put(dada0, this.codi+":"+dada1+":"+dada2);
                                                                    }
                                                                }
                                                        this.temporal2 = this.temporal2 + this.temporal; 
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
        
        //Calculem les permanències del professor l'horari ha d'estar creat prèviament
        public int calculapermanencies() {
          
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
                            //si es blanc i forat no iniciat i venim d'X --> obrim forat i comptem forat (si no es pati) i venim d'X false
                                if (bb == '-' && this.foratiniciat == false && this.venimdix == true){
                                    this.foratiniciat = true;
                                    this.venimdix = false;
                                    if (m != 3){
                                    this.temporal++; 
                                    } 
                                    //System.out.print("\n logica: _01"); 
                                }else
                            //si es blanc i hi ha forat i no venim d'X --> es forat i comptem forat
                                    if (bb == '-' && this.foratiniciat == true && this.venimdix == false){
                                        this.foratiniciat = true;
                                        this.venimdix = false;
                                        if (m != 3){
                                        this.temporal++; 
                                        } 
                                        //System.out.print("\n logica: _10"); 
                                    }else
                            //si es blanc i estem en forat i venim d'X --> venim d'X false i omptem forat         
                                        if (bb == '-' && this.foratiniciat == true && this.venimdix == true){
                                            this.foratiniciat = true;
                                            this.venimdix = false;
                                            if (m != 3){
                                            this.temporal++; 
                                            } 
                                            //System.out.print("\n logica: _11"); 
                                        }else
                            //si hi ha classe no hi havia forat i no venim d'X --> venim d'X i Sí comptem                 
                                            if (bb != '-' && this.foratiniciat == false && this.venimdix == false){
                                                this.foratiniciat = false;
                                                this.venimdix = true;
                                                this.comptapermanencies++;
                                                //com que és la primera és la primera hora terminal del dia i última per si de cas
                                                this.terminals[k][0]= m; //1a hora terminal
                                                this.terminals[k][1]= m; //última hora terminal
                                                //System.out.print("\n logica: X00"); 
                                            }else
                            //si hi ha classe no hi havia forat i sí venim d'X --> venim d'X i Sí comptem                   
                                                if (bb != '-' && this.foratiniciat == false && this.venimdix == true){
                                                   this.foratiniciat = false;
                                                   this.venimdix = true;
                                                   this.comptapermanencies++;
                                                   this.terminals[k][1]= m; //última hora terminal
                                                   //System.out.print("\n logica: X01"); 
                                                }else
                            //si hi ha classe i hi havia forat i no venim d'X --> no comptem forat, venim d'X, incrementem comptador globali this.temporal a 0                       
                                                    if (bb != '-' && this.foratiniciat == true && this.venimdix == false){
                                                        this.foratiniciat = false;
                                                        this.venimdix = true;
                                                        this.comptapermanencies++;
                                                        this.comptapermanencies = this.comptapermanencies + this.temporal;
                                                        this.temporal = 0;
                                                        this.terminals[k][1]= m; //última hora terminal
                                                        //System.out.print("\n logica: X10"); 
                                                    }else{
                            //si hi ha classe i hi ha havia forat i venim d'X --> cas extrany i no fem res                           
                                                        //System.out.print("\n logica: X11"); 
                                                    }
                      //System.out.print("\n trec el comptador: " + this.comptapermanencies +" dades despres: "+" | "+bb+" | "+this.foratiniciat+" | "+this.venimdix+"\n");    
                        }
                    }
            
        return this.comptapermanencies;
        }
        
        public void imprimeix_horari() {
            System.out.print(" \n ================ " + this.codi);
                    for (int m = 0; m < HORA; m++) {
                        System.out.print("\n");
                        for (int k = 0; k < DIES; k++) {
                            System.out.print(this.horari[k][m]);
                        }
                    }
            System.out.print(" \n ================ FORATS TEMPORAALS: "+ this.temp_forats +" \n");
            System.out.print(" \n ================ COMPTAFORATS ABANS: "+ this.comptaforats +" \n");
            System.out.print(" \n ================ HORES GUARDIA: "+ this.hores_guardia +" \n");
        }
        public void imprimeix_llista_forats(){
            Iterator ite = this.llista_forats.keySet().iterator();
                String horarecollit;
                while (ite.hasNext()) {
                    String key1 = ite.next().toString();
                    horarecollit = llista_forats.get(key1);
                    System.out.println("\n Clau: " + key1 + " -> Valor(profe:dia:hora): " + horarecollit);
                    
                }
        }
        
        public void imprimeix_hores_terminals() {
            System.out.print(" \n ================ " + this.codi);
                    for (int m = 0; m < 2; m++) {
                        System.out.print("\n");
                        for (int k = 0; k < DIES; k++) {
                            System.out.print(this.terminals[k][m]);
                        }
                    }
            System.out.print(" \n ================ \n");
            
        }
}
