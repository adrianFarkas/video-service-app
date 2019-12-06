package com.codecool.videoservice;

import org.springframework.content.commons.repository.Store;
import org.springframework.content.rest.StoreRestResource;

@StoreRestResource(path="video-store")
public interface VideoStore extends Store<String> {}
