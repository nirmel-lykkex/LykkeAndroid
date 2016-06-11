package com.lykkex.LykkeWallet.rest.history.reposnse.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by e.kazimirova on 28.03.2016.
 */
public class History {

    @SerializedName("MarketOrders")
    private MarketOrder[] marketOrders;

    @SerializedName("CashInOut")
    private CashInOut[] cashInOuts;

    @SerializedName("Trades")
    private Trading[] trades;

    public List<ItemHistory> getList(){
        List<ItemHistory> list = new ArrayList<>();
        if (cashInOuts != null) {
            Collections.addAll(list, cashInOuts);
        }

        if (trades != null) {
            Collections.addAll(list, trades);
        }

        Collections.sort(list, new DateComparator(false));
        return list;
    }

    private class DateComparator implements Comparator<ItemHistory> {
        private boolean asc;

        public DateComparator(boolean asc) {
            this.asc = asc;
        }

        @Override
        public int compare(ItemHistory lhs, ItemHistory rhs) {
            if (lhs.getDateTime() == null || rhs.getDateTime() == null) {
                return 1;
            }

            return (asc ? 1 : -1) * lhs.getDateTime()
                    .compareTo(rhs.getDateTime());
        }
    }
}
