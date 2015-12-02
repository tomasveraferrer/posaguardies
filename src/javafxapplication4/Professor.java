/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication4;


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
                            //si es blanc i forat no iniciat i venim d'X --> obrim forat i comptem forat i venim d'X false
                                if (bb == '-' && this.foratiniciat == false && this.venimdix == true){
                                    this.foratiniciat = true;
                                    this.venimdix = false;
                                    this.temporal++;
                                    //System.out.print("\n logica: _01"); 
                                }else
                            //si es blanc i hi ha forat i no venim d'X --> es forat i comptem forat
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
                            //si hi ha classe no hi havia forat i no venim d'X --> venim d'X i Sí comptem                 
                                            if (bb != '-' && this.foratiniciat == false && this.venimdix == false){
                                                this.foratiniciat = false;
                                                this.venimdix = true;
                                                this.comptapermanencies++;
                                                //System.out.print("\n logica: X00"); 
                                            }else
                            //si hi ha classe no hi havia forat i sí venim d'X --> venim d'X i Sí comptem                   
                                                if (bb != '-' && this.foratiniciat == false && this.venimdix == true){
                                                   this.foratiniciat = false;
                                                   this.venimdix = true;
                                                   this.comptapermanencies++;
                                                   //System.out.print("\n logica: X01"); 
                                                }else
                            //si hi ha classe i hi havia forat i no venim d'X --> no comptem forat, venim d'X, incrementem comptador globali this.temporal a 0                       
                                                    if (bb != '-' && this.foratiniciat == true && this.venimdix == false){
                                                        this.foratiniciat = false;
                                                        this.venimdix = true;
                                                        this.comptapermanencies++;
                                                        this.comptapermanencies = this.comptapermanencies + this.temporal;
                                                        this.temporal = 0;
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
    
}
