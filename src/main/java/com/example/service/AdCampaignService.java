package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.AdCampaign;
import com.example.repository.AdCampaignRepository;

@Service
public class AdCampaignService {

	@Autowired
	private AdCampaignRepository adCampaignRepo;
	public boolean isDuplicateAdCampaign(AdCampaign adRequest)
	{
		List<AdCampaign> existingAds = adCampaignRepo.findByPartnerID(adRequest.getPartnerID());
		if (existingAds.isEmpty())
		{
			return false;
		}
		else{
			for(AdCampaign ad: existingAds)
			{
				if(ad.getExpiryTime() > System.currentTimeMillis())
				{
					return true;
				}
			}
			return false;
		}
	}
	
	public void saveAdCampaign(AdCampaign adRequest)
	{
		adRequest.setExpiryTime(System.currentTimeMillis()+(adRequest.getDuration()*1000));
		adCampaignRepo.save(adRequest);
	}
	public AdCampaign retrieveAdCampaign( String partnerID)
	{
		List<AdCampaign> existingAds = adCampaignRepo.findByPartnerID(partnerID);
		if(existingAds.isEmpty())
		{
			return null;
		}else{
			List <AdCampaign> activeAdList = new ArrayList<AdCampaign>();
			for(AdCampaign ad: existingAds)
			{
				if(ad.getExpiryTime() > System.currentTimeMillis())
				{
					activeAdList.add(ad);
				}
			}
			if(activeAdList.isEmpty() || activeAdList.size() >1)
			{
				return null;
			}
			else
			{
				return activeAdList.get(0);
			}
		}

		
	}
}
