package rdstestapp

import com.mysql.cj.jdbc.MysqlDataSource
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import javax.sql.DataSource

@SpringBootApplication
class App {

    @Bean
    fun dataSource(): DataSource {
        val mysqlDataSource = MysqlDataSource().apply {
            setURL("${env("JDBC_URL")}?useSSL=true&requireSSL=true&verifyServerCertificate=true")
            user = env("JDBC_USER")
            setPassword(env("JDBC_PASSWORD"))
        }

        return HikariDataSource().apply {
            dataSource = mysqlDataSource
            connectionTestQuery = "select case when @@innodb_read_only = 0 then 1 else (select table_name from information_schema.tables) end as `1`"
            maximumPoolSize = 2
        }
    }

    private fun env(name: String) = System.getenv(name)
        ?: throw IllegalStateException("Need env variable $name to be defined")
}

@RestController
class HomeController(val dataSource: DataSource) {

    @GetMapping("/")
    fun get() = dataSource.connection.use { connection ->
        val stmt = connection.prepareStatement("SELECT COUNT(1) FROM terms_and_conditions_acceptance")
        val rs = stmt.executeQuery()

        if (rs.next()) rs.getLong(1)
        else -1
    }
}


fun main(args: Array<String>) {
    SpringApplication.run(App::class.java, *args)
}
