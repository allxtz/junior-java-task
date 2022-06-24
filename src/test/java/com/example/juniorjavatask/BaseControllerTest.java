package com.example.juniorjavatask;

import com.example.juniorjavatask.controller.BaseController;
import com.example.juniorjavatask.services.MyUserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.swing.*;

public class BaseControllerTest {

    private MockMvc mockMvc;


    @BeforeEach
    public void setup() throws Exception {
        mockMvc= MockMvcBuilders.standaloneSetup(new BaseController()).build();


    }
}
