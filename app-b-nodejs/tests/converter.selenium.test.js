/**
 * Selenium UI Tests - run against the live Express server.
 * ChromeOptions are configured for headless mode (required by GCP Cloud Build).
 */

const { Builder, By, until } = require('selenium-webdriver');
const chrome = require('selenium-webdriver/chrome');
const app = require('../src/app');

let driver;
let server;
let baseUrl;

beforeAll(async () => {
  // Start Express on a random port
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

test('UI: page title contains Unit Converter', async () => {
  await driver.get(baseUrl + '/');
  const title = await driver.getTitle();
  expect(title).toContain('Unit Converter');
});

test('UI: converts 1 meter and shows ~3.28084 feet in result div', async () => {
  await driver.get(baseUrl + '/');
  await driver.findElement(By.id('value')).sendKeys('1');
  await driver.findElement(By.css('button[type="submit"]')).click();

  const resultDiv = driver.findElement(By.id('result'));
  await driver.wait(until.elementIsVisible(resultDiv), 5000);
  const text = await resultDiv.getText();
  expect(text).toContain('3.28084');
});

test('UI: selects Feet to Meters and converts 1 foot correctly', async () => {
  await driver.get(baseUrl + '/');
  const select = await driver.findElement(By.id('direction'));
  await select.findElement(By.css('option[value="fToM"]')).click();
  await driver.findElement(By.id('value')).sendKeys('1');
  await driver.findElement(By.css('button[type="submit"]')).click();

  const resultDiv = driver.findElement(By.id('result'));
  await driver.wait(until.elementIsVisible(resultDiv), 5000);
  const text = await resultDiv.getText();
  expect(text).toContain('0.30480');
});

test('UI: non-numeric input shows error message', async () => {
  await driver.get(baseUrl + '/');
  await driver.findElement(By.id('value')).sendKeys('abc');
  await driver.findElement(By.css('button[type="submit"]')).click();

  const errorDiv = driver.findElement(By.id('error'));
  await driver.wait(until.elementIsVisible(errorDiv), 5000);
  const text = await errorDiv.getText();
  expect(text.toLowerCase()).toContain('error');
});
