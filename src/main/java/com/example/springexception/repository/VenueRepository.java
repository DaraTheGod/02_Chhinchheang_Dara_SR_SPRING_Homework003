package com.example.springexception.repository;

import com.example.springexception.model.entity.VenueModel;
import com.example.springexception.model.request.VenueRequest;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface VenueRepository {
    @Results(id = "venueMapper", value = {
            @Result(property = "venueId", column = "venue_id"),
            @Result(property = "venueName", column = "venue_name")
    })

    @Select("select * from venues LIMIT #{size} OFFSET #{offset}")
    List<VenueModel> getAllVenues(@Param("offset") int offset, @Param("size") Integer size);

    @ResultMap("venueMapper")
    @Select("select * from venues WHERE venue_id = #{venueId}")
    VenueModel getVenueById(Long venueId);

    @ResultMap("venueMapper")
    @Select("""
       insert into venues (venue_name, location)
       values (#{req.venueName}, #{req.location})
       returning venue_id, venue_name, location
    """)
    VenueModel saveVenue(@Param("req") VenueRequest request);

    @ResultMap("venueMapper")
    @Select("""
       update venues set venue_name = #{req.venueName}, location = #{req.location}
       where venue_id = #{venueId}
       returning venue_id, venue_name, location
    """)
    VenueModel updateVenueById(@Param("venueId") Long venueId, @Param("req") VenueRequest request);

    @ResultMap("venueMapper")
    @Select("""
       delete from venues where venue_id = #{venueId}
       returning venue_id, venue_name, location
    """)
    VenueModel deleteVenueById(Long venueId);

    @Select("select count(*) > 0 from venues where venue_name = #{venueName}")
    boolean existsByName(String venueName);

    @Select("""
        select count(*) > 0 from venues where venue_name = #{venueName} and venue_id != #{venueId}
    """)
    boolean existsByNameAndIdNot(@Param("venueName") String venueName, @Param("venueId") Long venueId);
}