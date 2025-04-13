package pers.tlx.kingdeescpserver.model;

import lombok.Data;
import java.util.List;

@Data
public class PomOrderSubmitResponse {
    private SubmitData data;
    private String errorCode;
    private String message;
    private boolean status;

    @Data
    public static class SubmitData {
        private String failCount;
        private String filter;
        private List<SubmitResult> result;
        private String successCount;
        private String totalCount;
    }

    @Data
    public static class SubmitResult {
        private boolean billStatus;
        private List<String> errors;
        private String id;
        private String number;
    }
} 