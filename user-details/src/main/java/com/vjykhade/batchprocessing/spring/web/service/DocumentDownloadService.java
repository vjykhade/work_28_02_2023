package com.vjykhade.batchprocessing.spring.web.service;

import com.vjykhade.batchprocessing.spring.web.entity.UserDetails;
import com.vjykhade.batchprocessing.spring.web.helper.ExcelDownloadHelper;
import com.vjykhade.batchprocessing.spring.web.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.List;

@Service
public class DocumentDownloadService {
    @Autowired
    UserDetailsRepository userDetailsRepository;
    public ByteArrayInputStream loadDataToExcel()
    {
        List<UserDetails> userDetailsList = userDetailsRepository.findAll();
        ByteArrayInputStream getByteStreamIn = ExcelDownloadHelper.currencyDetailsToExcel(userDetailsList);
        return getByteStreamIn;
    }

    public List<UserDetails> getUserList()
    {
        return userDetailsRepository.findAll();
    }

}
