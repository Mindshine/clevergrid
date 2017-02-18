package com.mindshine.clevergrid.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mindshine.clevergrid.domain.SocialUserConnection;

/**
 * Spring Data MongoDB repository for the Social User Connection entity.
 */
public interface SocialUserConnectionRepository extends MongoRepository<SocialUserConnection, String> {

    List<SocialUserConnection> findAllByProviderIdAndProviderUserId(String providerId, String providerUserId);

    List<SocialUserConnection> findAllByProviderIdAndProviderUserIdIn(String providerId, Set<String> providerUserIds);

    List<SocialUserConnection> findAllByUserIdOrderByProviderIdAscRankAsc(String userId);

    List<SocialUserConnection> findAllByUserIdAndProviderIdOrderByRankAsc(String userId, String providerId);

    List<SocialUserConnection> findAllByUserIdAndProviderIdAndProviderUserIdIn(String userId, String providerId, List<String> provideUserId);

    SocialUserConnection findOneByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);

    void deleteByUserIdAndProviderId(String userId, String providerId);

    void deleteByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);
}
