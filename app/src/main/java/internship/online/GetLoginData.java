package internship.online;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetLoginData {

    @SerializedName("Status")
    @Expose
    public Boolean status;
    @SerializedName("Message")
    @Expose
    public String message;
    @SerializedName("UserData")
    @Expose
    public List<GetUserData> userData;

    public class GetUserData {
        @SerializedName("userid")
        @Expose
        public String userid;
        @SerializedName("userName")
        @Expose
        public String userName;
        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("gender")
        @Expose
        public String gender;
        @SerializedName("city")
        @Expose
        public String city;
    }
}
