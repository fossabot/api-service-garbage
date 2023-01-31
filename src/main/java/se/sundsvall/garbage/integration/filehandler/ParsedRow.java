package se.sundsvall.garbage.integration.filehandler;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown=true)
public class ParsedRow {
  
    private String address;
    private String adressNumber;
    private String additionalInformation;
    private String facilityCategory;
    private String driveSchedule;
    private String zipcode;
    private String city;
}
