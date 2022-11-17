package com.teamway.test.repositories;

import com.teamway.test.repositories.entities.WorkerEntity;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity, Long> {

     Optional<WorkerEntity> findByIdAndActive(Long id, Boolean active);

     @Modifying
     @Transactional
     @Query("UPDATE WorkerEntity w SET w.active = false WHERE w.id = :id")
     int deactivateWorker(@Param("id") Long id);
}
