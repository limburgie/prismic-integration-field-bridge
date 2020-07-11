package be.webfactor.pifb.service;

import be.webfactor.pifb.domain.PrismicApiResponse;

public interface PrismicApiService {

	PrismicApiResponse getResponse(int page);
}
