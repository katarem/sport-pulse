package com.bytecodes.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeagueResponse

 {
     private int id;
     private String name;
     private String type;
     private String country;
     private String logo;
     private Integer currentSeason;
     private String startDate;
     private String endDate;

         }

