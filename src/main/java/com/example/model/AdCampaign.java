package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class AdCampaign {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	@JsonProperty("partner_id")
	private String partnerID;
	private int duration;
	@JsonProperty("ad_content")
	private String content;
	@JsonIgnore
	private long expiryTime;
	
	public long getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(long expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getPartnerID() {
		return partnerID;
	}
	public void setPartnerID(String partnerID) {
		this.partnerID = partnerID;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public AdCampaign()
	{
		
	}
	public AdCampaign (String partnerID, int duration, String content)
	{
		this.partnerID = partnerID;
		this.duration = duration;
		this.content = content;
	}
	public AdCampaign (String partnerID, int duration, String content, long expiryTime)
	{
		this.partnerID = partnerID;
		this.duration = duration;
		this.content = content;
		this.expiryTime = expiryTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + duration;
		result = prime * result + (int) (expiryTime ^ (expiryTime >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((partnerID == null) ? 0 : partnerID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdCampaign other = (AdCampaign) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (duration != other.duration)
			return false;
		if (expiryTime != other.expiryTime)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (partnerID == null) {
			if (other.partnerID != null)
				return false;
		} else if (!partnerID.equals(other.partnerID))
			return false;
		return true;
	}
	
		
	
}
