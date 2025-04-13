package pers.tlx.kingdeescpserver.model;

import lombok.Data;

@Data
public class PomOrderQueryRequest {
    private QueryData data;
    private int pageSize;
    private int pageNo;

    @Data
    public static class QueryData {
        private String billstatus;
    }
} 