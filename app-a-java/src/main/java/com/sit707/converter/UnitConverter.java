package com.sit707.converter;

/**
 * REFACTOR STAGE - expanded from meters/feet only to four conversion categories:
 *   Length    : meters<->feet, km<->miles, cm<->inches
 *   Weight    : kg<->pounds
 *   Temperature: celsius<->fahrenheit (unique validation: absolute-zero boundary)
 */
public class UnitConverter {

    // --- Length constants ---
    private static final double METERS_TO_FEET = 3.28084;
    private static final double KM_TO_MILES    = 0.621371;
    private static final double CM_TO_INCHES   = 0.393701;

    // --- Weight constants ---
    private static final double KG_TO_LBS = 2.20462;

    // --- Temperature limits (absolute zero) ---
    private static final double ABSOLUTE_ZERO_C = -273.15;
    private static final double ABSOLUTE_ZERO_F = -459.67;

    // ------------------------------------------------------------------ //
    //  LENGTH - Meters <-> Feet
    // ------------------------------------------------------------------ //

    public static double metersToFeet(double meters) {
        validateNonNegative(meters, "meters");
        return meters * METERS_TO_FEET;
    }

    public static double feetToMeters(double feet) {
        validateNonNegative(feet, "feet");
        return feet / METERS_TO_FEET;
    }

    // ------------------------------------------------------------------ //
    //  LENGTH - Kilometers <-> Miles
    // ------------------------------------------------------------------ //

    public static double kmToMiles(double km) {
        validateNonNegative(km, "kilometers");
        return km * KM_TO_MILES;
    }

    public static double milesToKm(double miles) {
        validateNonNegative(miles, "miles");
        return miles / KM_TO_MILES;
    }

    // ------------------------------------------------------------------ //
    //  LENGTH - Centimeters <-> Inches
    // ------------------------------------------------------------------ //

    public static double cmToInches(double cm) {
        validateNonNegative(cm, "centimeters");
        return cm * CM_TO_INCHES;
    }

    public static double inchesToCm(double inches) {
        validateNonNegative(inches, "inches");
        return inches / CM_TO_INCHES;
    }

    // ------------------------------------------------------------------ //
    //  WEIGHT - Kilograms <-> Pounds
    // ------------------------------------------------------------------ //

    public static double kgToPounds(double kg) {
        validateNonNegative(kg, "kilograms");
        return kg * KG_TO_LBS;
    }

    public static double poundsToKg(double pounds) {
        validateNonNegative(pounds, "pounds");
        return pounds / KG_TO_LBS;
    }

    // ------------------------------------------------------------------ //
    //  TEMPERATURE - Celsius <-> Fahrenheit
    //  Negative values are valid (e.g. -10°C); only below absolute zero throws.
    // ------------------------------------------------------------------ //

    public static double celsiusToFahrenheit(double celsius) {
        if (celsius < ABSOLUTE_ZERO_C) {
            throw new IllegalArgumentException(
                "Celsius below absolute zero (-273.15): " + celsius);
        }
        return celsius * 9.0 / 5.0 + 32.0;
    }

    public static double fahrenheitToCelsius(double fahrenheit) {
        if (fahrenheit < ABSOLUTE_ZERO_F) {
            throw new IllegalArgumentException(
                "Fahrenheit below absolute zero (-459.67): " + fahrenheit);
        }
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    // ------------------------------------------------------------------ //
    //  Private helpers
    // ------------------------------------------------------------------ //

    private static void validateNonNegative(double value, String unit) {
        if (value < 0) {
            throw new IllegalArgumentException(
                unit + " must be non-negative, got: " + value);
        }
    }
}
