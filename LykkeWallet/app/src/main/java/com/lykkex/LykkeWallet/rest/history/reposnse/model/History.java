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

        private SimpleDateFormat getSimpleFormat(){
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
        private boolean asc;

        public DateComparator(boolean asc) {
            this.asc = asc;
        }

        @Override
        public int compare(ItemHistory lhs, ItemHistory rhs) {
            if (lhs.getDateTime() == null || rhs.getDateTime() == null) {
                return 1;
            }
            int indexLhs = lhs.getDateTime().indexOf(".");
            int indexRhs = rhs.getDateTime().indexOf(".");
            if (indexLhs != -1) {
                lhs.setDateTime(lhs.getDateTime().substring(0, indexLhs));
            }
            if (indexRhs != -1) {
                rhs.setDateTime(rhs.getDateTime().substring(0, indexRhs));
            }
            try {
                return (asc ? 1 : -1) * getSimpleFormat().parse(lhs.getDateTime())
                        .compareTo(getSimpleFormat().parse(rhs.getDateTime()));
            } catch (ParseException e) {
                e.printStackTrace();
                return 1;
            }
        }
    }
}
