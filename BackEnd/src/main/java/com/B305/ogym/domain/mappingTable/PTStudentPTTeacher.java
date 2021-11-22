package com.B305.ogym.domain.mappingTable;

import com.B305.ogym.domain.users.ptStudent.PTStudent;
import com.B305.ogym.domain.users.ptTeacher.PTTeacher;
import java.time.LocalDateTime;
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
@Table(
    name = "pt_student_pt_teacher",
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"pt_teacher_id", "reservationDate"}
        )
    }
)
public class PTStudentPTTeacher {

    @Builder
    public PTStudentPTTeacher(PTStudent ptStudent, PTTeacher ptTeacher,
        LocalDateTime reservationDate, String description) {
        this.ptStudent = ptStudent;
        this.ptTeacher = ptTeacher;
        this.reservationDate = reservationDate;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pt_student_pt_teacher_id")
    private Long id; // 대리 키

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pt_student_id")
    private PTStudent ptStudent; // 학생

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pt_teacher_id")
    private PTTeacher ptTeacher; // 센세

    private LocalDateTime reservationDate; // 예약 월 일

    private String description;

}
