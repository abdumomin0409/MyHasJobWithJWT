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

import java.util.List;

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
        adsRepository.findByTitleAndPrice(dto.getTitle(), dto.getPrice())
                .orElseThrow(() -> new RuntimeException("Ads already exists"));
        Ads ads = ADS_MAPPER.toEntity(dto);
        ads.setUser(getUser());
        return adsRepository.save(ads);
    }


    public Page<Ads> getAllActive(Pageable pageable) {
        return adsRepository.findAllActive(pageable);
    }

    public Page<Ads> getAllFixed(Pageable pageable) {
        if (!getUser().getRole().equals(UserRole.ADMIN)) {
            throw new RuntimeException("You can't see all ads");
        }
        return adsRepository.findAllFixed(pageable);
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
        ADS_MAPPER.updateAdsFromDTO(dto, oldAds);
        return adsRepository.save(oldAds);
    }

    public String delete(String id) {
        Ads ads = this.findById(id);
        if (!ads.getUser().equals(getUser())) {
            throw new RuntimeException("You can't delete this ads");
        }
        ads.setActive(false);
        adsRepository.save(ads);
        return "Ads successfully deleted";
    }

    public Ads getById(String id) {
        return adsRepository.findId(id).orElseThrow(() -> new RuntimeException("Ads not found"));
    }

    public User getUser() {
        return sessionUser.user();
    }

    public Ads findById(String id) {
        return adsRepository.findId(id).orElseThrow(() -> new RuntimeException("Ads not found"));
    }
}
