package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class EmailConsole implements CanalNotification {

    private static final String SMTP_HOST = "smtp.transfret.fr";
    private static final String EXPEDITEUR = "noreply@transfret.fr";

    private final List<String[]> journal = new ArrayList<>();

    @Override
    public void envoyer(Client client, String sujet, String corps) {
        String destinataire = client.getEmail();
        if (destinataire == null || !destinataire.contains("@")) {
            return;
        }
        System.out.println("[EMAIL via " + SMTP_HOST + "] " + EXPEDITEUR + " -> " + destinataire + " | " + sujet);
        System.out.println("        " + corps);
        journal.add(new String[]{destinataire, sujet, corps});
    }

    @Override
    public List<String[]> journal() {
        return journal;
    }
}
