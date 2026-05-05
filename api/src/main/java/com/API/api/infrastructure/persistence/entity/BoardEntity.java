package com.API.api.infrastructure.persistence.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "boards", uniqueConstraints = {
        @UniqueConstraint(name = "uc_board_slug_workspace", columnNames = { "slug", "workspace_id" })
})
@Getter
@Setter
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "workspace_id", nullable = false)
    private WorkspaceEntity workspace;

    private String slug;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
