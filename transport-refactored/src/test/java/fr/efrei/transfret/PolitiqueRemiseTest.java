package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolitiqueRemiseTest {

    private static final double DELTA = 1e-9;

    @Test
    void particulier_sans_remise() {
        assertEquals(1000, new SansRemise().appliquer(1000), DELTA);
    }

    @Test
    void entreprise_10_pourcent() {
        assertEquals(900, new RemiseEntreprise().appliquer(1000), DELTA);
    }

    @Test
    void entreprise_15_pourcent_au_dela_de_5000() {
        assertEquals(6000 * 0.85, new RemiseEntreprise().appliquer(6000), DELTA);
    }

    @Test
    void entreprise_10_pourcent_a_5000_pile() {
        assertEquals(4500, new RemiseEntreprise().appliquer(5000), DELTA);
    }

    @Test
    void premium_20_pourcent() {
        assertEquals(800, new RemisePremium().appliquer(1000), DELTA);
    }
}
