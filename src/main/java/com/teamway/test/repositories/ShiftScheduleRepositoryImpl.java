package com.teamway.test.repositories;

import com.teamway.test.controllers.dto.SearchShiftScheduleDto;
import com.teamway.test.repositories.models.ShiftScheduleData;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ShiftScheduleRepositoryImpl implements ShiftScheduleCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<ShiftScheduleData> searchShiftSchedule(SearchShiftScheduleDto searchShiftScheduleDto) {
        var queryBuilder = new StringBuilder("SELECT new com.teamway.test.repositories.models.ShiftScheduleData(sc.id, sc.workerId, w.firstName, w.lastName, sc.shift, sc.date) from ShiftScheduleEntity AS sc LEFT JOIN WorkerEntity AS w ON sc.workerId = w.id ");

        var andConditions = buildQueryConditions(searchShiftScheduleDto);

        //append AND conditions
        if (!andConditions.isEmpty()) {
            var andConditionsString = andConditions.stream()
                .map(QueryFilterCondition::condition)
                .collect(Collectors.joining(" AND "));

            queryBuilder.append(" WHERE ");
            queryBuilder.append(andConditionsString);
        }

        //default sorting
        queryBuilder.append(" ORDER BY sc.date ASC");

        var query = this.entityManager.createQuery(queryBuilder.toString(), ShiftScheduleData.class);

        //populate conditions params
        for (QueryFilterCondition condition : andConditions) {
            query.setParameter(condition.field, condition.value);
        }

        return query.getResultList();
    }

    private Set<QueryFilterCondition> buildQueryConditions(SearchShiftScheduleDto searchShiftScheduleDto) {
        Set<QueryFilterCondition> andConditions = new HashSet<>();

        if (searchShiftScheduleDto.workerId() != null) {
            andConditions.add(new QueryFilterCondition("workerId", "sc.workerId = :workerId", searchShiftScheduleDto.workerId()));
        }

        if (searchShiftScheduleDto.startDate() != null) {
            andConditions.add(new QueryFilterCondition("startDate", "sc.date >= :startDate", searchShiftScheduleDto.startDate()));
        }

        if (searchShiftScheduleDto.endDate() != null) {
            andConditions.add(new QueryFilterCondition("endDate", "sc.date <= :endDate", searchShiftScheduleDto.endDate()));
        }

        return andConditions;
    }

    record QueryFilterCondition(String field, String condition, Object value) {
    }
}
