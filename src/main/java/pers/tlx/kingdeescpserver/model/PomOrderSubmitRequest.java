package pers.tlx.kingdeescpserver.model;

import lombok.Data;

@Data
public class PomOrderSubmitRequest {
    private SubmitData data;

    @Data
    public static class SubmitData {
        private String billno;
    }
} 