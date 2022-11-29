package pl.agh.bsi.backend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticSiteController {
    @RequestMapping(method = RequestMethod.GET, value = "/tasks/{id}")
    public ModelAndView taskDetails(@PathVariable String id) {
        System.out.println(id);
        ModelAndView modelAndView = new ModelAndView("task");
        modelAndView.addObject("content", id);
        return modelAndView;
    }
}