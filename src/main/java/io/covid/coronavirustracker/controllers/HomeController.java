package io.covid.coronavirustracker.controllers;

import io.covid.coronavirustracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;
    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("states",coronaVirusDataService.getLocationStateList());
        return "home";
    }
}
