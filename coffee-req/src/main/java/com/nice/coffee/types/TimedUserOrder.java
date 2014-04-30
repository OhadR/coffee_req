package com.nice.coffee.types;

import java.util.Date;
import java.util.Map;

public class TimedUserOrder extends UserOrder implements Comparable {
	private Date firstOrderDate;

    public TimedUserOrder(String username, Map<String, Integer> order, Date firstOrderDate) {
        super(username, order);
        this.firstOrderDate = firstOrderDate;
    }

    public Date getFirstOrderDate() {
        return firstOrderDate;
    }

    public void setFirstOrderDate(Date firstOrderDate) {
        this.firstOrderDate = firstOrderDate;
    }


    public int compareTo(Object o) {
        if (! (o instanceof TimedUserOrder)){
            return 1;
        }

        TimedUserOrder other = (TimedUserOrder)o;
        return this.firstOrderDate.compareTo(other.getFirstOrderDate());
    }
}
