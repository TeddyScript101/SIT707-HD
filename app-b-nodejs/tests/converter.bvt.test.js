/**
 * Build Verification Test (BVT) - Smoke Test (Refactor Stage)
 * One representative test per conversion type. CI Gate 1.
 */

const {
  metersToFeet, feetToMeters,
  kmToMiles, milesToKm,
  cmToInches, inchesToCm,
  kgToPounds, poundsToKg,
  celsiusToFahrenheit, fahrenheitToCelsius,
} = require('../src/converter');

describe('BVT - Smoke Tests', () => {
  // Length: Meters <-> Feet
  test('BVT: 1 meter = 3.28084 feet',   () => expect(metersToFeet(1)).toBeCloseTo(3.28084, 4));
  test('BVT: 1 foot = 0.3048 meters',   () => expect(feetToMeters(1)).toBeCloseTo(0.3048,  4));

  // Length: Kilometers <-> Miles
  test('BVT: 1 km = 0.621371 miles',    () => expect(kmToMiles(1)).toBeCloseTo(0.621371, 4));
  test('BVT: 1 mile = 1.60934 km',      () => expect(milesToKm(1)).toBeCloseTo(1.60934,  4));

  // Length: Centimeters <-> Inches
  test('BVT: 1 cm = 0.393701 inches',   () => expect(cmToInches(1)).toBeCloseTo(0.393701, 4));
  test('BVT: 1 inch = 2.54 cm',         () => expect(inchesToCm(1)).toBeCloseTo(2.54,     4));

  // Weight: Kilograms <-> Pounds
  test('BVT: 1 kg = 2.20462 pounds',    () => expect(kgToPounds(1)).toBeCloseTo(2.20462, 4));
  test('BVT: 1 pound = 0.453592 kg',    () => expect(poundsToKg(1)).toBeCloseTo(0.453592, 4));

  // Temperature: Celsius <-> Fahrenheit
  test('BVT: 0°C = 32°F',              () => expect(celsiusToFahrenheit(0)).toBeCloseTo(32, 4));
  test('BVT: 32°F = 0°C',              () => expect(fahrenheitToCelsius(32)).toBeCloseTo(0, 4));

  // Zero boundary
  test('BVT: 0 meters = 0 feet',        () => expect(metersToFeet(0)).toBe(0));
});
