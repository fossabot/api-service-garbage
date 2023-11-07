package se.sundsvall.garbage.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static se.sundsvall.garbage.TestDataFactory.buildGarbageScheduleRequest;
import static se.sundsvall.garbage.TestDataFactory.buildGarbageScheduleResponse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.sundsvall.garbage.api.model.FacilityCategory;
import se.sundsvall.garbage.api.model.GarbageScheduleResponse;
import se.sundsvall.garbage.service.GarbageService;

@ExtendWith(MockitoExtension.class)
class GarbageResourceTest {

	@Mock
	private GarbageService service;

	@InjectMocks
	private GarbageResource resource;

	@Test
	void getGarbage() {
		final var response = buildGarbageScheduleResponse();
		final var request = buildGarbageScheduleRequest();
		when(service.getGarbageSchedules(any())).thenReturn(response);

		final var result = resource.getGarbage(request);

		assertThat(result).isNotNull();
		assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
		assertThat(result.getBody()).isNotNull();
		assertThat(request.getPage()).isEqualTo(1);
		assertThat(request.getLimit()).isEqualTo(10);

		final var resultBody = result.getBody().get(0);
		assertThat(resultBody.getAdditionalInformation()).isNull();
		assertThat(resultBody.getGarbageScheduledWeek()).isEqualTo(GarbageScheduleResponse.Week.EVEN);
		assertThat(resultBody.getGarbageScheduledDay()).isEqualTo(GarbageScheduleResponse.WeekDay.TUESDAY);
		assertThat(resultBody.getFacilityCategory()).isEqualTo(FacilityCategory.VILLA);
		assertThat(resultBody.getAddress().getStreet()).isEqualTo(request.getStreet());
		assertThat(resultBody.getAddress().getPostalCode()).isEqualTo(request.getPostalCode());
		assertThat(resultBody.getAddress().getHouseNumber()).isEqualTo(request.getHouseNumber());
		assertThat(resultBody.getAddress().getCity()).isEqualTo(request.getCity());

		verify(service, times(1)).getGarbageSchedules(any());
		verifyNoMoreInteractions(service);

	}
}
