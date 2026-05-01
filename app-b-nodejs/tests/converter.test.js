/**
 * Full TDD Test Suite - Right-BICEP / Equivalence Class / BVA (Refactor Stage)
 *
 * Covers four conversion categories:
 *   Length     : meters<->feet, km<->miles, cm<->inches
 *   Weight     : kg<->pounds
 *   Temperature: celsius<->fahrenheit (absolute-zero boundary, NOT non-negative)
 */

const {
  metersToFeet, feetToMeters,
  kmToMiles, milesToKm,
  cmToInches, inchesToCm,
  kgToPounds, poundsToKg,
  celsiusToFahrenheit, fahrenheitToCelsius,
} = require('../src/converter');

const M_TO_FT  = 3.28084;
const KM_TO_MI = 0.621371;
const CM_TO_IN = 0.393701;
const KG_TO_LB = 2.20462;

// =======================================================================
//  METERS <-> FEET
// =======================================================================
describe('Meters<->Feet: RIGHT', () => {
  test('5 m = 16.4042 ft',    () => expect(metersToFeet(5)).toBeCloseTo(5 * M_TO_FT,   4));
  test('100 m = 328.084 ft',  () => expect(metersToFeet(100)).toBeCloseTo(100 * M_TO_FT, 4));
  test('5 ft = 1.524 m',      () => expect(feetToMeters(5)).toBeCloseTo(5 / M_TO_FT,   4));
  test('100 ft = 30.48 m',    () => expect(feetToMeters(100)).toBeCloseTo(100 / M_TO_FT, 4));
});

describe('Meters<->Feet: BVA', () => {
  test('0 m -> 0 ft',                () => expect(metersToFeet(0)).toBe(0));
  test('0.0001 m (just above zero)', () => expect(metersToFeet(0.0001)).toBeCloseTo(0.0001 * M_TO_FT, 6));
  test('1,000,000 m (very large)',   () => expect(metersToFeet(1_000_000)).toBeCloseTo(1_000_000 * M_TO_FT, 0));
  test('0 ft -> 0 m',                () => expect(feetToMeters(0)).toBe(0));
  test('0.0001 ft (just above zero)',() => expect(feetToMeters(0.0001)).toBeCloseTo(0.0001 / M_TO_FT, 6));
});

describe('Meters<->Feet: INVERSE', () => {
  test('m->ft->m round trip: 42.5', () => expect(feetToMeters(metersToFeet(42.5))).toBeCloseTo(42.5, 4));
  test('ft->m->ft round trip: 100', () => expect(metersToFeet(feetToMeters(100))).toBeCloseTo(100,  4));
});

describe('Meters<->Feet: CROSS-CHECK', () => {
  test('10 m via division',  () => expect(metersToFeet(10)).toBeCloseTo(10 / (1 / M_TO_FT), 4));
  test('10 ft via division', () => expect(feetToMeters(10)).toBeCloseTo(10 / M_TO_FT, 4));
});

describe('Meters<->Feet: ERROR + EQUIVALENCE', () => {
  test('negative meters throws',        () => expect(() => metersToFeet(-1)).toThrow());
  test('-0.0001 m throws',              () => expect(() => metersToFeet(-0.0001)).toThrow());
  test('negative feet throws',          () => expect(() => feetToMeters(-1)).toThrow());
  test('null throws',                   () => expect(() => metersToFeet(null)).toThrow());
  test('string throws',                 () => expect(() => metersToFeet('abc')).toThrow());
  test('valid positive int > 0',        () => expect(metersToFeet(10)).toBeGreaterThan(0));
  test('valid positive float > 0',      () => expect(metersToFeet(3.5)).toBeGreaterThan(0));
});

// =======================================================================
//  KILOMETERS <-> MILES
// =======================================================================
describe('Km<->Miles: RIGHT', () => {
  test('5 km = 3.107 mi',   () => expect(kmToMiles(5)).toBeCloseTo(5 * KM_TO_MI,   4));
  test('100 km = 62.137 mi',() => expect(kmToMiles(100)).toBeCloseTo(100 * KM_TO_MI, 4));
  test('5 mi = 8.047 km',   () => expect(milesToKm(5)).toBeCloseTo(5 / KM_TO_MI,   4));
  test('100 mi = 160.934 km',()=> expect(milesToKm(100)).toBeCloseTo(100 / KM_TO_MI, 4));
});

describe('Km<->Miles: BVA', () => {
  test('0 km -> 0 mi',                () => expect(kmToMiles(0)).toBe(0));
  test('0.001 km (just above zero)',  () => expect(kmToMiles(0.001)).toBeCloseTo(0.001 * KM_TO_MI, 6));
  test('1,000,000 km (very large)',   () => expect(kmToMiles(1_000_000)).toBeCloseTo(1_000_000 * KM_TO_MI, 0));
  test('0 mi -> 0 km',               () => expect(milesToKm(0)).toBe(0));
});

