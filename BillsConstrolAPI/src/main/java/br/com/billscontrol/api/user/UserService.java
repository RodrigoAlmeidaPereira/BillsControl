package br.com.billscontrol.api.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {

    User save(User user);
    Optional<User> findById(Long id);
    User update(User user);
    Page<User> findAll(Pageable pageable);
    boolean isEmpty();

    void init(User user);
}
