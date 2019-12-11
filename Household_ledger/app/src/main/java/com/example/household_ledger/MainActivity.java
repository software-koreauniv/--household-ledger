package com.example.household_ledger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

    String id;

    int account = 0;

    String income;

    String date;
    String timePayPlace;
    String paySum;

    String month;
    String monthPSum;

    String sort = "date";

    ArrayAdapter<String> arrayAdapter;
    static ArrayList<String> arrayInfo =  new ArrayList<String>();
    static ArrayList<String> arrayIndex =  new ArrayList<String>();
    static ArrayList<String> arrayData = new ArrayList<String>();


    //DB에서 데이터를 가져오는 어댑터
    //CursorAdapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //리스트뷰
        ListView theListView = findViewById(R.id.mainListView);

        //화면에 출력 준비
        final ArrayList<Item> items = Item.getTestingList();

        // btn 핸들 추가
        items.get(0).setRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "CUSTOM HANDLER FOR FIRST BUTTON", Toast.LENGTH_SHORT).show();
            }
        });

        //어댑터 만들기
        final FoldingCellListAdapter adapter = new FoldingCellListAdapter(this, items);

        // default 핸들 추가
        adapter.setDefaultRequestBtnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "DEFAULT HANDLER FOR ALL BUTTONS", Toast.LENGTH_SHORT).show();
            }
        });


        //리스트 뷰에 어댑터 설정하기
        theListView.setAdapter(adapter);

        //짧게 클릭한 경우, 해당 날짜 디테일(미정)
        theListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                ((FoldingCell) view).toggle(false);
                adapter.registerToggle(pos);
            }
        });

        getFirebaseDatabase();
    }
    /*
    // income 입력시
    {
        mPostReference = FirebaseDatabase.getInstance();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;

        FirebasePost = new FirebasePost(id, time, income, memo);
        postValues.put(id, time, income, memo);

        childUpdates.put("/id_list/" + time, postValues);
        mPostReference.updateChildren(childUpdates);

        account += Integer.parseInt(income);
    }
    */

    /*
    // 스케줄 뷰로 이동하는 함수(리스너)
    private AdapterView.OnItemClickListener onClickListener = new AdapterView.OnItemClickListener() {
        //View 버튼 클릭시, tempData에 있는 스트링이 각기 editText에 들어가며,
        // tempData의 3번째 주소에 있는 젠더가 체크박스표시,
        // 나머지 edit_ID는 더이상 수정 불가, insert키도 수정불가
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.e("On Click", "position = " + position);
            Log.e("On Click", "Data: " + arrayInfo.get(position));
            String tempData = arrayInfo.get(position);  //스왑용(배열 데이터, split에서 받음?
            Log.e("On Click", "Split Result = " + tempData);

            // 확인 로그
            Toast.makeText(getApplicationContext(), "일정 보기", Toast.LENGTH_SHORT).show();

            // 액티비티 넘어가기
            Intent intent;
            intent = new Intent(getApplicationContext(), PayInfo.class);
            intent.putExtra("title", tempData);        //id정보 전달
            startActivity(intent);
        }
    };
*/

    //데이터 송신
    public void postFirebaseDatabase(boolean add){
        //데이터베이스 입력인데, 해당 엑티비티에서는 입력하지 않음.
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add) {
            //FirebasePost클래스의 객체 post선언 (스트링 입력)
            FirebasePost post = new FirebasePost(id, date, timePayPlace, paySum);
            postValues = post.toMap();
        }
        childUpdates.put("/id_list/" + date, postValues);
        mPostReference.updateChildren(childUpdates);

        // account -= Integer.parseInt(pay);
    }

    //데이터 수신 + 화면 출력
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

                    String[] info = {get.id, get.date, get.timePayPlace};
                    String Result = setTextLength(info[0],10) + setTextLength(info[1],10) + setTextLength(info[2],10);

                    //String[] info = {get.timePayPlace};
                    //String Result = setTextLength(info[0],10);

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
                    //Log.d("getFirebaseDatabase", "info: " + info[3]);
                    //Log.d("getFirebaseDatabase", "info: " + info[4]);
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
    //합산 함수
    public void addUp(String text){
        String sum = "";
        //String type으로 인자를 받는다.(pay)
        //전역변수 String paySum을 받아온다.
        //paySum += pay를 한다.
    }

    //스트링 구간 나누는 함수
    public String devideText(String text){
        String Stime = "";
        //String type으로 인자를 받는다
        //해당 인자를 구간별로 나눈다.
        // 시간 / 지출 / 장소
        //String type으로 반환한다.
        //혹은 void형식으로 반환, 전역변수 설정해서 따로 변수 초기화를 한다.
        return Stime;
    }

    //일정 간격 갭 생성 함수
    public String setTextLength(String text, int length){
        if(text.length()<length){
            int gap = length - text.length();
            for (int i=0; i<gap; i++){
                text = text + " ";
            }
        }
        return text;
    }

 /*   // 메뉴화면 전환 함수
    public void menu(View view)
    {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

  */
}
