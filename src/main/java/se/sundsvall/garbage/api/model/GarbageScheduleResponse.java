package se.sundsvall.garbage.api.model;

import java.util.Arrays;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GarbageScheduleResponse {
    @Schema(description = "Address")
    private Address address;
    @Schema(description = "Any other identifier. Example HouseLetter or building clarification", example = "A")
    private String additionalInformation;
    @Schema(description = "What weekday garbage is collected ", example = "MONDAY")
    private WeekDay garbageScheduledDay;
    @Schema(description = "What week garbage is collected. Odd/even numbers ", example = "ODD")
    private Week garbageScheduledWeek;
    @Schema(description = "Which type of facility ", example = "VILLA")
    private FacilityCategory facilityCategory;
    
    public enum Week {
        ODD,
        EVEN
    }
    
    @Getter
    @AllArgsConstructor
    public enum WeekDay {
        MONDAY("MO"),
        TUESDAY("TI"),
        WEDNESDAY("ON"),
        THURSDAY("TO"),
        FRIDAY("FR");
        
        final String shortCode;
        
        public static WeekDay forValue(final String shortCode) {
            return Arrays.stream(WeekDay.values())
                .filter(weekday -> shortCode.equals(weekday.shortCode))
                .findFirst()
                .orElse(null);
        }
    }
    
    
    
}
