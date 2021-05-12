package com.abfresh.in.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Adapter.SlotAdapterTest;
import com.abfresh.in.Adapter.SlotHeaderAdapter;
import com.abfresh.in.Model.ChildItem;
import com.abfresh.in.Model.ParentItem;
import com.abfresh.in.Model.SlotDateList;
import com.abfresh.in.Model.SlotModel;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.muddzdev.styleabletoast.StyleableToast;
import com.abfresh.in.Adapter.SlotAdapterTomorrow;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.Controller.Utility;
import com.abfresh.in.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.abfresh.in.Model.CartList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;
import static com.facebook.FacebookSdk.getApplicationContext;

public class DeliverySlotBottomSheetDialog extends BottomSheetDialogFragment {
    TextView slot1,slot2,slot3,slot4,slot5,slot6,slot7,slot8,slot9,slot10;
    public static BottomSheetListener mListener;
    public static DeliverySlotBottomSheetDialog activity;
    SessionManagement sessionManagement;
    ArrayList<CartList> cdListsNew;
    ArrayList<SlotModel> HeaderList;
    List<ParentItem> parItemList;
    List<ChildItem> childItemList;
    ArrayList<CartList> cdListsNew2;
    ArrayList<SlotDateList> slotDateListsMod;
    ArrayList arraySlotDate;
    SlotAdapterTest slotAdapter;
    SlotAdapterTomorrow slotAdapterTomorrow;
    SlotHeaderAdapter slotHeaderAdapter;
    RecyclerView today_rv,tomorrow_rv,tomorrow_rv_par;
    LinearLayout today_ll,tomorrow_ll;
    TextView date_today,date_tomorrow;
    private java.util.ArrayList<ArrayList> dateList;
    public static int row_index = -1;
    public static boolean clickbtn=false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.slot_layout_bottom_sheet, container, false);
        activity = this;
//------------------Today Slots-------------------------
        date_today = (TextView)v.findViewById(R.id.date_today);
        date_tomorrow = (TextView)v.findViewById(R.id.date_tomorrow);
        arraySlotDate = new ArrayList<>();
        today_rv = (RecyclerView) v.findViewById(R.id.today_rv);
        tomorrow_rv_par = (RecyclerView) v.findViewById(R.id.tomorrow_rv_par);
        today_ll = (LinearLayout) v.findViewById(R.id.today_ll);
        tomorrow_ll = (LinearLayout) v.findViewById(R.id.tomorrow_ll);

    getSlots();
    
//------------------Today Slots setOnclick Listner-------------------------

