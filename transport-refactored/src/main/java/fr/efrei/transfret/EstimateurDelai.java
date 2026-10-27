package fr.efrei.transfret;

/**
 * Calcule le délai de livraison en jours.
 */
public class EstimateurDelai {

    private static final double VITESSE_ROUTE_KMH = 70.0;
    private static final int HEURES_CONDUITE_JOUR = 9;
    private static final int HEURES_CONDUITE_JOUR_URGENT = 18;
    private static final double KM_PAR_JOUR_RAIL = 1440.0;
    private static final double KM_PAR_JOUR_MER = 720.0;
    private static final int JOURS_MANUTENTION_RAIL = 1;
    private static final int JOURS_MANUTENTION_MER = 2;
    private static final int SEUIL_LONG_COURRIER_KM = 3000;
    private static final int DELAI_AIR_JOURS = 1;
    private static final int DELAI_AIR_LONG_COURRIER_JOURS = 2;
    private static final int DELAI_MINIMUM_JOURS = 1;

    public int calculerDelai(int distance, ModeTransport mode, boolean urgent) {
        int jours = 0;
        switch (mode) {
            case ROUTE:
                double heures = distance / VITESSE_ROUTE_KMH;
                if (urgent) {
                    jours = (int) Math.ceil(heures / HEURES_CONDUITE_JOUR_URGENT);
                } else {
                    jours = (int) Math.ceil(heures / HEURES_CONDUITE_JOUR);
                }
                break;
            case RAIL:
                jours = (int) Math.ceil(distance / KM_PAR_JOUR_RAIL) + JOURS_MANUTENTION_RAIL;
                if (urgent) jours = jours - 1;
                break;
            case MER:
                jours = (int) Math.ceil(distance / KM_PAR_JOUR_MER) + JOURS_MANUTENTION_MER;
                break;
            case AIR:
                jours = DELAI_AIR_JOURS;
                if (distance > SEUIL_LONG_COURRIER_KM) jours = DELAI_AIR_LONG_COURRIER_JOURS;
                if (urgent) jours = DELAI_AIR_JOURS;
                break;
        }
        if (jours < DELAI_MINIMUM_JOURS) jours = DELAI_MINIMUM_JOURS;
        return jours;
    }
}
