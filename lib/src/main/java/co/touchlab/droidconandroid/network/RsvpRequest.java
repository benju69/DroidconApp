package co.touchlab.droidconandroid.network;

import co.touchlab.droidconandroid.network.dao.MyRsvpResponse;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by kgalligan on 7/20/14.
 */
public interface RsvpRequest
{
    @FormUrlEncoded
    @POST("/dataTest/rsvpEvent/{eventId}")
    BasicIdResult addRsvp(@Path("eventId") Long eventId, @Field("rsvpUuid") String rsvpUuid);

    @FormUrlEncoded
    @POST("/dataTest/unRsvpEvent/{eventId}")
    Response removeRsvp(@Path("eventId") Long eventId, @Field("dummy") String justFiller);

    @GET("/dataTest/allRsvps/{conventionId}")
    MyRsvpResponse getMyRsvps(@Path("conventionId") Long conventionId)throws Exception;
}
