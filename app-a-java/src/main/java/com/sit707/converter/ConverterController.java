package com.sit707.converter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ConverterController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/convert")
    public String convert(
            @RequestParam String direction,
            @RequestParam String value,
            Model model) {

        model.addAttribute("direction", direction);
        model.addAttribute("inputValue", value);

        try {
            double input = Double.parseDouble(value);
            double result;
            String fromUnit, toUnit;

            switch (direction) {
                case "mToF":
                    result = UnitConverter.metersToFeet(input);
                    fromUnit = "meters"; toUnit = "feet"; break;
                case "fToM":
                    result = UnitConverter.feetToMeters(input);
                    fromUnit = "feet"; toUnit = "meters"; break;
                case "kmToMi":
                    result = UnitConverter.kmToMiles(input);
                    fromUnit = "kilometers"; toUnit = "miles"; break;
                case "miToKm":
                    result = UnitConverter.milesToKm(input);
                    fromUnit = "miles"; toUnit = "kilometers"; break;
                case "cmToIn":
                    result = UnitConverter.cmToInches(input);
                    fromUnit = "centimeters"; toUnit = "inches"; break;
                case "inToCm":
                    result = UnitConverter.inchesToCm(input);
                    fromUnit = "inches"; toUnit = "centimeters"; break;
                case "kgToLb":
                    result = UnitConverter.kgToPounds(input);
                    fromUnit = "kilograms"; toUnit = "pounds"; break;
                case "lbToKg":
                    result = UnitConverter.poundsToKg(input);
                    fromUnit = "pounds"; toUnit = "kilograms"; break;
                case "cToF":
                    result = UnitConverter.celsiusToFahrenheit(input);
                    fromUnit = "°C"; toUnit = "°F"; break;
                case "fToC":
                    result = UnitConverter.fahrenheitToCelsius(input);
                    fromUnit = "°F"; toUnit = "°C"; break;
                default:
                    model.addAttribute("error", "Unknown conversion type.");
                    return "index";
            }

            model.addAttribute("fromUnit", fromUnit);
            model.addAttribute("toUnit", toUnit);
            model.addAttribute("result", String.format("%.5f", result));

        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid input: please enter a numeric value.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "index";
    }
}
