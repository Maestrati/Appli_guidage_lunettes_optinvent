package centrale.localisation.lunettes_appli;

/**
 * Created by Christopher on 01/04/2018.
 */

import java.util.ArrayList;


public class Graphe {

    Noeud startNoeud;
    Noeud endNoeud;

    ArrayList <Noeud> graphList;
    public static Graphe dernierGraphe;

    public static ArrayList <Noeud> getListNoeud(Graphe Graphe) {
        return(Graphe.graphList);
    }


    public Graphe(){
        this.generateGraph();
        dernierGraphe =  this;
    }

    public Noeud getNoeud( Lieu lieu ) {
        for (Noeud noeud : graphList) {
            if( noeud.nom.equalsIgnoreCase( lieu.toString() ) )
                return noeud;
        }
        return null;
    }

    public void generateGraph(){


        graphList = new ArrayList <Noeud>();

        graphList.add(new  Noeud(Lieu.S1, new double []{50.607759, 3.133428}));
        graphList.add(new  Noeud(Lieu.S2, new double []{50.607660, 3.133386}));
        graphList.add(new  Noeud(Lieu.S3, new double []{50.607572, 3.133305}));
        graphList.add(new  Noeud(Lieu.S4, new double []{50.607671, 3.133246}));
        graphList.add(new  Noeud(Lieu.S5, new double []{50.607677, 3.133155}));
        graphList.add(new  Noeud(Lieu.S6, new double []{50.607732, 3.133137}));
        graphList.add(new  Noeud(Lieu.S7, new double []{50.607724, 3.133251}));
        graphList.add(new  Noeud(Lieu.S8, new double []{50.607764, 3.133222}));
        graphList.add(new  Noeud(Lieu.S9, new double []{50.607732, 3.133151}));

        Noeud.createLien(getNoeudByName(Lieu.S1), getNoeudByName(Lieu.S2));
        Noeud.createLien(getNoeudByName(Lieu.S2), getNoeudByName(Lieu.S3));
        Noeud.createLien(getNoeudByName(Lieu.S3), getNoeudByName(Lieu.S6));
        Noeud.createLien(getNoeudByName(Lieu.S6), getNoeudByName(Lieu.S9));
        Noeud.createLien(getNoeudByName(Lieu.S6), getNoeudByName(Lieu.S5));
        Noeud.createLien(getNoeudByName(Lieu.S5), getNoeudByName(Lieu.S8));
        Noeud.createLien(getNoeudByName(Lieu.S8), getNoeudByName(Lieu.S7));
        Noeud.createLien(getNoeudByName(Lieu.S7), getNoeudByName(Lieu.S4));
        Noeud.createLien(getNoeudByName(Lieu.S4), getNoeudByName(Lieu.S5));
        Noeud.createLien(getNoeudByName(Lieu.S4), getNoeudByName(Lieu.S1));

    }

    public void generateGraph2(){


        graphList = new ArrayList <Noeud>();

        graphList.add(new  Noeud(Lieu.Salle1, new double []{50.607759, 3.133428}));
        graphList.add(new  Noeud(Lieu.Salle2, new double []{50.607660, 3.133386}));
        graphList.add(new  Noeud(Lieu.Salle3, new double []{50.607572, 3.133305}));
        graphList.add(new  Noeud(Lieu.Ascenseur, new double []{50.607671, 3.133246}));
        graphList.add(new  Noeud(Lieu.Porte, new double []{50.607677, 3.133155}));
        graphList.add(new  Noeud(Lieu.WC, new double []{50.607732, 3.133251}));

        Noeud.createLien(getNoeudByName(Lieu.Salle1), getNoeudByName(Lieu.Porte));
        Noeud.createLien(getNoeudByName(Lieu.WC), getNoeudByName(Lieu.Porte));
        Noeud.createLien(getNoeudByName(Lieu.Porte), getNoeudByName(Lieu.Ascenseur));
        Noeud.createLien(getNoeudByName(Lieu.Ascenseur), getNoeudByName(Lieu.Salle2));
        Noeud.createLien(getNoeudByName(Lieu.Ascenseur), getNoeudByName(Lieu.Salle3));
        Noeud.createLien(getNoeudByName(Lieu.WC), getNoeudByName(Lieu.Salle3));

    }

    public Noeud getNoeudByName(String name){
        for (int i=0; i<graphList.size(); i++){
            if (graphList.get(i).getNom().equals(name)) return graphList.get(i);
        }
        System.out.println("ERREUR GetByName : "+name+" ! ");
        return null;
    }

    public Noeud getNoeudByName(Lieu lieu){	return getNoeudByName(lieu.toString());	}

    public Lien getLienTo(Noeud noeud1, Noeud noeud2){
        return noeud1.getLienTo(noeud2);
    }
    public Lien getLienTo(Lieu lieu1, Lieu lieu2){ return getLienTo(this.getNoeudByName(lieu1), this.getNoeudByName(lieu2));}


