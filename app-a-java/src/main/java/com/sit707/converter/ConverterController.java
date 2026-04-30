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

            if ("mToF".equals(direction)) {
                result = UnitConverter.metersToFeet(input);
                model.addAttribute("fromUnit", "meters");
                model.addAttribute("toUnit", "feet");
            } else {
                result = UnitConverter.feetToMeters(input);
                model.addAttribute("fromUnit", "feet");
                model.addAttribute("toUnit", "meters");
            }

            model.addAttribute("result", String.format("%.5f", result));

        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid input: please enter a numeric value.");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }

        return "index";
    }
}
