package fr.efrei.transfret;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TarifTransportTest {

    private static final double DELTA = 1e-9;

    @Test
    void route_prix_au_km_et_au_kg() {
        assertEquals(100 * 1.2 + 50 * 0.10, new TarifRoute().calculer(100, 50, 1), DELTA);
    }

    @Test
    void rail_prix_au_km_et_au_kg() {
        assertEquals(500 * 0.8 + 100 * 0.07, new TarifRail().calculer(500, 100, 1), DELTA);
    }

    @Test
    void rail_minimum_150() {
        assertEquals(150, new TarifRail().calculer(10, 1, 1), DELTA);
    }

    @Test
    void mer_prix_au_km_et_au_kg() {
        assertEquals(5850 * 0.5 + 1000 * 0.03, new TarifMer().calculer(5850, 1000, 1), DELTA);
    }

    @Test
    void mer_minimum_500() {
        assertEquals(500, new TarifMer().calculer(100, 10, 1), DELTA);
    }

    @Test
    void air_poids_reel_si_plus_lourd() {
        assertEquals(1000 * 0.6 + 100 * 4.5, new TarifAir().calculer(1000, 100, 0.1), DELTA);
    }

    @Test
    void air_poids_volumetrique_si_plus_encombrant() {
        assertEquals(1000 * 0.6 + 2 * 167 * 4.5, new TarifAir().calculer(1000, 10, 2), DELTA);
    }
}
