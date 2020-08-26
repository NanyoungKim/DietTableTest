package com.example.diettabletest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String LOG_TAG = RecyclerViewAdapter.class.getSimpleName();

    private ArrayList<Long> UniqueFoodcodeList;
    private ArrayList<RecyclerViewModel> UniqueFList;
    private ArrayList<RecyclerViewModel> FoodObject;


    private static View.OnClickListener onClickListener;

    private Map<RecyclerViewModel, Boolean> checkedMap = new HashMap<>();
    private ArrayList<RecyclerViewModel> ingredientPerFood = new ArrayList();
    private ArrayList<RecyclerViewModel> ingredientToOrder = new ArrayList<>();
    private ArrayList<Integer> listItem;


    private int menuCnt = 0;
    ItemListener mListener;
    Context context;
    public static int position;

    public interface DataTransferInterface{
        //public void setValues(ArrayList<FoodInfo> ingredientToOrder);
    }
    DataTransferInterface dtInterface;

    public RecyclerViewAdapter(DataTransferInterface dtInterface){
        this.dtInterface = dtInterface;
    }

    public RecyclerViewAdapter(Activity context,
                               ArrayList<Long> _UniqueFoodcodeList, ArrayList<RecyclerViewModel> _UniqueFList, ArrayList<RecyclerViewModel> foodobject, ItemListener listener){
        this.context = context;
        UniqueFoodcodeList = _UniqueFoodcodeList;
        UniqueFList = _UniqueFList;
        FoodObject = foodobject;
        mListener = listener;
        listItem = new ArrayList<>();

    }

    public void setOnItemClickListener(ItemListener listener){
        mListener = listener;
    }


    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        context = parent.getContext();

        final ViewHolder vh= new ViewHolder(v);

        vh.check_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //눌렸나 안 눌렸나를 checkedMap 에 저장한다

                final int checkBoxPosition = vh.getAdapterPosition();
                RecyclerViewModel food = UniqueFList.get(vh.getAdapterPosition());
                Vector<RecyclerViewModel> vecfood = getGroup(food.getFoodCode());

                System.out.println("UniqueFlist 크기는 : " + UniqueFList.size());
                System.out.println("checkedMap 크기는 : " + checkedMap.size());
                Log.d("체크 확인", "체크됨!!!");

                checkedMap.put(food, isChecked);

                if (isChecked) {
                    menuCnt++;

                    for (int i = 0; i < listItem.size(); i++) {
                        if (listItem.get(i) == checkBoxPosition) {
                            return;
                        }

                    }
                    listItem.add(checkBoxPosition);

                    for (int i = 0; i < vecfood.size(); i++) {

                        ingredientPerFood.add(vecfood.elementAt(i));

                        System.out.println("What is Checked? " + vecfood.elementAt(i).getFoodName());
                    }
                    choiceFoods();
                    ingredientPerFood.clear();
                    vecfood.clear();

                } else {


                    for (int i = 0; i < listItem.size(); i++) {
                        if (listItem.get(i) == checkBoxPosition) {
                            listItem.remove(i);
                            break;
                        }
                    }


                    for (int i = 0; i < vecfood.size(); i++) {
                        ingredientToOrder.remove(vecfood.elementAt(i));
                    }
                }


            }
        });









       //return new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, int position) {

        holder.setData(FoodObject.get(position));

        RecyclerViewModel foods = UniqueFList.get(position);

        holder.name.setText(String.valueOf(UniqueFoodcodeList.get(position)));
        holder.check_box.setText(getFoodName(UniqueFoodcodeList.get(position)));

        boolean isChecked = checkedMap.get(foods) == null ? false : checkedMap.get(foods);   //그 객체가 체크 됐는지 (발주 넣을 메뉴 : checkedMap.get(food)


        holder.check_box.setChecked(isChecked);

        Intent intent = new Intent("custom-message");
        intent.putExtra("result", ingredientToOrder);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);





    }

    @Override
    public int getItemCount() {
        return UniqueFoodcodeList == null ? 0 : UniqueFoodcodeList.size();
    }
    public String getFoodName(Long fcode){
        String ans = "";
        for(int i = 0; i< FoodObject.size(); i++){
            if(fcode == FoodObject.get(i).getFoodCode()){
                ans =  FoodObject.get(i).getFoodName();
                break;
            }
        }
        return ans;
    }

    public Vector<RecyclerViewModel> getGroup(long fcode){
        Vector<RecyclerViewModel> vecIngred = new Vector();
        vecIngred.removeAllElements();
        boolean chk = false;

        for(int i = 0; i < FoodObject.size(); i++){   //전체 객체 돌면서
            if(fcode == FoodObject.get(i).getFoodCode()){
                chk = true;
                vecIngred.add(FoodObject.get(i));

            }
            else if((fcode != FoodObject.get(i).getFoodCode()) && (chk == true)){
                break;
            }
        }
        return vecIngred;

    }


    public boolean choiceFoods(){

        boolean result = ingredientToOrder.addAll(ingredientPerFood);     //체크된 객체들이 담긴 리스트를 리스트에 넣는다.
//        if(result){
//            System.out.println(ingredientPerFood.get(0));
//            Log.d("추가", ingredientPerFood.get(0).getFoodName());
//            notifyDataSetChanged();
//        }

        System.out.println("몇개 보내져?" + ingredientToOrder.size());

        return result;

    }


    public ArrayList<RecyclerViewModel> getOrderedFList(){

        return this.ingredientToOrder == null ? null : this.ingredientToOrder;
    }


    //public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public RecyclerViewModel pName;
        TextView name;
        View textContainer;
        CheckBox check_box;


    public ViewHolder(View itemView) {

        super(itemView);
        //itemView.setOnClickListener(this);
        cardView = (CardView)itemView.findViewById(R.id.cvItem);
        name = (TextView) itemView.findViewById(R.id.list_text);
        textContainer = itemView.findViewById(R.id.text_container);
        check_box = (CheckBox) itemView.findViewById(R.id.check_box);
    }



    public void setData(RecyclerViewModel pName) {
        this.pName = pName;
        name.setText(pName.getFoodName());
    }






//    @Override
//    public void onClick(View v) {
//        if (mListener != null) {
//            mListener.onItemClick(pName, getAdapterPosition());
//        }
//        Toast.makeText(context,pName.getFoodName(),Toast.LENGTH_SHORT).show();
//
//    }

}

public interface ItemListener {
    void onItemClick(RecyclerViewModel pName, int position);
}





}
