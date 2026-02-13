package com.Hibernate_spring.Hibernate.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class})
public class HibernateProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(HibernateProjectApplication.class, args);
	}

}
