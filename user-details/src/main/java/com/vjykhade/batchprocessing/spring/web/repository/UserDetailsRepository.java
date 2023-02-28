package com.vjykhade.batchprocessing.spring.web.repository;

import com.vjykhade.batchprocessing.spring.web.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

}
