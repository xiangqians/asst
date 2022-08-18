package org.calendar.db.generator;

/**
 * @author xiangqian
 * @date 21:48 2022/08/15
 */
public class MybatisGeneratorTest {

    public static void main(String[] args) {
        MybatisGenerator mybatisGenerator = new MybatisGenerator.Builder()
                .author("xiangqian")

                // db config
                .dbConfig()
                .driverClassName("org.sqlite.JDBC")
                .url("jdbc:sqlite:E:\\workspace\\idea-my\\calendar\\src\\main\\resources\\db\\calendar.db3")
                .username("")
                .password("")
                .and()

                // output config
                .outputConfig()
                .moduleName(null)
                .tables("note", "event")
                .outputDir(null)
                .and()

                // build
                .build();

        mybatisGenerator.execute();
    }

}
