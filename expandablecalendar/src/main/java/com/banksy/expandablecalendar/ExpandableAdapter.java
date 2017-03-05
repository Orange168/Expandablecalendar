package com.banksy.expandablecalendar;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;
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
  private SparseArray<String> mWeekList = new SparseArray<>();
  private boolean isExpand = false;


  private int selection ;
  private final Calendar mCalendar;

  public ExpandableAdapter(Context context) {
    mContext = new WeakReference<Context>(context);
    mCalendar = Calendar.getInstance();

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

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    Log.d(TAG, "getView: parent == " + (parent == null));

    ViewHolder viewHolder ;

    if (convertView == null) {
      convertView = View.inflate(mContext.get(), R.layout.adatpter__calender__, null);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    }else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    if (selection == position) {
      viewHolder.tv.setSelected(true);
      viewHolder.tv.setTextColor(Color.WHITE);
      viewHolder.tv.setBackgroundResource(R.drawable.calendar__shape_circle_red);
    }else {
      viewHolder.tv.setSelected(false);
      viewHolder.tv.setTextColor(Color.BLACK);
      viewHolder.tv.setBackgroundColor(Color.TRANSPARENT);
    }


    return convertView;
  }
  public int getSelection() {
    return selection;
  }

  public void setSelection(int selection) {
    this.selection = selection;
  }


  private static class ViewHolder {
    private TextView tv;
    public ViewHolder(View view) {
      tv = (TextView) view.findViewById(R.id.tv);
    }
  }
}
