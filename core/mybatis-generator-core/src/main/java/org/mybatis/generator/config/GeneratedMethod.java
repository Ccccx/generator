/**
 *    Copyright 2006-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.config;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chengjz
 * @version 1.0
 * @date 2019-06-20 11:10
 */
public class GeneratedMethod {

    private List<GeneratedMethodBean> generatedMethodBeans;

    private boolean enableSelectByExample;

    private boolean enableUpdateByExample;

    private boolean enablePostPositionQuery;

    public GeneratedMethod() {
    }

    public GeneratedMethod(Boolean enableSelectByExample, Boolean enableUpdateByExample, Boolean enablePostPositionQuery) {
        this.enableSelectByExample = enableSelectByExample;
        this.enableUpdateByExample = enableUpdateByExample;
        this.enablePostPositionQuery = enablePostPositionQuery;
    }


    public void setGeneratedMethodList(GeneratedMethodBean generatedMethodBean) {
        if (this.generatedMethodBeans == null || this.generatedMethodBeans.isEmpty()) {
            this.generatedMethodBeans = new ArrayList<GeneratedMethodBean>();
        }

        this.generatedMethodBeans.add(generatedMethodBean);
    }

    public boolean isEnableSelectByExample() {
        return enableSelectByExample;
    }

    public void setEnableSelectByExample(boolean enableSelectByExample) {
        this.enableSelectByExample = enableSelectByExample;
    }

    public boolean isEnableUpdateByExample() {
        return enableUpdateByExample;
    }

    public void setEnableUpdateByExample(boolean enableUpdateByExample) {
        this.enableUpdateByExample = enableUpdateByExample;
    }


    public boolean isEnablePostPositionQuery() {
        return enablePostPositionQuery;
    }

    public void setEnablePostPositionQuery(boolean enablePostPositionQuery) {
        this.enablePostPositionQuery = enablePostPositionQuery;
    }

    public List<GeneratedMethodBean> getGeneratedMethodBeans() {
        return generatedMethodBeans;
    }

    public void setGeneratedMethodBeans(List<GeneratedMethodBean> generatedMethodBeans) {
        this.generatedMethodBeans = generatedMethodBeans;
    }

    @Override
    public String toString() {
        return "GeneratedMethod{" +
                "generatedMethodBeans=" + generatedMethodBeans +
                ", enableSelectByExample=" + enableSelectByExample +
                ", enableUpdateByExample=" + enableUpdateByExample +
                ", enablePostPositionQuery=" + enablePostPositionQuery +
                '}';
    }

    public static class GeneratedMethodBean {

        private String methodName;

        private List<String> columns;

        private boolean enableSelectByExample;

        private boolean enableUpdateByExample;

        private boolean enablePostPositionQuery;

        public GeneratedMethodBean() {
        }

        public GeneratedMethodBean(String methodName, List<String> columns, Boolean enableSelectByExample, Boolean enableUpdateByExample, Boolean enablePostPositionQuery) {
            this.methodName = methodName;
            this.columns = columns;
            this.enableSelectByExample = enableSelectByExample == null ? true : enableSelectByExample;
            this.enableUpdateByExample = enableUpdateByExample == null ? true : enableUpdateByExample;
            this.enablePostPositionQuery = enablePostPositionQuery == null ? true : enablePostPositionQuery;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public List<String> getColumns() {
            return columns;
        }

        public void setColumns(List<String> columns) {
            this.columns = columns;
        }

        public boolean isEnableSelectByExample() {
            return enableSelectByExample;
        }

        public void setEnableSelectByExample(boolean enableSelectByExample) {
            this.enableSelectByExample = enableSelectByExample;
        }

        public boolean isEnableUpdateByExample() {
            return enableUpdateByExample;
        }

        public void setEnableUpdateByExample(boolean enableUpdateByExample) {
            this.enableUpdateByExample = enableUpdateByExample;
        }

        public boolean isEnablePostPositionQuery() {
            return enablePostPositionQuery;
        }

        public void setEnablePostPositionQuery(boolean enablePostPositionQuery) {
            this.enablePostPositionQuery = enablePostPositionQuery;
        }

        @Override
        public String toString() {
            return "GeneratedMethodBean{" +
                    "methodName='" + methodName + '\'' +
                    ", columns=" + columns +
                    ", enableSelectByExample=" + enableSelectByExample +
                    ", enableUpdateByExample=" + enableUpdateByExample +
                    ", enablePostPositionQuery=" + enablePostPositionQuery +
                    '}';
        }
    }

}
