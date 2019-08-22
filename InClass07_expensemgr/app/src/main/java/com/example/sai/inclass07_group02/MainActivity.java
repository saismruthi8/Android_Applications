package com.example.kshitijjaju.inclass07_group02;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements ExpenseAppFragment.OnFragmentInteractionListener , AddExpenseFragment.OnFragmentInteractionListener, ShowExpenseFragment.OnFragmentInteractionListener {

    Context context;
    LinearLayout linlyt_container;
    ArrayList<ExpenseData> expenseDataArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        getSupportFragmentManager().beginTransaction()
                .add(R.id.linlyt_container, new ExpenseAppFragment(), "ExpenseAppFragment")
                .commit();
        //expenseDataArrayList.add(expenseData);
        linlyt_container = findViewById(R.id.linlyt_container);


    }

    @Override
    public void addButtonClick(boolean clicked) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container, new AddExpenseFragment(), "AddExpenseFragment")
                .addToBackStack(null)
                .commit();
    }



    @Override
    public void gotoShowFragment(ExpenseData e) {
        ShowExpenseFragment s = new ShowExpenseFragment();
        // expenseDataArrayList.add(e);
        Bundle b = new Bundle();
        b.putSerializable("expensedetails", e);
        s.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container, s, "ShowExpenseFragment")
                .commit();

      /*  final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                Fragment fragment = (ShowExpenseFragment) getSupportFragmentManager().findFragmentByTag("ShowExpenseFragment");
                ((ShowExpenseFragment) fragment).expenseDataResult(e);
            }
        }, 2000);
*/

    }

    @Override
    public void onAddExpClick(ExpenseData e) {

        expenseDataArrayList.add(e);

        ExpenseAppFragment frag = new ExpenseAppFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("expenselist", expenseDataArrayList);
        Log.d("list from addexp",""+expenseDataArrayList);
//        bundle.putParcelable("list", (Parcelable) listOfExpenses);
        frag.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container,frag,"ExpenseAppFragment").commit();

    }

    @Override
    public void onCancelClick() {
        ExpenseAppFragment e = new ExpenseAppFragment();
        Bundle b = new Bundle();
        b.putSerializable("expenselist", expenseDataArrayList);
        e.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container, e, "ExpenseAppFragment")
                .commit();
     /* if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }else{
            Log.d("back_pressed2:::","stack empty");
        } */
    }


    @Override
    public void onCloseButtonClick() {
        ExpenseAppFragment s = new ExpenseAppFragment();
        // expenseDataArrayList.add(e);
        Bundle b = new Bundle();
        b.putSerializable("expenselist", expenseDataArrayList);
        s.setArguments(b);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.linlyt_container, s, "ExpenseAppFragment")
                .commit();

      /*  if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }else{
            Log.d("back_pressed3:::","stack empty");
        } */

    }
}
// all fragments shud be called as bundle
