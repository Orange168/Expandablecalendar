package com.banksy.expandablecalendar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;
import java.util.Calendar;

/**
 * Created by Edward.D.Lin on 17-2-18.
 */

public class ExpandableCalenderFragment extends Fragment {

  private static final String TAG = "CalenderFragment";
  private ViewFlipper mViewFlipper;
  private GestureDetector mGestureDetector;
  private ExpandableAdapter mExAdapter;
  private GridView mGridView;
  private Calendar mSelectCalendar = Calendar.getInstance();

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment__calender__, container, false);
  }


  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    mViewFlipper = (ViewFlipper) view.findViewById(R.id.mViewFlipper);
    mGestureDetector = new GestureDetector(getActivity(), listener);
    mExAdapter = new ExpandableAdapter(getContext());
    addGridView();
    mViewFlipper.addView(mGridView, 0);
    mGridView.setAdapter(mExAdapter);
    mGridView.setSelection(mExAdapter.getSelection());
  }

  private ExpandableAdapter.onListener adapterListener = new ExpandableAdapter.onListener() {
    @Override public void onMonthChange(int month) {
      Toast.makeText(getContext(),"==>>" + month, Toast.LENGTH_LONG).show();
    }
  };

  private void addGridView() {

    LinearLayout.LayoutParams params =
        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    mGridView = new GridView(getContext());
    mGridView.setLayoutParams(params);
    mGridView.setNumColumns(7);
    mGridView.setGravity(Gravity.CENTER_VERTICAL);
    mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
    mGridView.setVerticalSpacing(1);
    mGridView.setHorizontalSpacing(1);
    mGridView.setOnTouchListener(new View.OnTouchListener() {
      @Override public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
      }
    });

    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mExAdapter.getSelection() == position) {
          if (mExAdapter.isExpand()) {
            mExAdapter.collapse();
            mSelectCalendar = mExAdapter.getSelectCalendar();
          }else {
            mExAdapter.expand();
          }
        }else {
          mExAdapter.setSelection(position);
          mSelectCalendar = mExAdapter.getSelectCalendar();
        }
      }
    });

  }

  private  GestureDetector.SimpleOnGestureListener listener = new GestureDetector.SimpleOnGestureListener(){
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

      if (e1.getX() - e2.getX() > 80) {
        Log.d(TAG, "onFling: >>>>>>");
        addGridView();
        Calendar mCalendar = mExAdapter.getData();
        boolean expand = mExAdapter.isExpand();
        mExAdapter = new ExpandableAdapter(getContext(),mCalendar,mSelectCalendar,adapterListener,expand);
        mExAdapter.next();
        mExAdapter.setListener(adapterListener);
        mGridView.setAdapter(mExAdapter);
        mViewFlipper.addView(mGridView, 1);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out));
        mViewFlipper.showNext();
        mViewFlipper.removeViewAt(0);

        return true;
      }else if (e1.getX() - e2.getX() < -80){
        Log.d(TAG, "onFling: <<<<<<");

        addGridView();
        Calendar mCalendar = mExAdapter.getData();
        boolean expand = mExAdapter.isExpand();
        mExAdapter = new ExpandableAdapter(getContext(),mCalendar,mSelectCalendar,adapterListener,expand);
        mExAdapter.previous();
        mGridView.setAdapter(mExAdapter);
        mViewFlipper.addView(mGridView, 1);
        mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_in));
        mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out));
        mViewFlipper.showPrevious();
        mViewFlipper.removeViewAt(0);
        return true;
      }
      return super.onFling(e1, e2, velocityX, velocityY);
    }
  };


}
