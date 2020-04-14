package org.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {

    private UUID id;

    @JsonProperty("event_date")
    private Date eventDate;

    private String description;

    private Priority priority;

}
