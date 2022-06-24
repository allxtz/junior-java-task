package com.example.juniorjavatask;

import com.example.juniorjavatask.services.DataService;
import com.example.juniorjavatask.services.MyUserDetails;
import com.example.juniorjavatask.services.MyUserDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.security.auth.kerberos.KerberosKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class JuniorJavaTaskApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataService dataService;

    @MockBean
    private MyUserDetailsService userDetailsService;

    @Test
    public void testGetBlogNotAuth() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/blog")).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(302, status);
    }

    @Test
    public void testGetBlogAuth() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/blog").with(user("user")
                .password("user").authorities(new SimpleGrantedAuthority("USER")))).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(200, status);
    }

    @Test
    public void testAuthDelete() throws Exception {


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete("/blog?action=delete&id=2").with(user("user")
                .password("user").authorities(new SimpleGrantedAuthority("USER")))).andReturn();
        int status = mvcResult.getResponse().getStatus();

        assertEquals(403, status);
    }

}
