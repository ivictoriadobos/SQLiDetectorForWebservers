package core.implementations;

import core.constants.ClusterScoreThreshold;
import core.interfaces.ILogPoint;
import core.interfaces.ICommandInQueryParamsOrHeadersFilter;
import core.interfaces.IParameter;

public class SQLCommandInQueryParamsOrHeadersFilterer implements ICommandInQueryParamsOrHeadersFilter {


    @Override
    public boolean doesContainCommandInQueryParamsOrHeaders(ILogPoint aLogPoint) {

        double score = 0.0;
        var headers = aLogPoint.getLog().getHeaders();
        var queryParameters = aLogPoint.getLog().getQueryParameters();

        for (IParameter header : headers) {
            score += header.getRawAttackScore();
        }

        for ( IParameter queryParam : queryParameters)
        {
            score += queryParam.getRawAttackScore();
        }

        if ( score >= ClusterScoreThreshold.POSSIBLE_ATTACK_LOWER_THRESHOLD.getValue())
            return true;

        else return false;
    }
}
