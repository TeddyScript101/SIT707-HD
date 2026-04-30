package com.sit707.converter;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Full TDD Test Suite - Right-BICEP / Equivalence Class / BVA
 *
 * RED stage : all tests fail (stub returns 0 and never throws).
 * GREEN stage: all tests pass EXCEPT intentionalFail_1MeterEquals100Feet,
 *              which remains failing to demonstrate CI pipeline error interception.
 *
 * Conversion constants:
 *   1 m = 3.28084 ft
 *   1 ft = 0.3048 m
 */
public class UnitConverterTest {

    private static final double DELTA     = 0.0001;
    private static final double M_TO_FT   = 3.28084;
    private static final double FT_TO_M   = 0.3048;

    // ------------------------------------------------------------------
    // RIGHT - correct results for representative typical inputs
    // ------------------------------------------------------------------

    @Test
    public void right_metersToFeet_5Meters() {
        assertEquals(5 * M_TO_FT, UnitConverter.metersToFeet(5.0), DELTA);
    }

    @Test
    public void right_feetToMeters_5Feet() {
        assertEquals(5 * FT_TO_M, UnitConverter.feetToMeters(5.0), DELTA);
    }

    @Test
    public void right_metersToFeet_100Meters() {
        assertEquals(100 * M_TO_FT, UnitConverter.metersToFeet(100.0), DELTA);
    }

    @Test
    public void right_feetToMeters_100Feet() {
        assertEquals(100 * FT_TO_M, UnitConverter.feetToMeters(100.0), DELTA);
    }

    // ------------------------------------------------------------------
    // BOUNDARY VALUE ANALYSIS (B)
    // Critical points: 0, 0.0001, -0.0001, 1_000_000
    // ------------------------------------------------------------------

    @Test
    public void bva_metersToFeet_zero() {
        assertEquals(0.0, UnitConverter.metersToFeet(0.0), DELTA);
    }

    @Test
    public void bva_feetToMeters_zero() {
        assertEquals(0.0, UnitConverter.feetToMeters(0.0), DELTA);
    }

    @Test
    public void bva_metersToFeet_justAboveZero_0_0001() {
        assertEquals(0.0001 * M_TO_FT, UnitConverter.metersToFeet(0.0001), 1e-7);
    }

    @Test
    public void bva_feetToMeters_justAboveZero_0_0001() {
        assertEquals(0.0001 * FT_TO_M, UnitConverter.feetToMeters(0.0001), 1e-7);
    }

    @Test
    public void bva_metersToFeet_veryLargeNumber_1Million() {
        assertEquals(1_000_000 * M_TO_FT, UnitConverter.metersToFeet(1_000_000.0), 0.1);
    }

    @Test
    public void bva_feetToMeters_veryLargeNumber_1Million() {
        assertEquals(1_000_000 * FT_TO_M, UnitConverter.feetToMeters(1_000_000.0), 0.1);
    }

    // ------------------------------------------------------------------
    // INVERSE (I) - M -> Ft -> M round-trip should return the original
    // ------------------------------------------------------------------

    @Test
    public void inverse_metersToFeetToMeters_roundTrip_42_5() {
        double original  = 42.5;
        double roundTrip = UnitConverter.feetToMeters(UnitConverter.metersToFeet(original));
        assertEquals(original, roundTrip, DELTA);
    }

    @Test
    public void inverse_feetToMetersToFeet_roundTrip_100() {
        double original  = 100.0;
        double roundTrip = UnitConverter.metersToFeet(UnitConverter.feetToMeters(original));
        assertEquals(original, roundTrip, DELTA);
    }

    // ------------------------------------------------------------------
    // CROSS-CHECK (C) - verify against a separate calculation method
    // ------------------------------------------------------------------

    @Test
    public void crossCheck_metersToFeet_10m_usingDivision() {
        // Alternative: 10 m / (1 / 3.28084) == 10 * 3.28084
        double expected = 10.0 / (1.0 / 3.28084);
        assertEquals(expected, UnitConverter.metersToFeet(10.0), DELTA);
    }

    @Test
    public void crossCheck_feetToMeters_10ft_usingDivision() {
        // Alternative: 10 ft / 3.28084
        double expected = 10.0 / 3.28084;
        assertEquals(expected, UnitConverter.feetToMeters(10.0), DELTA);
    }

    // ------------------------------------------------------------------
    // ERROR CONDITIONS (E) - negative inputs and boundary -0.0001
    // ------------------------------------------------------------------

    @Test(expected = IllegalArgumentException.class)
    public void error_metersToFeet_negativeOne_throwsIllegalArgument() {
        UnitConverter.metersToFeet(-1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void error_feetToMeters_negativeOne_throwsIllegalArgument() {
        UnitConverter.feetToMeters(-1.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void error_metersToFeet_justBelowZero_neg0_0001() {
        UnitConverter.metersToFeet(-0.0001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void error_feetToMeters_justBelowZero_neg0_0001() {
        UnitConverter.feetToMeters(-0.0001);
    }

    // ------------------------------------------------------------------
    // EQUIVALENCE CLASSES - valid partition (positive int / float)
    // ------------------------------------------------------------------

    @Test
    public void eq_validClass_positiveInteger_metersToFeet_returnsPositive() {
        assertTrue(UnitConverter.metersToFeet(10) > 0);
    }

    @Test
    public void eq_validClass_positiveFloat_metersToFeet_returnsPositive() {
        assertTrue(UnitConverter.metersToFeet(3.5) > 0);
    }

    @Test
    public void eq_validClass_positiveInteger_feetToMeters_returnsPositive() {
        assertTrue(UnitConverter.feetToMeters(10) > 0);
    }

    @Test
    public void eq_validClass_positiveFloat_feetToMeters_returnsPositive() {
        assertTrue(UnitConverter.feetToMeters(3.5) > 0);
    }

    // ------------------------------------------------------------------
    // INTENTIONAL FAILURE - kept failing to demonstrate CI interception
    // Correct value: 1 m = 3.28084 ft, NOT 100 ft.
    // ------------------------------------------------------------------

    @Test
    public void intentionalFail_1MeterEquals100Feet() {
        // This assertion is deliberately wrong: used to show the CI pipeline
        // catches failing tests even after the Green stage is implemented.
        assertEquals("INTENTIONAL: 1 m should equal 100 ft (wrong, CI demo)",
                100.0, UnitConverter.metersToFeet(1.0), DELTA);
    }
}
