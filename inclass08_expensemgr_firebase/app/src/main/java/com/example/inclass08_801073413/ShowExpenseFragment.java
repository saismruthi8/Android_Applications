package com.example.inclass08_801073413;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ShowExpenseFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView tv_sh_name, tv_category, tv_amount, tv_date;
    Button btn_close,edit;
    ExpenseData exp=null;

    public ShowExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_show_expense, container, false);
        View view =inflater.inflate(R.layout.fragment_show_expense, container, false);

        tv_sh_name = view.findViewById(R.id.tv_sh_name);
        tv_category = view.findViewById(R.id.tv_category);
        tv_amount = view.findViewById(R.id.tv_amount);
        tv_date = view.findViewById(R.id.tv_date);
        btn_close = view.findViewById(R.id.btn_close);
        edit=view.findViewById(R.id.btn_edit);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            exp=(ExpenseData) bundle.getSerializable("expensedetails");
            Log.d("sai","Expense Objs "+exp);
            expenseDataResult(exp);
        }


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCloseButtonClick();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("inside editclick",""+exp);
                mListener.onEditButtonClick(exp);
            }
        });

        return view;
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

    public void expenseDataResult (ExpenseData expenseData){

        tv_sh_name.setText(expenseData.name);
        tv_amount.setText(expenseData.amount);
        tv_category.setText(expenseData.category);
        tv_date.setText(expenseData.date);
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCloseButtonClick();
        void onEditButtonClick(ExpenseData e);
    }
}
