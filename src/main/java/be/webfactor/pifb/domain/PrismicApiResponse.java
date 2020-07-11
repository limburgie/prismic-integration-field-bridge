package be.webfactor.pifb.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PrismicApiResponse {

	public static final int MAX_RESULTS_PER_PAGE = 50;

	@JsonProperty("results_size")
	private int resultCount;

	private List<PrismicApiResult> results;
}
