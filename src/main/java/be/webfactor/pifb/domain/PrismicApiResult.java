package be.webfactor.pifb.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PrismicApiResult {

	private String id;
	private String title;
	private String description;

	@JsonProperty("image_url")
	private String imageUrl;

	@JsonProperty("last_update")
	private long modifiedDate;

	private Object blob;
}
