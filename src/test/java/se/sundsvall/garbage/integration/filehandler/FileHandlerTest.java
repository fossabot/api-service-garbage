package se.sundsvall.garbage.integration.filehandler;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import se.sundsvall.garbage.api.model.FacilityCategory;

@ExtendWith(MockitoExtension.class)
class FileHandlerTest {
    
    FileHandler fileHandler = new FileHandler();
    
    @Test
    void parseFile() {
        var multipartFile = new MockMultipartFile("sourceFile.csv", ("Testgatan;12;;VI;357;KP50MÅ2;85185 ;Sundsvall\nTestgatan;13;;VI;357;KP50FR2;85185 ;Sundsvall").getBytes(StandardCharsets.ISO_8859_1));
    
        var result = fileHandler.parseFile(multipartFile);
    
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
    
        var firstRow = result.get(0);
        assertThat(firstRow.getDriveSchedule()).isEqualTo("KP50MÅ2");
        assertThat(firstRow.getId()).isNull();
        assertThat(firstRow.getCity()).isEqualTo("Sundsvall");
        assertThat(firstRow.getStreet()).isEqualTo("Testgatan");
        assertThat(firstRow.getHouseNumber()).isEqualTo("12");
        assertThat(firstRow.getPostalCode()).isEqualTo("85185 ");
        assertThat(firstRow.getCity()).isEqualTo("Sundsvall");
        assertThat(firstRow.getFacilityCategory()).isEqualTo(FacilityCategory.VILLA);
        assertThat(firstRow.getAdditionalInformation()).isEmpty();
    }
    
    @Test
    void parseFileAndExpectError(){
     
     var result = fileHandler.parseFile(null);
     
     assertThat(result).isNull();
        
    }
}