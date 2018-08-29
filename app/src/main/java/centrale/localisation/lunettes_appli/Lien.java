package centrale.localisation.lunettes_appli;

/**
 * Created by Christopher on 01/04/2018.
 */


public class Lien {


    Noeud noeud1;
    Noeud noeud2;
    Boolean estBloque;

    public Lien(Noeud noeud1, Noeud noeud2) {
        this.noeud1 = noeud1;
        this.noeud2 = noeud2;
        this.estBloque = false;

        noeud1.addLien(this);
        noeud2.addLien(this);
    }

    public void bloque(){ this.estBloque = true;}


    public double getDistance(){
        return Calcul.gpsDistance(noeud1.coordonnées, noeud2.coordonnées);
    }

    public String toString(){
        return noeud1.nom+" <-> "+noeud2.nom+" | distance = "+getDistance();
    }

    public Noeud getOtherNoeud(Noeud noeud){
        if (noeud.equals(noeud1)) return noeud2;
        else if (noeud.equals(noeud2)) return noeud1;
        System.out.println("error lien");
        return null;
    }

}
