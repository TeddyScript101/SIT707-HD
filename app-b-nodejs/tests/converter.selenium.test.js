/**
 * Selenium UI Tests - run against the live Express server.
 * ChromeOptions configured for headless mode (required by GCP Cloud Build).
 * Covers all conversion types added in the Refactor Stage.
 */

const { Builder, By, until } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');
const app = require('../src/app');

let driver;
let server;
let baseUrl;

beforeAll(async () => {
  await new Promise((resolve) => {
    server = app.listen(0, () => {
      baseUrl = `http://localhost:${server.address().port}`;
      resolve();
    });
  });

  const options = new chrome.Options();
  options.addArguments('--headless');
  options.addArguments('--no-sandbox');
  options.addArguments('--disable-dev-shm-usage');
  options.addArguments('--disable-gpu');
  options.addArguments('--window-size=1280,800');

  driver = await new Builder()
    .forBrowser('chrome')
    .setChromeOptions(options)
    .build();
});

afterAll(async () => {
  if (driver) await driver.quit();
  if (server) server.close();
});

async function selectDirection(value) {
  const select = await driver.findElement(By.id('direction'));
  await select.findElement(By.css(`option[value="${value}"]`)).click();
}

async function submitValue(value) {
  const field = await driver.findElement(By.id('value'));
  await field.clear();
  await field.sendKeys(value);
  await driver.findElement(By.css('button[type="submit"]')).click();
}

async function getResultText() {
  const resultDiv = driver.findElement(By.id('result'));
  await driver.wait(until.elementIsVisible(resultDiv), 5000);
  return resultDiv.getText();
}

async function getErrorText() {
  const errorDiv = driver.findElement(By.id('error'));
  await driver.wait(until.elementIsVisible(errorDiv), 5000);
  return errorDiv.getText();
}

// ---- Page load -----------------------------------------------------------

test('UI: page title contains Unit Converter', async () => {
  await driver.get(baseUrl + '/');
  expect(await driver.getTitle()).toContain('Unit Converter');
});

// ---- Length: Meters <-> Feet ---------------------------------------------

test('UI: 1 meter -> 3.28084 feet', async () => {
  await driver.get(baseUrl + '/');
  await submitValue('1');
  expect(await getResultText()).toContain('3.28084');
});

test('UI: 1 foot -> 0.30480 meters', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('fToM');
  await submitValue('1');
  expect(await getResultText()).toContain('0.30480');
});

// ---- Length: Kilometers <-> Miles ----------------------------------------

test('UI: 1 km -> 0.62137 miles', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('kmToMi');
  await submitValue('1');
  expect(await getResultText()).toContain('0.62137');
});

test('UI: 1 mile -> 1.60934 km', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('miToKm');
  await submitValue('1');
  expect(await getResultText()).toContain('1.60934');
});

// ---- Length: Centimeters <-> Inches --------------------------------------

test('UI: 2.54 cm -> 1.00000 inches', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('cmToIn');
  await submitValue('2.54');
  expect(await getResultText()).toContain('1.00000');
});

test('UI: 1 inch -> 2.54000 cm', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('inToCm');
  await submitValue('1');
  expect(await getResultText()).toContain('2.54000');
});

// ---- Weight: Kilograms <-> Pounds ----------------------------------------

test('UI: 1 kg -> 2.20462 pounds', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('kgToLb');
  await submitValue('1');
  expect(await getResultText()).toContain('2.20462');
});

test('UI: 1 pound -> 0.45359 kg', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('lbToKg');
  await submitValue('1');
  expect(await getResultText()).toContain('0.45359');
});

// ---- Temperature: Celsius <-> Fahrenheit ---------------------------------

test('UI: 0°C -> 32.00000°F', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('cToF');
  await submitValue('0');
  expect(await getResultText()).toContain('32.00000');
});

test('UI: 32°F -> 0.00000°C', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('fToC');
  await submitValue('32');
  expect(await getResultText()).toContain('0.00000');
});

test('UI: -10°C is valid and shows 14.00000°F', async () => {
  await driver.get(baseUrl + '/');
  await selectDirection('cToF');
  await submitValue('-10');
  expect(await getResultText()).toContain('14.00000');
});

// ---- Error conditions ----------------------------------------------------

test('UI: non-numeric input shows error', async () => {
  await driver.get(baseUrl + '/');
  await submitValue('abc');
  expect((await getErrorText()).toLowerCase()).toContain('error');
});

test('UI: negative length input shows error', async () => {
  await driver.get(baseUrl + '/');
  await submitValue('-5');
  expect((await getErrorText()).toLowerCase()).toContain('error');
});
