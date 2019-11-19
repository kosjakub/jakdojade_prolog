package pl.edu.agh.JakDojadeProlog.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@Controller
public class SimpleController {

    String startBusStop;
    String stopBusStop;
    List<String> busStops;

    @Value("${spring.application.name}")
    String appName;

    @GetMapping("/")
    public String homePage(Model model, @ModelAttribute("startBusStop") String startBusStop, @ModelAttribute("stopBusStop") String stopBusStop) {
        busStops = Arrays.asList("A", "B", "C", "D");
        model.addAttribute("busStops", busStops);
        model.addAttribute("appName", appName);
        return "home";
    }
}

