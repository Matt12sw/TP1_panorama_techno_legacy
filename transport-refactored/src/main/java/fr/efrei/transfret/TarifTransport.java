package fr.efrei.transfret;

/**
 * Strategy : tarif de base d'un mode de transport.
 */
public interface TarifTransport {

    double calculer(int distance, double poids, double volume);
}
