package com.example.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AdCampaign;
import com.example.service.AdCampaignService;


@RestController
@RequestMapping("/ad")
public class AdCampaignResource {
	@Autowired
	private AdCampaignService adCampaignService;

		@RequestMapping(value="/{partner_id}", method = RequestMethod.GET)
		ResponseEntity<AdCampaign> retrieveAdCampaign(@PathVariable("partner_id") String partnerID)
		{
			AdCampaign adCampaign = adCampaignService.retrieveAdCampaign(partnerID); 
		if(adCampaign != null)
		{
			return new ResponseEntity<AdCampaign>(adCampaign, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		}
		
		@RequestMapping (method = RequestMethod.POST)
		
		ResponseEntity<?> saveAdCampaign(@RequestBody AdCampaign adRequest)
		{
			if(adRequest == null || adRequest.getPartnerID() ==null || adRequest.getDuration()== 0 || adRequest.getContent()==null)
			{
				return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
			}
			if(adCampaignService.isDuplicateAdCampaign(adRequest))
			{
				return new ResponseEntity<Void>( HttpStatus.CONFLICT);
			}
			else{
				adCampaignService.saveAdCampaign(adRequest);
				return new ResponseEntity<String>("SUCCESS",HttpStatus.CREATED);
			}
		}
}

