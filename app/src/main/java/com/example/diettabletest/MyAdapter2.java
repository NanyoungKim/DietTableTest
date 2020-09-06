package com.example.diettabletest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyAdapter2 extends RecyclerView.Adapter<MyAdapter2.ViewHolder> {

    Map<Long, Double> ingredientMap = new HashMap<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_ingredNum;
        public TextView TextView_ingredName;
        public TextView TextView_portion;





        public ViewHolder(View v) {
            super(v);
            TextView_ingredNum = v.findViewById(R.id.TextView_ingredNum);
            TextView_ingredName = v.findViewById(R.id.TextView_ingredName);
            TextView_portion = v.findViewById(R.id.TextView_portion);


        }
    }
    public MyAdapter2(){

    }


    // Provide a suitable constructor (depend s on the kind of dataset)
    public MyAdapter2(Map<Long, Double> _ingredientMap) {
        ingredientMap = _ingredientMap;
    }

    // Create new views (invoked by the layout manager)`
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_page, parent, false);

        final ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //FoodInfo food = mDataset.get(position);
        //holder.textViewFood.setText(String.valueOf(food.getFoodCode()));


//        Set<Integer> keyset = ingredientMap.keySet();
        List<Long> keyList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        List<Double> valueList = new ArrayList<>();
//        Iterator<Integer> it = keyset.iterator();
//
//        while(it.hasNext()){
//            keyList.add(it.next());
//        }
//        while(it.hasNext()){
//            valueList.add(ingredientMap.get(it.next()));
//        }
//
//
//

        Set set = ingredientMap.entrySet();
        Iterator iterator = set.iterator();

        int i = 0;
        while(iterator.hasNext()){

            Map.Entry entry = (Map.Entry)iterator.next();

            long key = (Long)entry.getKey();
           // System.out.println(i + " key 값: " + key);

            double value = (Double)entry.getValue();
            //System.out.println(i + " value 값: " + value);

            keyList.add(key);
            valueList.add(value);
            i++;

        }

        holder.TextView_ingredNum.setText(String.valueOf(keyList.get(position)));
        holder.TextView_portion.setText(String.valueOf(valueList.get(position)));



        //holder.textViewFoodName.setText(food.getFoodName());

        /*int ingredNum = food.getIngredNum();
        holder.TextView_ingredNum.setText(String.valueOf(ingredNum));

        String ingredName = food.getIngredName();
        holder.TextView_ingredName.setText(ingredName);

        int portion = food.getPortion();
        holder.TextView_portion.setText(String.valueOf(portion));*/




        //holder.rootView.setTag(position);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {     //음식 종류 수
        return ingredientMap == null ? 0 : ingredientMap.size();
    }

    //public FoodInfo getFoodInfo(int position){
    //    return mDataset2 != null ? mDataset2.get(position) : null;
   // }




}