package ru.melvuze.telegramloggingbot.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.Instant;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    private Long id;

    @Builder.Default
    private Instant createdAt = Instant.now();

}
