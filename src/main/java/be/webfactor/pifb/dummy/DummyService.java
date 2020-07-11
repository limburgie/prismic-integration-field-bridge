package be.webfactor.pifb.dummy;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import be.webfactor.pifb.domain.PrismicApiResponse;
import be.webfactor.pifb.domain.PrismicApiResult;
import be.webfactor.pifb.service.PrismicApiService;

@Service
public class DummyService implements PrismicApiService {

	public static final String ACCESS_TOKEN = "secure";
	private static final int TOTAL_COUNT = 120;

	private List<PrismicApiResult> results;

	@PostConstruct
	public void init() {
		results = new ArrayList<>();

		for (int i = 1; i <= TOTAL_COUNT; i++) {
			PrismicApiResult result = new PrismicApiResult();

			result.setId(String.valueOf(i));
			result.setTitle("Title " + i);
			result.setDescription("Description " + i);
			result.setImageUrl("https://via.placeholder.com/150?text=" + i);
			result.setModifiedDate(LocalDateTime.of(2019, Month.JANUARY, 1, 0, 0).plusDays(i).toEpochSecond(ZoneOffset.UTC));

			DummyObject dummyObject = new DummyObject();
			dummyObject.setId(i);
			dummyObject.setName("Name " + i);

			result.setBlob(dummyObject);

			results.add(result);
		}
	}

	public PrismicApiResponse getResponse(int page) {
		PrismicApiResponse response = new PrismicApiResponse();

		response.setResultCount(TOTAL_COUNT);

		if (page < 1 || (page - 1) * PrismicApiResponse.MAX_RESULTS_PER_PAGE > TOTAL_COUNT) {
			response.setResults(Collections.emptyList());

			return response;
		}

		int fromIndex = (page - 1) * PrismicApiResponse.MAX_RESULTS_PER_PAGE;
		int toIndex = Math.min(TOTAL_COUNT, page * PrismicApiResponse.MAX_RESULTS_PER_PAGE);

		response.setResults(results.subList(fromIndex, toIndex));

		return response;
	}
}
