package com.cdcn.apartmentonlinemarket.stores.service;

import com.cdcn.apartmentonlinemarket.auth.dto.request.SignupCommand;
import com.cdcn.apartmentonlinemarket.auth.dto.response.SignupResponse;
import com.cdcn.apartmentonlinemarket.stores.domain.dto.StoreDto;
import com.cdcn.apartmentonlinemarket.stores.domain.entity.Store;
import com.cdcn.apartmentonlinemarket.stores.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StoreService {

    List<Store> findAll();

    Store findById(String id);

    void deleteByIdS(String id);

    Store updateStore(String id, Store store);

}
