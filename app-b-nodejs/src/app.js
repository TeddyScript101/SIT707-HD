const express = require('express');
const path = require('path');
const { metersToFeet, feetToMeters } = require('./converter');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'public')));

app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

app.post('/convert', (req, res) => {
  const { direction, value } = req.body;
  const input = parseFloat(value);

  if (value === null || value === undefined || value.trim() === '' || isNaN(input)) {
    return res.status(400).json({ error: 'Invalid input: please enter a numeric value.' });
  }
  if (input < 0) {
    return res.status(400).json({ error: 'Invalid input: value must be non-negative.' });
  }

  let result, fromUnit, toUnit;
  if (direction === 'mToF') {
    result = metersToFeet(input);
    fromUnit = 'meters';
    toUnit = 'feet';
  } else {
    result = feetToMeters(input);
    fromUnit = 'feet';
    toUnit = 'meters';
  }

  res.json({ result: result.toFixed(5), fromUnit, toUnit, inputValue: value });
});

if (require.main === module) {
  app.listen(PORT, () => {
    console.log(`Unit Converter (Node.js) running on port ${PORT}`);
  });
}

module.exports = app;
