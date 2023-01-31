package se.sundsvall.garbage.service;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import se.sundsvall.garbage.api.model.GarbageScheduleRequest;
import se.sundsvall.garbage.api.model.GarbageScheduleResponse;
import se.sundsvall.garbage.integration.db.GarbageScheduleRepository;
import se.sundsvall.garbage.integration.db.specification.GarbageScheduleSpecification;
import se.sundsvall.garbage.integration.filehandler.FileHandler;
import se.sundsvall.garbage.service.mapper.Mapper;

@Service
public class GarbageService {
    
    private final GarbageScheduleRepository repository;
    private final Mapper mapper;
    private final FileHandler fileHandler;
    private final GarbageScheduleSpecification garbageScheduleSpecification;
    
    
    public GarbageService(GarbageScheduleRepository repository, Mapper mapper, FileHandler fileHandler, GarbageScheduleSpecification garbageScheduleSpecification) {
        this.repository = repository;
        this.mapper = mapper;
        this.fileHandler = fileHandler;
        this.garbageScheduleSpecification = garbageScheduleSpecification;
    }
    
    public List<GarbageScheduleResponse> getGarbageSchedules(GarbageScheduleRequest request) {
        return repository.findAll(garbageScheduleSpecification.createGarbageScheduleSpecification(request), getPagingParameters(request))
            .stream()
            .map(mapper::entityToResponse)
            .toList();
    }
    
    public void updateGarbageSchedules(MultipartFile file) {
        repository.deleteAllInBatch();
        repository.saveAll(fileHandler.parseFile(file));
        
    }
    
    private Pageable getPagingParameters(GarbageScheduleRequest request) {
        return PageRequest.of(request.getPage() - 1, request.getLimit());
    }
    
}
