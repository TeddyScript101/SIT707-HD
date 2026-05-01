package com.sit707.converter;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Full TDD Test Suite - Right-BICEP / Equivalence Class / BVA (Refactor Stage)
 *
 * Covers four conversion categories:
 *   Length     : meters<->feet, km<->miles, cm<->inches
 *   Weight     : kg<->pounds
 *   Temperature: celsius<->fahrenheit (absolute-zero boundary, NOT non-negative boundary)
 *
 * Conversion constants:
 *   1 m  = 3.28084 ft  |  1 km = 0.621371 mi  |  1 cm = 0.393701 in
 *   1 kg = 2.20462 lb
 *   °F   = °C * 9/5 + 32  |  Absolute zero: -273.15 °C / -459.67 °F
 */
public class UnitConverterTest {

    private static final double DELTA        = 0.0001;
    private static final double M_TO_FT      = 3.28084;
    private static final double KM_TO_MI     = 0.621371;
    private static final double CM_TO_IN     = 0.393701;
    private static final double KG_TO_LB     = 2.20462;
    private static final double ABS_ZERO_C   = -273.15;
    private static final double ABS_ZERO_F   = -459.67;

    // ==================================================================
    //  METERS <-> FEET
    // ==================================================================

    @Test public void right_metersToFeet_5m()    { assertEquals(5 * M_TO_FT,   UnitConverter.metersToFeet(5.0),   DELTA); }
    @Test public void right_metersToFeet_100m()  { assertEquals(100 * M_TO_FT, UnitConverter.metersToFeet(100.0), DELTA); }
    @Test public void right_feetToMeters_5ft()   { assertEquals(5 / M_TO_FT,   UnitConverter.feetToMeters(5.0),   DELTA); }
    @Test public void right_feetToMeters_100ft() { assertEquals(100 / M_TO_FT, UnitConverter.feetToMeters(100.0), DELTA); }

    @Test public void bva_metersToFeet_zero()            { assertEquals(0.0, UnitConverter.metersToFeet(0.0),      DELTA); }
    @Test public void bva_metersToFeet_justAboveZero()   { assertEquals(0.0001 * M_TO_FT, UnitConverter.metersToFeet(0.0001), 1e-7); }
    @Test public void bva_metersToFeet_veryLarge()       { assertEquals(1_000_000 * M_TO_FT, UnitConverter.metersToFeet(1_000_000.0), 0.1); }
    @Test public void bva_feetToMeters_zero()            { assertEquals(0.0, UnitConverter.feetToMeters(0.0),      DELTA); }
    @Test public void bva_feetToMeters_justAboveZero()   { assertEquals(0.0001 / M_TO_FT, UnitConverter.feetToMeters(0.0001), 1e-7); }

    @Test public void inverse_metersToFeet_roundTrip()   { double v = 42.5; assertEquals(v, UnitConverter.feetToMeters(UnitConverter.metersToFeet(v)), DELTA); }
    @Test public void inverse_feetToMeters_roundTrip()   { double v = 100.0; assertEquals(v, UnitConverter.metersToFeet(UnitConverter.feetToMeters(v)), DELTA); }

    @Test public void crossCheck_metersToFeet_10m()      { assertEquals(10.0 / (1.0 / M_TO_FT), UnitConverter.metersToFeet(10.0), DELTA); }
    @Test public void crossCheck_feetToMeters_10ft()     { assertEquals(10.0 / M_TO_FT, UnitConverter.feetToMeters(10.0), DELTA); }

    @Test(expected = IllegalArgumentException.class) public void error_metersToFeet_negative()       { UnitConverter.metersToFeet(-1.0); }
    @Test(expected = IllegalArgumentException.class) public void error_metersToFeet_justBelowZero()  { UnitConverter.metersToFeet(-0.0001); }
    @Test(expected = IllegalArgumentException.class) public void error_feetToMeters_negative()        { UnitConverter.feetToMeters(-1.0); }
    @Test(expected = IllegalArgumentException.class) public void error_feetToMeters_justBelowZero()   { UnitConverter.feetToMeters(-0.0001); }

    @Test public void eq_metersToFeet_positiveInt()   { assertTrue(UnitConverter.metersToFeet(10) > 0); }
    @Test public void eq_metersToFeet_positiveFloat() { assertTrue(UnitConverter.metersToFeet(3.5) > 0); }
    @Test public void eq_feetToMeters_positiveInt()   { assertTrue(UnitConverter.feetToMeters(10) > 0); }

