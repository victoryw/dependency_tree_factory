package com.victoryw.dependence.tree.story.factory.neo4j;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;

public class DriverProvider {
    private static DriverProvider driverProvider;
    private final String uri;
    private final String userName;
    private final String passWord;
    private Driver driver;

    private DriverProvider(String uri, String userName, String passWord) {

        this.uri = uri;
        this.userName = userName;
        this.passWord = passWord;
    }

    static Driver createDriver() {
        if (driverProvider == null) {
            driverProvider = new DriverProvider("bolt://localhost:7687",
                    "neo4j",
                    "neo4j");

        }

        return driverProvider.
                get();
    }

    private Driver get() {
        if (driver == null) {
            driver = GraphDatabase.driver(this.uri,
                    AuthTokens.basic(this.userName, this.passWord));
        }
        return driver;
    }
}
