package com.ing.atmlocator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ATMResource {

	private static final Logger log = LogManager.getLogger();

	public List<Atm> loadAtms() {
		String goodJson = "";
		List<Atm> atmlist = new ArrayList<Atm>();
		try {
			RestTemplate restTemplate = new RestTemplate();
			String rawJson = restTemplate.getForObject("https://www.ing.nl/api/locator/atms/", String.class);
			goodJson = rawJson.substring(rawJson.indexOf('\n') + 1);

			ObjectMapper om = new ObjectMapper();
			Atm[] listOfAtms = om.readValue(goodJson, Atm[].class);
			for (Atm itr : listOfAtms) {
				atmlist.add(itr);
				//log.info("Val of number is: " + itr.getAddress().city);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return atmlist;
	}

	@RequestMapping("atms")
	public List<Atm> getAtms() {
		return loadAtms();
	}

	@RequestMapping("atms/{city}")
	public List<Atm> getAtmsByCity(@PathVariable("city") String city) {
		List<Atm> atms = loadAtms();
		return atms.stream().filter(c -> city.equals(c.getAddress().city)).collect(Collectors.toList());
	}

}
