package com.example.demo.repo;

import com.example.demo.model.DTO.GetNumberOfRole;
import com.example.demo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
    @Query(nativeQuery = true, value = "select name, count(user_id) as number from role left join security1.user_roles ur on role.id = ur.roles_id group by name;\n")

    Iterable<GetNumberOfRole> getAllNumberOfRole();
}
