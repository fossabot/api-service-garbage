package se.sundsvall.garbage.integration.filehandler;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;


@JsonIgnoreProperties(ignoreUnknown=true)
public class ParsedRow {
  
    private String address;
    private String adressNumber;
    private String additionalInformation;
    private String facilityCategory;
    private String driveSchedule;
    private String zipcode;
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public String getAdressNumber() {
        return adressNumber;
    }
    
    public void setAdressNumber(String adressNumber) {
        this.adressNumber = adressNumber;
    }
    
    public String getAdditionalInformation() {
        return additionalInformation;
    }
    
    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
    
    public String getFacilityCategory() {
        return facilityCategory;
    }
    
    public void setFacilityCategory(String facilityCategory) {
        this.facilityCategory = facilityCategory;
    }
    
    public String getDriveSchedule() {
        return driveSchedule;
    }
    
    public void setDriveSchedule(String driveSchedule) {
        this.driveSchedule = driveSchedule;
    }
    
    public String getZipcode() {
        return zipcode;
    }
    
    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    private String city;
}
