package com.example.service;


import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;


import com.example.model.AdCampaign;
import com.example.repository.AdCampaignRepository;


import java.util.List;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

  
public class AdCampaignServiceTest {


	@InjectMocks
    private AdCampaignService adService;
	@Mock
	private AdCampaignRepository adCampaignRepo;
    

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }
	 
    @Test
    public void test_service_retrieve_ad_none_active() throws Exception {
    	 AdCampaign ad1 = new AdCampaign("1", 30, "Ad Content 1", System.currentTimeMillis() - 60000);
         AdCampaign ad2 = new AdCampaign("1", 60, "Ad Content 2", System.currentTimeMillis() - 60000);
         List <AdCampaign> adList = new ArrayList<AdCampaign>();
         adList.add(ad1);
         adList.add(ad2);
        when(adCampaignRepo.findByPartnerID("1")).thenReturn(adList);
        AdCampaign result = adService.retrieveAdCampaign("1");
        assertNull(result);
    }

    @Test
    public void test_service_retrieve_ad_multiple_record_one_active() throws Exception {
        AdCampaign ad1 = new AdCampaign("1", 30, "Ad Content 1", System.currentTimeMillis() - 60000);
        AdCampaign ad2 = new AdCampaign("1", 60, "Ad Content 2", System.currentTimeMillis() + 60000);
        List <AdCampaign> adList = new ArrayList<AdCampaign>();
        adList.add(ad1);
        adList.add(ad2);
        when(adCampaignRepo.findByPartnerID("1")).thenReturn(adList);
        AdCampaign result = adService.retrieveAdCampaign("1");
        assertEquals("1", result.getPartnerID());
        assertEquals("Ad Content 2", result.getContent());
        assertTrue(result.getExpiryTime() > System.currentTimeMillis());
    }
    
    @Test
    public void test_service_retrieve_ad_no_records() throws Exception {
        List <AdCampaign> adList = new ArrayList<AdCampaign>();
        when(adCampaignRepo.findByPartnerID("1")).thenReturn(adList);
        AdCampaign result = adService.retrieveAdCampaign("1");
       assertNull(result);
    }
    
    @Test
    public void test_service_is_duplicate_ad_none_active() throws Exception {
    	 AdCampaign ad1 = new AdCampaign("1", 30, "Ad Content 1", System.currentTimeMillis() - 60000);
         AdCampaign ad2 = new AdCampaign("1", 60, "Ad Content 2", System.currentTimeMillis() - 60000);
         List <AdCampaign> adList = new ArrayList<AdCampaign>();
         adList.add(ad1);
         adList.add(ad2);
         AdCampaign adRequest = new AdCampaign("1", 30, "New Content");
        when(adCampaignRepo.findByPartnerID("1")).thenReturn(adList);
        assertFalse(adService.isDuplicateAdCampaign(adRequest));
        
    }

    @Test
    public void test_service_is_duplicate_ad_multiple_record_one_active() throws Exception {
        AdCampaign ad1 = new AdCampaign("1", 30, "Ad Content 1", System.currentTimeMillis() - 60000);
        AdCampaign ad2 = new AdCampaign("1", 60, "Ad Content 2", System.currentTimeMillis() + 60000);
        List <AdCampaign> adList = new ArrayList<AdCampaign>();
        adList.add(ad1);
        adList.add(ad2);
        AdCampaign adRequest = new AdCampaign("1", 30, "New Content");
        when(adCampaignRepo.findByPartnerID("1")).thenReturn(adList);
        assertTrue(adService.isDuplicateAdCampaign(adRequest));
    }
    
    @Test
    public void test_service_is_duplicate_ad_no_records() throws Exception {
        List <AdCampaign> adList = new ArrayList<AdCampaign>();
        when(adCampaignRepo.findByPartnerID("1")).thenReturn(adList);
        AdCampaign adRequest = new AdCampaign("1", 30, "New Content");
        when(adCampaignRepo.findByPartnerID("1")).thenReturn(adList);
        assertFalse(adService.isDuplicateAdCampaign(adRequest));
    }
}
