package com.logging.shortenurl.application;

import com.logging.shortenurl.domain.LackOfShortenUrlKeyException;
import com.logging.shortenurl.domain.NotFoundShortenUrlException;
import com.logging.shortenurl.domain.ShortenUrl;
import com.logging.shortenurl.domain.ShortenUrlRepository;
import com.logging.shortenurl.presentation.ShortenUrlCreateRequestDto;
import com.logging.shortenurl.presentation.ShortenUrlCreateResponseDto;
import com.logging.shortenurl.presentation.ShortenUrlInformationDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SimpleShortenUrlService {

    private ShortenUrlRepository shortenUrlRepository;

    @Autowired
    SimpleShortenUrlService(ShortenUrlRepository shortenUrlRepository) {
        this.shortenUrlRepository = shortenUrlRepository;
    }

    public ShortenUrlCreateResponseDto generateShortenUrl(
        ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        // 1. 단축 URL 키(Key) 생성
        // 2. 원래의 URL과 단축 URL 키를 통해 ShortenUrl 도메인 객체 생성
        // 3. 생성된 ShortenUrl을 레포지토리를 통해 저장
        // 4. ShortenUrl을 ShortenUrlCreateResponseDto로 변환하여 반환
        String originalUrl = shortenUrlCreateRequestDto.getOriginalUrl();
        String shortenUrlKey = getUniqueShortenUrlKey();
        log.debug("getUniqueShortenUrlKey: {}", shortenUrlKey); // 실제로 오류 없이 제대로 데이터가 나오는 지를 확인할 수 있음.

        ShortenUrl shortenUrl = new ShortenUrl(originalUrl, shortenUrlKey);
        shortenUrlRepository.saveShortenUrl(shortenUrl);
        log.info("shortenUrl 생성: {}", shortenUrl); // 새로 생성된 것들에 대해서 확인을 함.

        ShortenUrlCreateResponseDto shortenUrlCreateResponseDto = new ShortenUrlCreateResponseDto(shortenUrl);
        return shortenUrlCreateResponseDto;
    }

    private String getUniqueShortenUrlKey() {
        final int MAX_RETRY_COUNT = 5;
        int count = 0;
        while(count++ < MAX_RETRY_COUNT) {
            String shortenUrlKey = ShortenUrl.generateShortenUrlKey();
            ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

            if (null == shortenUrl)
                return shortenUrlKey;

            log.warn("단축 URL 재시도! 재시도 횟수 : {}", count + 1);
        }
        throw new LackOfShortenUrlKeyException();
    }

    public ShortenUrlInformationDto getShortenUrlInformationByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if (null == shortenUrl) {
            throw new NotFoundShortenUrlException("단축 URL을 생성하지 못했습니다. shortenUrlKey=" + shortenUrlKey);
        }

        ShortenUrlInformationDto shortenUrlInformationDto = new ShortenUrlInformationDto(shortenUrl);
        return shortenUrlInformationDto;
    }

    public String getOriginalUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrlRepository.findShortenUrlByShortenUrlKey(shortenUrlKey);

        if (null == shortenUrl) {
            throw new NotFoundShortenUrlException("단축 URL을 생성하지 못했습니다. shortenUrlKey=" + shortenUrlKey);
        }

        shortenUrl.increaseRedirectCount();
        shortenUrlRepository.saveShortenUrl(shortenUrl);

        String originalUrl = shortenUrl.getOriginalUrl();

        return originalUrl;
    }
}
