package com.oauth2.login.auth.repository;

import com.oauth2.login.auth.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}
