package com.B305.ogym.domain.users.ptTeacher;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "certificate")
public class Certificate {

    @Builder
    public Certificate(String name, String publisher, LocalDate date) {
        this.name = name;
        this.publisher = publisher;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Long id; // 대리 키

    @Column(name = "cert_name")
    private String name; // 자격증 명칭

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pt_teacher_id")
    private PTTeacher ptTeacher; // 선생님

    private String publisher; // 발급 기관

    private LocalDate date; // 획득일

    public void setPtTeacher(PTTeacher ptTeacher) {
        this.ptTeacher = ptTeacher;
        if (!ptTeacher.getCertificates().contains(this)) {
            this.ptTeacher.getCertificates().add(this);
        }
    }

}
