package com.sit707.converter;

/**
 * GREEN STAGE implementation.
 *
 * To activate: rename this file to UnitConverter.java (replacing the stub).
 *
 * After swapping, all tests pass EXCEPT intentionalFail_1MeterEquals100Feet,
 * which remains to demonstrate how the CI pipeline intercepts failures.
 */
public class UnitConverterGreen {

    private static final double METERS_TO_FEET = 3.28084;
    private static final double FEET_TO_METERS = 0.3048;

    public static double metersToFeet(double meters) {
        if (meters < 0) {
            throw new IllegalArgumentException("Input must be non-negative, got: " + meters);
        }
        return meters * METERS_TO_FEET;
    }

    public static double feetToMeters(double feet) {
        if (feet < 0) {
            throw new IllegalArgumentException("Input must be non-negative, got: " + feet);
        }
        return feet * FEET_TO_METERS;
    }
}
