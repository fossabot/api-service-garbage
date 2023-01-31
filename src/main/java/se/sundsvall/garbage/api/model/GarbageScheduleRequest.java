package se.sundsvall.garbage.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor
public class GarbageScheduleRequest extends BaseRequest {
    
    @Schema(description = "Address", nullable = true, example = "Testgatan")
    private String street;
    @Schema(description = "Address", nullable = true, example = "12")
    private String houseNumber;
    @Schema(description = "Zipcode", nullable = true, example = "85731")
    private String postalCode;
    @Schema(description = "City", nullable = true, example = "Sundsvall")
    private String city;
    @Schema(description = "Any other identifier. Example HouseLetter or building clarification",
        example = "A", nullable = true)
    private String additionalInformation;
}
