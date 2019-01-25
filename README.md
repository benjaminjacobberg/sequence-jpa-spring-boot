# sequence-jpa-spring-boot

Annotation for selecting the nextval of a sequence independently from @Id.

## Installation

Add `@EnableAspectJAutoProxy` and `@ComponentScan(basePackages = "com.github.radtin")` to your Spring Boot project's configuration class. 

```java
@ComponentScan(basePackages = "com.github.radtin")
@EnableAspectJAutoProxy
public class ApplicationConfiguration {
    
}
```

In your entity

```java
@Entity
@Table(name = "APPLICATION_ENTITY")
@Data
public class ApplicationEntity {

    ...

    @OracleSequence(sequenceName = "APPLICATION_ENTITY_NUMBER_SEQ")
    @Column(name = "APPLICATION_ENTITY_NUMBER")
    private Long applicationEntityNumber;
    
    ...
}
```

Make sure you have your sequence created in your Oracle database.

```sql
create sequence APPLICATION_ENTITY_NUMBER_SEQ;
```

## TODO

* Add support for more database sequences. 