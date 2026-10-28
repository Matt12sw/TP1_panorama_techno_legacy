package fr.efrei.transfret;

/**
 * Envoie les notifcations au client : email, plus SMS pour les clients premium.
 */
public class Notificateur {

    private final CanalNotification email;
    private final CanalNotification sms;

    public Notificateur(CanalNotification email, CanalNotification sms) {
        this.email = email;
        this.sms = sms;
    }

    public void notifier(Client client, String sujet, String corps, String messageSms) {
        email.envoyer(client, sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            sms.envoyer(client, sujet, messageSms);
        }
    }
}
