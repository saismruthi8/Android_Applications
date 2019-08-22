package com.example.kshitijjaju.inclass07_group02;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ExpenseAppFragment extends Fragment {

    ListView rv_results;
    TextView tv_no_list;
    Button btn_add;
    //RecyclerView.LayoutManager layoutManager;
    //RecyclerView.Adapter mAdapter;
    ArrayList<ExpenseData> expenseDataArrayList;
    ExpenseAdapter adapter;

    private OnFragmentInteractionListener mListener;

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


        Log.d("expenseDataArrayList",""+expenseDataArrayList);
        if(null!=expenseDataArrayList && expenseDataArrayList.size()>0) {
            /*mAdapter = new ExpenseAdapter(getActivity(), expenseDataArrayList);
            rv_results.setAdapter(mAdapter);*/
            expenseListResult(expenseDataArrayList);
            tv_no_list.setVisibility(View.INVISIBLE);
            rv_results.setVisibility(View.VISIBLE);
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

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            expenseDataArrayList=(ArrayList<ExpenseData>) bundle.getSerializable("expenselist");
            Log.d("sai","Expense Objs "+expenseDataArrayList);
            expenseListResult(expenseDataArrayList);

        }



       /* rv_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ExpenseData e=new ExpenseData();
                Log.d("inside listview",""+expenseDataArrayList);
                e= adapter.getItem(position);
                mListener.gotoShowFragment(e);
            }
        });

        rv_results.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseData e= new ExpenseData();
                e= (ExpenseData)adapter.getItem(position);
                expenseDataArrayList.remove(e);

                if(expenseDataArrayList.size() == 0)
                {
                    //statusTextView.setVisibility(View.VISIBLE);
                    rv_results.setVisibility(View.INVISIBLE);
                }
                else {
                    //statusTextView.setVisibility(View.INVISIBLE);
                    //adapter=new ExpenseAdapter(getActivity(), R.layout.row_item, expenseDataArrayList);
                    rv_results.setAdapter(adapter);
                    adapter.setNotifyOnChange(true);
                    rv_results.setVisibility(View.VISIBLE);
                }
                //refresh listview
                Toast.makeText(getContext(),"Expense deleted",Toast.LENGTH_SHORT).show();

                return false;
            }
        }); */
        super.onActivityCreated(savedInstanceState);


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

    public void expenseListResult ( final ArrayList<ExpenseData> expenseDataArrayList){
        Log.d("List inside expfunc",""+expenseDataArrayList);

        final ExpenseAdapter adapter=new ExpenseAdapter(getContext(),R.layout.row_item,expenseDataArrayList);

        rv_results.setAdapter(adapter);
        rv_results.setVisibility(View.VISIBLE);
        adapter.setNotifyOnChange(true);


        rv_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ExpenseData e=new ExpenseData();
                Log.d("inside listview",""+expenseDataArrayList);
                e= adapter.getItem(position);
                mListener.gotoShowFragment(e);
            }
        });

        rv_results.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ExpenseData e= new ExpenseData();
                e= (ExpenseData)adapter.getItem(position);
                expenseDataArrayList.remove(e);

                if(expenseDataArrayList.size() == 0)
                {
                    //statusTextView.setVisibility(View.VISIBLE);
                    rv_results.setVisibility(View.INVISIBLE);
                }
                else {
                    //statusTextView.setVisibility(View.INVISIBLE);
                    //adapter=new ExpenseAdapter(getActivity(), R.layout.row_item, expenseDataArrayList);
                    rv_results.setAdapter(adapter);
                    adapter.setNotifyOnChange(true);
                    rv_results.setVisibility(View.VISIBLE);
                }
                //refresh listview
                Toast.makeText(getContext(),"Expense deleted",Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        this.expenseDataArrayList = expenseDataArrayList;

    }

    public interface OnFragmentInteractionListener {
        void addButtonClick(boolean clicked);
        //void itemClick();
        void gotoShowFragment(ExpenseData e);
    }
}
