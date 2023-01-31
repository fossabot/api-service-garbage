package se.sundsvall.garbage.api.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseRequest {
    
    @Schema(description = "Page number", example = "1", defaultValue = "1")
    @Min(1)
    private int page = 1;
    
    @Schema(description = "Result size per page", example = "100", defaultValue = "20")
    @Min(1)
    @Max(1000)
    private int limit = 20;
}
