package fr.efrei.transfret;

/**
 * Envoie les notifcations au client : email, plus SMS pour les clients premium.
 */
public class Notificateur {

    public void notifier(Client client, String sujet, String corps, String sms) {
        EmailService.envoyer(client.getEmail(), sujet, corps);
        if (client.getType() == TypeClient.PREMIUM) {
            new SmsService().envoyerSms(client.getTelephone(), sms);
        }
    }
}
