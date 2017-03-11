package com.banksy.expandablecalendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by orange on 3/5/17.
 */

public class ExpandableAdapter extends BaseAdapter {

  private static final String TAG = "ExpandableAdapter";

  private WeakReference<Context> mContext;
  private Calendar mSelectCalendar = Calendar.getInstance();
  private SparseIntArray mWeekList = new SparseIntArray();
  private List<MyDay> mMonthList = new ArrayList<>();

  private boolean isExpand = false;

  private int selection = -1;
  private final Calendar mCalendar;
  private int mCurMonth = -1;
  private SharedPreferences preferences;
  private Calendar mMonthCalendar;
  private int mCurYear;

  public void next() {
    if (isExpand){
      if (mCurMonth != 11) {
        mCurMonth += 1 ;
      }else {
        mCurMonth = 0 ;
        mCurYear += 1 ;
      }
      Log.v(TAG, "month == " + mCurMonth + " year == " + mCurYear);
      saveDate();
      initMonthData();
    }else {
      initWeekData();
    }
  }

  public void previous() {
    if (isExpand) {
      if (mCurMonth != 0) {
        mCurMonth -= 1 ;
      }else {
        mCurMonth = 11 ;
        mCurYear -= 1 ;
      }
      Log.v(TAG, "month == " + mCurMonth + " year == " + mCurYear);
      saveDate();
      initMonthData();
    }else {
      mCalendar.add(Calendar.DAY_OF_YEAR,-14);
      initWeekData();
    }
  }

  public interface onListener {
    void onMonthChange(int month);
  }

  private onListener listener;

  public void setListener(onListener listener) {
    this.listener = listener;
  }

  public ExpandableAdapter(Context context) {
    mContext = new WeakReference<>(context);
    this.mCalendar = Calendar.getInstance();
    mCurMonth = this.mCalendar.get(Calendar.MONTH);
    preferences = context.getSharedPreferences("calender:month:pref", Context.MODE_PRIVATE);
    mCurYear = mCalendar.get(Calendar.YEAR) ;
    selection = mCalendar.get(Calendar.DAY_OF_WEEK) - 1;
    saveDate();
    mSelectCalendar.setTime(mCalendar.getTime());
    //initData();
    mCalendar.add(Calendar.DAY_OF_YEAR, -mCalendar.get(Calendar.DAY_OF_WEEK));
    initWeekData();
  }

  private void saveDate(){
    preferences.edit().putInt("month", mCurMonth).apply();
    preferences.edit().putInt("year", mCurYear).apply();
  }

  private void getDate() {
    mCurMonth = preferences.getInt("month", -1);
    mCurYear = preferences.getInt("year", -1);
  }

  public ExpandableAdapter(Context context, Calendar mCalendar, Calendar mSelectCalendar,
      onListener listener, boolean expand) {
    mContext = new WeakReference<>(context);
    this.mCalendar = mCalendar;
    this.listener = listener;
    selection = -1 ;
    this.mSelectCalendar = mSelectCalendar;
    preferences = context.getSharedPreferences("calender:month:pref", Context.MODE_PRIVATE);
    getDate();
    this.isExpand = expand;
  }


  private  void initWeekData(){
    mWeekList.clear();
    for (int i = 0; i < 7; i++) {
      mCalendar.add(Calendar.DAY_OF_YEAR, 1);
      if (mSelectCalendar != null) {
        if (checkSelect(mSelectCalendar, mCalendar)) {
          selection = i;
        }

        if (mCurMonth != -1 && listener != null) {
          int month = mCalendar.get(Calendar.MONTH);
          if (mCurMonth != month) {
            listener.onMonthChange(month);
            mCurMonth = month ;
            mCurYear = mCalendar.get(Calendar.YEAR);
            saveDate();
          }
        }
      }
      mWeekList.put(i, mCalendar.get(Calendar.DAY_OF_MONTH));
    }
    notifyDataSetChanged();
  }

  private void initMonthData(){

    boolean canSeeSelection = selection != -1 ;

    mMonthList.clear();
    mMonthCalendar = Calendar.getInstance();
    if (canSeeSelection) {
      mMonthCalendar.set(Calendar.YEAR,mSelectCalendar.get(Calendar.YEAR));
      mMonthCalendar.set(Calendar.MONTH,mSelectCalendar.get(Calendar.MONTH));
    }else {
      mMonthCalendar.set(Calendar.YEAR, mCurYear);
      mMonthCalendar.set(Calendar.MONTH, mCurMonth);
    }
    //当月有多少天
    int dayMaximum = mMonthCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);

    mMonthCalendar.set(Calendar.DAY_OF_MONTH, 1);
    int week = mMonthCalendar.get(Calendar.DAY_OF_WEEK);
    mMonthCalendar.add(Calendar.DAY_OF_YEAR, -mMonthCalendar.get(Calendar.DAY_OF_WEEK));

    //获取上月补充的月份天数
    for (int i = 0; i < week - 1; i++) {
      mMonthCalendar.add(Calendar.DAY_OF_MONTH, 1);
      MyDay day = new MyDay();
      day.year = mMonthCalendar.get(Calendar.YEAR);
      day.month = mMonthCalendar.get(Calendar.MONTH);
      day.day = mMonthCalendar.get(Calendar.DAY_OF_MONTH);
      mMonthList.add(day);
      if (checkSelect(mMonthCalendar,mSelectCalendar)) {
        selection = mMonthList.size() -1 ;
      }
    }

