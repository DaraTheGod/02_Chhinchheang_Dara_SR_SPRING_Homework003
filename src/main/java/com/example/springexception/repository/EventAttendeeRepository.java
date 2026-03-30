package com.example.springexception.repository;

import com.example.springexception.model.entity.AttendeeModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface EventAttendeeRepository {
    @Results(id = "attendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name"),
    })

    @Select("""
       select a.attendee_id, a.attendee_name, a.email from attendees a
          join event_attendee ea on a.attendee_id = ea.attendee_id
          where ea.event_id = #{eventId}
    """)
    List<AttendeeModel> getAttendeesByEventId(Long eventId);

    @Insert("""
      insert into event_attendee values (#{attendeeId}, #{eventId})
    """)
    void saveEventAndAttendee(Long attendeeId, Long eventId);

    @Delete("""
      delete from event_attendee
      where event_id = #{eventId}
    """)
    void deleteAllByEventId(Long eventId);
}
