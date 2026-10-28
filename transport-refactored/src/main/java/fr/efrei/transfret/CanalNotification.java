package fr.efrei.transfret;

import java.util.List;

public interface CanalNotification {

    void envoyer(Client client, String sujet, String message);

    /** Destinataire et sujet (ou message pour le SMS) de chaque envoi, dans l'ordre. */
    List<String[]> journal();
}