//        slot1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mListener.onButtonClicked("10AM - 12PM");
//                Utility.deliverySlot = "10 AM - 12 PM";
//                dismiss();
//            }
//        });

        dateList = new java.util.ArrayList<ArrayList>();

        return v;
    }


    private void getSlots() {
        try {
        JSONObject cartListObject = new JSONObject();
//        cd_pd.setVisibility(View.VISIBLE);
        sessionManagement = new SessionManagement(getActivity());
        String tempid= Settings.Secure.getString(getActivity().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        cartListObject.put( "temp_user_id",tempid);
        cartListObject.put( "user_id",sessionManagement.getUserDetails().get(KEY_USERID));
        cartListObject.put("city_id",sessionManagement.getUserDetails().get(KEY_City_ID));
        cartListObject.put("pincode",sessionManagement.getUserDetails().get(KEY_Pincode));
        Log.w("CDTAG", "JSONObject cartListObject" + cartListObject);
        JsonObjectRequest clRequest = new JsonObjectRequest(Request.Method.POST, Utility.CartSummery, cartListObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.w("CDTAG", "JSONObject response" + response);

                    if ((response.getString("success")).equals("1")){
                        cdListsNew = new ArrayList<CartList>();
                        cdListsNew2 = new ArrayList<CartList>();
                        HeaderList = new ArrayList<SlotModel>();
                        slotDateListsMod = new ArrayList<SlotDateList>();
                        parItemList = new ArrayList<>();

                        if(!(response.getJSONArray("deliverySlot").length()==0)){
                            tomorrow_ll.setVisibility(View.VISIBLE);
                            JSONArray clnewArray = response.getJSONArray("deliverySlot");

                            for(int i=0;i<clnewArray.length();i++){

                                childItemList = new ArrayList<>();

                                JSONObject newObject = clnewArray.getJSONObject(i);
                                JSONArray clnewArrayIn = newObject.getJSONArray("list");
                                Log.w("DELTAG","clnewArrayIn length===>"+clnewArrayIn.length());
                                String title = newObject.getString("date");

                                for(int j=0;j<clnewArrayIn.length();j++) {
                                    JSONObject newObjectIn = clnewArrayIn.getJSONObject(j);
                                    Log.w("DELTAG","j===>"+j);
                                    Log.w("DELTAG","delivery_slots_time===>"+newObjectIn.getString("delivery_slots_time"));

                                    Log.w("DELTAG","id deli===>"+newObjectIn.getString("delivery_slots_id"));
                                    Log.w("DELTAG","delivery_slots_name===>"+newObjectIn.getString("delivery_slots_name"));
                                    date_tomorrow.setText("Tomorrow -" + newObjectIn.getString("delivery_slots_name"));
                                    String delivery_slots_id = newObjectIn.getString("delivery_slots_id");
                                    String delivery_slots_name = newObjectIn.getString("delivery_slots_name");
                                    String delivery_slots_time = newObjectIn.getString("delivery_slots_time");
                                    String is_disable = newObjectIn.getString("is_disable");

//                                    cdListsNew.add(new CartList(delivery_slots_name,delivery_slots_time));
//                                parItemList.add(delivery_slots_time,childItemList);
                                    childItemList.add(new ChildItem(delivery_slots_time,delivery_slots_name,is_disable));
                                    slotDateListsMod.add(new SlotDateList(delivery_slots_id,delivery_slots_time,delivery_slots_name));

                                }
                                SlotModel slotModel = new SlotModel(title,childItemList);
                                Log.w("SlTAG","slotModel Date===>"+slotModel.getDate());

                                arraySlotDate.add(slotModel.getDate());

                                parItemList.add(new ParentItem(title,
                                        childItemList));
                            }
                            Log.w("SlTAG","slotModel List===>"+arraySlotDate);
                            slotAdapterTomorrow = new SlotAdapterTomorrow(parItemList);
                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false);
                            tomorrow_rv_par.setLayoutManager(linearLayoutManager);
                            tomorrow_rv_par.setAdapter(slotAdapterTomorrow);
                            tomorrow_rv_par.setNestedScrollingEnabled(false);




                            slotAdapterTomorrow.setOnItemClickListner(new SlotAdapterTomorrow.OnItemClickListner() {
                                @Override
                                public void onSlotTomClick(int position, ParentItem cartList,TextView parent_item_title,String dateName) {
                                    date_today.setText(cartList .getParentItemTitle());
//                                    parent_item_title.setTextColor(getResources().getColor(R.color.newYellow));
//                                    row_index=position;
//                                    if(row_index==position){
//                                        parent_item_title.setTextColor(getResources().getColor(R.color.newYellow));
//                                    }
                                    tomorrow_rv_par.smoothScrollToPosition(position);
//                                    tomorrow_rv_par.scrollTo((int)tomorrow_rv_par.getScrollX() + 250, (int)tomorrow_rv_par.getScrollY());
                                    Log.w("ClTAG"," parent_item_title===>"+dateName);
                                    Log.w("ClTAG"," cartList .getParentItemTitle()===>"+"Date: "+cartList .getParentItemTitle());
                                    slotAdapter = new SlotAdapterTest(getApplicationContext(),cartList.getChildItemList());
                                    final LinearLayoutManager horizontalDays
                                            = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                    today_rv.setLayoutManager(horizontalDays);
                                    today_rv.setNestedScrollingEnabled(false);
                                    today_rv.setAdapter(slotAdapter);



                                }
                            });

                            if (parItemList.size()>0){

                                date_today.setText(parItemList.get(0).getParentItemTitle());

                                slotAdapter = new SlotAdapterTest(getApplicationContext(),parItemList.get(0).getChildItemList());
                                final LinearLayoutManager horizontalDays
                                        = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                today_rv.setLayoutManager(horizontalDays);
                                today_rv.setNestedScrollingEnabled(false);
                                today_rv.setAdapter(slotAdapter);
                            }


//                            slotAdapter = new SlotAdapterTest(getApplicationContext(),arraySlotDate);
//                            final LinearLayoutManager horizontalDays
//                                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
//                            today_rv.setLayoutManager(horizontalDays);
//                            today_rv.setNestedScrollingEnabled(false);
//                            today_rv.setAdapter(slotAdapter);
//                            slotAdapter.setOnItemClickListner(new SlotAdapterTest.OnItemClickListner() {
//                                @Override
//                                public void onSlotClick(int position, TextView textView) {
//
//                                        Toast.makeText(getApplicationContext(), ""+textView.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                                }
//                            });
//                            slotAdapterTomorrow.setOnItemClickListner(new SlotAdapterTomorrow.OnItemClickListner() {
//                                @Override
//                                public void onSlotTomClick(int position, TextView textView) {
//                                    mListener.onButtonClicked(cdListsNew.get(position).getDelivery_location_id());
//                                    Utility.deliverySlot = cdListsNew.get(position).getDelivery_location_id();
//                                    Utility.deliveryDay = "Tomorrow";

//                                }
//                            });
                        }else{
                            tomorrow_ll.setVisibility(View.GONE);
                        }

//                        if(!(response.getJSONArray("delivery_slots_today_list").length()==0)){
//                            today_ll.setVisibility(View.VISIBLE);
//                            JSONArray clnewArray = response.getJSONArray("delivery_slots_today_list");
//
//                            for(int i=0;i<clnewArray.length();i++){
//
//                                JSONObject newObject = clnewArray.getJSONObject(i);
//                                String delivery_slots_name = newObject.getString("delivery_slots_name");
//                                date_today.setText("Today - " +newObject.getString("delivery_slots_name"));
//                                String delivery_slots_time = newObject.getString("delivery_slots_time");
//
//                                cdListsNew2.add(new CartList(delivery_slots_name,delivery_slots_time));
//                            }
//                            slotAdapter = new SlotAdapter(getActivity(), cdListsNew2);
//                            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                            today_rv.setLayoutManager(linearLayoutManager);
//                            today_rv.setAdapter(slotAdapter);
//                            today_rv.setNestedScrollingEnabled(false);
//                            slotAdapter.setOnItemClickListner(new SlotAdapter.OnItemClickListner() {
//                                @Override
//                                public void onSlotClick(int position, TextView textView) {
//                                    mListener.onButtonClicked(cdListsNew2.get(position).getDelivery_location_id());
//                                    Utility.deliverySlot = cdListsNew2.get(position).getDelivery_location_id();
//                                    Utility.deliveryDay = "Today";
//                                    dismiss();
//                                }
//                            });
//
//                        }else{
//                            today_ll.setVisibility(View.GONE);
//                        }
//                        cd_pd.setVisibility(View.GONE);

                    }else{
//                        cd_pd.setVisibility(View.GONE);
                        StyleableToast.makeText(getActivity(),"Please try after some time", R.style.mySizeToast).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    cd_pd.setVisibility(View.GONE);
                    StyleableToast.makeText(getActivity(),"Please try after some time", R.style.mySizeToast).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                cd_pd.setVisibility(View.GONE);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String,String> header = new HashMap<>();
                header.put(Utility.ServerUsername,Utility.ServerPassword);
                return header;
            }
        };clRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addRequestInQueue(clRequest);


    } catch (JSONException e) {
        e.printStackTrace();
//        cd_pd.setVisibility(View.GONE);

    }

}

    public interface BottomSheetListener {
        void onButtonClicked(String text);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (BottomSheetListener) context;
//            dismiss();

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }


}

