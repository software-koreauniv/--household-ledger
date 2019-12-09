package com.example.household_ledger;

import android.view.View;

import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Item {

    private String price;
    private String category;
    private String address;
    private String histroy;
    private String cashcard;
    private String date;
    private String time;

    private View.OnClickListener requestBtnClickListener;

    public Item() {
    }

    public Item(String price, String category, String address, String histroy, String cashcard, String date, String time) {
        this.price = price;
        this.category = category;
        this.address = address;
        this.histroy = histroy;
        this.cashcard = cashcard;
        this.date = date;
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getcategory() {
        return category;
    }

    public void setcategory(String category) {
        this.category = category;
    }

    public String getaddress() {
        return address;
    }

    public void setaddress(String address) {
        this.address = address;
    }

    public String gethistroy() {
        return histroy;
    }

    public void sethistroy(String histroy) {
        this.histroy = histroy;
    }

    public String getcashcard() {
        return cashcard;
    }

    public void setcashcard(String  cashcard) {
        this.cashcard = cashcard;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public View.OnClickListener getRequestBtnClickListener() {
        return requestBtnClickListener;
    }

    public void setRequestBtnClickListener(View.OnClickListener requestBtnClickListener) {
        this.requestBtnClickListener = requestBtnClickListener;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (price != null ? !price.equals(item.price) : item.price != null) return false;
        if (category != null ? !category.equals(item.category) : item.category != null)
            return false;
        if (address != null ? !address.equals(item.address) : item.address != null)
            return false;
        if (histroy != null ? !histroy.equals(item.histroy) : item.histroy != null)
            return false;
        if (cashcard != null ? !cashcard.equals(item.cashcard) : item.cashcard != null)
            return false;
        if (date != null ? !date.equals(item.date) : item.date != null) return false;
        return !(time != null ? !time.equals(item.time) : item.time != null);

    }

    @Override
    public int hashCode() {
        int result = price != null ? price.hashCode() : 0;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (histroy != null ? histroy.hashCode() : 0);
        result = 31 * result + (cashcard != null ? cashcard.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    /**
     * @return List of elements prepared for tests
     */
    public static ArrayList<Item> getTestingList() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item("$20", "음식", "조치원읍", "도미노피자", "card", "TODAY", "05:10 PM"));

        items.add(new Item("$30", "음식", "조치원읍", "고기만두", "card", "TODAY", "11:10 AM"));
        items.add(new Item("$1", "교통", "조치원읍", "지하철", "card", "yesterday", "07:11 PM"));
        items.add(new Item("$5", "과자", "조치원읍", "감자칩", "cash", "2days ago", "4:15 AM"));
        items.add(new Item("$60", "옷", "조치원읍", "코트", "card", "3days ago", "06:15 PM"));

        return items;

    }

}
