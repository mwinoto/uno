package com.uno.uno.dynamodb;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface UnoDateRequestsRepository extends CrudRepository<UnoDateDTO, String> {

}