package com.B305.ogym.domain.users.ptStudent;

import static com.B305.ogym.domain.users.ptStudent.QMonthly.monthly;
import static com.B305.ogym.domain.users.ptStudent.QPTStudent.pTStudent;

import com.B305.ogym.domain.mappingTable.PTStudentPTTeacher;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;

public class PTStudentRepositoryCustomImpl implements PTStudentRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    Map<String, Expression> check = new HashMap<>();

    public PTStudentRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);

    }

    // 자신의 예약 정보를 확인하는 메서드
    @Override
    public List<PTStudentPTTeacher> getReservationTime(String studentEmail) {
        return em.createQuery("select pt"
            + " from PTStudentPTTeacher pt"
            + " join fetch pt.ptTeacher t"
            + " join fetch pt.ptStudent s"
            + " where s.email =: studentEmail"
            + " order by pt.reservationDate", PTStudentPTTeacher.class)
            .setParameter("studentEmail", studentEmail)
            .getResultList();

    }

}
