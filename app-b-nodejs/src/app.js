const express = require('express');
const path = require('path');
const converter = require('./converter');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'public')));

app.get('/', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

const CONVERSIONS = {
  mToF:   { fn: converter.metersToFeet,        from: 'meters',      to: 'feet'        },
  fToM:   { fn: converter.feetToMeters,        from: 'feet',        to: 'meters'      },
  kmToMi: { fn: converter.kmToMiles,           from: 'kilometers',  to: 'miles'       },
  miToKm: { fn: converter.milesToKm,           from: 'miles',       to: 'kilometers'  },
  cmToIn: { fn: converter.cmToInches,          from: 'centimeters', to: 'inches'      },
  inToCm: { fn: converter.inchesToCm,          from: 'inches',      to: 'centimeters' },
  kgToLb: { fn: converter.kgToPounds,          from: 'kilograms',   to: 'pounds'      },
  lbToKg: { fn: converter.poundsToKg,          from: 'pounds',      to: 'kilograms'   },
  cToF:   { fn: converter.celsiusToFahrenheit, from: '°C',          to: '°F'          },
  fToC:   { fn: converter.fahrenheitToCelsius, from: '°F',          to: '°C'          },
};

app.post('/convert', (req, res) => {
  const { direction, value } = req.body;

  if (value === null || value === undefined || String(value).trim() === '') {
    return res.status(400).json({ error: 'Invalid input: please enter a numeric value.' });
  }

  const input = parseFloat(value);
  if (isNaN(input)) {
    return res.status(400).json({ error: 'Invalid input: please enter a numeric value.' });
  }

  const conv = CONVERSIONS[direction];
  if (!conv) {
    return res.status(400).json({ error: 'Unknown conversion type.' });
  }

  try {
    const result = conv.fn(input);
    res.json({ result: result.toFixed(5), fromUnit: conv.from, toUnit: conv.to, inputValue: value });
  } catch (err) {
    res.status(400).json({ error: err.message });
  }
});

if (require.main === module) {
  app.listen(PORT, () => console.log(`Unit Converter (Node.js) running on port ${PORT}`));
}

module.exports = app;
