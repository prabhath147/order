//package com.order.service.service.implementation;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.atLeast;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import com.order.service.dto.RepeatOrderDto;
//import com.order.service.dto.RepeatOrderResponse;
//import com.order.service.model.Address;
//import com.order.service.model.RepeatOrder;
//import com.order.service.repository.RepeatOrderRepository;
//
//import java.time.LocalDate;
//import java.util.HashSet;
//
//import org.junit.jupiter.api.Disabled;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//@ContextConfiguration(classes = {RepeatOrderServiceImplementation.class})
//@ExtendWith(SpringExtension.class)
//class RepeatOrderServiceImplementationTest {
//    @MockBean
//    private ModelMapper modelMapper;
//
//    @MockBean
//    private RepeatOrderRepository repeatOrderRepository;
//
//    @Autowired
//    private RepeatOrderServiceImplementation repeatOrderServiceImplementation;
//
//    @Test
//    void testCreateOptIn() {
//        Address address = new Address();
//        address.setAddressId(123L);
//        address.setCity("Oxford");
//        address.setCountry("GB");
//        address.setPinCode("Pin Code");
//        address.setState("MD");
//        address.setStreet("Street");
//
//        RepeatOrder repeatOrder = new RepeatOrder();
//        repeatOrder.setAddress(address);
//        repeatOrder.setDeliveryDate(LocalDate.ofEpochDay(1L));
//        repeatOrder.setId(123L);
//        repeatOrder.setIntervalInDays(42);
//        repeatOrder.setName("Name");
//        repeatOrder.setNumberOfDeliveries(10);
//        repeatOrder.setRepeatOrderItems(new HashSet<>());
//        when(repeatOrderRepository.save((RepeatOrder) any())).thenReturn(repeatOrder);
//        RepeatOrderResponse actualCreateOptInResult = repeatOrderServiceImplementation
//                .createOptIn(new RepeatOrderDto());
//        assertNull(actualCreateOptInResult.getAddress());
//        assertTrue(actualCreateOptInResult.getRepeatOrderItems().isEmpty());
//        assertEquals(0, actualCreateOptInResult.getNumberOfDeliveries());
//        assertNull(actualCreateOptInResult.getName());
//        assertEquals(0, actualCreateOptInResult.getIntervalInDays());
//        assertNull(actualCreateOptInResult.getId());
//        verify(repeatOrderRepository).save((RepeatOrder) any());
//    }
//
//    @Test
//    void testCreateOptIn2() {
//        Address address = new Address();
//        address.setAddressId(123L);
//        address.setCity("Oxford");
//        address.setCountry("GB");
//        address.setPinCode("Pin Code");
//        address.setState("MD");
//        address.setStreet("Street");
//
//        RepeatOrder repeatOrder = new RepeatOrder();
//        repeatOrder.setAddress(address);
//        repeatOrder.setDeliveryDate(LocalDate.ofEpochDay(1L));
//        repeatOrder.setId(123L);
//        repeatOrder.setIntervalInDays(42);
//        repeatOrder.setName("Name");
//        repeatOrder.setNumberOfDeliveries(10);
//        repeatOrder.setRepeatOrderItems(new HashSet<>());
//        when(repeatOrderRepository.save((RepeatOrder) any())).thenReturn(repeatOrder);
//
//        Address address1 = new Address();
//        address1.setAddressId(123L);
//        address1.setCity("Oxford");
//        address1.setCountry("GB");
//        address1.setPinCode("Pin Code");
//        address1.setState("MD");
//        address1.setStreet("Street");
//        RepeatOrderDto repeatOrderRequest = mock(RepeatOrderDto.class);
//        when(repeatOrderRequest.getAddress()).thenReturn(address1);
//        when(repeatOrderRequest.getIntervalInDays()).thenReturn(42);
//        when(repeatOrderRequest.getNumberOfDeliveries()).thenReturn(10);
//        when(repeatOrderRequest.getName()).thenReturn("Name");
//        when(repeatOrderRequest.getRepeatOrderItems()).thenReturn(new HashSet<>());
//        RepeatOrderResponse actualCreateOptInResult = repeatOrderServiceImplementation.createOptIn(repeatOrderRequest);
//        assertEquals(address, actualCreateOptInResult.getAddress());
//        assertTrue(actualCreateOptInResult.getRepeatOrderItems().isEmpty());
//        assertEquals(42, actualCreateOptInResult.getIntervalInDays());
//        assertEquals("Name", actualCreateOptInResult.getName());
//        assertNull(actualCreateOptInResult.getId());
//        assertEquals(10, actualCreateOptInResult.getNumberOfDeliveries());
//        verify(repeatOrderRepository).save((RepeatOrder) any());
//        verify(repeatOrderRequest).getAddress();
//        verify(repeatOrderRequest, atLeast(1)).getIntervalInDays();
//        verify(repeatOrderRequest).getNumberOfDeliveries();
//        verify(repeatOrderRequest).getName();
//        verify(repeatOrderRequest).getRepeatOrderItems();
//    }
//}
//