    // ==================================================================
    //  KILOMETERS <-> MILES
    // ==================================================================

    @Test public void right_kmToMiles_5km()      { assertEquals(5 * KM_TO_MI,   UnitConverter.kmToMiles(5.0),   DELTA); }
    @Test public void right_kmToMiles_100km()    { assertEquals(100 * KM_TO_MI, UnitConverter.kmToMiles(100.0), DELTA); }
    @Test public void right_milesToKm_5mi()      { assertEquals(5 / KM_TO_MI,   UnitConverter.milesToKm(5.0),   DELTA); }
    @Test public void right_milesToKm_100mi()    { assertEquals(100 / KM_TO_MI, UnitConverter.milesToKm(100.0), DELTA); }

    @Test public void bva_kmToMiles_zero()             { assertEquals(0.0, UnitConverter.kmToMiles(0.0),     DELTA); }
    @Test public void bva_kmToMiles_justAboveZero()    { assertEquals(0.001 * KM_TO_MI, UnitConverter.kmToMiles(0.001), 1e-6); }
    @Test public void bva_kmToMiles_veryLarge()        { assertEquals(1_000_000 * KM_TO_MI, UnitConverter.kmToMiles(1_000_000.0), 0.1); }
    @Test public void bva_milesToKm_zero()             { assertEquals(0.0, UnitConverter.milesToKm(0.0),     DELTA); }

    @Test public void inverse_kmToMiles_roundTrip()    { double v = 26.2; assertEquals(v, UnitConverter.milesToKm(UnitConverter.kmToMiles(v)), DELTA); }
    @Test public void inverse_milesToKm_roundTrip()    { double v = 50.0; assertEquals(v, UnitConverter.kmToMiles(UnitConverter.milesToKm(v)), DELTA); }

    // Cross-check: 1 km = 1000 m, 1 mile = 1609.34 m -> 1 km = 1000/1609.34 miles
    @Test public void crossCheck_kmToMiles_1km()       { assertEquals(1000.0 / 1609.344, UnitConverter.kmToMiles(1.0), DELTA); }
    @Test public void crossCheck_milesToKm_1mi()       { assertEquals(1609.344 / 1000.0, UnitConverter.milesToKm(1.0), DELTA); }

    @Test(expected = IllegalArgumentException.class) public void error_kmToMiles_negative()      { UnitConverter.kmToMiles(-1.0); }
    @Test(expected = IllegalArgumentException.class) public void error_kmToMiles_justBelowZero() { UnitConverter.kmToMiles(-0.001); }
    @Test(expected = IllegalArgumentException.class) public void error_milesToKm_negative()       { UnitConverter.milesToKm(-1.0); }

    @Test public void eq_kmToMiles_positiveInt()   { assertTrue(UnitConverter.kmToMiles(10) > 0); }
    @Test public void eq_milesToKm_positiveFloat() { assertTrue(UnitConverter.milesToKm(3.5) > 0); }

    // ==================================================================
    //  CENTIMETERS <-> INCHES
    // ==================================================================

    @Test public void right_cmToInches_100cm()   { assertEquals(100 * CM_TO_IN, UnitConverter.cmToInches(100.0), DELTA); }
    @Test public void right_cmToInches_30_48cm() { assertEquals(12.0, UnitConverter.cmToInches(30.48), DELTA); }  // 1 foot exactly
    @Test public void right_inchesToCm_12in()    { assertEquals(30.48, UnitConverter.inchesToCm(12.0), DELTA); }   // 1 foot
    @Test public void right_inchesToCm_1in()     { assertEquals(2.54, UnitConverter.inchesToCm(1.0), DELTA); }

    @Test public void bva_cmToInches_zero()            { assertEquals(0.0, UnitConverter.cmToInches(0.0),    DELTA); }
    @Test public void bva_cmToInches_justAboveZero()   { assertEquals(0.01 * CM_TO_IN, UnitConverter.cmToInches(0.01), 1e-6); }
    @Test public void bva_cmToInches_veryLarge()       { assertEquals(100_000 * CM_TO_IN, UnitConverter.cmToInches(100_000.0), 0.01); }
    @Test public void bva_inchesToCm_zero()            { assertEquals(0.0, UnitConverter.inchesToCm(0.0),   DELTA); }

