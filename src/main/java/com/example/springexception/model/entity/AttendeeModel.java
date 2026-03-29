package com.example.springexception.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendeeModel {
    private Long attendeeId;
    private String attendeeName;
    private String email;
}
