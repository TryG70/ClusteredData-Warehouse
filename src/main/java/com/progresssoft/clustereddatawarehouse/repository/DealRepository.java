package com.progresssoft.clustereddatawarehouse.repository;

import com.progresssoft.clustereddatawarehouse.entity.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DealRepository extends JpaRepository<Deal, Long> {

}
