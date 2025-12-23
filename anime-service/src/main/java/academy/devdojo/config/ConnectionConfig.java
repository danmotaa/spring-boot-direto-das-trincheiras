package academy.devdojo.config;

import external.dependency.Connection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ConnectionConfig {

    @Bean
    @Primary
    public Connection connectionMySQL(){
        return new Connection("localhost", "devdojoMySQL", "goku");
    }

    @Bean(name = "connectionMongoDB")
    public Connection connectionMongo(){
        return new Connection("localhost", "devdojoMongo", "goku");
    }
}
