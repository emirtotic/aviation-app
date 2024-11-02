package com.aviationeventsservice.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(schema = "aviation-events", name = "events")
public class AviationEvent {

    @Id
    private long id;
    private String date;
    @Lob
    private String events;

    @Override
    public String toString() {
        return "AviationEvent{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", events='" + events + '\'' +
                '}';
    }
}
