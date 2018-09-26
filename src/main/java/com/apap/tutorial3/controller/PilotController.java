package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import com.apap.tutorial3.model.PilotModel;
import com.apap.tutorial3.service.PilotService;
import com.apap.tutorial3.service.PilotInMemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PilotController {
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping("/pilot/add")
	public String add(@RequestParam(value = "id", required = true) String id, 
			@RequestParam(value = "licenseNumber", required = true) String licenseNumber,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "flyHour", required = true) int flyHour) {
		PilotModel pilot = new PilotModel(id, licenseNumber, name, flyHour);
		pilotService.addPilot(pilot);
		return "add";
	}
	
	@RequestMapping("/pilot/view")
	public String view(@RequestParam("licenseNumber") String licenseNumber, Model model) {
		PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		model.addAttribute("pilot", archive);
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/viewall")
	public String viewall(Model model) {
		List<PilotModel> archive = pilotService.getPilotList();
		model.addAttribute("pilotList", archive);
		return "viewall-pilot";
	}
	
	
	//Dari baris ini sampai bawah adalah implementasi dari bagian 'Latihan'
	@RequestMapping("/error-page-licenseNumber")
	public String errorPageLicenseNumber() {
		return "error-page-license-number";
	}
	
	@RequestMapping("/error-page-id")
	public String errorPageId() {
		return "error-page-id";
	}
	
	@RequestMapping(value = {"/pilot/view/license-number","/pilot/view/license-number/{licenseNumber}"})
	public String viewPath(@PathVariable Optional<String> licenseNumber, Model model){
		if (licenseNumber.isPresent()) {
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if (archive == null) {
				return "redirect:/error-page-licenseNumber";
			}
			model.addAttribute("pilot", archive);
		} else {
			return "redirect:/error-page-licenseNumber";
		}
		return "view-pilot";
	}
	
	@RequestMapping("/pilot/update/license-number/{licenseNumber}/fly-hour/{flyHour}")
	public String updatePath(@PathVariable Optional<String> licenseNumber, @PathVariable int flyHour,  Model model){
		if (licenseNumber.isPresent()) {
			PilotModel archive = pilotService.getPilotDetailByLicenseNumber(licenseNumber.get());
			if (archive == null) {
				return "redirect:/error-page-licenseNumber";
			}
			archive.setFlyHour(flyHour);
			model.addAttribute("pilot", archive);
		} else {
			return "redirect:/error-page-licenseNumber";
		}
		return "update-pilot";
	}
	
	@RequestMapping(value = {"/pilot/delete/id", "/pilot/delete/id/{id}"})
	public String deletePath(@PathVariable Optional<String> id,  Model model){
		if (id.isPresent()) {
			PilotModel archive = pilotService.getPilotDetailById(id.get());
			if (archive == null) {
				return "redirect:/error-page-id";
			}
			int indexArchive = pilotService.getPilotList().indexOf(archive);
			pilotService.getPilotList().remove(indexArchive);
		} else {
			return "redirect:/error-page-id";
		}
		model.addAttribute("pilotList", pilotService.getPilotList());
		return "delete-pilot";
	}

}