describe('Km<->Miles: INVERSE', () => {
  test('km->mi->km round trip: 26.2',() => expect(milesToKm(kmToMiles(26.2))).toBeCloseTo(26.2, 4));
  test('mi->km->mi round trip: 50',  () => expect(kmToMiles(milesToKm(50))).toBeCloseTo(50,   4));
});

describe('Km<->Miles: CROSS-CHECK', () => {
  // 1 km = 1000 m; 1 mile = 1609.344 m
  test('1 km cross-check', () => expect(kmToMiles(1)).toBeCloseTo(1000 / 1609.344, 4));
  test('1 mi cross-check', () => expect(milesToKm(1)).toBeCloseTo(1609.344 / 1000, 4));
});

describe('Km<->Miles: ERROR + EQUIVALENCE', () => {
  test('negative km throws',     () => expect(() => kmToMiles(-1)).toThrow());
  test('-0.001 km throws',       () => expect(() => kmToMiles(-0.001)).toThrow());
  test('negative miles throws',  () => expect(() => milesToKm(-1)).toThrow());
  test('null throws',            () => expect(() => kmToMiles(null)).toThrow());
  test('valid int > 0',          () => expect(kmToMiles(10)).toBeGreaterThan(0));
  test('valid float > 0',        () => expect(milesToKm(3.5)).toBeGreaterThan(0));
});

// =======================================================================
//  CENTIMETERS <-> INCHES
// =======================================================================
describe('Cm<->Inches: RIGHT', () => {
  test('100 cm = 39.37 in',  () => expect(cmToInches(100)).toBeCloseTo(100 * CM_TO_IN, 4));
  test('30.48 cm = 12 in',   () => expect(cmToInches(30.48)).toBeCloseTo(12.0, 4));
  test('12 in = 30.48 cm',   () => expect(inchesToCm(12)).toBeCloseTo(30.48, 4));
  test('1 in = 2.54 cm',     () => expect(inchesToCm(1)).toBeCloseTo(2.54, 4));
});

describe('Cm<->Inches: BVA', () => {
  test('0 cm -> 0 in',              () => expect(cmToInches(0)).toBe(0));
  test('0.01 cm (just above zero)', () => expect(cmToInches(0.01)).toBeCloseTo(0.01 * CM_TO_IN, 6));
  test('100,000 cm (very large)',   () => expect(cmToInches(100_000)).toBeCloseTo(100_000 * CM_TO_IN, 0));
  test('0 in -> 0 cm',              () => expect(inchesToCm(0)).toBe(0));
});

describe('Cm<->Inches: INVERSE', () => {
  test('cm->in->cm round trip: 17.5', () => expect(inchesToCm(cmToInches(17.5))).toBeCloseTo(17.5, 4));
  test('in->cm->in round trip: 6',    () => expect(cmToInches(inchesToCm(6))).toBeCloseTo(6, 4));
});

describe('Cm<->Inches: CROSS-CHECK', () => {
  // 1 inch = 2.54 cm exactly (international standard)
  test('2.54 cm = 1 inch',  () => expect(cmToInches(2.54)).toBeCloseTo(1.0, 4));
  test('1 inch = 2.54 cm',  () => expect(inchesToCm(1)).toBeCloseTo(2.54, 4));
});

describe('Cm<->Inches: ERROR + EQUIVALENCE', () => {
  test('negative cm throws',    () => expect(() => cmToInches(-1)).toThrow());
  test('-0.01 cm throws',       () => expect(() => cmToInches(-0.01)).toThrow());
  test('negative inches throws',() => expect(() => inchesToCm(-1)).toThrow());
  test('null throws',           () => expect(() => cmToInches(null)).toThrow());
  test('valid int > 0',         () => expect(cmToInches(10)).toBeGreaterThan(0));
  test('valid float > 0',       () => expect(inchesToCm(5.5)).toBeGreaterThan(0));
});

// =======================================================================
//  KILOGRAMS <-> POUNDS
// =======================================================================
describe('Kg<->Pounds: RIGHT', () => {
  test('1 kg = 2.20462 lb',  () => expect(kgToPounds(1)).toBeCloseTo(KG_TO_LB, 4));
  test('70 kg body weight',  () => expect(kgToPounds(70)).toBeCloseTo(70 * KG_TO_LB, 4));
  test('1 lb = 0.4536 kg',   () => expect(poundsToKg(1)).toBeCloseTo(1 / KG_TO_LB, 4));
  test('154 lb body weight', () => expect(poundsToKg(154)).toBeCloseTo(154 / KG_TO_LB, 4));
});

describe('Kg<->Pounds: BVA', () => {
  test('0 kg -> 0 lb',               () => expect(kgToPounds(0)).toBe(0));
  test('0.001 kg (just above zero)', () => expect(kgToPounds(0.001)).toBeCloseTo(0.001 * KG_TO_LB, 5));
  test('10,000 kg (very large)',     () => expect(kgToPounds(10_000)).toBeCloseTo(10_000 * KG_TO_LB, 0));
  test('0 lb -> 0 kg',               () => expect(poundsToKg(0)).toBe(0));
});

