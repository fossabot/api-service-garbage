package se.sundsvall.garbage.integration.filehandler;

import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import se.sundsvall.garbage.api.model.FacilityCategory;
import se.sundsvall.garbage.integration.db.entity.GarbageScheduleEntity;

@Component
public class FileHandler {
    
    private static final Logger log = LoggerFactory.getLogger(FileHandler.class);
    
    public List<GarbageScheduleEntity> parseFile(MultipartFile newFile) {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = buildSchema();
        
        try (MappingIterator<ParsedRow> it =
                 csvMapper.readerFor(ParsedRow.class)
                     .with(schema)
                     .readValues(new String(newFile.getBytes(), StandardCharsets.ISO_8859_1))) {
            
            return it.readAll().stream()
                .filter(parsedRow -> !parsedRow.getDriveSchedule().startsWith("SL"))
                .map(this::mapToEntity)
                .toList();
            
        } catch (Exception e) {
            log.info("Something went wrong parsing file", e);
            return null;
        }
    }
    
    private GarbageScheduleEntity mapToEntity(ParsedRow row) {
        return GarbageScheduleEntity.builder()
            .withStreet(row.getAddress())
            .withHouseNumber(row.getAdressNumber())
            .withPostalCode(row.getZipcode())
            .withCity(row.getCity())
            .withAdditionalInformation(row.getAdditionalInformation())
            .withFacilityCategory(FacilityCategory.forValue(row.getFacilityCategory()))
            .withDriveSchedule(row.getDriveSchedule())
            .build();
    }
    
    private CsvSchema buildSchema() {
        return CsvSchema.builder()
            .addColumn("address")
            .addColumn("adressNumber")
            .addColumn("additionalInformation")
            .addColumn("facilityCategory")
            .addColumn("ignored")
            .addColumn("driveSchedule")
            .addColumn("zipcode")
            .addColumn("city")
            .build().withColumnSeparator(';');
    }
}
