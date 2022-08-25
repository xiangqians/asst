package org.asst.configure;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ClassUtils;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author xiangqian
 * @date 22:06 2022/08/15
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableKnife4j
@Profile({"dev", "test"}) // 仅在 dev、test环境下开启swagger文档
public class DocumentConfiguration {

    // 文档标题
    private static final String TITLE = "Asst API";
    // 文档描述
    private static final String DESCRIPTION = "asst";

    // 组名称
    private static final String DEFAULT_GROUP_NAME = "default";
    // 基础包
    public static final String[] BASE_PACKAGES = {"org.asst.controller"};

    @Bean
    public Docket defaultDocket() {
        return new BasePackageDocketImpl(DEFAULT_GROUP_NAME, BASE_PACKAGES);
    }

    /**
     * 按antPattern进行分组
     */
    public static class AntPatternDocketImpl extends AbstractDocket {

        public AntPatternDocketImpl(String groupName, String... antPatterns) {
            /**
             * {@link PathSelectors#ant(String)}
             */
            Predicate<String> pathsSelector = input -> {
                AntPathMatcher matcher = new AntPathMatcher();
                for (String antPattern : antPatterns) {
                    if (matcher.match(antPattern, input)) {
                        return true;
                    }
                }
                return false;
            };
            setGroupName(groupName);
            setApisSelector(RequestHandlerSelectors.any());
            setPathsSelector(pathsSelector);
        }

    }

    /**
     * 根据basePackage进行分组
     */
    public static class BasePackageDocketImpl extends AbstractDocket {

        public BasePackageDocketImpl(String groupName, String... basePackages) {
            /**
             * see {@link RequestHandlerSelectors#basePackage(String)}
             */
            Function<Class<?>, Boolean> handlerPackage = input ->
                    Arrays.stream(basePackages)
                            .filter(basePackage -> ClassUtils.getPackageName(input).startsWith(basePackage))
                            .findFirst()
                            .map(basePackage -> true)
                            .orElse(false);
            Predicate<RequestHandler> apisSelector = requestHandler ->
                    Optional.ofNullable(requestHandler.declaringClass())
                            .map(handlerPackage)
                            .orElse(true);
            setGroupName(groupName);
            setApisSelector(apisSelector);
            setPathsSelector(PathSelectors.any());
        }

    }

    public static abstract class AbstractDocket extends Docket {

        @Value("${spring.application.name}")
        private String name;

        @Value("${server.port}")
        private int port;

        @Value("${spring.application.version}")
        private String version;

        @Setter
        private String groupName;

        @Setter
        private Predicate<RequestHandler> apisSelector;

        @Setter
        private Predicate<String> pathsSelector;

        public AbstractDocket() {
            this(DocumentationType.SWAGGER_2);
        }

        public AbstractDocket(DocumentationType documentationType) {
            super(documentationType);
        }

        @PostConstruct
        public void init() {
            useDefaultResponseMessages(false);

            // ApiInfo
            ApiInfo apiInfo = new ApiInfoBuilder()
                    // 设置文档标题
                    .title(TITLE)
                    // 文档描述
                    .description(DESCRIPTION)
                    // 服务条款URL
                    .termsOfServiceUrl(String.format("http://localhost:%d/", port))
                    // 版本号
                    .version(String.format("v%s", version))
                    // 联系
                    .contact(new Contact("xiangqian", "https://github.com/xiangqians/asst", "xiangqian@xiangqians.com"))
                    .build();
            apiInfo(apiInfo);

            enable(true);
            select()
                    .apis(apisSelector)
                    .paths(pathsSelector)
                    .build();

            // 组名称
            groupName(groupName);
        }

    }

}
