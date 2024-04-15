package com.tawasupermarket.customermicroservice.repository;

import com.tawasupermarket.customermicroservice.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, String> {
    Optional<UserModel> getUserModelByUsername(String username);
}