    //填充当月参数
    for (int i = 0; i < dayMaximum; i++) {
      mMonthCalendar.add(Calendar.DAY_OF_MONTH, 1);
      MyDay day = new MyDay();
      day.year = mMonthCalendar.get(Calendar.YEAR);
      day.month = mMonthCalendar.get(Calendar.MONTH);
      day.day = mMonthCalendar.get(Calendar.DAY_OF_MONTH);
      mMonthList.add(day);
      if (checkSelect(mMonthCalendar,mSelectCalendar)) {
        selection = mMonthList.size() -1 ;
      }
    }

    //设置天数为当月最后一天
    mMonthCalendar.set(Calendar.DAY_OF_MONTH, dayMaximum);
    int theLastDayWeek = mMonthCalendar.get(Calendar.DAY_OF_WEEK);
    //填充下月天数
    for (int i = theLastDayWeek; i < 7; i++) {
      mMonthCalendar.add(Calendar.DAY_OF_MONTH, 1);
      MyDay day = new MyDay();
      day.year = mMonthCalendar.get(Calendar.YEAR);
      day.month = mMonthCalendar.get(Calendar.MONTH);
      day.day = mMonthCalendar.get(Calendar.DAY_OF_MONTH);
      mMonthList.add(day);
      if (checkSelect(mMonthCalendar,mSelectCalendar)) {
        selection = mMonthList.size() -1 ;
      }
    }
    notifyDataSetChanged();
  }


  /**
   * 验证是否是选中的日期
   *
   * @param mSelectCalendar 选中的日期
   * @param mCalendar 测试的日期
   * @return true is selected date
   */
  private boolean checkSelect(Calendar mSelectCalendar, Calendar mCalendar) {
    return mSelectCalendar.get(Calendar.DAY_OF_MONTH) == mCalendar.get(Calendar.DAY_OF_MONTH)
        && mSelectCalendar.get(Calendar.MONTH) == mCalendar.get(Calendar.MONTH)
        && mSelectCalendar.get(Calendar.YEAR) == mCalendar.get(Calendar.YEAR);
  }

  private void initData() {
    mCalendar.add(Calendar.DAY_OF_YEAR, -mCalendar.get(Calendar.DAY_OF_WEEK));
    for (int i = 0; i < 7; i++) {
      mCalendar.add(Calendar.DAY_OF_YEAR, 1);
      mWeekList.put(i, mCalendar.get(Calendar.DAY_OF_MONTH));
    }
  }

  public void expand() {
    if (!isExpand) {
      isExpand = true;
      initMonthData();
    }
  }

  public void collapse() {
    if (isExpand) {
      isExpand = false;
      mCalendar.setTime(mSelectCalendar.getTime());
      mCalendar.add(Calendar.DAY_OF_YEAR, -mCalendar.get(Calendar.DAY_OF_WEEK));
      initWeekData();
    }
  }

  public boolean isExpand() {
    return isExpand;
  }

  public Calendar getData() {
    return mCalendar;
  }

  @Override public int getCount() {
    return isExpand ? mMonthList.size() : mWeekList.size();
  }

  @Override public Object getItem(int position) {
    return null;
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(final int position, View convertView, ViewGroup parent) {

    Log.v(TAG, "getView: parent == " + (parent.getContext() == null));

    ViewHolder viewHolder;

    if (convertView == null) {
      convertView = View.inflate(mContext.get(), R.layout.adatpter__calender__, null);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    String day;
    if (isExpand) {
      MyDay myDay = mMonthList.get(position);
      day = String.valueOf(myDay.day);
      if (myDay.month != mCurMonth) {
        viewHolder.tv.setTextColor(Color.BLUE);
      } else {
        viewHolder.tv.setTextColor(Color.BLACK);
      }
      viewHolder.tv.setSelected(false);
      viewHolder.tv.setBackgroundColor(Color.TRANSPARENT);
    } else {
      day = String.valueOf(mWeekList.get(position));
      viewHolder.tv.setSelected(false);
      viewHolder.tv.setTextColor(Color.BLACK);
      viewHolder.tv.setBackgroundColor(Color.TRANSPARENT);
    }

    if (selection == position) {
      viewHolder.tv.setSelected(true);
      viewHolder.tv.setTextColor(Color.WHITE);
      viewHolder.tv.setBackgroundResource(R.drawable.calendar__shape_circle_red);
    }

    viewHolder.tv.setText(day);

    return convertView;
  }

  public int getSelection() {
    return selection;
  }

  public void updateSelectCalendar(int selection){
    Log.d(TAG, "getCount =" + getCount() + "  selection= " + selection);
    int offset = getCount() - selection - 1;
    mSelectCalendar.add(Calendar.DAY_OF_YEAR, -offset);
    Log.d(TAG, "setSelection: " + mSelectCalendar.get(Calendar.DAY_OF_MONTH));
  }

  public void setSelection(int selection) {
    updateSelectCalendar(selection);
    this.selection = selection;
    notifyDataSetChanged();
  }

  public Calendar getSelectCalendar() {
    return mSelectCalendar;
  }

  private static class ViewHolder {

    private TextView tv;

    public ViewHolder(View view) {
      tv = (TextView) view.findViewById(R.id.tv);
    }
  }

  private class MyDay {
    int day;
    int month;
    int year;
    boolean isCurMonth;
  }
}
