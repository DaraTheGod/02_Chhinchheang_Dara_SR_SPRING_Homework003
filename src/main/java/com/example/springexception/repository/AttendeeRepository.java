package com.example.springexception.repository;

import com.example.springexception.model.entity.AttendeeModel;
import com.example.springexception.model.request.AttendeeCreateRequest;
import com.example.springexception.model.request.AttendeeUpdateRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AttendeeRepository {
    @Results(id = "attendeeMapper", value = {
            @Result(property = "attendeeId", column = "attendee_id"),
            @Result(property = "attendeeName", column = "attendee_name"),
    })

    @Select("select * from attendees limit #{size} offset #{offset}")
    List<AttendeeModel> getAllAttendees(Integer offset, Integer size);

    @ResultMap("attendeeMapper")
    @Select("select attendee_id, attendee_name, email from attendees where attendee_id = #{id}")
    AttendeeModel getAttendeeById(Long attendeeId);

    @ResultMap("attendeeMapper")
    @Select("""
       insert into attendees values (default, #{req.attendeeName}, #{req.email})
       returning attendee_id, attendee_name, email;
    """)
    AttendeeModel saveAttendee(@Param("req") AttendeeCreateRequest request);

    @ResultMap("attendeeMapper")
    @Select("""
       update attendees set attendee_name = #{req.attendeeName}
                     where attendee_id = #{attendeeId}
       returning attendee_id, attendee_name, email;
    """)
    AttendeeModel updateAttendeeById(Long attendeeId, @Param("req") AttendeeUpdateRequest request);

    @Select("""
        select count(*) > 0 from attendees
        where attendee_name = #{attendeeName}
    """)
    boolean existsByName(String attendeeName);

    @Select("""
        select count(*) > 0 from attendees
        where email = #{email}
    """)
    boolean existsByEmail(String email);

    @Select("""
        select count(*) > 0 from attendees where attendee_id = #{attendeeId} and attendee_name not like #{attendeeName};
    """)
    boolean getNameById( String attendeeName, Long attendeeId);

    @ResultMap("attendeeMapper")
    @Select("""
       delete from attendees where attendee_id = #{attendeeId}
       returning attendee_id, attendee_name, email;
    """)
    AttendeeModel deleteAttendeeById(Long attendeeId);
}
