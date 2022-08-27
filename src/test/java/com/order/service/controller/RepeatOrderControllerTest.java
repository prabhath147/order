//package com.order.service.controller;
//
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.when;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.order.service.dto.RepeatOrderDto;
//import com.order.service.dto.RepeatOrderResponse;
//import com.order.service.model.Address;
//import com.order.service.service.RepeatOrderService;
//
//import java.util.HashSet;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//@ContextConfiguration(classes = {RepeatOrderController.class})
//@ExtendWith(SpringExtension.class)
//class RepeatOrderControllerTest {
//    @Autowired
//    private RepeatOrderController repeatOrderController;
//
//    @MockBean
//    private RepeatOrderService repeatOrderService;
//
//    @Test
//    void testCreateOptIn() throws Exception {
//        when(repeatOrderService.createOptIn((RepeatOrderDto) any())).thenReturn(new RepeatOrderResponse());
//
//        Address address = new Address();
//        address.setAddressId(123L);
//        address.setCity("Oxford");
//        address.setCountry("GB");
//        address.setPinCode("Pin Code");
//        address.setState("MD");
//        address.setStreet("Street");
//
//        RepeatOrderDto repeatOrderRequest = new RepeatOrderDto();
//        repeatOrderRequest.setAddress(address);
//        repeatOrderRequest.setId(123L);
//        repeatOrderRequest.setIntervalInDays(42);
//        repeatOrderRequest.setName("Name");
//        repeatOrderRequest.setNumberOfDeliveries(10);
//        repeatOrderRequest.setRepeatOrderItems(new HashSet<>());
//        String content = (new ObjectMapper()).writeValueAsString(repeatOrderRequest);
//        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/order/optin")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(content);
//        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(repeatOrderController)
//                .build()
//                .perform(requestBuilder);
//        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
//                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
//                .andExpect(MockMvcResultMatchers.content()
//                        .string(
//                                "{\"id\":null,\"name\":null,\"deliveryDate\":null,\"numberOfDeliveries\":0,\"intervalInDays\":0,\"address\":null,"
//                                        + "\"repeatOrderItems\":[]}"));
//    }
//}
//
