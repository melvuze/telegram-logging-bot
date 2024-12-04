package ru.melvuze.telegramloggingbot.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.melvuze.telegramloggingbot.domain.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> { }
