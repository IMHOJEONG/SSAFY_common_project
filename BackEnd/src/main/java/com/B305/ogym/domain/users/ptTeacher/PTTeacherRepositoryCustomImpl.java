package com.B305.ogym.domain.users.ptTeacher;


import static com.B305.ogym.domain.mappingTable.QPTStudentPTTeacher.pTStudentPTTeacher;
import static com.B305.ogym.domain.users.ptStudent.QPTStudent.pTStudent;
import static com.B305.ogym.domain.users.ptTeacher.QPTTeacher.pTTeacher;

import com.B305.ogym.common.util.RestResponsePage;
import com.B305.ogym.controller.dto.HealthDto.MyStudentsHealthListResponse;
import com.B305.ogym.controller.dto.HealthDto.StudentHealth;
import com.B305.ogym.controller.dto.PTDto.SearchDto;
import com.B305.ogym.domain.mappingTable.PTStudentPTTeacher;
import com.B305.ogym.domain.users.common.Gender;
import com.B305.ogym.domain.users.common.ProfilePicture;
import com.B305.ogym.domain.users.ptStudent.Monthly;
import com.B305.ogym.domain.users.ptStudent.PTStudent;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

public class PTTeacherRepositoryCustomImpl implements PTTeacherRepositoryCustom {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;
    Map<String, Expression> check = new HashMap<>();
    Map<String, Function> check2 = new HashMap<>();

    public PTTeacherRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        queryFactory = new JPAQueryFactory(em);
    }

    // 해당 선생님 관련 학생들의 건강정보 조회 메서드
    @Override
    public MyStudentsHealthListResponse findMyStudentsHealth(String teacherEmail) {
        List<PTStudent> students = queryFactory.selectDistinct(pTStudent)
            .from(pTStudentPTTeacher)
            .join(pTStudentPTTeacher.ptStudent, pTStudent)
            .join(pTStudentPTTeacher.ptTeacher, pTTeacher)
            .where(pTTeacher.email.eq(teacherEmail))
            .fetch();

        List<StudentHealth> result = new ArrayList<>();

        students.forEach(o -> {
            StudentHealth studentHealth = StudentHealth.builder()
                .username(o.getUsername())
                .nickname(o.getNickname())
//                .profileUrl(o.getProfilePicture().getPictureAddr())
                .age(o.getAge())
                .gender(o.getGender())
                .build();
            ProfilePicture profilePicture = o.getProfilePicture();
            if(profilePicture != null){
                studentHealth.setProfileUrl(profilePicture.getPictureAddr());
            }
            List<Monthly> monthly = o.getMonthly();
            monthly.sort((o1, o2) -> {
                return o1.getMonth() - o2.getMonth();
            });
//            monthly.forEach(m -> {
//                studentHealth.addWeight(m.getWeight());
//                studentHealth.addHeight(m.getHeight());
//            });
            for(Monthly m : monthly){
                studentHealth.addWeight(m.getWeight());
                studentHealth.addHeight(m.getHeight());
            }
            for(int i = monthly.size(); i<12 ;i++){
                studentHealth.addWeight(-1);
                studentHealth.addHeight(-1);
            }
            result.add(studentHealth);
        });

        MyStudentsHealthListResponse myStudentsHealthListResponse = new MyStudentsHealthListResponse();
        myStudentsHealthListResponse.setStudentHealthList(result);
        return myStudentsHealthListResponse;
    }

    // 선생님 리스트 조건 검색
    @Override
    public RestResponsePage<PTTeacher> searchAll(SearchDto searchDto, Pageable pageable) {

        QueryResults<PTTeacher> results = queryFactory
            .selectFrom(pTTeacher)
            .where(
                likeName(searchDto.getName()),
                eqGender(searchDto.getGender()),
                loeMaxPrice(searchDto.getMaxPrice()),
                goeMinPrice(searchDto.getMinPrice()),
                loeMaxAge(searchDto.getMaxAge()),
                goeMinAge(searchDto.getMinAge())
            )
            .offset(pageable.getOffset())   // 페이징 처리(offset)
            .limit(pageable.getPageSize())  // 페이징 처리(한 페이지에 출력할 개체 수)
            .orderBy(pTTeacher.starRating.desc())   // 별점 높은 순으로 출력
            .fetchResults();    // 결과 + count
        return new RestResponsePage<>(results.getResults(), pageable, results.getTotal());
    }

    // 이름 포함
    private BooleanExpression likeName(String username) {
        if (username == null) {
            return null;
        }
        return pTTeacher.username.like("%" + username + "%");
    }

    // 성별 일치
    private BooleanExpression eqGender(Gender gender) {
        if (gender == null) {
            return null;
        }
        return pTTeacher.gender.eq(gender);
    }

    // 최소 가격
    private BooleanExpression loeMaxPrice(Integer maxPrice) {
        if (maxPrice == null) {
            return null;
        }
        return pTTeacher.price.loe(maxPrice);
    }

    // 최대 가격
    private BooleanExpression goeMinPrice(Integer minPrice) {
        if (minPrice == null) {
            return null;
        }
        return pTTeacher.price.goe(minPrice);
    }


    // 최대 나이
    private BooleanExpression loeMaxAge(Integer maxAge) {
        if (maxAge == null) {
            return null;
        }
        return pTTeacher.age.loe(maxAge);
    }

    // 최소 나이
    private BooleanExpression goeMinAge(Integer minAge) {
        if (minAge == null) {
            return null;
        }
        return pTTeacher.age.goe(minAge);

    }

    // 선생님의 예약 정보 조회 메서드
    @Override
    public List<LocalDateTime> reservationTime(String teacherEmail) {
        return em.createQuery("select st.reservationDate "
            + " from PTStudentPTTeacher st "
            + " join st.ptTeacher t "
            + " where t.email =: teacherEmail", LocalDateTime.class)
            .setParameter("teacherEmail", teacherEmail)
            .getResultList();
    }

    // 자신의 예약정보 조회
    @Override
    public List<PTStudentPTTeacher> getReservationTime(String teacherEmail) {
        return em.createQuery("select pt"
            + " from PTStudentPTTeacher pt"
            + " join fetch pt.ptTeacher t"
            + " join fetch pt.ptStudent s"
            + " where t.email =: teacherEmail"
            + " order by pt.reservationDate", PTStudentPTTeacher.class)
            .setParameter("teacherEmail", teacherEmail)
            .getResultList();
    }
}


