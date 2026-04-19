package com.bytecodes.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeasonResponseDTO {
       private Integer year;
        private String startDate;
        private String endDate;
        private boolean current;
}

