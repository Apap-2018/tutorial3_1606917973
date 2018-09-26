package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import com.apap.tutorial3.model.PilotModel;
import org.springframework.stereotype.Service;

/**
 * PilotInMemoryService
 */
@Service
public class PilotInMemoryService implements PilotService {
	private List<PilotModel> archivePilot;
	
	public PilotInMemoryService() {
		archivePilot = new ArrayList<>();
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		archivePilot.add(pilot);
	}
	
	@Override
	public List<PilotModel> getPilotList() {
		return archivePilot;
	}
	
	//Dari baris ini sampai bawah adalah implementasi dari bagian 'Latihan'
	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		
		ListIterator<PilotModel> itr = archivePilot.listIterator(); 
		while(itr.hasNext()){  
			PilotModel cekPilot = itr.next();
			if (cekPilot.getLicenseNumber().equals(licenseNumber)) {
				return cekPilot;
			}
		}  
		return null;
	}
	
	@Override
	public PilotModel getPilotDetailById(String id) {
		
		ListIterator<PilotModel> itr = archivePilot.listIterator(); 
		while(itr.hasNext()){  
			PilotModel cekPilot = itr.next();
			if (cekPilot.getId().equals(id)) {
				return cekPilot;
			}
		}  
		return null;
	}
	
	
	
}
