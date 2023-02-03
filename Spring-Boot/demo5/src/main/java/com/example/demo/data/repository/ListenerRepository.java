package com.example.demo.data.repository;


import com.example.demo.data.entity.ListenerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListenerRepository extends JpaRepository<ListenerEntity, Long> {

}