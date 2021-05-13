package fr.shoppo.msadmin;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class MsAdminApplicationTest {

  @Test
    void should_be_annotated(){
        var app = MsAdminApplication.class;

        var annotation = app.getAnnotation(SpringBootApplication.class);

        assertNotNull(annotation);
    }

    @Test
    void should_have_main_method() throws NoSuchMethodException{
        var app = MsAdminApplication.class;
        var main = app.getMethod("main",String[].class);

        assertNotNull(main);
    }

    @Test
    void should_launch_springapplication(){
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

            mocked.when(() -> SpringApplication.run(MsAdminApplication.class,
                    "foo", "bar"))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            MsAdminApplication.main(new String[] { "foo", "bar" });

            mocked.verify(() -> SpringApplication.run(MsAdminApplication.class,
                    "foo", "bar"));

        }
    }

}