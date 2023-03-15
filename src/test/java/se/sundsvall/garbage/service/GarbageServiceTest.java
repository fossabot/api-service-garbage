package se.sundsvall.garbage.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static se.sundsvall.garbage.TestDataFactory.buildGarbageScheduleEntity;
import static se.sundsvall.garbage.TestDataFactory.buildGarbageScheduleRequest;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import se.sundsvall.garbage.api.model.FacilityCategory;
import se.sundsvall.garbage.api.model.GarbageScheduleResponse;
import se.sundsvall.garbage.integration.db.GarbageScheduleRepository;
import se.sundsvall.garbage.integration.db.entity.GarbageScheduleEntity;
import se.sundsvall.garbage.integration.db.specification.GarbageScheduleSpecification;
import se.sundsvall.garbage.integration.filehandler.FileHandler;
import se.sundsvall.garbage.service.mapper.Mapper;

@ExtendWith(MockitoExtension.class)
class GarbageServiceTest {
    
    private final Page<GarbageScheduleEntity> garbageSchedulePage = new PageImpl<>(Collections.singletonList(buildGarbageScheduleEntity()));
    @Mock
    private Specification<GarbageScheduleEntity> mockSpecification;
    @Mock
    private GarbageScheduleRepository repository;
    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private Mapper mapper;
    @Mock
    private FileHandler fileHandler;
    @Mock
    private GarbageScheduleSpecification garbageScheduleSpecification;
    @InjectMocks
    private GarbageService service;
    
    @Test
    void getGarbageSchedule() {
        var request = buildGarbageScheduleRequest();
        
        when(garbageScheduleSpecification.createGarbageScheduleSpecification(any())).thenReturn(mockSpecification);
        when(repository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(garbageSchedulePage);
        
        var result = service.getGarbageSchedules(request);
        
        assertThat(result).isNotNull();
        assertThat(result.get(0).getGarbageScheduledDay()).isEqualTo(GarbageScheduleResponse.WeekDay.FRIDAY);
        assertThat(result.get(0).getGarbageScheduledWeek()).isEqualTo(GarbageScheduleResponse.Week.ODD);
        assertThat(result.get(0).getAdditionalInformation()).isEqualTo(request.getAdditionalInformation());
        assertThat(result.get(0).getAddress().getPostalCode()).isEqualTo(request.getPostalCode());
        assertThat(result.get(0).getAddress().getStreet()).isEqualTo(request.getStreet());
        assertThat(result.get(0).getAddress().getHouseNumber()).isEqualTo(request.getHouseNumber());
        assertThat(result.get(0).getAddress().getCity()).isEqualTo(request.getCity());
        assertThat(result.get(0).getFacilityCategory()).isEqualTo(FacilityCategory.VILLA);
        
        verifyNoInteractions(fileHandler);
        verify(repository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        verify(mapper, times(1)).entityToResponse(any());
        verifyNoMoreInteractions(mapper);
        verifyNoMoreInteractions(repository);
    }
    
    
    @Test
    void updateGarbageSchedules() {
        
        service.updateGarbageSchedules();
        
        verify(fileHandler, times(1)).downloadFile();
        verify(fileHandler, times(1)).parseFile();
        verify(repository, times(1)).deleteAllInBatch();
        verify(repository, times(1)).saveAll(any());
        verifyNoMoreInteractions(fileHandler);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
        
    }
}