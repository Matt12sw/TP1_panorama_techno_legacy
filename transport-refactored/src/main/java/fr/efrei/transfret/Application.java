package fr.efrei.transfret;

/**
 * Construit le graphe d'objets de TransFret (seul endroit qui fait des {@code new} de services).
 */
public class Application {

    private final ClientRepository clients = new ClientRepositoryMemoire();
    private final ExpeditionRepository expeditions = new ExpeditionRepositoryMemoire();
    private final CanalNotification email = new EmailConsole();
    private final CanalNotification sms = new SmsConsole();
    private final GenerateurFacture generateurFacture = new GenerateurFacture();
    private final ServiceClient serviceClient;
    private final ServiceExpedition serviceExpedition;

    public Application() {
        Notificateur notificateur = new Notificateur(email, sms);
        serviceClient = new ServiceClient(clients, new ValidateurClient(), email);
        serviceExpedition = new ServiceExpedition(expeditions, new CalculateurPrix(),
                new AffectationVehicule(Flotte.parDefaut()), new ValidateurExpedition(), new EstimateurDelai(),
                notificateur);
    }

    public ClientRepository clients() {
        return clients;
    }

    public ExpeditionRepository expeditions() {
        return expeditions;
    }

    public CanalNotification email() {
        return email;
    }

    public CanalNotification sms() {
        return sms;
    }

    public GenerateurFacture generateurFacture() {
        return generateurFacture;
    }

    public ServiceClient serviceClient() {
        return serviceClient;
    }

    public ServiceExpedition serviceExpedition() {
        return serviceExpedition;
    }
}
