/**
 * Build Verification Test (BVT) - Smoke Test
 *
 * Gate 1 in the CI pipeline. Covers only the core conversion logic.
 * All tests must pass before the full TDD suite runs.
 *
 * RED stage : all fail (stub returns 0).
 * GREEN stage: all pass.
 */

const { metersToFeet, feetToMeters } = require('../src/converter');

describe('BVT - Smoke Tests', () => {
  test('BVT: 1 meter converts to 3.28084 feet', () => {
    expect(metersToFeet(1.0)).toBeCloseTo(3.28084, 4);
  });

  test('BVT: 1 foot converts to 0.3048 meters', () => {
    expect(feetToMeters(1.0)).toBeCloseTo(0.3048, 4);
  });

  test('BVT: 0 meters converts to 0 feet', () => {
    expect(metersToFeet(0)).toBe(0);
  });
});
