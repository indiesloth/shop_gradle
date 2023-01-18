package com.shop.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Data
public abstract class BaseTimeEntity {

  @CreatedDate
  @Column(updatable = false)
  @Comment("등록 시간")
  private LocalDateTime regTime;
  @LastModifiedDate
  @Comment("수정 시간")
  private LocalDateTime updateTime;
}
