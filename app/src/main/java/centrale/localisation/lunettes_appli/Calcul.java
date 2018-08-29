package centrale.localisation.lunettes_appli;

/**
 * Created by Christopher on 01/04/2018.
 */
import java.util.Arrays;

public class Calcul {


    public static double distance(double [] position1, double[] position2){
        return Math.sqrt( (position1[0]-position2[0])*(position1[0]-position2[0])
                + (position1[1]-position2[1])*(position1[1]-position2[1]) );
    }

    public static double distance(Noeud noeud1, Noeud noeud2){
        return gpsDistance(noeud1.getCoordonnées(), noeud2.getCoordonnées());
    }

    public static double[] degToRad(double[] position){ return new double [] {(Math.PI * position[0])/180,(Math.PI * position[1])/180} ; }

    public static double gpsDistance(double[] position1, double[] position2){
        double [] position1conv = Calcul.degToRad(position1);
        double [] position2conv = Calcul.degToRad(position2);
        int R = 6371008;
        return R * (Math.PI/2 - Math.asin( Math.sin(position2conv[0]) * Math.sin(position1conv[0])
                + Math.cos(position2conv[1] - position1conv[1]) * Math.cos(position2conv[0]) * Math.cos(position1conv[0])));
        //résultat exprimé en mètre//
    }


}