    public String toString(){
        String string="";
        for (int i=0; i<graphList.size(); i++)
            string+=graphList.get(i).toString()+"\n";
        return string;
    }

    public void visitLightestPoidsVoisin() {
        if(this.getLightestPoidsNonVisited()!=null) {
            this.getLightestPoidsNonVisited().visitNoeud();
        }
        else {
            System.out.println("PLUS DE CHEMIN DISPONIBLE !!");
            System.exit(1);}
    }

    public Noeud getLightestPoidsNonVisited() {
        Noeud bestNoeud = null;
        double poidsMin = -1;
        for (int i=0; i < graphList.size(); i++) {
            Noeud tempNoeud = graphList.get(i);
            if (!tempNoeud.estVisite && tempNoeud.poids != -1  && (tempNoeud.poids < poidsMin || poidsMin == -1)){
                bestNoeud = tempNoeud;
            }
        }

        return bestNoeud;
    }
    public ArrayList<Noeud> getShortestChemin(Lieu startLieu , Lieu endLieu){
        return getShortestChemin(getNoeudByName(startLieu),getNoeudByName(endLieu));
    }

    public void initPoids(){
        for (int i=0; i<this.graphList.size(); i++){
            graphList.get(i).initNoeud();
        }
    }

    public ArrayList<Noeud> getShortestChemin(Noeud startNoeud, Noeud endNoeud){
        initPoids();
        startNoeud.visitNoeud();
        startNoeud.setPoids(0);

        while (!endNoeud.estVisite){
            this.visitLightestPoidsVoisin();

        }
        return(getCheminToNoeud(startNoeud , endNoeud));

    }

    public ArrayList<Noeud> getCheminToNoeud(Noeud startNoeud , Noeud endNoeud){
        Noeud tempNoeud = endNoeud;
        ArrayList<Noeud> tempChemin = new ArrayList<Noeud>();
        ArrayList<Noeud> chemin = new ArrayList<Noeud>();
        while (tempNoeud != startNoeud){
            tempChemin.add(tempNoeud);
            tempNoeud = tempNoeud.previousNoeud;
        }
        tempChemin.add(startNoeud);

        int size = tempChemin.size();
        for (int i=0; i< size ; i++){
            chemin.add(tempChemin.get(size-i-1));
        }

        return chemin;
    }

    public static void removeLienTo(Noeud noeud1, Noeud noeud2){
        noeud1.removeLienTo(noeud2);
        noeud2.removeLienTo(noeud1);
    }

    /*public ArrayList<Noeud> parcoursChemin(Lieu startLieu , Lieu endLieu){
        Noeud endNoeud = this.getNoeudByName(endLieu);

        ArrayList<Noeud> victor = new ArrayList<Noeud>();
        ArrayList<Noeud> chemin = getShortestChemin(startLieu, endLieu);
        victor.add(chemin.get(0));
        int i = 0 ;
        while ( i<chemin.size()-1){

            int dialogButtonValue = JOptionPane.showConfirmDialog(null, "Êtes vous à : "+chemin.get(i+1).nom+" (Origine : "+chemin.get(i).nom+")", "WARNING", JOptionPane.YES_NO_OPTION);
            if (dialogButtonValue == JOptionPane.YES_OPTION) {
                if (chemin.get(i).getLienTo(chemin.get(i+1)).estBloque){
                    Graphe.removeLienTo(chemin.get(i),chemin.get(i+1));
                    chemin = getShortestChemin(chemin.get(i), endNoeud);
                    i=0;
                }
                else {
                    i++;victor.add(chemin.get(i));
                }
            }
            else {
                int dialogButton2 = JOptionPane.YES_NO_OPTION;
                JOptionPane.showConfirmDialog(null, "Le chemin était-il bloqué ?", "WARNING", dialogButton2);
                if (dialogButton2 == JOptionPane.YES_OPTION) {
                    this.getLienTo(chemin.get(i), chemin.get(i+1)).bloque();
                    Graphe.removeLienTo(chemin.get(i),chemin.get(i+1));
                }

                String positionnement = JOptionPane.showInputDialog(null, "Ou êtes-vous ? ");
                chemin = getShortestChemin(this.getNoeudByName(positionnement), endNoeud);
                i=0;

            }
            System.out.println("Vous êtes à : "+chemin.get(i));
        }
        return victor;


    }*/

    public ArrayList<Noeud> getGraphList() {
        return graphList;
    }

    public static void printParkour(ArrayList <Noeud> listNoeuds){
        System.out.println("RESUME :");
        System.out.println("");
        for(int i=0; i<listNoeuds.size()-1; i++){
            System.out.println(i+" : "+listNoeuds.get(i).nom+" -> "+listNoeuds.get(i+1).nom);
        }
        System.out.println("");
        System.out.println("********************************************");
        System.out.println("**    VOUS ETES ARRIVES A DESTINATION     **");
        System.out.println("              ->  "+listNoeuds.get(listNoeuds.size()-1).nom+"  <-");
        System.out.println("********************************************");
    }


}
