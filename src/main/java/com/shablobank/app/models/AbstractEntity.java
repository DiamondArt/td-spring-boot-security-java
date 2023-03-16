package com.shablobank.app.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
// ecouter automatiquement cette classe et mettre à jour dans la base de donnée les champs ci-dessous
public class AbstractEntity implements Serializable {

    /**@CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    @JsonIgnore
    private Date updatedAt;
}
