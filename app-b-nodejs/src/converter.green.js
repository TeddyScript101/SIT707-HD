/**
 * GREEN STAGE implementation.
 *
 * To activate: replace converter.js content with this.
 *
 * After swapping, all tests pass EXCEPT the intentional failure test,
 * which remains to demonstrate how the CI pipeline intercepts failures.
 */

const METERS_TO_FEET = 3.28084;
const FEET_TO_METERS = 0.3048;

function metersToFeet(meters) {
  if (meters === null || meters === undefined || typeof meters !== 'number' || isNaN(meters)) {
    throw new Error('Input must be a valid number');
  }
  if (meters < 0) {
    throw new Error(`Input must be non-negative, got: ${meters}`);
  }
  return meters * METERS_TO_FEET;
}

function feetToMeters(feet) {
  if (feet === null || feet === undefined || typeof feet !== 'number' || isNaN(feet)) {
    throw new Error('Input must be a valid number');
  }
  if (feet < 0) {
    throw new Error(`Input must be non-negative, got: ${feet}`);
  }
  return feet * FEET_TO_METERS;
}

module.exports = { metersToFeet, feetToMeters };
