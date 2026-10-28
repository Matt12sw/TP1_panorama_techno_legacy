package fr.efrei.transfret;

import java.util.ArrayList;
import java.util.List;

public class SmsConsole implements CanalNotification {

    private static final int LONGUEUR_MAX_SMS = 160;
    private static final String SUITE = "...";

    private final List<String[]> journal = new ArrayList<>();

    @Override
    public void envoyer(Client client, String sujet, String message) {
        if (message.length() > LONGUEUR_MAX_SMS) {
            message = message.substring(0, LONGUEUR_MAX_SMS - SUITE.length()) + SUITE;
        }
        System.out.println("[SMS] -> " + client.getTelephone() + " : " + message);
        journal.add(new String[]{client.getTelephone(), message});
    }

    @Override
    public List<String[]> journal() {
        return journal;
    }
}
