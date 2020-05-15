package com.dap.cloud;

import com.dap.cloud.base.service.BaseService;
import org.springframework.stereotype.Service;

@Service
public class TestService extends BaseService {

    public void testFunc() {
        System.out.println("TestService.testFunc 执行了");
    }

}
