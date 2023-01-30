package com.example.demo.data.service;

import com.example.demo.data.entity.ListenerEntity;

public interface ListenerService {

  ListenerEntity getEntity(Long id);

  void saveEntity(ListenerEntity listener);

  void updateEntity(ListenerEntity listener);

  void removeEntity(ListenerEntity listener);

}