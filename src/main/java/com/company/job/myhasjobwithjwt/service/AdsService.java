package com.company.job.myhasjobwithjwt.service;

import com.company.job.myhasjobwithjwt.config.security.SessionUser;
import com.company.job.myhasjobwithjwt.domains.Ads;
import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.enums.UserRole;
import com.company.job.myhasjobwithjwt.payload.ads.AdsCreateDto;
import com.company.job.myhasjobwithjwt.payload.ads.AdsUpdateDto;
import com.company.job.myhasjobwithjwt.repository.AdsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.company.job.myhasjobwithjwt.mappers.AdsMapper.ADS_MAPPER;

@Service
@RequiredArgsConstructor
public class AdsService {
    private final AdsRepository adsRepository;
    private final SessionUser sessionUser;

    public Ads save(AdsCreateDto dto) {
        try {
            double v = Double.parseDouble(dto.getLatitude());
            double v1 = Double.parseDouble(dto.getLongitude());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Latitude or Longitude must be double");
        }
        if (this.existsTitleAndAddressAndPrice(dto.getTitle(), dto.getAddress(), dto.getPrice())) {
            throw new RuntimeException("This Ads already exists");
        }
        Ads ads = ADS_MAPPER.toEntity(dto);
        ads.setUser(getUser());
        return this.adsRepository.save(ads);
    }


    public Page<Ads> getAllActive(Pageable pageable) {
        return this.adsRepository.findAllActive(pageable);
    }

    public Page<Ads> getAllFixed(Pageable pageable) {
        if (!getUser().getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("You can't see all ads");
        }
        return this.adsRepository.findAllFixed(pageable);
    }

    public Ads update(AdsUpdateDto dto) {
        Ads oldAds = this.findById(dto.getId());
        if (!oldAds.getUser().equals(getUser())) {
            throw new RuntimeException("You can't update this ads");
        }
        try {
            double v = Double.parseDouble(dto.getLatitude());
            double v1 = Double.parseDouble(dto.getLongitude());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Latitude or Longitude must be double");
        }
        if (this.existsTitleAndAddressAndPrice(dto.getTitle(), dto.getAddress(), dto.getPrice()))
            throw new RuntimeException("This Ads already exists");
        ADS_MAPPER.updateAdsFromDTO(dto, oldAds);
        return this.adsRepository.save(oldAds);
    }

    public String delete(String id) {
        Ads ads = this.findById(id);
        if (!ads.getUser().equals(getUser())) {
            throw new RuntimeException("You can't delete this ads");
        }
        ads.setActive(false);
        this.adsRepository.save(ads);
        return "Ads successfully deleted";
    }

    public Ads getById(String id) {
        return this.adsRepository.findId(id).orElseThrow(() -> new RuntimeException("Ads not found"));
    }

    public User getUser() {
        return this.sessionUser.user();
    }

    public Ads findById(String id) {
        return this.adsRepository.findId(id).orElseThrow(() -> new RuntimeException("Ads not found"));
    }

    public boolean existsTitleAndAddressAndPrice(String title, String address, Double price) {
        return this.adsRepository.existsByActiveTrueAndTitleAndPrice(title, address, price);
    }
}
