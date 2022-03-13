package com.fgroupindonesia.fgimobilebaru.helper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fgroupindonesia.fgimobilebaru.object.RemoteLoginClient;
import com.fgroupindonesia.fgimobilebaru.DesktopActivity;
import com.fgroupindonesia.fgimobilebaru.R;

import java.util.ArrayList;

public class RemoteLoginClientAdapter extends ArrayAdapter<RemoteLoginClient> {
    private Context context;
    private ArrayList<RemoteLoginClient> values;
    //private Activity act;
    private DesktopActivity desktopAct;

    private static class ViewHolder {
        TextView txtCountry;
        TextView txtIPAddress;
        TextView txtStatus;
        TextView txtMachID;
        ImageView imageClientLogout;
        ImageView imageCountry;
    }

    public void setActivity(Activity actIn) {
        desktopAct = (DesktopActivity) actIn;
    }

    private Activity getActivity() {
        return desktopAct;
    }


    public RemoteLoginClientAdapter(Context context, ArrayList<RemoteLoginClient> values) {
        super(context, R.layout.list_desktop, values);
        this.context = context;
        this.values = values;
    }

    public RemoteLoginClient getItem(int post) {
        return values.get(post);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RemoteLoginClient dataModel = getItem(position);
        final ViewHolder viewHolder;
        View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_desktop, parent, false);

            viewHolder.txtMachID = (TextView) convertView.findViewById(R.id.textviewMachineID);
            viewHolder.txtCountry = (TextView) convertView.findViewById(R.id.textviewCountryClient);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.textviewStatusClient);
            viewHolder.txtIPAddress = (TextView) convertView.findViewById(R.id.textviewIPAddressClient);
            viewHolder.imageClientLogout = (ImageView) convertView.findViewById(R.id.imageViewClientLogout);
            viewHolder.imageCountry = (ImageView) convertView.findViewById(R.id.imageViewCountryFlag);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        viewHolder.txtMachID.setText("Machine : "+dataModel.getIp_address());
        viewHolder.txtIPAddress.setText("IP Address : "+dataModel.getIp_address());
        viewHolder.txtStatus.setText("Status : " + dataModel.getStatus());
        viewHolder.txtCountry.setText("Country : " + dataModel.getCountry());


        if (dataModel.getCountry() != null) {
            viewHolder.imageCountry.setImageResource(getNumberImage(dataModel.getCountry()));
        }

        return convertView;
    }

    private int getNumberImage(String countryName){

        int resourceId = getActivity().getResources().getIdentifier("countryflag_"+countryName.toLowerCase(), "drawable", context.getPackageName());
        return resourceId;

    }

}
