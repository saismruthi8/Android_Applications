package com.example.kshitijjaju.inclass07_group02;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class AddExpenseFragment extends Fragment {

    RecyclerView rv_results;
    EditText et_name, et_amount;
    TextView tv_category;
    Button btn_add_exp, btn_cancel;
    String[] categoryArray;
    String category = null;

    private OnFragmentInteractionListener mListener;

    public AddExpenseFragment() {
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
        View view =inflater.inflate(R.layout.fragment_add_expense, container, false);
        et_name = view.findViewById(R.id.et_name);
        et_amount = view.findViewById(R.id.et_amount);
        tv_category = view.findViewById(R.id.tv_category);
        btn_add_exp = view.findViewById(R.id.btn_add_exp);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        categoryArray = new String[]{"Groceries", "Invoice", "Transportation", "Shopping", "Rent", "Trips", "Utilities", "Other"};

        btn_add_exp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=false;
                if(et_name.getText().toString() == null ||et_name.getText().toString().length() == 0)
                {
                    et_name.setError("Give expense Name");
                    flag = true;
                }
                if(et_amount.getText().toString() == null || et_amount.getText().toString().length() == 0)
                {
                   et_amount.setError("Enter a valid number");
                    flag = true;
                }
                if(tv_category.getText().toString() == null|| tv_category.getText().toString().length() == 0)
                {
                    tv_category.setError("Give a category");
                    flag = true;
                }

                if(flag==false){
                    ExpenseData ex=new ExpenseData();
                    ex.setName(et_name.getText().toString());
                    ex.setAmount(et_amount.getText().toString());
                    ex.setCategory(category);
                    Date date=new Date();
                    String str=new SimpleDateFormat("MM/dd/yyyy").format(date);
                    ex.setDate(str);
                    mListener.onAddExpClick(ex);
                    Log.d("expenseDataArrayList",""+et_name.getText().toString()+ et_amount.getText().toString()+ category);


                }

                else if(flag==true){
                    Toast.makeText(getActivity(), "Please enter all the values", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelClick();
            }
        });

        tv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setItems(categoryArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                category= categoryArray[which];
                                tv_category.setText(category);
                            }
                        });
                builder.create().show();

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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

    public interface OnFragmentInteractionListener {

        void onAddExpClick(ExpenseData e);
        void onCancelClick();
        //void onCategoryClick();
    }
}
