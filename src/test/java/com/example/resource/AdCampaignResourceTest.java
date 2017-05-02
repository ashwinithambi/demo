package com.example.resource;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.model.AdCampaign;
import com.example.service.AdCampaignService;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AdCampaignResourceTest {

	private MockMvc mockMvc;

    @Mock
    private AdCampaignService adService;

    @InjectMocks
    private AdCampaignResource adResource;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(adResource).build();
    }
    
    @Test
    public void test_resource_retrieve_by_partner_id_success() throws Exception {
        AdCampaign ad = new AdCampaign("1", 30, "Ad Content");
        when(adService.retrieveAdCampaign("1")).thenReturn(ad);
        mockMvc.perform(get("/ad/{partnerID}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.partner_id", is("1")))
                .andExpect(jsonPath("$.duration", is(30)))
                .andExpect(jsonPath("$.ad_content", is("Ad Content")));
        verify(adService, times(1)).retrieveAdCampaign("1");
        verifyNoMoreInteractions(adService);
    }
    
    @Test
    public void test_resource_retrieve_by_partner_id_404_not_found() throws Exception {
        when(adService.retrieveAdCampaign("1")).thenReturn(null);
        mockMvc.perform(get("/ad/{partnerID}", "1"))
                .andExpect(status().isNotFound());
        verify(adService, times(1)).retrieveAdCampaign("1");
        verifyNoMoreInteractions(adService);
    }
    
    @Test
	public void test_resource_ad_campaign_null_partner_id_request() throws Exception {
    	AdCampaign ad = new AdCampaign(null, 30, "Ad Content");
	    mockMvc.perform(
	            post("/ad")      
	            .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ad)))
	           .andExpect(status().isBadRequest());
	            
	    verify(adService, times(0)).isDuplicateAdCampaign(ad);
	    verify(adService, times(0)).saveAdCampaign(ad);
	    verifyNoMoreInteractions(adService);
	}

    @Test
	public void test_resource_ad_campaign_null_content_request() throws Exception {
    	AdCampaign ad = new AdCampaign("2", 30, null);
	    mockMvc.perform(
	            post("/ad")      
	            .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ad)))
	           .andExpect(status().isBadRequest());
	            
	    verify(adService, times(0)).isDuplicateAdCampaign(ad);
	    verify(adService, times(0)).saveAdCampaign(ad);
	    verifyNoMoreInteractions(adService);
	}
    

    @Test
	public void test_resource_ad_campaign_zero_duration_request() throws Exception {
    	AdCampaign ad = new AdCampaign("2", 0, "test");
	    mockMvc.perform(
	            post("/ad")      
	            .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ad)))
	           .andExpect(status().isBadRequest());
	            
	    verify(adService, times(0)).isDuplicateAdCampaign(ad);
	    verify(adService, times(0)).saveAdCampaign(ad);
	    verifyNoMoreInteractions(adService);
	}
    
	@Test
	public void test_resource_ad_campaign_duplicate() throws Exception {
		AdCampaign ad = new AdCampaign("1", 30, "Ad Content");
	   when(adService.isDuplicateAdCampaign(ad)).thenReturn(true);
	
	    mockMvc.perform(
	            post("/ad")      
	            .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ad)))
	           .andExpect(status().isConflict());
	    verify(adService, times(1)).isDuplicateAdCampaign(ad);
	    verify(adService, times(0)).saveAdCampaign(ad);
	   verifyNoMoreInteractions(adService);
	}

	@Test
	public void test_resource_ad_campaign_success() throws Exception {
		AdCampaign ad = new AdCampaign("1", 30, "Ad Content");
	    when(adService.isDuplicateAdCampaign(ad)).thenReturn(false);
	    doNothing().when(adService).saveAdCampaign(ad);
	    mockMvc.perform(
	            post("/ad")      
	            .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(ad)))
		           .andExpect(status().isCreated());
	    verify(adService, times(1)).isDuplicateAdCampaign(ad);
	    verify(adService, times(1)).saveAdCampaign(ad);
	    verifyNoMoreInteractions(adService);
	}
	
	public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	
}
