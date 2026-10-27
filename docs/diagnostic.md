# Étape 1 – Diagnostic

## Rôle et dépendances des classes

| Classe | Rôle | Dépend de |
|---|---|---|
| `ServiceClient` | valide un client, l'enregistre, envoie l'email de bienvenue | `Client`, `Database`, `EmailService` |
| `ServiceExpedition` | validation, affectation véhicule, prix, délais, cycle de vie, notifications | `Database`, `CalculateurPrix`, `AffectationVehicule`, `Utils`, `EmailService`, `SmsService`, `Vehicule` |
| `CalculateurPrix` | prix HT : tarif par mode, suppléments, urgence, remise, assurance | `Colis` |
| `AffectationVehicule` | choisit un véhicule libre et compatible | `Flotte`, `Camion`, `CamionFrigorifique`, `Train`, `Bateau`, `Avion` |
| `Flotte` | liste statique des véhicules | véhicules concrets |
| `Vehicule` / `IVehicule` + sous-classes | flotte, chargement, déplacement | `Colis` |
| `Client`, `Colis`, `Expedition` | modèle ; `Expedition` génère aussi la facture | `Utils` |
| `Database` | singleton de persistance (maps statiques) | `Expedition`, `Client` |
| `EmailService`, `SmsService` | notifications (statique / instancié en dur) | — |
| `Utils` | dates, arrondi, table des distances | — |

## Défaut → emplacement → principe violé

| Défaut | Emplacement | Principe |
|---|---|---|
| Expedition crée la facture | `Expedition.genererFacture()` | S |
| Service qui valide, tarife, calcule les délais, gère le cycle de vie et notifie | `ServiceExpedition` | S |
| `ServiceClient` valide + enregistre + notifie | `ServiceClient.creerClient` | S |
| `if/else` sur le mode et le type de client | `CalculateurPrix.calculerPrixHT` | O |
| `switch` sur le mode pour les délais | `ServiceExpedition.calculerDelai` | O |
| Cascade d'`instanceof` | `AffectationVehicule.trouverVehicule` | O |
| `if` sur le mode pour rouler/naviguer/voler | `ServiceExpedition.demarrerExpedition` | O / L |
| `CamionFrigorifique.charger()` refuse les colis non réfrigérés | `CamionFrigorifique` | L |
| `Train.faireLePlein()` lève une exception (catch vide) | `Train`, `ServiceExpedition.demarrerExpedition` | L |
| `IVehicule` impose rouler/naviguer/voler/réfrigération | `IVehicule` + toutes les sous-classes (`UnsupportedOperationException`) | I |
| Singleton `Database.getInstance()` | `ServiceClient`, `ServiceExpedition` | D |
| `EmailService.envoyer` statique, `new SmsService()` | `ServiceExpedition.notifier`, `ServiceClient` | D |
| `new CalculateurPrix()`, `new AffectationVehicule()` en dur | `ServiceExpedition` | D |
| `Flotte.vehicules` statique, `compteur` statique, journaux statiques | `Flotte`, `ServiceExpedition`, `EmailService`, `SmsService` | D |
| Nombres magiques (`1.2`, `0.10`, `167`, `5000`, `70`, `1440`…) | `CalculateurPrix`, `ServiceExpedition` | lisibilité |
| Codes `String`/`int` pour mode, statut, type client, type colis | partout | lisibilité / O |
| Clé d'API en dur | `SmsService` | sécurité |

## Endroits à modifier pour ajouter le mode `FLUVIAL`
- `CalculateurPrix.calculerPrixHT` (tarif + minimum)
- `ServiceExpedition.creerExpedition` (réfrigéré interdit, pas d'urgence)
- `ServiceExpedition.calculerDelai` (délai)
- `ServiceExpedition.demarrerExpedition` (déplacement)
- `AffectationVehicule.trouverVehicule` (type de véhicule)
- nouvelle classe `Peniche` + `IVehicule` / autres véhicules (méthode de déplacement)
- `Flotte.initialiser` (péniche `PEN-0001`)
- `Utils.getDistance` (trajet fluvial)

## Endroits à modifier pour le supplément « hors gabarit »
- `CalculateurPrix.calculerPrixHT` (détection + ×1,10)
- `Colis` (dimension max)
