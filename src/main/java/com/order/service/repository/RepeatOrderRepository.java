package com.order.service.repository;

import java.time.LocalDate;
import java.util.List;

import com.order.service.model.RepeatOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepeatOrderRepository extends JpaRepository<RepeatOrder,Long> {
	
	List<RepeatOrder> findAllByDeliveryDate(LocalDate date);
	
	List<RepeatOrder> findAllByUserId(Long id);
	
}
