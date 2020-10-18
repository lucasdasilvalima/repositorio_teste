package app.dao;

import java.util.Arrays;
import java.util.stream.Collectors;

public interface ProcedureStatement {

    default String Varchar(String str) {
        return "\'" + str + "\'";
    }

    default String prepareProcedure(String procedureName, String... args) {
        return "CALL " + procedureName + Arrays
                .stream(args)
                .collect(Collectors
                        .joining(",","(",")"));
    }

}
