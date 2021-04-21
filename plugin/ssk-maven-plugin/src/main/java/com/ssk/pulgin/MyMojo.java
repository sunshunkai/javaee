package com.ssk.pulgin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/4/21
 *
 *
 * maven插件的元数据
 *   @goal CustomMavenMojo：表示该插件的服务目标
 *   @phase compile：表示该插件的生效周期阶段
 *   @requiresProject false：表示是否依托于一个项目才能运行该插件
 *   @parameter expression="${name}"：表示插件参数，使用插件的时候会用得到
 *   @required:代表该参数不能省略
 *
 */
public class MyMojo extends AbstractMojo {

    /**
     * 参数可以通过命令赋值
     * @parameter expression="${echo.message}" default-value="Hello World..."
     */
    private String message;

    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("hello world");

        getLog().info("hello mymojo : "+message);
    }
}
