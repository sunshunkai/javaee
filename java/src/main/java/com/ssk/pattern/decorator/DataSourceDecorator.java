package com.ssk.pattern.decorator;

/**
 * @author ssk
 * @date 2021/1/17
 * 抽象基础装饰
 */
public class DataSourceDecorator implements DataSource{

    private DataSource wrappee;

    DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        return wrappee.readData();
    }

}
