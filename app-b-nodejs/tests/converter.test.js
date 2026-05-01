/**
 * Full TDD Test Suite - Right-BICEP / Equivalence Class / BVA
 *
 * RED stage : all tests fail (stub returns 0 and never throws).
 * GREEN stage: all tests pass EXCEPT the intentional failure block,
 *              which remains to demonstrate CI pipeline error interception.
 *
 * 1 m = 3.28084 ft  |  1 ft = 0.3048 m
 */

const { metersToFeet, feetToMeters } = require('../src/converter');

// -----------------------------------------------------------------------
// RIGHT - correct results for typical inputs
// -----------------------------------------------------------------------
describe('RIGHT - Correct results for typical inputs', () => {
  test('5 meters = 16.4042 feet', () => {
    expect(metersToFeet(5)).toBeCloseTo(5 * 3.28084, 4);
  });

  test('5 feet = 1.524 meters', () => {
    expect(feetToMeters(5)).toBeCloseTo(5 * 0.3048, 4);
  });

  test('100 meters = 328.084 feet', () => {
    expect(metersToFeet(100)).toBeCloseTo(100 * 3.28084, 4);
  });

  test('100 feet = 30.48 meters', () => {
    expect(feetToMeters(100)).toBeCloseTo(100 * 0.3048, 4);
  });
});

// -----------------------------------------------------------------------
// BOUNDARY VALUE ANALYSIS (B)
// Critical points: 0, 0.0001, -0.0001, 1_000_000
// -----------------------------------------------------------------------
describe('BOUNDARY VALUE ANALYSIS (B)', () => {
  test('BVA: 0 meters -> 0 feet', () => {
    expect(metersToFeet(0)).toBe(0);
  });

  test('BVA: 0 feet -> 0 meters', () => {
    expect(feetToMeters(0)).toBe(0);
  });

  test('BVA: 0.0001 meters (just above zero)', () => {
    expect(metersToFeet(0.0001)).toBeCloseTo(0.0001 * 3.28084, 6);
  });

  test('BVA: 0.0001 feet (just above zero)', () => {
    expect(feetToMeters(0.0001)).toBeCloseTo(0.0001 * 0.3048, 6);
  });

  test('BVA: 1,000,000 meters (very large number)', () => {
    expect(metersToFeet(1_000_000)).toBeCloseTo(1_000_000 * 3.28084, 0);
  });

  test('BVA: 1,000,000 feet (very large number)', () => {
    expect(feetToMeters(1_000_000)).toBeCloseTo(1_000_000 * 0.3048, 0);
  });
});

// -----------------------------------------------------------------------
// INVERSE (I) - round-trip conversions
// -----------------------------------------------------------------------
describe('INVERSE (I) - Round-trip conversions', () => {
  test('M -> Ft -> M round trip: 42.5 meters returns to original', () => {
    const original = 42.5;
    expect(feetToMeters(metersToFeet(original))).toBeCloseTo(original, 4);
  });

  test('Ft -> M -> Ft round trip: 100 feet returns to original', () => {
    const original = 100.0;
    expect(metersToFeet(feetToMeters(original))).toBeCloseTo(original, 4);
  });
});

// -----------------------------------------------------------------------
// CROSS-CHECK (C) - verify against an independent calculation
// -----------------------------------------------------------------------
describe('CROSS-CHECK (C) - Independent calculation verification', () => {
  test('10 meters: result equals 10 / (1 / 3.28084)', () => {
    const expected = 10 / (1 / 3.28084);
    expect(metersToFeet(10)).toBeCloseTo(expected, 4);
  });

  test('10 feet: result equals 10 / 3.28084', () => {
    const expected = 10 / 3.28084;
    expect(feetToMeters(10)).toBeCloseTo(expected, 4);
  });
});

// -----------------------------------------------------------------------
// ERROR CONDITIONS (E) - invalid inputs must throw
// -----------------------------------------------------------------------
describe('ERROR CONDITIONS (E) - Invalid inputs', () => {
  test('Negative meters throws Error', () => {
    expect(() => metersToFeet(-1)).toThrow();
  });

  test('Negative feet throws Error', () => {
    expect(() => feetToMeters(-1)).toThrow();
  });

  test('Just below zero (-0.0001 meters) throws Error', () => {
    expect(() => metersToFeet(-0.0001)).toThrow();
  });

  test('Just below zero (-0.0001 feet) throws Error', () => {
    expect(() => feetToMeters(-0.0001)).toThrow();
  });

  test('Non-numeric string throws Error', () => {
    expect(() => metersToFeet('abc')).toThrow();
  });

  test('null input throws Error', () => {
    expect(() => metersToFeet(null)).toThrow();
  });
});

// -----------------------------------------------------------------------
// EQUIVALENCE CLASSES - valid partition (positive integers / floats)
// -----------------------------------------------------------------------
describe('EQUIVALENCE CLASSES - Valid partition', () => {
  test('Valid: positive integer metersToFeet returns positive', () => {
    expect(metersToFeet(10)).toBeGreaterThan(0);
  });

  test('Valid: positive float metersToFeet returns positive', () => {
    expect(metersToFeet(3.5)).toBeGreaterThan(0);
  });

  test('Valid: positive integer feetToMeters returns positive', () => {
    expect(feetToMeters(10)).toBeGreaterThan(0);
  });

  test('Valid: positive float feetToMeters returns positive', () => {
    expect(feetToMeters(3.5)).toBeGreaterThan(0);
  });
});

