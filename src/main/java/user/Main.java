package user;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {

        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDao dao = handle.attach(UserDao.class);
            User user = User.builder()
                    .name("James Bond")
                    .username("007")
                    .password("secretagent")
                    .email("agent007@mi6.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            User user2 = User.builder()
                    .name("Homer Simpson")
                    .username("SpiderPig")
                    .password("doooh")
                    .email("homers@springfield.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1966-08-08"))
                    .enabled(true)
                    .build();

            User user3 = User.builder()
                    .name("Arya Stark")
                    .username("WolfGirl")
                    .password("needle")
                    .email("littlestark@westeros.com")
                    .gender(User.Gender.FEMALE)
                    .dob(LocalDate.parse("1997-04-15"))
                    .enabled(true)
                    .build();

            dao.createTable();
            dao.insert(user);
            dao.insert(user2);
            dao.insert(user3);
            dao.list().stream().forEach(System.out::println);
            System.out.println(dao.findbyId(1));
            System.out.println(dao.findbyUsername("SpiderPig"));
            dao.delete(user2);
            dao.list().stream().forEach(System.out::println);

        }
    }
}
