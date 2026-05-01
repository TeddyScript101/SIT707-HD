package com.sit707.converter;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Build Verification Test (BVT) - Smoke Test (Refactor Stage)
 *
 * One representative test per conversion type.
 * All must pass before the full TDD suite runs (CI Gate 1).
 */
public class UnitConverterBVTTest {

    private static final double DELTA = 0.0001;

    // --- Length: Meters <-> Feet ---
    @Test public void bvt_metersToFeet_1m() { assertEquals(3.28084, UnitConverter.metersToFeet(1.0), DELTA); }
    @Test public void bvt_feetToMeters_1ft() { assertEquals(0.3048, UnitConverter.feetToMeters(1.0), DELTA); }

    // --- Length: Kilometers <-> Miles ---
    @Test public void bvt_kmToMiles_1km() { assertEquals(0.621371, UnitConverter.kmToMiles(1.0), DELTA); }
    @Test public void bvt_milesToKm_1mi() { assertEquals(1.60934, UnitConverter.milesToKm(1.0), DELTA); }

    // --- Length: Centimeters <-> Inches ---
    @Test public void bvt_cmToInches_1cm() { assertEquals(0.393701, UnitConverter.cmToInches(1.0), DELTA); }
    @Test public void bvt_inchesToCm_1in() { assertEquals(2.54, UnitConverter.inchesToCm(1.0), DELTA); }

    // --- Weight: Kilograms <-> Pounds ---
    @Test public void bvt_kgToPounds_1kg() { assertEquals(2.20462, UnitConverter.kgToPounds(1.0), DELTA); }
    @Test public void bvt_poundsToKg_1lb() { assertEquals(0.453592, UnitConverter.poundsToKg(1.0), DELTA); }

    // --- Temperature: Celsius <-> Fahrenheit ---
    @Test public void bvt_celsiusToFahrenheit_0c() { assertEquals(32.0, UnitConverter.celsiusToFahrenheit(0.0), DELTA); }
    @Test public void bvt_fahrenheitToCelsius_32f() { assertEquals(0.0, UnitConverter.fahrenheitToCelsius(32.0), DELTA); }

    // --- Zero boundary (shared across all non-temperature types) ---
    @Test public void bvt_zeroInput_metersToFeet() { assertEquals(0.0, UnitConverter.metersToFeet(0.0), DELTA); }
}
