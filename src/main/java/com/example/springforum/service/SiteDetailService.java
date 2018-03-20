package com.example.springforum.service;

import com.example.springforum.entity.SiteDetail;
import com.example.springforum.repository.SiteDetailsRepository;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class SiteDetailService {
    private final SiteDetailsRepository siteDetailsRepository;
    @Autowired
    public SiteDetailService(SiteDetailsRepository siteDetailsRepository) {
        this.siteDetailsRepository = siteDetailsRepository;
    }

    @Cacheable(value = "siteDetails",key = "#key")
    public Optional<SiteDetail> getKey(String key) {
        return siteDetailsRepository.getByKey(key);
    }
    @Cacheable("siteDetails")
    public Map<String,SiteDetail> getAllSiteDetails(){
        val siteDetails = siteDetailsRepository.findAll();
        Map<String,SiteDetail> siteDetailHashMap = new HashMap<>();
        siteDetails.forEach(siteDetail -> {
            siteDetailHashMap.put(siteDetail.getKey(),siteDetail);
        });
        return siteDetailHashMap;
    }
    private void createSiteDetail(String key, String value, String description) {
        siteDetailsRepository.save(new SiteDetail(key, value, description));
    }

    @CacheEvict(value = "siteDetails",allEntries = true)
    public void setValue(String key, String value) {
        siteDetailsRepository.getByKey(key).ifPresentOrElse(
                it -> siteDetailsRepository.updateValue(key, value),
                () -> createSiteDetail(key, value, ""));
    }
    @CacheEvict(value = "siteDetails", allEntries = true)
    public void setDescription(String key, String description) {
        siteDetailsRepository.getByKey(key).ifPresentOrElse(
                it -> siteDetailsRepository.updateDescription(key, description),
                () -> createSiteDetail(key, null, description));
    }
}
