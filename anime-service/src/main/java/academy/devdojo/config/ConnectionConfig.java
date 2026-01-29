package academy.devdojo.config;

import external.dependency.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ConnectionConfig {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Primary
    public Connection connectionMySQL(){
        return new Connection(url,username,password);
    }

    @Bean(name = "connectionMongoDB")
    public Connection connectionMongo(){
        return new Connection("localhost", "devdojoMongo", "goku");
    }
}
