package com.example.dul.foododering.Remote;




import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


import com.example.dul.foododering.Model.MyResponse;
import com.example.dul.foododering.Model.Sender;

public interface APIService {

    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAAxQNTZX4:APA91bF6QVM3CvEgRIFXWqr9WBkkb4D-zFTIfrFKDSlNgeG9LNYv2bEkKIJVlTul8IlE6G7vU4fZmaoZZlJVTWmMR7p-JtU2TbLdHduMg0IWjGg7cYvdwJehO_TNPNORnydKA-F5y1XO"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification (@Body Sender body);

}
