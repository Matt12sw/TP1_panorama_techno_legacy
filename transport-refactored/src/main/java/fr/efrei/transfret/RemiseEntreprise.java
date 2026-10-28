package fr.efrei.transfret;

public class RemiseEntreprise implements PolitiqueRemise {

    private static final double SEUIL = 5000;
    private static final double COEF_GROS_MONTANT = 0.85;
    private static final double COEF = 0.90;

    @Override
    public double appliquer(double prix) {
        return prix > SEUIL ? prix * COEF_GROS_MONTANT : prix * COEF;
    }
}
