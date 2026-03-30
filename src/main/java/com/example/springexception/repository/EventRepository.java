package com.example.springexception.repository;

import com.example.springexception.model.entity.EventModel;
import com.example.springexception.model.request.EventRequest;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface EventRepository {
    @Results(id = "eventMapper", value = {
            @Result(property = "eventId", column = "event_id"),
            @Result(property = "eventName", column = "event_name"),
            @Result(property = "eventDate", column = "event_date"),
            @Result(property = "venue", column = "venue_id",
                    one = @One(select = "com.example.springexception.repository.VenueRepository.getVenueById")),
            @Result(property = "attendees", column = "event_id",
                    many = @Many(select = "com.example.springexception.repository.EventAttendeeRepository.getAttendeesByEventId"))
    })

    @Select("""
        select * from events limit #{size} offset #{offset}
    """)
    List<EventModel> getAllEvents(int offset, Integer size);

    @ResultMap("eventMapper")
    @Select("select * from events where event_id = #{eventId}")
    EventModel getEventById(Long eventId);

    @ResultMap("eventMapper")
    @Select("""
      insert into events values (default, #{req.eventName}, #{req.eventDate}, #{req.venueId})
      returning *;
    """)
    EventModel saveEvent(@Param("req") EventRequest request);

    @Select("""
      SELECT COUNT(*) > 0 FROM events WHERE event_name = #{eventName} AND event_date = #{eventDate}
    """)
    boolean existsByEventNameAndEventDate(String eventName, LocalDate eventDate);

    @ResultMap("eventMapper")
    @Select("""
      update events set
          event_name = #{req.eventName},
          event_date = #{req.eventDate},
          venue_id = #{req.venueId}
      where event_id = #{eventId}
      returning *
    """)
    void updateEventById(Long eventId, @Param("req") EventRequest request);

    @ResultMap("eventMapper")
    @Select("""
      delete from events where event_id = #{eventId}
      returning *
    """)
    EventModel deleteEventById(Long eventId);
}
