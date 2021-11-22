package com.B305.ogym.domain.users.ptStudent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "monthly",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"pt_Student_id", "month"}
        )
    })
public class Monthly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "monthly_id")
    private Long id;   // 대리 키

    @Column(name = "month")
    private int month; // 달

    private int height; // 키
    private int weight; // 몸무게

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pt_student_id")
    private PTStudent ptStudent; // 학생

    @Builder
    public Monthly(int month, int height, int weight, PTStudent ptStudent) {
        this.month = month;
        this.height = height;
        this.weight = weight;
        this.ptStudent = ptStudent;
        if (!ptStudent.getMonthly().contains(this)) {
            ptStudent.getMonthly().add(this);
        }
    }


}
