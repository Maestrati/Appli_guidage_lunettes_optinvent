package centrale.localisation.lunettes_appli;

/**
 * Created by Christopher on 01/04/2018.
 */

import android.location.Location;

import java.util.ArrayList;
import java.util.Iterator;

public class Noeud {

    String nom;
    Boolean accessible;
    double [] coordonnées;

    ArrayList <Lien> listeLiens;

    Noeud previousNoeud;
    double poids;
    boolean estVisite;

    public Noeud(String nom, double[] coordonnées) {
        initNoeud();
        this.nom = nom;
        this.accessible = true;
        this.coordonnées = coordonnées;
        this.listeLiens = new ArrayList<Lien> ();
    }

    public Noeud(Lieu lieu, double[] coordonnées) {
        initNoeud();
        this.nom = lieu.toString();
        this.accessible = true;
        this.coordonnées = coordonnées;
        this.listeLiens = new ArrayList <Lien> ();
    }


    public Noeud(String nom, Boolean accessible, double[] coordonnées, ArrayList<Lien> listeLiens) {
        this.nom = nom;
        this.accessible = accessible;
        this.coordonnées = coordonnées;
        this.listeLiens = listeLiens;
    }
    public static void createLien(Noeud noeud1, Noeud noeud2){
        new Lien(noeud1, noeud2);
    }
    public void initNoeud(){
        this.poids = -1;
        this.estVisite = false;
    }

    public double[] getCoordonnées(){ return this.coordonnées; }
    public String getNom(){ return this.nom;}

    public void addLien(Lien lien){ this.listeLiens.add(lien); }

    public double getDistance(Noeud noeud){
        return Calcul.distance(noeud, this);
    }


    public Lien getLienTo(Noeud noeud) {
        for (int i = 0; i < this.listeLiens.size(); i++) {
            if (listeLiens.get(i).noeud1.equals(noeud) && listeLiens.get(i).noeud2.equals(this)
                    || listeLiens.get(i).noeud1.equals(this) && listeLiens.get(i).noeud2.equals(noeud))
                return listeLiens.get(i);
        }
        System.out.println("GET LIEN TO ERROR");
        return null;
    }




    public void removeLien(Lien lien){ this.listeLiens.remove(lien);}

    public void removeLienTo(Noeud noeud){

        int i=0;
        while(i<listeLiens.size())
            if(listeLiens.get(i).noeud1.equals(this) && listeLiens.get(i).noeud2.equals(noeud) ||
                    listeLiens.get(i).noeud1.equals(noeud) && listeLiens.get(i).noeud2.equals(this))
                listeLiens.remove(i);
            else i++;

    }



    public void miseAJourVoisin() {
        for (int i = 0; i < listeLiens.size(); i++) {
            Noeud voisin = this.listeLiens.get(i).getOtherNoeud(this);
            if (!voisin.estVisite) {
                double poidsPotentiel = this.poids + this.getDistance(voisin);
                if (poidsPotentiel < voisin.poids || voisin.poids == -1) {
                    voisin.setPoids(poidsPotentiel);
                    voisin.setPreviousNode(this);
                }
            }
        }
    }


    public void setPreviousNode(Noeud noeud) {
        this.previousNoeud = noeud;
    }

    public void visitNoeud(){
        this.estVisite = true;
        this.miseAJourVoisin();
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public Location toLocation() {
        Location location = new Location("");
        location.setLongitude(coordonnées[0]);
        location.setLatitude(coordonnées[1]);
        return location;
    }







    public String toString(){
        String msg = this.nom+" : \n";

        for (int i=0; i<listeLiens.size(); i++)
            msg += "lien "+i+" : "+listeLiens.get(i).toString()+"\n";
        return msg+"\n---------- \n";
    }

}
