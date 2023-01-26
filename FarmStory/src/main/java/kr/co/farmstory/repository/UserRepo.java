package kr.co.farmstory.repository;

import kr.co.farmstory.entity.UserEntity;
import org.springframework.data.repository.Repository;

public interface UserRepo extends Repository<UserEntity, String> {

    public UserEntity findByUid(String uid);

}
