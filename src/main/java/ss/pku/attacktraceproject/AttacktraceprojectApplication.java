package ss.pku.attacktraceproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("ss.pku.attacktraceproject.mapper")
public class AttacktraceprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(AttacktraceprojectApplication.class, args);
    }

}
