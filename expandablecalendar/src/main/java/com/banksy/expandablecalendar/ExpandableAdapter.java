package com.banksy.expandablecalendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Calendar;

/**
 * Created by orange on 3/5/17.
 */

public class ExpandableAdapter extends BaseAdapter {
  private static final String TAG = "ExpandableAdapter";
  private WeakReference<Context> mContext ;
  private SparseIntArray mWeekList = new SparseIntArray();
  private boolean isExpand = false;


  private int selection ;
  private final Calendar mCalendar;
  private final int mCurWeek;

  public ExpandableAdapter(Context context) {
    mContext = new WeakReference<Context>(context);
    mCalendar = Calendar.getInstance();
    mCurWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
    selection = mCurWeek - 1 ;
    initData();
  }

  private void initData() {
    if (mCurWeek != 7) {// not Saturday offset to Sunday
      mCalendar.add(Calendar.DAY_OF_YEAR,- mCalendar.get(Calendar.DAY_OF_WEEK));
    }

    for (int i = 0; i < 7; i++) {
      mCalendar.add(Calendar.DAY_OF_YEAR, 1);
      mWeekList.put(i ,mCalendar.get(Calendar.DAY_OF_MONTH));
    }
  }

  @Override public int getCount() {
    return mWeekList.size();
  }

  @Override public Object getItem(int position) {
    return null;
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(final int position, View convertView, ViewGroup parent) {

    Log.d(TAG, "getView: parent == " + (parent == null));

    ViewHolder viewHolder ;

    if (convertView == null) {
      convertView = View.inflate(mContext.get(), R.layout.adatpter__calender__, null);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    }else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    String day = String.valueOf(mWeekList.get(position));
    viewHolder.tv.setText(day);


    //
    if (selection == position) {
      viewHolder.tv.setSelected(true);
      viewHolder.tv.setTextColor(Color.WHITE);
      viewHolder.tv.setBackgroundResource(R.drawable.calendar__shape_circle_red);
    }else {
      viewHolder.tv.setSelected(false);
      viewHolder.tv.setTextColor(Color.BLACK);
      viewHolder.tv.setBackgroundColor(Color.TRANSPARENT);
    }

    //viewHolder.container.setOnClickListener(new View.OnClickListener() {
    //  @Override public void onClick(View v) {
    //    selection = position ;
    //    notifyDataSetChanged();
    //  }
    //});

    return convertView;
  }
  public int getSelection() {
    return selection;
  }

  public void setSelection(int selection) {
    this.selection = selection;
    notifyDataSetChanged();
  }


  private static class ViewHolder {
    private TextView tv;
    public ViewHolder(View view) {
      tv = (TextView) view.findViewById(R.id.tv);
    }
  }
}