    @Test public void inverse_cmToInches_roundTrip()   { double v = 17.5; assertEquals(v, UnitConverter.inchesToCm(UnitConverter.cmToInches(v)), DELTA); }
    @Test public void inverse_inchesToCm_roundTrip()   { double v = 6.0;  assertEquals(v, UnitConverter.cmToInches(UnitConverter.inchesToCm(v)), DELTA); }

    // Cross-check: 1 inch = 2.54 cm exactly (defined standard)
    @Test public void crossCheck_inchesToCm_2_54cm()   { assertEquals(1.0, UnitConverter.cmToInches(2.54), DELTA); }
    @Test public void crossCheck_cmToInches_1in()      { assertEquals(2.54, UnitConverter.inchesToCm(1.0), DELTA); }

    @Test(expected = IllegalArgumentException.class) public void error_cmToInches_negative()      { UnitConverter.cmToInches(-1.0); }
    @Test(expected = IllegalArgumentException.class) public void error_cmToInches_justBelowZero() { UnitConverter.cmToInches(-0.01); }
    @Test(expected = IllegalArgumentException.class) public void error_inchesToCm_negative()       { UnitConverter.inchesToCm(-1.0); }

    @Test public void eq_cmToInches_positiveInt()   { assertTrue(UnitConverter.cmToInches(10) > 0); }
    @Test public void eq_inchesToCm_positiveFloat() { assertTrue(UnitConverter.inchesToCm(5.5) > 0); }

    // ==================================================================
    //  WEIGHT - KILOGRAMS <-> POUNDS
    // ==================================================================

    @Test public void right_kgToPounds_1kg()     { assertEquals(KG_TO_LB, UnitConverter.kgToPounds(1.0),   DELTA); }
    @Test public void right_kgToPounds_70kg()    { assertEquals(70 * KG_TO_LB, UnitConverter.kgToPounds(70.0), DELTA); }
    @Test public void right_poundsToKg_1lb()     { assertEquals(1 / KG_TO_LB, UnitConverter.poundsToKg(1.0),  DELTA); }
    @Test public void right_poundsToKg_154lb()   { assertEquals(154 / KG_TO_LB, UnitConverter.poundsToKg(154.0), DELTA); }

    @Test public void bva_kgToPounds_zero()            { assertEquals(0.0, UnitConverter.kgToPounds(0.0),     DELTA); }
    @Test public void bva_kgToPounds_justAboveZero()   { assertEquals(0.001 * KG_TO_LB, UnitConverter.kgToPounds(0.001), 1e-5); }
    @Test public void bva_kgToPounds_veryLarge()       { assertEquals(10_000 * KG_TO_LB, UnitConverter.kgToPounds(10_000.0), 0.1); }
    @Test public void bva_poundsToKg_zero()            { assertEquals(0.0, UnitConverter.poundsToKg(0.0),     DELTA); }

    @Test public void inverse_kgToPounds_roundTrip()   { double v = 68.5; assertEquals(v, UnitConverter.poundsToKg(UnitConverter.kgToPounds(v)), DELTA); }
    @Test public void inverse_poundsToKg_roundTrip()   { double v = 150.0; assertEquals(v, UnitConverter.kgToPounds(UnitConverter.poundsToKg(v)), DELTA); }

    // Cross-check: 1 kg = 1000g, 453.592g = 1 lb -> 1 kg = 1000/453.592 lb
    @Test public void crossCheck_kgToPounds_1kg()      { assertEquals(1000.0 / 453.592, UnitConverter.kgToPounds(1.0), DELTA); }
    @Test public void crossCheck_poundsToKg_1lb()      { assertEquals(453.592 / 1000.0, UnitConverter.poundsToKg(1.0), DELTA); }

    @Test(expected = IllegalArgumentException.class) public void error_kgToPounds_negative()       { UnitConverter.kgToPounds(-1.0); }
    @Test(expected = IllegalArgumentException.class) public void error_kgToPounds_justBelowZero()  { UnitConverter.kgToPounds(-0.001); }
    @Test(expected = IllegalArgumentException.class) public void error_poundsToKg_negative()        { UnitConverter.poundsToKg(-1.0); }