describe('Kg<->Pounds: INVERSE', () => {
  test('kg->lb->kg round trip: 68.5', () => expect(poundsToKg(kgToPounds(68.5))).toBeCloseTo(68.5, 4));
  test('lb->kg->lb round trip: 150',  () => expect(kgToPounds(poundsToKg(150))).toBeCloseTo(150, 4));
});

describe('Kg<->Pounds: CROSS-CHECK', () => {
  // 1 kg = 1000 g; 1 lb = 453.592 g
  test('1 kg cross-check', () => expect(kgToPounds(1)).toBeCloseTo(1000 / 453.592, 4));
  test('1 lb cross-check', () => expect(poundsToKg(1)).toBeCloseTo(453.592 / 1000, 4));
});

describe('Kg<->Pounds: ERROR + EQUIVALENCE', () => {
  test('negative kg throws',     () => expect(() => kgToPounds(-1)).toThrow());
  test('-0.001 kg throws',       () => expect(() => kgToPounds(-0.001)).toThrow());
  test('negative pounds throws', () => expect(() => poundsToKg(-1)).toThrow());
  test('null throws',            () => expect(() => kgToPounds(null)).toThrow());
  test('valid int > 0',          () => expect(kgToPounds(10)).toBeGreaterThan(0));
  test('valid float > 0',        () => expect(poundsToKg(5.5)).toBeGreaterThan(0));
});

// =======================================================================
//  TEMPERATURE - CELSIUS <-> FAHRENHEIT
//  Negative temps ARE valid; only below absolute zero throws.
// =======================================================================
describe('Temperature: RIGHT', () => {
  test('0°C = 32°F',         () => expect(celsiusToFahrenheit(0)).toBeCloseTo(32,    4));
  test('100°C = 212°F',      () => expect(celsiusToFahrenheit(100)).toBeCloseTo(212, 4));
  test('37°C = 98.6°F',      () => expect(celsiusToFahrenheit(37)).toBeCloseTo(98.6, 4));
  test('32°F = 0°C',         () => expect(fahrenheitToCelsius(32)).toBeCloseTo(0,   4));
  test('212°F = 100°C',      () => expect(fahrenheitToCelsius(212)).toBeCloseTo(100, 4));
  test('98.6°F = 37°C',      () => expect(fahrenheitToCelsius(98.6)).toBeCloseTo(37, 4));
});

describe('Temperature: BVA', () => {
  // Absolute zero is the lower boundary
  test('-273.15°C (absolute zero) is valid', () => expect(celsiusToFahrenheit(-273.15)).toBeCloseTo(-459.67, 1));
  test('-459.67°F (absolute zero) is valid', () => expect(fahrenheitToCelsius(-459.67)).toBeCloseTo(-273.15, 1));
  test('-10°C is valid (negative temp)',     () => expect(celsiusToFahrenheit(-10)).toBeCloseTo(14, 4));
  test('-10°F is valid (negative temp)',     () => expect(fahrenheitToCelsius(-10)).toBeCloseTo((-10 - 32) * 5 / 9, 4));
  test('1000°C (very high) is valid',        () => expect(celsiusToFahrenheit(1000)).toBeCloseTo(1832, 4));
});

describe('Temperature: INVERSE', () => {
  test('C->F->C round trip: 20°C',  () => expect(fahrenheitToCelsius(celsiusToFahrenheit(20))).toBeCloseTo(20, 4));
  test('F->C->F round trip: 77°F',  () => expect(celsiusToFahrenheit(fahrenheitToCelsius(77))).toBeCloseTo(77, 4));
});

describe('Temperature: CROSS-CHECK', () => {
  // -40°C = -40°F: the unique point where both scales are equal
  test('-40°C equals -40°F (unique crossover)', () => {
    expect(celsiusToFahrenheit(-40)).toBeCloseTo(-40, 4);
    expect(fahrenheitToCelsius(-40)).toBeCloseTo(-40, 4);
  });
});

describe('Temperature: ERROR + EQUIVALENCE', () => {
  test('below absolute zero C throws',      () => expect(() => celsiusToFahrenheit(-273.16)).toThrow());
  test('far below absolute zero C throws',  () => expect(() => celsiusToFahrenheit(-1000)).toThrow());
  test('below absolute zero F throws',      () => expect(() => fahrenheitToCelsius(-459.68)).toThrow());
  test('null throws',                       () => expect(() => celsiusToFahrenheit(null)).toThrow());
  test('string throws',                     () => expect(() => celsiusToFahrenheit('hot')).toThrow());
  // Valid equivalence: negative temp above absolute zero is accepted
  test('valid: -100°C is accepted (above absolute zero)', () => expect(() => celsiusToFahrenheit(-100)).not.toThrow());
  test('valid: -100°F is accepted (above absolute zero)', () => expect(() => fahrenheitToCelsius(-100)).not.toThrow());
});
