package com.example.inclass08_801073413;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseAppFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseAppFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    ListView rv_results;
    TextView tv_no_list;
    FloatingActionButton btn_add;
   // RecyclerView.LayoutManager layoutManager;
   // RecyclerView.Adapter mAdapter;
    ArrayList<ExpenseData> expenseDataArrayList;


    public ExpenseAppFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_expense_app, container, false);

        btn_add = view.findViewById(R.id.btn_add);
        rv_results = view.findViewById(R.id.rv_results);
        tv_no_list = view.findViewById(R.id.tv_no_list);
        tv_no_list.setVisibility(View.VISIBLE);
        rv_results.setVisibility(View.INVISIBLE);

        //Log.d("expenseDataArrayList_expenseappfragment",""+expenseDataArrayList);
        if(arrayListSizeCheck(expenseDataArrayList)) {
            expenseListResult(expenseDataArrayList);
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.addButtonClick(true);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        arrayListSizeCheck(expenseDataArrayList);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            expenseDataArrayList = (ArrayList<ExpenseData>) bundle.getSerializable("expenselist");
            Log.d("sai", "Expense Objs " + expenseDataArrayList);
            expenseListResult(expenseDataArrayList);

        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public boolean arrayListSizeCheck(ArrayList<ExpenseData> expenseDataArrayList){

        if(null!=expenseDataArrayList && expenseDataArrayList.size()>0) {
            tv_no_list.setVisibility(View.INVISIBLE);
            rv_results.setVisibility(View.VISIBLE);
            return true;
        }else {
            tv_no_list.setVisibility(View.VISIBLE);
            rv_results.setVisibility(View.INVISIBLE);
            return false;
        }
    }

    public void expenseListResult (final ArrayList<ExpenseData> expenseDataArrayList){
        Log.d("expenselist func",""+expenseDataArrayList);

        final ExpenseAdapter expenseAdapter = new ExpenseAdapter(getContext(),R.layout.row_item,expenseDataArrayList);
        rv_results.setAdapter(expenseAdapter);
        rv_results.setVisibility(View.VISIBLE);
        expenseAdapter.setNotifyOnChange(true);
        arrayListSizeCheck(expenseDataArrayList);

        rv_results.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseData expenseData= new ExpenseData();
                expenseData = expenseAdapter.getItem(position);
                expenseDataArrayList.remove(expenseData);
                expenseAdapter.notifyDataSetChanged();


                mListener.itemLongClick(expenseDataArrayList,position);
                Toast.makeText(getContext(),getResources().getString(R.string.expense_deleted),Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        rv_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseData expenseData=new ExpenseData();
                expenseData= expenseAdapter.getItem(position);
                mListener.itemClick(expenseData,position);
            }
        });

        this.expenseDataArrayList = expenseDataArrayList;

    }

    public interface OnFragmentInteractionListener {
        void addButtonClick(boolean clicked);
        void itemLongClick(ArrayList<ExpenseData> explist,int delposition);
        void itemClick(ExpenseData expenseData,int pos);
    }
}
