package com.stream.video.streaming.webapp;


import com.stream.video.streaming.webapp.controller.StreamVideoController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebMvcTest(StreamVideoController.class)
public class StreamVideoControllerTest {



    @Autowired
    MockMvc mockMvc;


    @Test
    public void handleGetRequest() throws Exception {


        mockMvc.perform(MockMvcRequestBuilders.get("/videos")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }


}
