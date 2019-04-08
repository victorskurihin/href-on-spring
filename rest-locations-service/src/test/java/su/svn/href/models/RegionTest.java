package su.svn.href.models;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.data.r2dbc.function.DatabaseClient;
import reactor.test.StepVerifier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.TEST;
import static su.svn.utils.TestData.TEST_ID;
import static su.svn.utils.TestData.TEST_REGION_NAME;
import static su.svn.utils.TestUtil.databaseClientExecuteSql;

@DisplayName("Class Region")
public class RegionTest
{
    public static Region testRegion = new Region(TEST_ID, TEST_REGION_NAME);

    private Region region;

    public static void createTestTableForRegions(DatabaseClient client)
    {
        databaseClientExecuteSql(client,
            "CREATE TABLE IF NOT EXISTS regions (\n"
                + "  region_id SERIAL PRIMARY KEY\n"
                + ", region_name VARCHAR(25)\n"
                + ")"
        );
        client.insert()
            .into(Region.class)
            .using(testRegion)
            .then()
            .as(StepVerifier::create)
            .verifyComplete();
    }

    @Test
    @DisplayName("is instantiated with new object")
    void isInstantiatedWithNew()
    {
        new Region();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew
    {
        @BeforeEach
        void createNew()
        {
            region = new Region();
        }

        @Test
        @DisplayName("default values in the instance of class")
        void defaults()
        {
            assertThat(region).hasFieldOrPropertyWithValue("id", 0L);
            assertThat(region).hasFieldOrPropertyWithValue("regionName", null);
        }

        @Test
        @DisplayName("setter and getter for firstName")
        void testGetSetRegionName()
        {
            region.setRegionName(TEST);
            assertThat(region).hasFieldOrPropertyWithValue("regionName", TEST);
            assertEquals(TEST, region.getRegionName());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor
    {
        @BeforeEach
        void createNew()
        {
            region = new Region(TEST_ID, TEST_REGION_NAME);
        }

        @Test
        @DisplayName("initialized values in instance of class")
        void defaults()
        {
            assertThat(region).hasFieldOrPropertyWithValue("id", TEST_ID);
            assertThat(region).hasFieldOrPropertyWithValue("regionName", TEST_REGION_NAME);

        }

        @Test
        @DisplayName("equals and hashCode for class")
        void testEquals()
        {
            assertNotEquals(new Region(), region);
            Region expected = new Region(TEST_ID, TEST_REGION_NAME);
            assertEquals(expected.hashCode(), region.hashCode());
            assertEquals(expected, region);
        }

        @Test
        @DisplayName("the length of string from toString is great than zero")
        void testToString()
        {
            assertTrue(region.toString().length() > 0);
        }
    }

    private static final Log log = LogFactory.getLog(IntegrateWithDB.class);

    @Nested
    @DisplayName("do integrate with DB")
    class IntegrateWithDB
    {
        @Test
        @DisplayName("create table then inserts test record and then drop table")
        void createTableInsertsRecordAndDropTable()
        {
            ConnectionFactory connectionFactory = new H2ConnectionFactory(
                H2ConnectionConfiguration
                    .builder()
                    .url("mem:test;DB_CLOSE_DELAY=10")
                    .build()
            );
            DatabaseClient client = DatabaseClient.create(connectionFactory);
            createTestTableForRegions(client);
            client.select()
                .from(Region.class)
                .fetch()
                .first()
                .doOnNext(it -> log.info(it))
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
            databaseClientExecuteSql(client, "DROP TABLE IF EXISTS regions CASCADE");
        }
    }
}