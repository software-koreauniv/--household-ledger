package com.example.household_ledger;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference mPostReference;

    String pay;
    String place;
    String memo;


    String id;
    String time;
    String sort = "time";

    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayInfo =  new ArrayList<String>();
    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getFirebaseDatabase();
    }

    //데이터 송신
    public void postFirebaseDatabase(boolean add){
        //데이터베이스 입력인데, 해당 엑티비티에서는 입력하지 않음.
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add) {
            //FirebasePost클래스의 객체 post선언 (스트링 입력)
            FirebasePost post = new FirebasePost(id, time, pay, place, memo);
             postValues = post.toMap();
        }
        childUpdates.put("/id_list/" + time, postValues);
        mPostReference.updateChildren(childUpdates);
    }

    //데이터 수신
    public void getFirebaseDatabase(){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("getFirebaseDatabase", "key: " + dataSnapshot.getChildrenCount());
                arrayData.clear();
                arrayIndex.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    String key = postSnapshot.getKey();
                    FirebasePost get = postSnapshot.getValue(FirebasePost.class);

                    String[] info = {get.id, get.time, get.pay, get.place, get.memo};
                    String Result = setTextLength(info[0],10) + setTextLength(info[1],10) + setTextLength(info[2],10) + setTextLength(info[3],10) + setTextLength(info[4],10);
                    String Idnum = info[0];
                    //주소 저장
                    //아이디 저장
                    arrayInfo.add(Idnum);
                    arrayData.add(Result);
                    arrayIndex.add(key);
                    Log.d("getFirebaseDatabase", "key: " + key);
                    Log.d("getFirebaseDatabase", "info: " + info[0]);
                    Log.d("getFirebaseDatabase", "info: " + info[1]);
                    Log.d("getFirebaseDatabase", "info: " + info[2]);
                    Log.d("getFirebaseDatabase", "info: " + info[3]);
                    Log.d("getFirebaseDatabase", "info: " + info[4]);
                }
                arrayAdapter.clear();
                arrayAdapter.addAll(arrayData);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("getFirebaseDatabase","loadPost:onCancelled", databaseError.toException());
            }
        };
        //정렬
        Query sortbyAge = FirebaseDatabase.getInstance().getReference().child("id_list").orderByChild(sort);
        sortbyAge.addListenerForSingleValueEvent(postListener);
    }

    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }
}
