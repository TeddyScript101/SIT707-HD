/**
 * REFACTOR STAGE - expanded to four conversion categories:
 *   Length     : meters<->feet, km<->miles, cm<->inches
 *   Weight     : kg<->pounds
 *   Temperature: celsius<->fahrenheit (absolute-zero boundary)
 */

const METERS_TO_FEET = 3.28084;
const KM_TO_MILES    = 0.621371;
const CM_TO_INCHES   = 0.393701;
const KG_TO_LBS      = 2.20462;
const ABS_ZERO_C     = -273.15;
const ABS_ZERO_F     = -459.67;

function validateNumber(value, name) {
  if (value === null || value === undefined || typeof value !== 'number' || isNaN(value)) {
    throw new Error(`${name} must be a valid number`);
  }
}

function validateNonNegative(value, name) {
  validateNumber(value, name);
  if (value < 0) throw new Error(`${name} must be non-negative, got: ${value}`);
}

// --- Length: Meters <-> Feet ---
function metersToFeet(meters)  { validateNonNegative(meters, 'meters');      return meters * METERS_TO_FEET; }
function feetToMeters(feet)    { validateNonNegative(feet, 'feet');           return feet / METERS_TO_FEET; }

// --- Length: Kilometers <-> Miles ---
function kmToMiles(km)         { validateNonNegative(km, 'kilometers');       return km * KM_TO_MILES; }
function milesToKm(miles)      { validateNonNegative(miles, 'miles');         return miles / KM_TO_MILES; }

// --- Length: Centimeters <-> Inches ---
function cmToInches(cm)        { validateNonNegative(cm, 'centimeters');      return cm * CM_TO_INCHES; }
function inchesToCm(inches)    { validateNonNegative(inches, 'inches');       return inches / CM_TO_INCHES; }

// --- Weight: Kilograms <-> Pounds ---
function kgToPounds(kg)        { validateNonNegative(kg, 'kilograms');        return kg * KG_TO_LBS; }
function poundsToKg(pounds)    { validateNonNegative(pounds, 'pounds');       return pounds / KG_TO_LBS; }

// --- Temperature: Celsius <-> Fahrenheit ---
// Negative temperatures are valid; only below absolute zero throws.
function celsiusToFahrenheit(celsius) {
  validateNumber(celsius, 'celsius');
  if (celsius < ABS_ZERO_C) throw new Error(`Celsius below absolute zero (-273.15): ${celsius}`);
  return celsius * 9 / 5 + 32;
}

function fahrenheitToCelsius(fahrenheit) {
  validateNumber(fahrenheit, 'fahrenheit');
  if (fahrenheit < ABS_ZERO_F) throw new Error(`Fahrenheit below absolute zero (-459.67): ${fahrenheit}`);
  return (fahrenheit - 32) * 5 / 9;
}

module.exports = {
  metersToFeet, feetToMeters,
  kmToMiles, milesToKm,
  cmToInches, inchesToCm,
  kgToPounds, poundsToKg,
  celsiusToFahrenheit, fahrenheitToCelsius,
};
