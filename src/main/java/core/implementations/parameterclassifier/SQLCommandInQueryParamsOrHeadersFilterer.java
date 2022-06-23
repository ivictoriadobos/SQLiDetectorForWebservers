package core.implementations.parameterclassifier;

import core.interfaces.ILogPoint;
import core.interfaces.ICommandInQueryParamsOrHeadersFilter;

public class SQLCommandInQueryParamsOrHeadersFilterer implements ICommandInQueryParamsOrHeadersFilter {


    @Override
    public boolean doesContainCommandInQueryParamsOrHeaders(ILogPoint logPoint) {
        return false;
    }
}
