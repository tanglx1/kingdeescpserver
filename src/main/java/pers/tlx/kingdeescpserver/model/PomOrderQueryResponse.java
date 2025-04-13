package pers.tlx.kingdeescpserver.model;

import lombok.Data;
import java.util.List;

@Data
public class PomOrderQueryResponse {
    private QueryData data;
    private String errorCode;
    private String message;
    private boolean status;

    @Data
    public static class QueryData {
        private String filter;
        private boolean lastPage;
        private int pageNo;
        private int pageSize;
        private List<OrderRow> rows;
        private int totalCount;
    }

    @Data
    public static class OrderRow {
        private String billno;
        private String billstatus;
        private List<TreeEntryEntity> treeentryentity;
    }

    @Data
    public static class TreeEntryEntity {
        private String material_number;
        private String material_name;
    }
} 