package com.example.diettabletest;

import java.io.Serializable;

public class RecyclerViewModel implements Serializable {
    //private String mItemName;

    private long foodCode;       //국, 밥, 죽, ,,
    private String foodName;    //요리 이름 (감자밥,,)
    private long ingredNum;      //재료 종류
    private String ingredName;  //재료 이름
    private double portion;        //1인량

    public RecyclerViewModel(){

    }

    public RecyclerViewModel(long _foodCode, String _foodName, long _ingredNum, String _ingredName, double _portion){
        foodCode = foodCode;
        foodName = _foodName;
        ingredNum = _ingredNum;
        ingredName = _ingredName;
        portion = _portion;
    }


    public void setFoodCode(long code){
        this.foodCode = code;
    }
    public void setFoodName(String fname){
        this.foodName = fname;
    }
    public void setIngredNum(long inum){
        this.ingredNum = inum;
    }
    public void setIngredName(String iname){
        this.ingredName = iname;
    }
    public void setPortion(double portion){
        this.portion = portion;
    }



    public long getFoodCode(){
        return foodCode;
    }
    public String getFoodName(){
        return foodName;
    }
    public long getIngredNum(){
        return ingredNum;
    }
    public String getIngredName(){
        return ingredName;
    }
    public double getPortion(){
        return portion;
    }


//    public RecyclerViewModel(String ItemName){
//        mItemName = ItemName;
//    }
//    public String getmItemName(){
//        return mItemName;
//    }

}
