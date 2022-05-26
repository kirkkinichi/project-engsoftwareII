package com.engsoftwareII.repository;

import com.engsoftwareII.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserEntity, Long> {
}
