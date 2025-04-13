package pers.tlx.kingdeescpserver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pers.tlx.kingdeescpserver.model.*;
import pers.tlx.kingdeescpserver.util.TokenService;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;

@Service
public class KingdeeService {
    private static final Logger logger = LoggerFactory.getLogger(KingdeeService.class);
    private static final String BASE_URL = "https://feature.kingdee.com:1026/feature_sit_scm/kapi/v2/kdtest/pom/pom_mftorder";
    
    private final RestTemplate restTemplate;
    private final TokenService tokenService;

    @Autowired
    public KingdeeService(TokenService tokenService) {
        this.tokenService = tokenService;
        this.restTemplate = new RestTemplate();
        
        // 添加自定义的HttpMessageConverter来处理text/json
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(
            MediaType.APPLICATION_JSON,
            new MediaType("text", "json"),
            new MediaType("text", "json", StandardCharsets.UTF_8)
        ));
        this.restTemplate.getMessageConverters().add(0, converter);
    }

    @Tool(description = "查询生产工单pom_mftorder单据的数据信息，支持根据单据状态查询，支持指定分页参数")
    public String queryOrders(@ToolParam(description = "单据状态，例如：暂存-A,提交-B,审核-C，后台查询时使用字母，不匹配则默认A") String billStatus,
                              @ToolParam(description = "一页查询多少条，默认10条") int pageSize,
                              @ToolParam(description = "当前查询的是第几页，默认1") int pageNo) {
        String url = BASE_URL + "/pomorderquery";
        
        PomOrderQueryRequest request = new PomOrderQueryRequest();
        PomOrderQueryRequest.QueryData data = new PomOrderQueryRequest.QueryData();
        data.setBillstatus(billStatus);
        request.setData(data);
        request.setPageSize(pageSize);
        request.setPageNo(pageNo);

        HttpHeaders headers = createHeaders();
        HttpEntity<PomOrderQueryRequest> entity = new HttpEntity<>(request, headers);

        try {
            PomOrderQueryResponse response = restTemplate.postForObject(url, entity, PomOrderQueryResponse.class);
            if (response != null && response.isStatus() && response.getData() != null) {
                return formatQueryResponse(response);
            }
            return "查询失败：" + (response != null ? response.getMessage() : "未知错误");
        } catch (Exception e) {
            logger.error("Error querying orders", e);
            return "查询失败：" + e.getMessage();
        }
    }

    @Tool(description = "对生产工单pom_mftorder单据的进行提交操作，需要指定单据编码billNo")
    public String submitOrder(@ToolParam(description = "生产工单单据编码billno") String billNo) {
        String url = BASE_URL + "/pomordersubmit";
        
        PomOrderSubmitRequest request = new PomOrderSubmitRequest();
        PomOrderSubmitRequest.SubmitData data = new PomOrderSubmitRequest.SubmitData();
        data.setBillno(billNo);
        request.setData(data);

        HttpHeaders headers = createHeaders();
        HttpEntity<PomOrderSubmitRequest> entity = new HttpEntity<>(request, headers);

        try {
            PomOrderSubmitResponse response = restTemplate.postForObject(url, entity, PomOrderSubmitResponse.class);
            if (response != null && response.isStatus()) {
                return "单据" + billNo + "提交成功";
            }
            return "单据" + billNo + "提交失败：" + response.getMessage();
        } catch (Exception e) {
            logger.error("Error submitting order", e);
            return "单据" + billNo + "提交失败：" + e.getMessage();
        }
    }

    @Tool(description = "对生产工单pom_mftorder单据的进行审核操作，需要指定单据编码billNo")
    public String auditOrder(@ToolParam(description = "生产工单单据编码billno") String billNo) {
        String url = BASE_URL + "/pomorderaudit";
        
        PomOrderSubmitRequest request = new PomOrderSubmitRequest();
        PomOrderSubmitRequest.SubmitData data = new PomOrderSubmitRequest.SubmitData();
        data.setBillno(billNo);
        request.setData(data);

        HttpHeaders headers = createHeaders();
        HttpEntity<PomOrderSubmitRequest> entity = new HttpEntity<>(request, headers);

        try {
            PomOrderSubmitResponse response = restTemplate.postForObject(url, entity, PomOrderSubmitResponse.class);
            if (response != null && response.isStatus()) {
                return "单据" + billNo + "审核成功";
            }
            return "单据" + billNo + "审核失败：" + response.getMessage();
        } catch (Exception e) {
            logger.error("Error auditing order", e);
            return "单据" + billNo + "审核失败：" + e.getMessage();
        }
    }

    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("accessToken", tokenService.getToken());
        headers.set("Idempotency-Key", UUID.randomUUID().toString());
        return headers;
    }

    private String formatQueryResponse(PomOrderQueryResponse response) {
        StringBuilder result = new StringBuilder();
        if (response.getData() != null && response.getData().getRows() != null) {
            for (PomOrderQueryResponse.OrderRow row : response.getData().getRows()) {
                // 添加单据信息
                result.append("\t单据编码：").append(row.getBillno())
                      .append("，状态：").append(row.getBillstatus()).append("\n");
                
                // 添加物料信息
                if (row.getTreeentryentity() != null) {
                    for (PomOrderQueryResponse.TreeEntryEntity entity : row.getTreeentryentity()) {
                        result.append("\t\t物料编码：").append(entity.getMaterial_number())
                              .append("，物料名称：").append(entity.getMaterial_name()).append("\n");
                    }
                }
            }
        }
        return result.toString();
    }
} 