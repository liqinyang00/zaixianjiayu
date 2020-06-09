package com.ed.util;

import java.io.Serializable;
import java.util.List;

public class DataGridResult implements Serializable {
    /**
     * serialVersionUID : TODO
     */
    private static final long serialVersionUID = 4438889826054379161L;
    private long total;
    private List rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

	@Override
	public String toString() {
		return "DataGridResult [total=" + total + ", rows=" + rows + "]";
	}
}
