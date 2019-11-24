package com.example.household_ledger;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FirebasePost {
    public String id;

    //
    public String name;
    public String gender;


    public String time;
    public String pay;
    public String place;
    public String memo;


    //좌표 정보 저장
    public String Position;
    public Double Latitude;
    public Double Longitude;

    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }
    //일정 정보 데이터베이스 입력
    public FirebasePost(String id, String time, String pay, String place, String memo) {
        this.id = id;
        this.time =  time;
        this.pay = pay;
        this.place = place;
        this.memo = memo;
    }
    //좌표 정보 데이터베이스 입력
    public FirebasePost(String Position, Double Lat, Double Lon){
        this.Position = Position;
        this.Latitude = Lat;
        this.Longitude = Lon;
    }

    //Map<Str, Obj> 형태 반환하는 함수
    @Exclude
    public Map<String, Object> toMap() {
        //
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("time", time);
        result.put("pay", pay);
        result.put("place", place);
        result.put("memo", memo);
        return result;
    }

    @Exclude
    public Map<String, Object> toMap_pos() {
        //
        HashMap<String, Object> result = new HashMap<>();
        result.put("position", Position);
        result.put("latitude", Latitude);
        result.put("longitute", Longitude);
        return result;
    }
}