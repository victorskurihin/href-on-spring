package su.svn.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import java.io.IOException;

public class TestUtil
{
    public static byte[] convertObjectToJsonBytes(Object object) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

    public static String createStringWithLength(int length) {
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < length; index++) {
            builder.append("a");
        }

        return builder.toString();
    }

    public static void databaseClientExecuteSql(DatabaseClient client, String sql)
    {
        client.execute()
            .sql(sql)
            .fetch()
            .rowsUpdated()
            .as(StepVerifier::create)
            .expectNextCount(1)
            .verifyComplete();
    }
}
