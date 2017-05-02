package com.example.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.model.AdCampaign;

@Repository
public interface AdCampaignRepository extends CrudRepository<AdCampaign, Long> {

	List<AdCampaign> findByPartnerID(String partnerID);
}
