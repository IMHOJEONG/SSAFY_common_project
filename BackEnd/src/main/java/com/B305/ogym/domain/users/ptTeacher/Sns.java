package com.B305.ogym.domain.users.ptTeacher;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sns {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sns_id")
    private Long id; // 대리키

    private String url; // URL

    private String platform; // 어떤 플랫폼

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pt_teacher_id")
    private PTTeacher ptTeacher; // 선생님

    public void setPtTeacher(PTTeacher ptTeacher) {
        this.ptTeacher = ptTeacher;
    }

    @Builder
    public Sns(String url, String platform, PTTeacher ptTeacher) {
        this.url = url;
        this.platform = platform;
        this.ptTeacher = ptTeacher;
    }
}

