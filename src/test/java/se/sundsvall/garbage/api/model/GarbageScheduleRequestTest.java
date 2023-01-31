package se.sundsvall.garbage.api.model;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class GarbageScheduleRequestTest {
    
    @Test
    void GetterAndSetters(){
        
        var request = GarbageScheduleRequest.builder()
            .withStreet("SomeStreet")
            .withHouseNumber("2")
            .withPostalCode("12345")
            .withCity("someCity")
            .withAdditionalInformation("A").build();
        
        request.setPage(1);
        request.setLimit(10);
        
        assertThat(request.getPage()).isEqualTo(1);
        assertThat(request.getLimit()).isEqualTo(10);
        assertThat(request.getStreet()).isEqualTo("SomeStreet");
        assertThat(request.getPostalCode()).isEqualTo("12345");
        assertThat(request.getCity()).isEqualTo("someCity");
        assertThat(request.getAdditionalInformation()).isEqualTo("A");
        assertThat(request.getHouseNumber()).isEqualTo("2");
    
    }
    
}