package com.coral.test.spring.graphql_jdk8.config.graphql.directive;

import com.coral.base.common.StringUtils;
import com.coral.base.common.json.JsonUtil;
import com.coral.test.spring.graphql_jdk8.config.spring.SpringContext;
import com.coral.test.spring.graphql_jdk8.service.UserService;
import com.coral.test.spring.graphql_jdk8.vo.UserInfoVO;
import graphql.GraphQLContext;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * 需要权限
 *
 * @author huss
 * @date 2024/1/3 15:02
 * @packageName com.coral.test.spring.graphql.config.graphql
 * @className AuthorisationDirectiveWiring
 */
@Slf4j
public class AuthorisationDirectiveWiring implements DefSchemaDirectiveWiring {
    public final static String AUTH = "auth";

    private final static String ROLE = "role";

    public final static SchemaDirectiveWiring INSTANCE = new AuthorisationDirectiveWiring();

    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {
        GraphQLFieldDefinition field = environment.getElement();
        String role = getArgByFieldDefinition(field, AUTH, ROLE);
        DataFetcher<?> dataFetcher = environment.getFieldDataFetcher();
        DataFetcher<?> authDataFetcher = dataFetchingEnvironment -> {
            String currRole = role;
            Optional<Value> stv = getArgByField(dataFetchingEnvironment, AUTH, ROLE);
            if (stv.isPresent() && stv.get() instanceof StringValue) {
                currRole = ((StringValue) stv.get()).getValue();
            }
            log.info(">>>>>[@{}] role:{}", AUTH, currRole);
            GraphQLContext graphQLContext = dataFetchingEnvironment.getGraphQlContext();
            String account = graphQLContext.getOrDefault("account", "");
            UserService userService = SpringContext.getBean(UserService.class);
            Optional<UserInfoVO> userInfoOpt = userService.findUserInfo(account);

            if (userInfoOpt.isPresent()) {
                UserInfoVO userInfoVO = userInfoOpt.get();
                log.info(">>>>>userInfo：{}", JsonUtil.toJson(userInfoVO));
                if (StringUtils.isBlank(currRole) || userInfoVO.getRole().equals(currRole)) {
                    return dataFetcher.get(dataFetchingEnvironment);
                }
            }
            return null;
        };
        environment.setFieldDataFetcher(authDataFetcher);
        return field;
    }

}
