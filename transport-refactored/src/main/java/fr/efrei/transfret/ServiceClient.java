package fr.efrei.transfret;

/**
 * Crée les clients validés.
 */
public class ServiceClient {

    private final ClientRepository clients;
    private final ValidateurClient validateur;
    private final CanalNotification canalEmail;

    public ServiceClient(ClientRepository clients, ValidateurClient validateur, CanalNotification canalEmail) {
        this.clients = clients;
        this.validateur = validateur;
        this.canalEmail = canalEmail;
    }

    public Client creerClient(String nom, String email, String telephone, String adresse, TypeClient type, String siret) {
        if (!validateur.estValide(nom, email, type, siret)) {
            return null;
        }
        Client c = new Client(nom, email, telephone, adresse, type, siret);
        clients.sauvegarder(c);
        canalEmail.envoyer(c, "Bienvenue chez TransFret", "Bonjour " + c.getNom() + ", votre compte " + c.getType().name().toLowerCase() + " a été créé.");
        return c;
    }
}
