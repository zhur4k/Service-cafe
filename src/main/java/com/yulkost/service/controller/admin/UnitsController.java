package com.yulkost.service.controller.admin;

import com.yulkost.service.dto.UnitsEditDto;
import com.yulkost.service.model.Units;
import com.yulkost.service.service.UnitsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/units")
public class UnitsController {

    public UnitsService unitsService;

    public UnitsController(UnitsService unitsService) {
        this.unitsService = unitsService;
    }

    @GetMapping
        public String Units(Model model){
        List<Units> units = new ArrayList<>();
        unitsService.findAll().iterator().forEachRemaining(units::add);
        model.addAttribute("form", new UnitsEditDto(units));
        return "adminUnits";
        }

    @PostMapping
    public String UnitsEdit(@ModelAttribute UnitsEditDto form) {
        unitsService.saveAll(form.getUnits());
        return "redirect:/admin/units"; }

    @GetMapping("/add")
    public String UnitsAdd() {
        return "adminUnitsAdd"; }

    @PostMapping("/add")
    public String UnitsAdd(Units unit) {
        unitsService.save(unit);
        return "redirect:/admin/units"; }
}
