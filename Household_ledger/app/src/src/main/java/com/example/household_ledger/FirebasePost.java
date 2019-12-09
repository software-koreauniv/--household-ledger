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


    public String date;
    public String timePayPlace;
    public String timeInc;

    public String paySum;
    public String incSum;

    //public String time;
    //public String pay;
    //public String place;
    public String cardNum;
    public String card;

    public String month;
    public String monthPSum;
    public String monthISum;



    //public String income;


    //좌표 정보 저장
    public String Position;
    public Double Latitude;
    public Double Longitude;

    public FirebasePost(){
        // Default constructor required for calls to DataSnapshot.getValue(FirebasePost.class)
    }
    //현 지출 정보 데이터베이스 입력 (시간 지출 장소)
    public FirebasePost(String id, String date, String timePayPlace, String paySum){
        this.id = id;
        this.date = date;
        this.timePayPlace = timePayPlace;
        this.paySum = paySum;
    }
    //월 지출 합산
    public FirebasePost(String id, String month, String monthPSum){
        this.id = id;
        this.month = month;
        this.monthPSum = monthPSum;
    }


    /*
    //현 수입 정보 데이터베이스 입력 (시간 수입)
    public FirebasePost(String id, String date, String timeInc){
        this.id = id;
        this.date = date;
        this.timeInc = timeInc;
    }
    */
     /*
    //해당 날 거래 정보 데이터베이스 입력 (시간 지출 장소)
    public FirebasePost(String id, String date, String incSum){
        this.id = id;
        this.date = date;
        this.incSum = incSum;
    }
    */



    /*
    //일정 정보 데이터베이스 입력
    public FirebasePost(String id, String time, String pay, String place, String memo) {
        this.id = id;
        this.time =  time;
        this.pay = pay;
        this.place = place;
        //this.memo = memo;
    }
*/

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
        result.put("date", date);
        result.put("timePayPlace", timePayPlace);
        result.put("paySum", paySum);
        return result;
    }
    @Exclude
    public Map<String, Object> toMap_month() {
        //
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("date", month);
        result.put("monthPSum", monthPSum);
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