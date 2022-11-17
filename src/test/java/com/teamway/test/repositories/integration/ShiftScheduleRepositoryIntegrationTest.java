package com.teamway.test.repositories.integration;

import static org.hibernate.validator.internal.util.Contracts.assertNotEmpty;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.teamway.test.controllers.dto.SearchShiftScheduleDto;
import com.teamway.test.repositories.ShiftScheduleRepository;
import com.teamway.test.repositories.WorkerRepository;
import com.teamway.test.repositories.entities.Shift;
import com.teamway.test.repositories.entities.ShiftScheduleEntity;
import com.teamway.test.repositories.entities.WorkerEntity;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ShiftScheduleRepositoryIntegrationTest {

    @Autowired
    private ShiftScheduleRepository shiftScheduleRepository;

    @Autowired
    private WorkerRepository workerRepository;

    @Test
    public void testSearchShiftScheduleByWorkerId() {
        var worker1 = this.createDummyWorker();
        var worker2 = this.createDummyWorker();

        var shiftScheduleEntityWorker1 = ShiftScheduleEntity.builder()
            .workerId(worker1.getId())
            .shift(Shift.FIRST_SHIFT)
            .date(LocalDate.now().plusDays(1))
            .build();

        var shiftScheduleEntityWorker2 = ShiftScheduleEntity.builder()
            .workerId(worker2.getId())
            .shift(Shift.FIRST_SHIFT)
            .date(LocalDate.now().plusDays(1))
            .build();

        this.shiftScheduleRepository.save(shiftScheduleEntityWorker1);
        this.shiftScheduleRepository.save(shiftScheduleEntityWorker2);


        var searchParams = SearchShiftScheduleDto.builder()
            .workerId(worker1.getId())
            .build();

        var result = this.shiftScheduleRepository.searchShiftSchedule(searchParams);

        assertNotEmpty(result, "Empty results");
        assertEquals(1, result.size());
        assertEquals(worker1.getId(), result.get(0).workerId());
    }


    @Test
    public void testSearchShiftScheduleByDate() {
        var worker1 = this.createDummyWorker();
        var worker2 = this.createDummyWorker();

        var shiftScheduleEntityWorker1 = ShiftScheduleEntity.builder()
            .workerId(worker1.getId())
            .shift(Shift.FIRST_SHIFT)
            .date(LocalDate.now())
            .build();

        var shiftScheduleEntityWorker2 = ShiftScheduleEntity.builder()
            .workerId(worker2.getId())
            .shift(Shift.FIRST_SHIFT)
            .date(LocalDate.now().plusDays(1))
            .build();

        var shiftScheduleEntityWorker2Day2 = ShiftScheduleEntity.builder()
            .workerId(worker2.getId())
            .shift(Shift.FIRST_SHIFT)
            .date(LocalDate.now().plusDays(2))
            .build();

        this.shiftScheduleRepository.save(shiftScheduleEntityWorker1);
        this.shiftScheduleRepository.save(shiftScheduleEntityWorker2);
        this.shiftScheduleRepository.save(shiftScheduleEntityWorker2Day2);


        var searchParams = SearchShiftScheduleDto.builder()
            .startDate(LocalDate.now())
            .endDate(LocalDate.now().plusDays(1))
            .build();

        var result = this.shiftScheduleRepository.searchShiftSchedule(searchParams);

        assertNotEmpty(result, "Empty results");
        assertEquals(2, result.size());
        assertEquals(worker1.getId(), result.get(0).workerId());
        assertEquals(LocalDate.now(), result.get(0).date());
        assertEquals(worker2.getId(), result.get(1).workerId());
        assertEquals(LocalDate.now().plusDays(1), result.get(1).date());
    }



    private WorkerEntity createDummyWorker() {
        var workerEntity = WorkerEntity.builder()
            .firstName("firstName")
            .lastName("lastName")
            .active(true)
            .build();

        return workerRepository.save(workerEntity);
    }

}
