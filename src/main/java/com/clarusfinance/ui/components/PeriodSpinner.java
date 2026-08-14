package com.clarusfinance.ui.components;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.YearMonth;
import java.util.Date;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

public final class PeriodSpinner extends JSpinner {
    public PeriodSpinner() {
        super(new SpinnerDateModel());
        setEditor(new JSpinner.DateEditor(this, "MMMM yyyy"));
        setValue(Date.from(YearMonth.now().atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }

    public YearMonth period() {
        Date date = (Date) getValue();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return YearMonth.from(localDate);
    }

    public void setPeriod(YearMonth period) {
        setValue(Date.from(period.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    }
}
