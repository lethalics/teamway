package com.teamway.test.repositories;

import com.teamway.test.controllers.dto.SearchShiftScheduleDto;
import com.teamway.test.repositories.models.ShiftScheduleData;
import java.util.List;

public interface ShiftScheduleCustomRepository {

    List<ShiftScheduleData> searchShiftSchedule(SearchShiftScheduleDto searchShiftScheduleDto);
}
