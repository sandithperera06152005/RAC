package com.heavenscode.rac.repository;

import com.heavenscode.rac.domain.User;
import java.util.Optional;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the {@link User} entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByEmailIgnoreCase(String email);
    Optional<User> findOneByLogin(String login);

    @Query(
        value = """
        select
            [ID] as [id],
            [UserName] as [user_name],
            [Password] as [password],
            [FullName] as [full_name],
            [Surname] as [surname],
            [Email] as [email],
            [IsActive] as [is_active],
            [ImagePath] as [image_path],
            [RoleId] as [role_id]
        from dbo.[Employee]
        where lower([UserName]) = lower(:login)
        """,
        nativeQuery = true
    )
    Optional<User> findOneWithAuthoritiesByLogin(@Param("login") String login);

    @EntityGraph(attributePaths = "authorities")
    Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

    Page<User> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable);
}
