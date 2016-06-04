package com.lykkex.LykkeWallet.gui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lykkex.LykkeWallet.R;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodeData;
import com.lykkex.LykkeWallet.rest.registration.response.models.CountryPhoneCodesResult;

/**
 * Created by Murtic on 08/05/16.
 */
public class CountryPhoneCodesAdapter extends BaseAdapter {

    private final OnInteractionListener mListener;

    private final Context context;

    private final CountryPhoneCodesResult countryPhoneCodes;

    public CountryPhoneCodesAdapter(Context context, CountryPhoneCodesResult countryPhoneCodes, OnInteractionListener listener) {
        this.mListener = listener;
        this.context = context;
        this.countryPhoneCodes = countryPhoneCodes;
    }

    @Override
    public int getCount() {
        return countryPhoneCodes.getCountriesList().size();
    }

    @Override
    public Object getItem(int position) {
        return countryPhoneCodes.getCountriesList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;

        LayoutInflater inflater = LayoutInflater.from(context);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.country_phone_code_item, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CountryPhoneCodeData countryPhoneCode = (CountryPhoneCodeData) getItem(position);

        viewHolder.countryPhoneCode = countryPhoneCode;
        viewHolder.mCountryName.setText(countryPhoneCode.getName());
        viewHolder.mPhoneCode.setText(String.valueOf(countryPhoneCode.getPrefix()));

        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onSelected(viewHolder.countryPhoneCode, position);
                }
            }
        });

        return convertView;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final TextView mCountryName;
        final TextView mPhoneCode;

        CountryPhoneCodeData countryPhoneCode;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mCountryName = (TextView) view.findViewById(R.id.countryName);
            mPhoneCode = (TextView) view.findViewById(R.id.phoneCode);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mCountryName.getText() + "'";
        }
    }

    public interface OnInteractionListener {
        void onSelected(CountryPhoneCodeData item, int position);
    }
}
