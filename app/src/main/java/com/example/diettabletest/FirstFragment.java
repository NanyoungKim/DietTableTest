package com.example.diettabletest;

import androidx.lifecycle.ViewModelProviders;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FirstViewModel mViewModel;
    ArrayList<RecyclerViewModel> foodList = new ArrayList<RecyclerViewModel>();
    ArrayList<RecyclerViewModel> UniqueFList = new ArrayList<RecyclerViewModel>();
    ArrayList<RecyclerViewModel> resultList = new ArrayList<RecyclerViewModel>();

    ArrayList<Long> UniqueFcodeList = new ArrayList<>();


    private RecyclerViewAdapter mAdapter;


    RecyclerView clv;
    Button btn_order;





    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_layout, container,false);

        return rootView;

    }


    private String getJsonString(){
        String json = "";
        AssetManager am = getResources().getAssets() ;
        try {
            InputStream is = am.open("recipie_category1.json");
            int fileSize = is.available();

            byte[] buffer = new byte[fileSize];
            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private void jsonParsing(String json) {

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray foodArray = jsonObject.getJSONArray("food");

            for (int i = 0; i < foodArray.length(); i++) {
                //for(int i = 0; i<9921; i++){

                JSONObject foodObject = foodArray.getJSONObject(i);

                //FoodInfo food = new FoodInfo();


                RecyclerViewModel food = new RecyclerViewModel();

                food.setFoodCode(foodObject.getLong("요리코드"));
                food.setFoodName(foodObject.getString("요리명"));
                food.setIngredNum(foodObject.getLong("식품번호"));
                food.setIngredName(foodObject.getString("식품명"));
                food.setPortion(foodObject.getDouble("1인량"));


                if (!UniqueFcodeList.contains(food.getFoodCode())) {  //없으면
                    UniqueFcodeList.add(food.getFoodCode());            //중복 없는 요리코드 리스트
                    UniqueFList.add(food);

                }

                foodList.add(food);





//                 mAdapter = new RecyclerViewAdapter(FirstFragment.this.getActivity(),
//                UniqueFcodeList, UniqueFList, foodList, null);




            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            resultList = (ArrayList<RecyclerViewModel>) intent.getSerializableExtra("result");

            System.out.println(resultList.size());
            //Toast.makeText(FoodActivity.this, resultList).size( , Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        String j = getJsonString();
        jsonParsing(j);



        final RecyclerViewAdapter itemsAdapter = new RecyclerViewAdapter(FirstFragment.this.getActivity(),
                UniqueFcodeList, UniqueFList, foodList, null);
        clv = (RecyclerView) view.findViewById(R.id.clist);
        clv.setLayoutManager(new LinearLayoutManager(FirstFragment.this.getActivity()));
        clv.setHasFixedSize(true);
        clv.setAdapter(itemsAdapter);

















    }

}

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
//        // TODO: Use the ViewModel
//    }

