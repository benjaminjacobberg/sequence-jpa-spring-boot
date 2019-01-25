package com.github.radtin.sequencejpaspringboot;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class SequenceAspect {
    @NonNull private JdbcTemplate jdbcTemplate;

    @Before("execution(* org.springframework.data.repository.Repository+.saveAndFlush(*)) || org.springframework.data.repository.Repository+.save(*))")
    public void sequenceId(JoinPoint joinPoint) throws Throwable {
        Object entity = joinPoint.getArgs()[0];
        for (Field field : entity.getClass().getDeclaredFields()) {
            OracleSequence oracleSequence = field.getAnnotation(OracleSequence.class);
            if (oracleSequence != null) {
                String sequenceName = oracleSequence.sequenceName();
                String sql = String.format("select %s.NEXTVAL from dual", sequenceName);
                Long sequence = jdbcTemplate.queryForObject(sql, new Object[]{}, Long.class);
                field.setAccessible(true);
                field.set(entity, sequence);
            }
        }
    }

}
