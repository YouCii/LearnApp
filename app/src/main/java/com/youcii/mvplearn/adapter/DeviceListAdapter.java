package com.youcii.mvplearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youcii.mvplearn.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by sayyid on 2016/4/14.
 */
public class DeviceListAdapter extends BaseAdapter {

    private Context context;
    private List<Device> devices;
    private LayoutInflater inflater;
    private DeviceHolder deviceHolder;

    public DeviceListAdapter(Context context, List<Device> devices) {
        this.context = context;
        inflater = LayoutInflater.from(context);

        if (devices != null) {
            this.devices = devices;
        } else {
            this.devices = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return devices.size();
    }

    @Override
    public Object getItem(int position) {
        return devices.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            deviceHolder = new DeviceHolder();
            convertView = inflater.inflate(R.layout.item_device, parent, false);
            deviceHolder.tv_device = (TextView) convertView.findViewById(R.id.tv_device);
            deviceHolder.location = (TextView) convertView.findViewById(R.id.location);
            deviceHolder.tv_distance = (TextView) convertView.findViewById(R.id.tv_distance);

            convertView.setTag(deviceHolder);
        } else {
            deviceHolder = (DeviceHolder) convertView.getTag();
        }
        deviceHolder.location.setVisibility(View.VISIBLE);
        deviceHolder.tv_device.setText(devices.get(position).deviceName);
        if (getDouble(devices.get(position).X) == 0 || getDouble(devices.get(position).Y) == 0) {
            deviceHolder.location.setText("无坐标记录");
            deviceHolder.tv_distance.setVisibility(View.GONE);
        } else {
            DecimalFormat fot = new DecimalFormat("#.######");
            deviceHolder.location.setText(fot.format(getDouble(devices.get(position).X)) + "\n" + fot.format(getDouble(devices.get(position).Y)));
            deviceHolder.tv_distance.setVisibility(View.VISIBLE);
            if (devices.get(position).distance > 1000) {
                deviceHolder.tv_distance.setText("距离上次确定的距离大于1000米");
            } else {
                deviceHolder.tv_distance.setText(devices.get(position).distance + " 米");
            }
        }
        return convertView;
    }

    private double getDouble(String str) {
        double d;
        try {
            d = Double.valueOf(str);
        } catch (Exception e) {
            d = 0;
        }
        return d;
    }

    // ViewHolder
    private class DeviceHolder {
        TextView tv_device;
        TextView location;
        TextView tv_distance;
    }

    public static class Device {
        private String deviceName;
        private String X;
        private String Y;
        public double distance = 0;

        public Device(String deviceName, String x, String y, double distance) {
            this.deviceName = deviceName;
            X = x;
            Y = y;
            this.distance = distance;
        }
    }

    public static class DeviceComparator implements Comparator<Device> {
        @Override
        public int compare(Device o1, Device o2) {
            if (o2.distance < o1.distance) {
                return 1;
            }
            if (o2.distance > o1.distance) {
                return -1;
            }
            return 0;
        }
    }
}
