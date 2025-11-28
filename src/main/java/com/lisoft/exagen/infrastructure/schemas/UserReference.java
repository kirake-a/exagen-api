package com.lisoft.exagen.infrastructure.schemas;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserReference {
    @Column(name = "user_id", nullable = false)
    private String userId;
}
