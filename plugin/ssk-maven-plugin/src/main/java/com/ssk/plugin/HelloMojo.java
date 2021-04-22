package com.ssk.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/4/21
 *
 * mvn ssk:hello
 */
@Mojo(name = "hello")
public class HelloMojo extends AbstractMojo {

    public void execute() throws MojoExecutionException, MojoFailureException {
        System.out.println("hello world,my plugin run...");
    }

}