    @Test public void eq_kgToPounds_positiveInt()   { assertTrue(UnitConverter.kgToPounds(10) > 0); }
    @Test public void eq_poundsToKg_positiveFloat() { assertTrue(UnitConverter.poundsToKg(5.5) > 0); }

    // ==================================================================
    //  TEMPERATURE - CELSIUS <-> FAHRENHEIT
    //  Note: negative temperatures ARE valid (e.g. -10°C).
    //        Only below absolute zero throws.
    // ==================================================================

    @Test public void right_cToF_0c_is_32f()      { assertEquals(32.0,  UnitConverter.celsiusToFahrenheit(0.0),    DELTA); }
    @Test public void right_cToF_100c_is_212f()   { assertEquals(212.0, UnitConverter.celsiusToFahrenheit(100.0),  DELTA); }
    @Test public void right_cToF_37c_bodyTemp()   { assertEquals(98.6,  UnitConverter.celsiusToFahrenheit(37.0),   DELTA); }
    @Test public void right_fToC_32f_is_0c()      { assertEquals(0.0,   UnitConverter.fahrenheitToCelsius(32.0),   DELTA); }
    @Test public void right_fToC_212f_is_100c()   { assertEquals(100.0, UnitConverter.fahrenheitToCelsius(212.0),  DELTA); }
    @Test public void right_fToC_98_6f_bodyTemp() { assertEquals(37.0,  UnitConverter.fahrenheitToCelsius(98.6),   DELTA); }

    // BVA: absolute zero is the lower boundary (not zero)
    @Test public void bva_cToF_absoluteZero_validBoundary()    { assertEquals(-459.67, UnitConverter.celsiusToFahrenheit(ABS_ZERO_C), DELTA); }
    @Test public void bva_fToC_absoluteZero_validBoundary()    { assertEquals(ABS_ZERO_C, UnitConverter.fahrenheitToCelsius(ABS_ZERO_F), DELTA); }
    @Test public void bva_cToF_negativeTemp_minus10c()         { assertEquals(14.0, UnitConverter.celsiusToFahrenheit(-10.0), DELTA); }
    @Test public void bva_fToC_negativeTemp_minus10f()         { assertEquals(((-10.0 - 32) * 5.0 / 9.0), UnitConverter.fahrenheitToCelsius(-10.0), DELTA); }
    @Test public void bva_cToF_veryHighTemp_1000c()            { assertEquals(1832.0, UnitConverter.celsiusToFahrenheit(1000.0), DELTA); }

    // Inverse round-trips
    @Test public void inverse_cToF_roundTrip_20c()  { double v = 20.0;  assertEquals(v, UnitConverter.fahrenheitToCelsius(UnitConverter.celsiusToFahrenheit(v)), DELTA); }
    @Test public void inverse_fToC_roundTrip_77f()  { double v = 77.0;  assertEquals(v, UnitConverter.celsiusToFahrenheit(UnitConverter.fahrenheitToCelsius(v)), DELTA); }

    // Cross-check: -40°C = -40°F (the unique point where both scales are equal)
    @Test public void crossCheck_minus40_celsiusEqualsFahrenheit() {
        assertEquals(-40.0, UnitConverter.celsiusToFahrenheit(-40.0), DELTA);
        assertEquals(-40.0, UnitConverter.fahrenheitToCelsius(-40.0), DELTA);
    }

    // Error: below absolute zero
    @Test(expected = IllegalArgumentException.class) public void error_cToF_belowAbsoluteZero()       { UnitConverter.celsiusToFahrenheit(-273.16); }
    @Test(expected = IllegalArgumentException.class) public void error_cToF_farBelowAbsoluteZero()    { UnitConverter.celsiusToFahrenheit(-1000.0); }
    @Test(expected = IllegalArgumentException.class) public void error_fToC_belowAbsoluteZero()        { UnitConverter.fahrenheitToCelsius(-459.68); }

    // Equivalence class: valid negative (not below absolute zero) is accepted
    @Test public void eq_temperature_validNegativeCelsius_accepted() {
        assertNotNull(String.valueOf(UnitConverter.celsiusToFahrenheit(-100.0)));
    }
    @Test public void eq_temperature_validNegativeFahrenheit_accepted() {
        assertNotNull(String.valueOf(UnitConverter.fahrenheitToCelsius(-100.0)));
    }
}
