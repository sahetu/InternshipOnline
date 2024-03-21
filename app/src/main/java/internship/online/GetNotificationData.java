package internship.online;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetNotificationData {

    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("NotificationData")
    @Expose
    public List<GetNotificationResponse> notificationData;

    public class GetNotificationResponse {
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("created_date")
        @Expose
        public String createdDate;
    }
}
