package fr.shoppo.ms_commerce;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

class MSCommerceTest {

    @Test
    void should_be_annotated(){
        var app = MSCommerce.class;

        var annotation = app.getAnnotation(SpringBootApplication.class);

        assertNotNull(annotation);
    }

    @Test
    void should_have_main_method() throws NoSuchMethodException {
        var app = MSCommerce.class;
        var main = app.getMethod("main",String[].class);
        assertNotNull(main);
    }

    @Test
    void should_launch_springapplication(){
        try (MockedStatic<SpringApplication> mocked = mockStatic(SpringApplication.class)) {

            mocked.when(() -> SpringApplication.run(MSCommerce.class,
                    "foo", "bar"))
                    .thenReturn(mock(ConfigurableApplicationContext.class));

            MSCommerce.main(new String[] { "foo", "bar" });

            mocked.verify(() -> SpringApplication.run(MSCommerce.class,
                    "foo", "bar"));

        }
    }
}