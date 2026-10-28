package fr.efrei.transfret;

import java.util.EnumMap;
import java.util.Map;

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
        serviceExpedition = new ServiceExpedition(expeditions, new CalculateurPrix(tarifs(), remises()),
                new AffectationVehicule(Flotte.parDefaut()), new ValidateurExpedition(), new EstimateurDelai(),
                notificateur);
    }

    private static Map<ModeTransport, TarifTransport> tarifs() {
        Map<ModeTransport, TarifTransport> tarifs = new EnumMap<>(ModeTransport.class);
        tarifs.put(ModeTransport.ROUTE, new TarifRoute());
        tarifs.put(ModeTransport.RAIL, new TarifRail());
        tarifs.put(ModeTransport.MER, new TarifMer());
        tarifs.put(ModeTransport.AIR, new TarifAir());
        return tarifs;
    }

    private static Map<TypeClient, PolitiqueRemise> remises() {
        Map<TypeClient, PolitiqueRemise> remises = new EnumMap<>(TypeClient.class);
        remises.put(TypeClient.PARTICULIER, new SansRemise());
        remises.put(TypeClient.ENTREPRISE, new RemiseEntreprise());
        remises.put(TypeClient.PREMIUM, new RemisePremium());
        return remises;
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
