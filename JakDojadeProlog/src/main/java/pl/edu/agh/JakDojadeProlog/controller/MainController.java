package pl.edu.agh.JakDojadeProlog.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.edu.agh.JakDojadeProlog.model.BusStop;
import pl.edu.agh.JakDojadeProlog.model.Result;
import pl.edu.agh.JakDojadeProlog.model.Search;
import pl.edu.agh.JakDojadeProlog.prolog.BusStopService;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Controller
public class MainController {

    private List<Result> results = Collections.emptyList();
    private List<BusStop> busStops;

    @Autowired
    private BusStopService busStopService;

    @GetMapping("/")
    public String index(Model model, @ModelAttribute("startBusStop") BusStop startBusStop, @ModelAttribute("stopBusStop") BusStop stopBusStop) {
        this.busStops = busStopService.getAllBusStops();
        model.addAttribute("search", new Search());
        model.addAttribute("busStops", busStops);
        model.addAttribute("results", results);
        return "index";
    }

    @PostMapping("/route")
    public String route(Model model, @ModelAttribute("search") Search search) {
        this.results = busStopService.getAllRoutes(search.getStartBusStop(), search.getStopBusStop());
        this.results.sort(Result::compareTo);
        model.addAttribute("busStops", busStops);
        model.addAttribute("results", results);
        return "index";
    }
}

