package se.sundsvall.garbage.integration.filehandler;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.VFS;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import se.sundsvall.garbage.api.model.FacilityCategory;
import se.sundsvall.garbage.integration.db.entity.GarbageScheduleEntity;

@Component
@EnableConfigurationProperties(SftpProperties.class)
public class FileHandler {
    
    private final static String TEMP_FILE = System.getProperty("user.dir") + "/schedule.csv";
    private static final Logger log = LoggerFactory.getLogger(FileHandler.class);
    private final SftpProperties sftpProperties;
    
    public FileHandler(SftpProperties sftpProperties) {
        this.sftpProperties = sftpProperties;
    }
    
    public void downloadFile() {
        try {
            var manager = VFS.getManager();
            var local = manager.resolveFile(TEMP_FILE);
            var remote = manager.resolveFile("sftp://" + sftpProperties.getUsername()
                                             + ":" + sftpProperties.getPassword()
                                             + "@" + sftpProperties.getRemoteHost()
                                             + "/" + sftpProperties.getFilename());
            local.copyFrom(remote, Selectors.SELECT_SELF);
            local.close();
            remote.close();
            
        } catch (FileSystemException e) {
            log.info("Something went wrong downloading file", e);
        }
    }
    
    public List<GarbageScheduleEntity> parseFile() {
        var csvMapper = new CsvMapper();
        var schema = buildSchema();
        
        try (MappingIterator<ParsedRow> it =
                 csvMapper.readerFor(ParsedRow.class)
                     .with(schema)
                     .readValues(new FileReader(TEMP_FILE, StandardCharsets.ISO_8859_1))) {
            var result = it.readAll().stream()
                .filter(parsedRow -> !parsedRow.getDriveSchedule().startsWith("SL"))
                .map(this::mapToEntity)
                .toList();
            new File(TEMP_FILE).delete();
            return result;
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
