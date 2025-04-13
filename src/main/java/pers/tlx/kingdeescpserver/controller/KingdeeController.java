package pers.tlx.kingdeescpserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.tlx.kingdeescpserver.service.KingdeeService;

@RestController
@RequestMapping("/api/kingdee")
public class KingdeeController {
    private final KingdeeService kingdeeService;

    @Autowired
    public KingdeeController(KingdeeService kingdeeService) {
        this.kingdeeService = kingdeeService;
    }

    @GetMapping("/orders")
    public String queryOrders(
            @RequestParam(value = "billStatus", defaultValue = "A") String billStatus,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
        return kingdeeService.queryOrders(billStatus, pageSize, pageNo);
    }

    @GetMapping("/orders/submit")
    public String submitOrder(@RequestParam("billNo") String billNo) {
        return kingdeeService.submitOrder(billNo);
    }

    @GetMapping("/orders/audit")
    public String auditOrder(@RequestParam("billNo") String billNo) {
        return kingdeeService.auditOrder(billNo);
    }
} 