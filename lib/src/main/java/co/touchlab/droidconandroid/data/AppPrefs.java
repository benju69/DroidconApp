package co.touchlab.droidconandroid.data;

import android.content.Context;
import android.content.SharedPreferences;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Created by kgalligan on 6/28/14.
 */
public class AppPrefs
{
    public static final String USER_UUID        = "USER_UUID";
    public static final String USER_ID          = "USER_ID";
    public static final String SEEN_WELCOME     = "seen_welcome";
    public static final String AVATAR_KEY       = "avatar_key";
    public static final String NAME             = "name";
    public static final String EMAIL            = "email";
    public static final String EVENTBRITE_EMAIL            = "EVENTBRITE_EMAIL";
    public static final String CAN_VOTE            = "can_vote";
    public static final String COVER_KEY        = "cover_key";
    public static final String CONVENTION_START = "convention_start";
    public static final String CONVENTION_END  = "convention_end";
    public static final String REFRESH_TIME    = "refresh_time";
    public static final String MY_RSVPS_LOADED = "myrsvps3";
    public static final String VOTE_INTRO      = "vote_intro";
    public static final String ALLOW_NOTIFS    = "allow_notifs";
    public static final String SHOW_NOTIF_CARD = "show_notif_card";
    public static final String VIDEO_DEVICE_ID = "VIDEO_DEVICE_ID";
    public static final String SHOW_SLACK_DIALOG = "show_slack_dialog";

    private static AppPrefs instance;

    private SharedPreferences prefs;

    @NotNull
    public static synchronized AppPrefs getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new AppPrefs();
            instance.prefs = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE);
        }

        return instance;
    }

    public boolean once(String key)
    {
        Boolean shouldOnce = getBoolean(key, true);
        setBoolean(key, false);
        return shouldOnce;
    }

    public boolean isMyRsvpsLoaded()
    {
        return getBoolean(MY_RSVPS_LOADED, false);
    }

    public void setMyRsvpsLoaded(boolean b)
    {
        setBoolean(MY_RSVPS_LOADED, b);
    }

    public boolean isLoggedIn()
    {
        return StringUtils.isNoneEmpty(getUserUuid());
    }

    public String getUserUuid()
    {
        return getString(USER_UUID, null);
    }

    public void setUserUuid(String uuid)
    {
        setString(USER_UUID, uuid);
    }

    public Long getUserId()
    {
        long id = getLong(USER_ID, - 1l);
        return id == -1 ? null : id;
    }

    public void setUserId(Long id)
    {
        setLong(USER_ID, id);
    }

    public boolean getHasSeenWelcome()
    {
        return getBoolean(SEEN_WELCOME, false);
    }

    public void setHasSeenWelcome()
    {
        setBoolean(SEEN_WELCOME, true);
    }

    public void setAvatarKey(String key)
    {
        setString(AVATAR_KEY, key);
    }

    public String getAvatarKey()
    {
        return getString(AVATAR_KEY, null);
    }

    public void setName(String name)
    {
        setString(NAME, name);
    }

    public String getName()
    {
        return getString(NAME, null);
    }

    public void setEmail(String email)
    {
        setString(EMAIL, email);
    }

    public String getEmail()
    {
        return getString(EMAIL, null);
    }

    public void setEventbriteEmail(String email)
    {
        if(StringUtils.isEmpty(email))
            prefs.edit().remove(EVENTBRITE_EMAIL).apply();
        else
            setString(EVENTBRITE_EMAIL, email);
    }

    public String getEventbriteEmail()
    {
        String email = getString(EVENTBRITE_EMAIL, null);
        if(StringUtils.isEmpty(email))
        {
            email = getEmail();
        }
        return email;
    }

    public boolean canUserVote()
    {
        return getBoolean(CAN_VOTE, false);
    }

    public void setCanUserVote(boolean canVote)
    {
        setBoolean(CAN_VOTE, canVote);
    }

    public void setCoverKey(String key)
    {
        setString(COVER_KEY, key);
    }

    public String getCoverKey()
    {
        return getString(COVER_KEY, null);
    }

    public void setConventionStartDate(@NotNull String startDate)
    {
        setString(CONVENTION_START, startDate);
    }

    public String getConventionStartDate()
    {
        return getString(CONVENTION_START, null);
    }

    public void setConventionEndDate(@NotNull String endDate)
    {
        setString(CONVENTION_END, endDate);
    }

    public String getVideoDeviceId()
    {
        String deviceId = getString(VIDEO_DEVICE_ID, null);
        if(deviceId == null)
        {
            deviceId = UUID.randomUUID().toString();
            setString(VIDEO_DEVICE_ID, deviceId);
        }
        return deviceId;
    }

    public String getConventionEndDate()
    {
        return getString(CONVENTION_END, null);
    }

    public void setRefreshTime(@NotNull long time)
    {
        setLong(REFRESH_TIME, time);
    }

    public long getRefreshTime()
    {
        return prefs.getLong(REFRESH_TIME, 0);
    }

    public boolean getShowSlackDialog()
    {
        return prefs.getBoolean(SHOW_SLACK_DIALOG, true);
    }

    public void setShowSlackDialog(boolean show)
    {
        setBoolean(SHOW_SLACK_DIALOG, show);
    }

    public boolean getSeenVoteIntro(){return prefs.getBoolean(VOTE_INTRO, false);}
    public void setSeenVoteIntro(boolean seen){prefs.edit().putBoolean(VOTE_INTRO, seen).apply();}

    public boolean getAllowNotifications(){return prefs.getBoolean(ALLOW_NOTIFS, false);}
    public void setAllowNotifications(boolean allow){prefs.edit().putBoolean(ALLOW_NOTIFS, allow).apply();}

    public boolean getShowNotifCard(){return prefs.getBoolean(SHOW_NOTIF_CARD, true);}
    public void setShowNotifCard(boolean show){prefs.edit().putBoolean(SHOW_NOTIF_CARD, show).apply();}

    //helper methods
    private void setBoolean(String key, Boolean value)
    {
        prefs.edit().putBoolean(key, value).apply();
    }

    private Boolean getBoolean(String key, Boolean defaultVal)
    {
        return prefs.getBoolean(key, defaultVal);
    }

    private void setString(String key, String value)
    {
        prefs.edit().putString(key, value).apply();
    }

    private String getString(String key, String defaultVal)
    {
        return prefs.getString(key, defaultVal);
    }

    private void setInt(String key, Integer value)
    {
        prefs.edit().putInt(key, value).apply();
    }

    private Integer getInt(String key, Integer defaultVal)
    {
        return prefs.getInt(key, defaultVal);
    }

    private void setLong(String key, Long value)
    {
        prefs.edit().putLong(key, value).apply();
    }

    private Long getLong(String key, Long defaultVal)
    {
        return prefs.getLong(key, defaultVal);
    }
}
