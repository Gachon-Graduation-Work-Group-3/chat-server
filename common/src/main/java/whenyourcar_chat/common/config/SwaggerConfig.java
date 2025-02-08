package whenyourcar_chat.common.config;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {

        Info info = new Info()
                .title("When Your Car Chat API")
                .description("When Your Car Chat Server 명세서")
                .version("1.0.0");

        String sessionSchemeName = "X-User-Email";

        SecurityRequirement securityRequirement = new SecurityRequirement().addList(sessionSchemeName);

        Components components = new Components()
                .addSecuritySchemes(sessionSchemeName, new SecurityScheme()
                        .name("X-User-Email")
                        .type(SecurityScheme.Type.APIKEY)
                        .in(SecurityScheme.In.HEADER));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
