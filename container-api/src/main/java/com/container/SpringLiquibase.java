package com.container;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import liquibase.Liquibase;
import liquibase.change.Change;
import liquibase.changelog.ChangeSet;
import liquibase.changelog.DatabaseChangeLog;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.parser.ChangeLogParserFactory;
import liquibase.sql.Sql;
import liquibase.sqlgenerator.SqlGeneratorFactory;
import liquibase.statement.SqlStatement;

public class SpringLiquibase extends liquibase.integration.spring.SpringLiquibase {

    protected static final Logger logger = Logger.getLogger(SpringLiquibase.class.getName());

    protected Boolean showSql = Boolean.TRUE;

    @Override
    public void afterPropertiesSet() throws LiquibaseException {
        if (showSql)
            log();
        super.afterPropertiesSet();

    }

    protected void log() {
        try {
            Connection connection = getDataSource().getConnection();
            Liquibase liquibase = createLiquibase(connection);
            DatabaseChangeLog changeLog = ChangeLogParserFactory.getInstance().getParser(getChangeLog(), liquibase.getResourceAccessor()).parse(getChangeLog(), liquibase.getChangeLogParameters(), liquibase.getResourceAccessor());
            List<ChangeSet> changeSets = changeLog.getChangeSets();
            final Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(getDataSource().getConnection()));
            for (ChangeSet changeSet : changeSets) {
                List<Change> changes = changeSet.getChanges();
                for (Change change : changes) {
                    SqlStatement[] generateStatements = change.generateStatements(database);
                    for (SqlStatement sqlStatement : generateStatements) {
                        SqlGeneratorFactory instance = SqlGeneratorFactory.getInstance();
                        Sql[] generateSql = instance.generateSql(sqlStatement, database);
                        for (Sql sql : generateSql) {
                            logger.info("[Migration] " + sql.toSql());
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to log migration changelog.", e);
        }
    }

    public Boolean getShowSql() {
        return showSql;
    }

    public void setShowSql(Boolean showSql) {
        this.showSql = showSql;
    }

}