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

    private List<Result> allResults = Collections.emptyList();
    private List<Result> results = Collections.emptyList();
    private List<BusStop> busStops;
    private int showOptions = 5;
    private boolean isMore = false;
    private Search search;

    private BusStopService busStopService;

    @Autowired
    public MainController(BusStopService busStopService) {
        this.busStopService = busStopService;
        this.busStops = busStopService.getAllBusStops();
    }

    @GetMapping("/")
    public String index(Model model, @ModelAttribute("startBusStop") BusStop startBusStop, @ModelAttribute("stopBusStop") BusStop stopBusStop) {
        model.addAttribute("search", new Search());
        model.addAttribute("busStops", busStops);
        model.addAttribute("results", results);
        model.addAttribute("isMore", isMore);
        return "index";
    }

    @PostMapping("/route")
    public String route(Model model, @ModelAttribute("search") Search search) {
        this.search = search;
        this.showOptions = 5;
        this.allResults = busStopService.getAllRoutes(search.getStartBusStop(), search.getStopBusStop());
        this.allResults.sort(Result::compareTo);
        this.results = allResults.subList(0,getIndex(false));
        model.addAttribute("busStops", busStops);
        model.addAttribute("results", results);
        model.addAttribute("isMore", isMore);
        model.addAttribute("search", search);
        return "index";
    }

    @GetMapping("/more")
    public String more(Model model) {
        this.results = allResults.subList(0,getIndex(true));
        model.addAttribute("busStops", busStops);
        model.addAttribute("results", results);
        model.addAttribute("isMore", isMore);
        model.addAttribute("search", search);
        return "index";
    }

    private int getIndex(boolean more){
        if(more)
            showOptions += 5;
        if(showOptions < allResults.size()){
            isMore = true;
            return showOptions;
        } else {
            isMore = false;
            return allResults.size();
        }
    }
}

