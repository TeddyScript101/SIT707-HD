package com.sit707.converter;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Build Verification Test (BVT) - Smoke Test
 *
 * Gate 1 in the CI pipeline. Validates only core conversion logic.
 * All three tests must pass before the full TDD suite runs.
 *
 * RED stage: all three fail because UnitConverter.metersToFeet / feetToMeters return 0.
 * GREEN stage: all three pass after the real multiplication logic is added.
 */
public class UnitConverterBVTTest {

    private static final double DELTA = 0.0001;

    @Test
    public void bvt_metersToFeet_1MeterGivesCorrectFeet() {
        double result = UnitConverter.metersToFeet(1.0);
        assertEquals(3.28084, result, DELTA);
    }

    @Test
    public void bvt_feetToMeters_1FootGivesCorrectMeters() {
        double result = UnitConverter.feetToMeters(1.0);
        assertEquals(0.3048, result, DELTA);
    }

    @Test
    public void bvt_metersToFeet_zeroGivesZero() {
        assertEquals(0.0, UnitConverter.metersToFeet(0.0), DELTA);
    }
}
