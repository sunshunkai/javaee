package com.ssk.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ssk
 * @date 2021/3/28
 */
public class ParameterMappingTokenHandler implements TokenHandler{

    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    @Override
    public String handleToken(String content) {
        parameterMappings.add(buildParameterMapping(content));
        return "?";
    }

    private ParameterMapping buildParameterMapping(String content) {
        ParameterMapping parameterMapping = new ParameterMapping(content);
        return parameterMapping;
    }

    public void setParameterMappings(List<ParameterMapping> parameterMappings){
        this.parameterMappings = parameterMappings;
    }

    public List<ParameterMapping> getParameterMapping(){
        return parameterMappings;
    }

}
