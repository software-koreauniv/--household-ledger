package com.example.household_ledger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;

public class SmsReceiver extends BroadcastReceiver { // SMS를 수신해서 전달해 주는 용도임.
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String TAG = "SmsReceiver";
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private String cardName;
    private int useMoney;
    private String place;
    String cardInfo[];
    private static final String KB = "15881688";
    private static final String W = "15449955";
    private static final String SM = "15888900";
    private static final String NH = "15881600";
    private String sender;
    private String contents;
    private Bundle bundle;


    @Override
    public void onReceive(Context context, Intent intent) {// SMS_RECEIVED에 대한 액션일때 실행
        System.out.println("TAG onRECEIVE");
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Log.d(TAG, "Boot Completed");
        }
        if (intent.getAction().equals(Intent.ACTION_SCREEN_ON))
            Log.d(TAG, "Screen On");
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF))
            Log.d(TAG, "Screen Off");

        if (intent.getAction().equals(SMS_RECEIVED)) {
            Log.d(TAG, "onReceiver() 호출");
            Toast.makeText(context, "문자가 수신되었습니다.", Toast.LENGTH_SHORT).show();

            Bundle bundle = intent.getExtras(); // Bundle을 이용해서 메세지 내용을 가져옴
            SmsMessage[] messages = parseSmsMessage(bundle); // 메세지가 있을 경우 내용을 로그로 출력해 봄

            if (messages.length > 0) { // 메세지의 내용을 가져옴
                String sender = messages[0].getOriginatingAddress();
                String contents = messages[0].getMessageBody().toString();

                // 메시지를 제대로 읽어왔는지를 테스트 하는 코드
                // Log.d(TAG, "Sender :" + sender);
                // Log.d(TAG, "contents : " + contents);
                // Toast.makeText(context, "발신인 : " + sender, Toast.LENGTH_LONG).show();

                //getInfoSMS 호출
                boolean isValidNumber = sender.equals(KB) || sender.equals(W) || sender.equals(SM) || sender.equals(NH);
                if (isValidNumber) {
                    System.out.println("카드사 정상 인식");
                    cardName = setCardName(sender);
                    switch (sender) {
                        case KB:
                            Toast.makeText(context, "KB 정상 인식 됨", Toast.LENGTH_SHORT).show(); // 안쓸거임
                            //cardInfo = setKBCardMoneyInfo(contents);
                            break;
                        case W:
                            Toast.makeText(context, "우리 정상 인식 됨", Toast.LENGTH_SHORT).show();
                            cardInfo = setCardInfo(contents);
                            break;
                        case SM:
                            Toast.makeText(context, "삼성 정상 인식 됨", Toast.LENGTH_SHORT).show(); // 얘도 안 쓸거임
                            //cardInfo = setSMCardMoneyInfo(contents);
                            break;
                        case NH:
                            Toast.makeText(context, "NH 정상 인식 됨", Toast.LENGTH_SHORT).show(); // 얘도 안 쓸래
                            //cardInfo = setNHCardMoneyInfo(contents);
                            break;
                        default:
                            Toast.makeText(context, "카드사가 정상적으로 인식되지 않았습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                sendToActivity(context, cardInfo);
            }
        }
    } // 액티비티로 메세지의 내용을 전달해줌

    private void sendToActivity(Context context, String[] data) {
        System.out.println("TAG SendToActivity");
        Intent intent = new Intent(context, MainActivity.class); // Flag 설정
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        intent.putExtra("amount", data[0]);
        intent.putExtra("place", data[1]);
        intent.putExtra("date", data[2]);
        intent.putExtra("time", data[3]);
        intent.putExtra("cardNum", data[4]);
        intent.putExtra("sender", cardName);

        context.startActivity(intent);
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");
        SmsMessage[] messages = new SmsMessage[objs.length];
        for (int i = 0; i < objs.length; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                String format = bundle.getString("format");
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }
        return messages;
    }

    public String setCardName(String orgNumber) {
        String cardName = null;
        switch (orgNumber) {
            case KB:
                cardName = "국민";
                break;

            case W:
                cardName = "우리";
                break;

            case SM:
                cardName = "삼성";
                break;

            case NH://농협 체크카드
                cardName = "농협";
                break;

        }
        return cardName;
    }

    public String[] setCardInfo(String Message) {
        System.out.println("TAG card_info");

        int isWeb = Message.indexOf("[Web발신]");
        if (isWeb == -1)
            Message.replace("[Web발신]", "");

        String Amount; // 사용 금액
        String place; // 사용 장소
        String date; // 사용 날자
        String time; // 사용 시간
        String cardNum; // 카드 번호

        // 메세지에서 시간 추출
        int indexOfTime = Message.indexOf(":");
        time = Message.substring(indexOfTime - 2, indexOfTime + 3);
        System.out.println("TAG, time : " + time);

        // 메세지에서 날자 추출
        int indexOfDate = Message.indexOf("/");
        date = Message.substring(indexOfDate - 2, indexOfDate + 3);
        System.out.println("TAG, date : " + date);

        //메세지에서 금액 추출
        int indexOfMoney = Message.indexOf("원");
        Amount = Message.substring(indexOfTime + 4, indexOfMoney);
        Amount = Amount.replace(",", ""); // ',' 삭제
        System.out.println("TAG, amount : " + Amount);

        //메세지에서 사용처 추출
        place = Message.substring(indexOfMoney + 2);
        System.out.println("TAG, place : " + place);

        // 메세지에서 카드 번호 추출
        int startCardNum = Message.indexOf("(");
        cardNum = Message.substring(startCardNum + 1, startCardNum + 5);
        System.out.println("TAG Num : " + cardNum);

        return new String[]{Amount, place, date, time, cardNum};
    }
}