package com.aarrd.room_designer.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);
}
